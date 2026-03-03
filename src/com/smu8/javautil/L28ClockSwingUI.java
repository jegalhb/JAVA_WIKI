package com.smu8.javautil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class DigitalClock extends JFrame {
    Font font = new Font("Dialog",Font.BOLD,25);
    JLabel dateLabel; // 날짜 출력 라벨
    JLabel ClockLabel;// 시간출력
    JLabel tempLabel;//온도출력
    JLabel humLabel;//습도출력
    JPanel p; // 온도와 습도를 포함하는 패널!

    public DigitalClock(){
        dateLabel=new JLabel("2026년 02월 09일 (월)" , SwingConstants.CENTER);
        ClockLabel=new JLabel("13시 35분 07초", SwingConstants.CENTER);
        ClockLabel.setFont(font);
        ClockLabel.setPreferredSize(new Dimension(0,40));
        tempLabel=new JLabel("온도: -27도" , SwingConstants.CENTER);
        humLabel= new JLabel("습도 98%" , SwingConstants.CENTER);
        p= new JPanel();
        p.add(tempLabel);
        p.add(humLabel);

        this.add(dateLabel, BorderLayout.NORTH);
        this.add(ClockLabel);
        this.add(p,BorderLayout.SOUTH);

        //시간을 1초에 한번씩바꾸꾸끼
        Thread ClockThread = new Thread(()->{
            DateTimeFormatter dtr = DateTimeFormatter.ofPattern("HH시 mm분 ss초"); // HH MM ss -> HH시 M분 ss초를 원함
            while (true){
                threadSleep(1000);
                LocalTime time =LocalTime.now();
                //System.out.println(time);
                ClockLabel.setText(time.format(dtr));
            }
        });
        ClockThread.start();
        new Thread(()->dateThread()).start();

        this.setBounds(0,0,400,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    void dateThread(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy년MM월dd일 (E)" , Locale.KOREAN);
        while (true){
            threadSleep(1000);
            LocalDate now =LocalDate.now();
            dateLabel.setText(now.format(dtf)); // 2026 02 09 : yyyy MM dd -> yy MM dd
        }
    }
    void threadSleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class L28ClockSwingUI {
    static void main() {new DigitalClock();
    }
}
