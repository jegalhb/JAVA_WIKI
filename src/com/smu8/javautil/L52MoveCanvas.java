package com.smu8.javautil;

import javax.swing.*;
import java.awt.*;

public class L52MoveCanvas extends JFrame {
    public L52MoveCanvas(){
        super("움직이는 원이에요");
        this.setContentPane(new MyPanel()); // 캔버스 전체를 마이패널로 바꾸는것
        this.setBounds(0,0,500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    class MyPanel extends JPanel{
        int x = 0;
        Timer moveTimer;
        public MyPanel(){
            this.setBackground(new Color(0,0,0));
          moveTimer=new Timer(10,(e -> {
              if (x<=450-25){
                  ++x;

                  repaint();
              }
          }));
          moveTimer.start();

        }

        @Override
        protected void paintComponent(Graphics g){
            this.setBackground(Color.BLACK); // 프레임을 지우고 다시 그리는거라
            super.paintComponent(g);
            g.setColor(new Color(255,200,0));
            g.fillOval(x,200,50,50);
        }
    }
    static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame = new L52MoveCanvas();
            frame.setVisible(true);
        });
    }
}
