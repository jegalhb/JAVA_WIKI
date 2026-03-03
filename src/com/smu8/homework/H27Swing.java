package com.smu8.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class H27Swing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("스!노우맨");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new SnowmanPanel());
            f.setSize(600, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }


    static class SnowmanPanel extends JPanel {
        private Timer timer;
        private double bobbingOffset = 0;
        private ArrayList<Snowflake> snow = new ArrayList<>();
        private Random rnd = new Random();

        SnowmanPanel() {
            // 눈송이 초기화
            for (int i = 0; i < 500; i++) {
                snow.add(new Snowflake());
            }

            // 타이머로 30초마다 갱신
            timer = new Timer(30, e -> {
                bobbingOffset = Math.sin(System.currentTimeMillis() * 0.005) * 5;
                for (Snowflake s : snow) s.move();
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 그라데이션 배경 (밤하늘) == 바이브 코딩
            GradientPaint sky = new GradientPaint(0, 0, new Color(10, 20, 50), 0, getHeight(), new Color(40, 60, 120));
            g2.setPaint(sky);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // 눈 내리는 효과
            g2.setColor(Color.WHITE);
            for (Snowflake s : snow) {
                g2.fill(new Ellipse2D.Double(s.x, s.y, s.size, s.size));
            }

            //  쌓인 눈 바닥
            g2.setColor(new Color(240, 248, 255));
            g2.fillOval(-100, getHeight() - 100, getWidth() + 200, 200);

            //  눈사람 그리기
            //int bx = 240, by = (int)(100 + bobbingOffset);

            // 몸체 + 그림자 == 바이브코딩
            // 전체 크기에서 어디에 위치해야한다를 지정!
            int centerX = getWidth() /2; // 중앙잡어
            int groundY=getHeight() - 100; // 아래에서 100위에 올려
            int bx = centerX-30;
            int by = groundY-260 +(int)bobbingOffset;
            drawCircle(g2, bx - 30, by + 130, 150); // 하단
            drawCircle(g2, bx, by + 60, 100);       // 중단
            drawCircle(g2, bx + 20, by, 60);        // 머리


            // 모자
            g2.setColor(new Color(50, 50, 50));
            g2.fillRect(bx + 15, by - 25, 70, 10); // 챙
            g2.fillRect(bx + 30, by - 60, 40, 40); // 본체

            // 목도리!!
            g2.setColor(new Color(200, 50, 50));
            g2.fillRoundRect(bx + 15, by + 50, 70, 15, 10, 10);

            // 얼굴 요소 등등
            g2.setColor(Color.BLACK);
            g2.fillOval(bx + 38, by + 20, 8, 8); // 눈
            g2.fillOval(bx + 58, by + 20, 8, 8);

            g2.setColor(Color.ORANGE);
            int[] px = {bx+52, bx+75, bx+52};
            int[] py = {by+32, by+36, by+40};
            g2.fillPolygon(px, py, 3); // 당근 코

            // 팔 부분
            g2.setColor(new Color(101, 67, 33));
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(bx + 5, by + 100, bx - 60, by + 70);
            g2.drawLine(bx + 95, by + 100, bx + 160, by + 70);
        }

        private void drawCircle(Graphics2D g2, int x, int y, int r) {
            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, r, r);
            g2.setColor(new Color(200, 220, 240)); // 연한 하늘색 테두리
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x, y, r, r);
        }

        class Snowflake {
            double x, y, size, speed;
            Snowflake() { reset(); y = rnd.nextInt(1080); }
            void reset() {
                x = rnd.nextInt(1980);
                y = -10;
                size = rnd.nextInt(5) + 2;
                speed = rnd.nextDouble() * 2 + 1;
            }
            void move() {  // 눈움직임 표현
                y += speed;
                x += Math.tan(y * 0.05);
                if (y > 1080) reset();
            }
        }
    }
}