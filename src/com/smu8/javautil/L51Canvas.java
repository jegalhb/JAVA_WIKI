package com.smu8.javautil;

import javax.swing.*;
import java.awt.*;

public class L51Canvas extends JFrame {
    class MyPanel extends JPanel{
        //JPanel 이 캔버스가 되려면 paintComponent를 구현해야함
        public MyPanel (){
            this.setBackground(new Color(255,200,255));
        }

        @Override
        protected void paintComponent(Graphics g) { // 붓
            Graphics2D g2 =(Graphics2D)g;
            // 1. 붓의 색을 정하기
            // 2. 붓의 두께를 정하기
            // 3. 어떤 그림으 그릴껀지 정해서 그리기

            ((Graphics2D) g).setStroke(new BasicStroke(5)); // 붓의 굵기를 정할 수 있다잉
            g2.setColor(new Color(212, 238, 46, 185));
            g2.drawLine(0,0,50,50);

            g2.setStroke(new BasicStroke(10));
            g2.setColor(new Color(255,45,255));
            g2.drawLine(300,300,0,0);

            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.red);
            g2.drawOval(50,50,100,200);

            g2.setColor(new Color(0,255,0,120));
            g2.fillOval(300,50,200,100);

            g2.setColor(new Color(0,0,255,100));
            g2.fillRect(50,200,100,200);
        }
    }
    public L51Canvas(){
        super("캔버스 포함하는 윈도우~");
        //this.add(new MyPanel());
        this.setContentPane(new MyPanel());// 명시적으로 화면 켐퍼스를 꽉 채우겠는것
        setBounds(0,0,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
    }
}
