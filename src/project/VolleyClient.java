package project;

import java.io.*;
import java.net.*;

public class VolleyClient {
    private final String host;
    private final int port;
    private final StateConsumer consumer;

    private Socket socket;
    private PrintWriter out;

    public VolleyClient(String host, int port, StateConsumer consumer) {
        this.host = host;
        this.port = port;
        this.consumer = consumer;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            new Thread(this::readLoop, "VolleyClient-Reader").start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendInput(SimpleVolleyGame.InputState input) {
        if (out == null) return;
        out.println("INPUT," +
                (input.left ? "1" : "0") + "," +
                (input.right ? "1" : "0") + "," +
                (input.jump ? "1" : "0") + "," +
                (input.spike ? "1" : "0")
        );
    }

    public void readLoop() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // existing parsing logic
            }
        } catch (IOException e) {
            System.err.println("Client read error: " + e.getMessage());
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
                socket = null; // Help garbage collector
            }
            if (out != null) {
                out.close();
                out = null; // Help garbage collector
            }
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public interface StateConsumer {
        void accept(SimpleVolleyGame.GameSnapshot s);
    }
}