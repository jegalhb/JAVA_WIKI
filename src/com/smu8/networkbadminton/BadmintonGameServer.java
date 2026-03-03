package com.smu8.networkbadminton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BadmintonGameServer {

    private static final int PORT = 7777;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;
    private static final int PADDLE_WIDTH = 14;
    private static final int PADDLE_HEIGHT = 90;
    private static final int BALL_SIZE = 16;

    private final Object stateLock = new Object();
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    private float leftY = (HEIGHT - PADDLE_HEIGHT) / 2f;
    private float rightY = (HEIGHT - PADDLE_HEIGHT) / 2f;
    private int leftInputDir = 0;
    private int rightInputDir = 0;

    private float ballX = WIDTH / 2f;
    private float ballY = HEIGHT / 2f;
    private float ballVx = -5f;
    private float ballVy = 3f;

    private int leftScore = 0;
    private int rightScore = 0;

    public static void main(String[] args) {
        new BadmintonGameServer().start();
    }

    public void start() {
        Thread gameLoop = new Thread(this::runGameLoop, "game-loop");
        gameLoop.setDaemon(true);
        gameLoop.start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Badminton server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Server stopped", e);
        }
    }

    private void runGameLoop() {
        final long frameMs = 16L;
        while (true) {
            long before = System.currentTimeMillis();
            synchronized (stateLock) {
                updatePaddles();
                updateBall();
                broadcastState();
            }
            long elapsed = System.currentTimeMillis() - before;
            long sleep = frameMs - elapsed;
            if (sleep < 2L) {
                sleep = 2L;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void updatePaddles() {
        float speed = 7f;
        leftY += leftInputDir * speed;
        rightY += rightInputDir * speed;

        leftY = clamp(leftY, 0, HEIGHT - PADDLE_HEIGHT);
        rightY = clamp(rightY, 0, HEIGHT - PADDLE_HEIGHT);
    }

    private void updateBall() {
        ballX += ballVx;
        ballY += ballVy;

        if (ballY <= 0) {
            ballY = 0;
            ballVy *= -1;
        } else if (ballY >= HEIGHT - BALL_SIZE) {
            ballY = HEIGHT - BALL_SIZE;
            ballVy *= -1;
        }

        float leftPaddleX = 30;
        if (ballX <= leftPaddleX + PADDLE_WIDTH && ballX >= leftPaddleX && intersects(ballY, leftY)) {
            ballX = leftPaddleX + PADDLE_WIDTH;
            ballVx = Math.abs(ballVx) + 0.25f;
            ballVy += (ballY + BALL_SIZE / 2f - (leftY + PADDLE_HEIGHT / 2f)) * 0.03f;
            ballVy = clamp(ballVy, -8, 8);
        }

        float rightPaddleX = WIDTH - 30 - PADDLE_WIDTH;
        if (ballX + BALL_SIZE >= rightPaddleX && ballX + BALL_SIZE <= rightPaddleX + PADDLE_WIDTH && intersects(ballY, rightY)) {
            ballX = rightPaddleX - BALL_SIZE;
            ballVx = -Math.abs(ballVx) - 0.25f;
            ballVy += (ballY + BALL_SIZE / 2f - (rightY + PADDLE_HEIGHT / 2f)) * 0.03f;
            ballVy = clamp(ballVy, -8, 8);
        }

        if (ballX < -BALL_SIZE) {
            rightScore++;
            resetBall(-1);
        } else if (ballX > WIDTH + BALL_SIZE) {
            leftScore++;
            resetBall(1);
        }
    }

    private boolean intersects(float ballTop, float paddleTop) {
        float ballBottom = ballTop + BALL_SIZE;
        float paddleBottom = paddleTop + PADDLE_HEIGHT;
        return ballBottom >= paddleTop && ballTop <= paddleBottom;
    }

    private void resetBall(int direction) {
        ballX = WIDTH / 2f;
        ballY = HEIGHT / 2f;
        ballVx = 5f * direction;
        ballVy = (float) (Math.random() * 6 - 3);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private void broadcastState() {
        String state = String.format("STATE %.1f %.1f %.2f %.2f %.1f %.1f %d %d",
                ballX, ballY, ballVx, ballVy, leftY, rightY, leftScore, rightScore);
        for (ClientHandler client : clients) {
            client.send(state);
        }
    }

    private void onClientDisconnected(ClientHandler client) {
        clients.remove(client);
        synchronized (stateLock) {
            if (client.role == Role.LEFT) {
                leftInputDir = 0;
            } else if (client.role == Role.RIGHT) {
                rightInputDir = 0;
            }
            assignRoles();
        }
    }

    private void assignRoles() {
        ClientHandler left = null;
        ClientHandler right = null;

        for (ClientHandler client : clients) {
            if (left == null) {
                left = client;
            } else if (right == null) {
                right = client;
            } else {
                break;
            }
        }

        for (ClientHandler client : clients) {
            Role newRole;
            if (client == left) {
                newRole = Role.LEFT;
            } else if (client == right) {
                newRole = Role.RIGHT;
            } else {
                newRole = Role.SPECTATOR;
            }

            if (client.role != newRole) {
                client.role = newRole;
                client.send("ASSIGN " + newRole.name());
            }
        }

        if (left == null) {
            leftInputDir = 0;
        }
        if (right == null) {
            rightInputDir = 0;
        }
    }

    private enum Role {
        LEFT, RIGHT, SPECTATOR
    }

    private class ClientHandler extends Thread {
        private final Socket socket;
        private final BufferedReader in;
        private final PrintWriter out;
        private volatile Role role = Role.SPECTATOR;
        private String nickname = "guest";

        private ClientHandler(Socket socket) throws IOException {
            super("client-handler-" + socket.getPort());
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        }

        @Override
        public void run() {
            try {
                send("INFO Welcome to badminton server");
                synchronized (stateLock) {
                    assignRoles();
                }

                String line;
                while ((line = in.readLine()) != null) {
                    handleLine(line.trim());
                }
            } catch (IOException ignored) {
            } finally {
                closeQuietly();
                onClientDisconnected(this);
            }
        }

        private void handleLine(String line) {
            if (line.isBlank()) {
                return;
            }

            if (line.startsWith("HELLO ")) {
                String name = line.substring("HELLO ".length()).trim();
                if (!name.isBlank()) {
                    nickname = name;
                    send("INFO hello " + nickname);
                }
                return;
            }

            if (line.startsWith("INPUT ")) {
                int dir;
                try {
                    dir = Integer.parseInt(line.substring("INPUT ".length()).trim());
                } catch (NumberFormatException e) {
                    return;
                }
                if (dir < -1 || dir > 1) {
                    return;
                }

                synchronized (stateLock) {
                    if (role == Role.LEFT) {
                        leftInputDir = dir;
                    } else if (role == Role.RIGHT) {
                        rightInputDir = dir;
                    }
                }
            }
        }

        private void send(String message) {
            out.println(message);
        }

        private void closeQuietly() {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
