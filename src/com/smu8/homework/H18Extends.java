package com.smu8.homework;

import javax.swing.*;
import java.awt.*;

class Memo extends JFrame{
    JButton btn = new JButton("버튼");
    JTextArea ja = new JTextArea();
    JCheckBox Ch = new JCheckBox();
    JButton BTN = new JButton("나가기");
    public Memo (){
        super("메모장");
        // JFrame  super = new JFrame("메모장")
        super.add(btn,BorderLayout.NORTH);
        super.add(ja,BorderLayout.CENTER);
        super.add(Ch,BorderLayout.SOUTH);
        super.add(BTN,BorderLayout.SOUTH);
        super.setBounds(300,300,500,500);
        super.setVisible(true);
    }

}

public class H18Extends {
    public static void main(String[] args) {
       Memo memo = new Memo();


        /*
        JFrame frame = new JFrame("윈도우");
        frame.setSize(500,500);
        JButton btn = new JButton("버튼이야~");
        frame.setVisible(true);
        frame.add(btn);
         */
    }
}
