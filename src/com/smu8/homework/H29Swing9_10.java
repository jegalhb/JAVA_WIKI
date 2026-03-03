package com.smu8.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class H29Swing9_10 extends JFrame {
    private JButton eBtn;
    private JButton wBtn;
    private JLabel label;
    public H29Swing9_10(){
        super("Swing 9_10문제");
        eBtn = new JButton("오른쪽");
        eBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("안농");
            }
        });
        wBtn = new JButton("왼쪽");
        wBtn.addActionListener((e)->{
            label.setText("잘가아!!!!!!!!!!!!!!!!!!!!!!!!!");
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("잘가~~");

            }
        });

        label = new JLabel("",SwingConstants.CENTER);
        this.add(label);
        this.add(eBtn, BorderLayout.EAST);
        this.add(wBtn,BorderLayout.WEST);
        this.setBounds(0,0,300,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    static void main(String[] args) {
        H29Swing9_10 frame = new H29Swing9_10();
        frame.setVisible(true);
    }
}
