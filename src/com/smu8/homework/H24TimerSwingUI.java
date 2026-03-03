package com.smu8.homework;

import javax.swing.*;
import java.awt.*;

class TimerUI extends JFrame {

    JLabel timerLabel;
    JButton startBtn;
    JButton stopBtn;

    volatile boolean running = false; // 쓰레드 제어용
    int seconds = 0;

    public TimerUI() {

        Font font = new Font("Dialog", Font.BOLD, 30);

        // 타이머 라벨
        timerLabel = new JLabel("0", SwingConstants.CENTER);
        timerLabel.setFont(font);

        // 버튼
        startBtn = new JButton("시작");
        stopBtn = new JButton("종료");

        // 레이아웃
        this.setLayout(new BorderLayout());
        this.add(startBtn, BorderLayout.NORTH);
        this.add(timerLabel, BorderLayout.CENTER);
        this.add(stopBtn, BorderLayout.SOUTH);

        // 타이머 쓰레드
        Thread timerThread = new Thread(() -> {
            while (true) {
                if (running) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                    seconds++;
                    timerLabel.setText(String.valueOf(seconds));
                }
            }
        });
        timerThread.start();

        // 시작 버튼
        startBtn.addActionListener(e -> running = true);

        // 종료 버튼
        stopBtn.addActionListener(e -> running = false);

        // 프레임 설정
        this.setBounds(300, 200, 300, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

public class H24TimerSwingUI {
    public static void main(String[] args) {
        new TimerUI();
    }
}

