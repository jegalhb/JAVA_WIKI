package com.smu8.networkbadminton;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BadmintonGameClient extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;
    private static final int PADDLE_WIDTH = 14;
    private static final int PADDLE_HEIGHT = 90;
    private static final int BALL_SIZE = 16;

    private final JLabel roleLabel = new JLabel("Role: DISCONNECTED");
    private final JLabel scoreLabel = new JLabel("Score 0 : 0");
    private final JTextField hostField = new JTextField("127.0.0.1", 10);
    private final JTextField portField = new JTextField("7777", 5);
    private final JTextField nameField = new JTextField("player", 8);
    private final JButton connectBtn = new JButton("Connect");

    private final GamePanel gamePanel = new GamePanel();

    private volatile boolean running = false;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private volatile String role = "SPECTATOR";
    private volatile float leftY = (HEIGHT - PADDLE_HEIGHT) / 2f;
    private volatile float rightY = (HEIGHT - PADDLE_HEIGHT) / 2f;
    private volatile float ballX = WIDTH / 2f;
    private volatile float ballY = HEIGHT / 2f;
    private volatile int leftScore = 0;
    private volatile int rightScore = 0;

    private int lastInputDir = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BadmintonGameClient().setVisible(true));
    }

    public BadmintonGameClient() {
        setTitle("Socket Badminton Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(new JLabel("Host"));
        top.add(hostField);
        top.add(new JLabel("Port"));
        top.add(portField);
        top.add(new JLabel("Name"));
        top.add(nameField);
        top.add(connectBtn);
        top.add(roleLabel);
        top.add(scoreLabel);
        add(top, BorderLayout.NORTH);

        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(gamePanel, BorderLayout.CENTER);

        connectBtn.addActionListener(e -> {
            if (!running) {
                connect();
            } else {
                disconnect();
            }
        });

        setupKeyBinding();

        Timer repaintTimer = new Timer(16, e -> gamePanel.repaint());
        repaintTimer.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void setupKeyBinding() {
        gamePanel.setFocusable(true);
        gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke("pressed UP"), "upPressed");
        gamePanel.getActionMap().put("upPressed", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sendInput(-1);
            }
        });

        gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke("released UP"), "upReleased");
        gamePanel.getActionMap().put("upReleased", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sendInput(0);
            }
        });

        gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke("pressed DOWN"), "downPressed");
        gamePanel.getActionMap().put("downPressed", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sendInput(1);
            }
        });

        gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke("released DOWN"), "downReleased");
        gamePanel.getActionMap().put("downReleased", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sendInput(0);
            }
        });
    }

    private void connect() {
        String host = hostField.getText().trim();
        String name = nameField.getText().trim();
        int port;

        try {
            port = Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port is invalid");
            return;
        }

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Name is required");
            return;
        }

        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            running = true;
            connectBtn.setText("Disconnect");
            out.println("HELLO " + name);
            gamePanel.requestFocusInWindow();

            Thread reader = new Thread(this::readLoop, "client-reader");
            reader.setDaemon(true);
            reader.start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + e.getMessage());
            disconnect();
        }
    }

    private void readLoop() {
        try {
            String line;
            while (running && (line = in.readLine()) != null) {
                handleServerLine(line.trim());
            }
        } catch (IOException ignored) {
        } finally {
            SwingUtilities.invokeLater(this::disconnect);
        }
    }

    private void handleServerLine(String line) {
        if (line.startsWith("ASSIGN ")) {
            role = line.substring("ASSIGN ".length()).trim();
            SwingUtilities.invokeLater(() -> roleLabel.setText("Role: " + role));
            return;
        }

        if (line.startsWith("STATE ")) {
            String[] p = line.split("\\s+");
            if (p.length < 9) {
                return;
            }
            try {
                ballX = Float.parseFloat(p[1]);
                ballY = Float.parseFloat(p[2]);
                leftY = Float.parseFloat(p[5]);
                rightY = Float.parseFloat(p[6]);
                leftScore = Integer.parseInt(p[7]);
                rightScore = Integer.parseInt(p[8]);
                SwingUtilities.invokeLater(() -> scoreLabel.setText("Score " + leftScore + " : " + rightScore));
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private void sendInput(int dir) {
        if (!running || out == null) {
            return;
        }
        if ("SPECTATOR".equals(role)) {
            return;
        }
        if (lastInputDir == dir) {
            return;
        }
        lastInputDir = dir;
        out.println("INPUT " + dir);
    }

    private void disconnect() {
        running = false;
        role = "SPECTATOR";
        lastInputDir = 0;
        connectBtn.setText("Connect");
        roleLabel.setText("Role: DISCONNECTED");

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
        socket = null;
        in = null;
        out = null;
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(25, 120, 55));
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            g2.setColor(Color.WHITE);
            for (int y = 0; y < HEIGHT; y += 30) {
                g2.fillRect(WIDTH / 2 - 2, y, 4, 18);
            }

            g2.fillRect(30, Math.round(leftY), PADDLE_WIDTH, PADDLE_HEIGHT);
            g2.fillRect(WIDTH - 30 - PADDLE_WIDTH, Math.round(rightY), PADDLE_WIDTH, PADDLE_HEIGHT);

            g2.setColor(new Color(240, 240, 240));
            g2.fillOval(Math.round(ballX), Math.round(ballY), BALL_SIZE, BALL_SIZE);

            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            g2.setColor(Color.YELLOW);
            g2.drawString("Use UP / DOWN", 20, 24);
        }
    }
}
