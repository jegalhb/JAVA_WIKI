package com.smu8.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class E15ChatClientSwing extends JFrame {

    // 채팅 출력 영역(읽기 전용)
    private JTextArea chatArea;

    // 채팅 입력 영역
    private JTextField inputField;

    // 전송 버튼
    private JButton sendBtn;

    // 접속자 목록을 보여줄 리스트 UI
    private JList<String> userList;
    private DefaultListModel<String> userListModel;

    // 소켓 통신 객체들
    private Socket socket;
    private BufferedReader in; // 서버 -> 클라이언트(문자열 한 줄씩 읽기)
    private PrintWriter out;   // 클라이언트 -> 서버(문자열 한 줄씩 보내기)

    // reader 스레드를 종료시키기 위한 플래그
    // (UI 스레드와 reader 스레드가 함께 읽고 쓰므로 volatile로 가시성 보장)
    private volatile boolean running = false;

    public static void main(String[] args) {
        // Swing UI는 반드시 EDT(Event Dispatch Thread)에서 생성/표시해야 한다.
        SwingUtilities.invokeLater(() -> new E15ChatClientSwing().setVisible(true));
    }

    public E15ChatClientSwing() {
        setTitle("Swing Group Chat");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 520); // 접속자 목록 패널 추가로 너비 확대
        setLocationRelativeTo(null);

        initUI();      // UI 구성
        connectFlow(); // 서버 접속 흐름

        // 창 닫을 때 소켓 정리
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
            }
        });
    }

    private void initUI() {
        // 채팅 출력창
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JScrollPane chatScroll = new JScrollPane(chatArea);

        // 접속자 목록(오른쪽 패널)
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(220, 0));

        // 채팅 입력 + 전송 버튼(하단)
        inputField = new JTextField();
        sendBtn = new JButton("전송");

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);

        // 가운데 채팅 + 오른쪽 접속자 목록을 나누기
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatScroll, userScroll);
        splitPane.setResizeWeight(0.75); // 채팅 영역을 더 넓게
        splitPane.setDividerSize(6);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // 엔터 키로 전송
        inputField.addActionListener(e -> send());
        sendBtn.addActionListener(e -> send());

        // 접속 전에는 입력 비활성화
        setInputEnabled(false);
    }

    private void connectFlow() {
        // 입력창으로 서버 접속 정보를 받는다
        String host = JOptionPane.showInputDialog(this, "서버 주소", "192.168.0.116"); //내컴퓨터는 127.0.0.1
        if (host == null) { dispose(); return; }

        String portStr = JOptionPane.showInputDialog(this, "포트", "6000");
        if (portStr == null) { dispose(); return; }

        String nickname = JOptionPane.showInputDialog(this, "닉네임", "user");
        if (nickname == null) { dispose(); return; }

        int port;
        try {
            port = Integer.parseInt(portStr.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "포트가 올바르지 않습니다.");
            dispose();
            return;
        }

        try {
            // 서버 연결
            socket = new Socket(host.trim(), port);

            // 작은 패킷 지연(Nagle) 비활성화: 짧은 채팅 메시지를 즉시 보내고 싶을 때 사용
            socket.setTcpNoDelay(true);

            // UTF-8로 문자열 송수신 준비(한 줄 단위 프로토콜)
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // autoFlush=true: println 호출 시 flush가 자동 수행되어 상대가 바로 받기 쉬움
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

            // 서버가 첫 줄을 닉네임으로 받는다고 약속했으므로, 접속 직후 닉네임을 한 줄로 보낸다.
            out.println(nickname.trim());

            running = true;
            setInputEnabled(true);
            appendLine("[SYSTEM] connected");

            // 서버에서 오는 메시지를 계속 읽는 스레드 시작
            Thread reader = new Thread(this::readLoop);
            reader.setDaemon(true); // 메인 종료 시 같이 종료되게
            reader.start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "서버 연결 실패: " + e.getMessage());
            dispose();

        }
    }

    private void readLoop() {
        try {
            String line;

            // 서버가 보내는 한 줄 메시지를 계속 읽는다.
            // readLine()은 줄바꿈(\n)이 올 때까지 대기한다.
            while (running && (line = in.readLine()) != null) {

                // 서버가 보낸 특수 메시지: 접속자 목록 업데이트
                // 예) @USERS user1,user2,user3
                if (line.startsWith("@USERS ")) {

                    // Swing 컴포넌트 갱신은 EDT에서 해야 안전하다.(스레드에서 직접 gui를 변경하지 않음)
                    final String finalLine = line; //지역변수 캡처를 가능하게 하기 위해 임시 상수 생성
                    SwingUtilities.invokeLater(() -> updateUserList(finalLine));
                    continue;
                }

                // 일반 채팅 메시지는 채팅창에 출력
                String msg = line;
                SwingUtilities.invokeLater(() -> appendLine(msg));
            }
        } catch (IOException ignored) {
        } finally {
            // 연결 종료 처리
            SwingUtilities.invokeLater(() -> {
                appendLine("[SYSTEM] disconnected");
                setInputEnabled(false);
                userListModel.clear();
            });
            disconnect();
        }
    }

    private void send() {
        if (!running || out == null) return;

        String text = inputField.getText();
        if (text == null) return;

        text = text.trim();
        if (text.isBlank()) return;

        // println은 자동으로 줄바꿈 포함 + autoFlush=true면 flush까지 수행
        out.println(text);

        inputField.setText("");
        inputField.requestFocus();

        // /quit 입력 시 종료(서버도 /quit을 처리하도록 해두면 좋음)
        if (text.equalsIgnoreCase("/quit")) {
            disconnect();
            dispose();
        }
    }

    private void appendLine(String msg) {
        chatArea.append(msg + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void updateUserList(String line) { //"@USERS 경민,철수,영희"

        String payload = line.substring("@USERS ".length()).trim(); //"경민,철수"
        if(payload.isBlank()) return;
        String [] userNames = payload.split(",");

        userListModel.clear(); //기존 접속한 유저리스트 정리
        for (String u : userNames) userListModel.addElement(u);
    }

    private void setInputEnabled(boolean enabled) {
        inputField.setEnabled(enabled);
        sendBtn.setEnabled(enabled);
        if (enabled) inputField.requestFocus();
    }

    private void disconnect() {
        running = false;
        try { if (socket != null) socket.close(); } catch (Exception ignored) {}
        socket = null;
        in = null;
        out = null;
    }
}