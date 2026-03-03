package com.smu8.javautil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class L53keyMoveCanvas extends JFrame {
    int width = 500;
    int height = 500;
    Color bgColor = new Color(0,0,0);
    public L53keyMoveCanvas(){
        super("aswd로움직이는 원 ㄷㄷ");
        this.setContentPane(new MyPanel());
        this.setBounds(0,0,width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    class MyPanel extends JPanel{
        int x=200;
        int y=200;
        int r=50;
        public MyPanel(){
            this.setBackground(L53keyMoveCanvas.this.bgColor);
            this.setFocusable(true);
            this.requestFocusInWindow(); // 키보드이벤트를 받을 수 있다
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) { // 누른키를알 수 있다
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_S -> {
                            y+=10;
                            repaint();
                            break;
                        }
                        case KeyEvent.VK_D -> {
                            x+=10;
                            repaint();
                            break;
                        }
                        case KeyEvent.VK_A -> {
                            x-=10;
                            repaint();
                            break;
                        }
                        case KeyEvent.VK_W -> {
                            y-=10;
                            repaint();
                            break;
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // 기존의 그림을 지우려면 그대루 둔다잉
            g.setColor(new Color(255,0,0));
            g.fillOval(x,y,r,r);
        }
    }
    public static void main(String[] args) {
        JFrame frame = new L53keyMoveCanvas();
        frame.setVisible(true);
    }
}
