package com.smu8.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class H28swing extends JFrame {
    private JButton btm;
    private JLabel label;
    private JTextField tf;
    class  BtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) { // 콜백함수
            String numStr = tf.getText();
            double num = Double.parseDouble(numStr);
            label.setText(Double.toString(num*2));
        }
    }

    public H28swing(){ // 하나의 생성자에 도구들 정리
    super("swing 문제8");
    btm = new JButton("더블로올라요");
    btm.addActionListener((e)->{});
    label = new JLabel("더블인수에요" , SwingConstants.CENTER);
    tf = new JTextField("0" , 5);
    JPanel p = new JPanel();
    p.setBackground(new Color(84, 20, 119, 255));
    p.add(tf);
    p.add(btm);
    this.add(label);
    this.add(p,BorderLayout.SOUTH);
    this.setBounds(0,0,200,200);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    static void main(String[] args) {
    new H28swing();
    }
}
