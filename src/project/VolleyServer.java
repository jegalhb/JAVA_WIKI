package project;

import java.io.*;
import java.net.*;

public class VolleyServer implements Runnable {
    private final int port;
    private final BiConsumerLike tickCallback;
    private final SupplierLike snapshotSupplier;

    private volatile boolean running = true;
    private Socket left;
    private Socket right;

    private SimpleVolleyGame.InputState leftInput = new SimpleVolleyGame.InputState();
    private SimpleVolleyGame.InputState rightInput = new SimpleVolleyGame.InputState();

    public VolleyServer(int port, BiConsumerLike tickCallback, SupplierLike snapshotSupplier) {
        this.port = port;
        this.tickCallback = tickCallback;
        this.snapshotSupplier = snapshotSupplier;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            left = serverSocket.accept();
            right = serverSocket.accept();

            new Thread(() -> listenInputs(left, true)).start();
            new Thread(() -> listenInputs(right, false)).start();

            while (running) {
                tickCallback.accept(leftInput, rightInput);
                SimpleVolleyGame.GameSnapshot snap = snapshotSupplier.get();

                sendState(left, snap);
                sendState(right, snap);

                Thread.sleep(16);
            }
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            running = false; // 모든 예외 처리 후 서버를 안전하게 종료
        }
    }

    private void listenInputs(Socket s, boolean isLeft) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("INPUT")) continue;
                String[] t = line.split(",");
                SimpleVolleyGame.InputState st = new SimpleVolleyGame.InputState();
                st.left = "1".equals(t[1]);
                st.right = "1".equals(t[2]);
                st.jump = "1".equals(t[3]);
                st.spike = "1".equals(t[4]);

                if (isLeft) leftInput = st;
                else rightInput = st;
            }
        } catch (Exception ignored) {
        }
    }

    private void sendState(Socket s, SimpleVolleyGame.GameSnapshot snap) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
        pw.println("STATE," +
                snap.p1x + "," + snap.p1y + "," +
                snap.p2x + "," + snap.p2y + "," +
                snap.bx + "," + snap.by + "," +
                snap.scoreL + "," + snap.scoreR + "," +
                bool(snap.started) + "," + bool(snap.paused) + "," +
                bool(snap.gameOver) + "," + bool(snap.countdownActive) + "," +
                snap.countdownValue + "," + bool(snap.roundEndActive) + "," +
                snap.fadeAlpha + "," +
                safe(snap.gameOverMsg) + "," +
                safe(snap.roundWinnerMsg)
        );
    }

    private String bool(boolean b) { return b ? "1" : "0"; }
    private String safe(String s) { return s == null ? "" : s.replace(",", " "); }

    public interface BiConsumerLike {
        void accept(SimpleVolleyGame.InputState p1, SimpleVolleyGame.InputState p2);
    }

    public interface SupplierLike {
        SimpleVolleyGame.GameSnapshot get();
    }
}