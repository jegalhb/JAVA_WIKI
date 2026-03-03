package com.smu8.javautil;

import javax.swing.*;

public class L49EDT {
    public static void main(String[] args) {
        JFrame frame = new JFrame("EDT");
        frame.setLayout(null);

        JButton btn = new JButton("안녕");
        btn.setBounds(184,270,100,100);
        JLabel label = new JLabel("라벨");
        label.setBounds(0,0,100,200);
        frame.add(label);
        frame.add(btn);
        btn.addActionListener((event)->{ // addActionListener 이벤트 정보를 가지고있음
            try {
                Thread.sleep(3000); // EDT가 윈도우에서 발생하는 모든 이벤트를 총괄한다! 클릭 후 3초간 어떠한 이벤트로 발생하지 않는다.
                // 오래걸리는 일은 스레드로 만들어서 진행해라!
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            label.setText("안녕!!!!!!!!!!!!!!!!!!!!!!!!");
        });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,300,400);
        frame.setVisible(true);
    }
}
