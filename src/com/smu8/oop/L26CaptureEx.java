package com.smu8.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CounterApp extends JFrame{
    private JLabel label;
    private JButton plusBtn;
    private JButton minusBtn;

    int count = 0;
    public CounterApp(){
        super("카운터");
        //int count=0;
        // 지역변수 카운터를 람다식이나 익명클래스에서 접근하면 캡처가 되면서 상수로 변경
        label=new JLabel();

        Font font = new Font("Serif" , Font.BOLD,30 );
        label.setHorizontalAlignment(JLabel.CENTER); // 버튼이 중앙으로 가게
        label.setFont(font);
        label.setText(count+ "");

        plusBtn = new JButton("+");
        minusBtn = new JButton("-");
        plusBtn.addActionListener((event)->{
            label.setText(++count+"");
        });

        minusBtn.addActionListener((e)->{
            if (count>0) {
                label.setText(--count + "");
            }
               label.setText(--count + "");
        });

        minusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    label.setText(Integer.toString(--count));
                }
            }
        );


        this.add(label);
        this.add(plusBtn, BorderLayout.SOUTH);
        this.add(minusBtn, BorderLayout.NORTH);
        this.setBounds(100 , 100 , 300 , 300);
        this.setVisible(true);
    }
}
public class L26CaptureEx {
    public static void main(String[] args) {
        CounterApp counterApp = new CounterApp();
    }
}
