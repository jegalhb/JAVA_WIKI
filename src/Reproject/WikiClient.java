package Reproject;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class WikiClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final MainWikiFrame mainFrame;
    private final String nickname;

    public WikiClient(MainWikiFrame mainFrame, String nickname) {
        this.mainFrame = mainFrame;
        this.nickname = (nickname == null || nickname.trim().isEmpty()) ? "guest" : nickname.trim();
    }

    public static void main(String[] args) {
        ConceptRepository repo = new ConceptRepository();
        SearchService search = new SearchService(repo);

        SwingUtilities.invokeLater(() -> {
            MainWikiFrame frame = new MainWikiFrame(search, repo);
            frame.setVisible(true);

            String nickname = JOptionPane.showInputDialog(frame, "이름을 입력하세요:", "guest");
            if (nickname == null || nickname.trim().isEmpty()) {
                nickname = "guest";
            }

            String serverIp = "192.168.0.34";
            String portStr = "9999";

            while (true) {
                serverIp = JOptionPane.showInputDialog(frame, "접속할 서버 IP를 입력하세요:", serverIp);
                if (serverIp == null) {
                    frame.setClient(null);
                    frame.appendChat(">> IP 입력이 취소되어 오프라인 모드로 시작합니다.");
                    break;
                }
                serverIp = serverIp.trim();
                if (serverIp.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "서버 IP를 입력하세요.");
                    continue;
                }

                portStr = JOptionPane.showInputDialog(frame, "접속할 포트 번호를 입력하세요:", portStr);
                if (portStr == null) {
                    frame.setClient(null);
                    frame.appendChat(">> 포트 입력이 취소되어 오프라인 모드로 시작합니다.");
                    break;
                }
                portStr = portStr.trim();

                int port;
                try {
                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "포트는 숫자만 입력할 수 있습니다. 다시 입력하세요.");
                    continue;
                }

                WikiClient client = new WikiClient(frame, nickname);
                frame.setClient(client);

                if (client.start(serverIp, port)) {
                    break;
                }

                frame.setClient(null);
                JOptionPane.showMessageDialog(frame, "서버 연결에 실패했습니다. IP/포트를 다시 확인하세요.");
            }
        });
    }

    public boolean start(String ip, int port) {
        try {
            socket = new Socket(ip, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            mainFrame.appendChat(">> 서버(" + ip + ":" + port + ") 연결 성공!");

            Thread listener = new Thread(this::listenLoop, "wiki-client-listener");
            listener.setDaemon(true);
            listener.start();
            return true;
        } catch (Exception e) {
            mainFrame.appendChat(">> 서버 연결 실패 (접속 정보나 서버 상태를 확인해주세요)");
            closeQuietly();
            return false;
        }
    }

    private void listenLoop() {
        try {
            while (true) {
                String type = in.readUTF();
                if ("REFRESH".equals(type)) {
                    send("LIST", null);
                } else if ("CHAT_MSG".equals(type)) {
                    String msg = in.readUTF();
                    mainFrame.appendChat(msg);
                } else if ("LIST_DATA".equals(type)) {
                    Object data = in.readObject();
                    if (data instanceof List) {
                        mainFrame.applyServerData((List<Concept>) data);
                    }
                }
            }
        } catch (EOFException e) {
            mainFrame.appendChat(">> [연결 종료] 서버와 통신이 끊어졌습니다.");
        } catch (Exception e) {
            mainFrame.appendChat(">> 서버 수신 중 오류가 발생했습니다.");
        } finally {
            closeQuietly();
            SwingUtilities.invokeLater(() -> mainFrame.setClient(null));
        }
    }

    public void send(String command, Object data) {
        try {
            if (out == null) return;
            out.writeUTF(command);
            if (data instanceof Concept) {
                out.writeObject(data);
            } else if (data instanceof String) {
                String text = (String) data;
                if ("CHAT".equals(command)) {
                    out.writeUTF("[" + nickname + "]: " + text);
                } else {
                    out.writeUTF(text);
                }
            }
            out.flush();
            out.reset();
        } catch (IOException e) {
            mainFrame.appendChat(">> 메시지 전송에 실패했습니다.");
        }
    }

    private void closeQuietly() {
        try {
            if (in != null) in.close();
        } catch (IOException ignored) {
        }
        try {
            if (out != null) out.close();
        } catch (IOException ignored) {
        }
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {
        }
    }
}