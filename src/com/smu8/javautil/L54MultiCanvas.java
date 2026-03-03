package com.smu8.javautil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Random;

class ShapeRect{
    int x =0;
    int y =0;
    int width=50;
    int heigh=50;
    double speed=1.5;
    //int r,g,b;
    Color color = Color.BLUE;
    public static Color createRandomColor(){

        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        Color color = new Color(r,g,b);
        return color;

    }
    //오버로드 : 함수or생성자 의 이름이 같은데 매개변수가 다른것
    public ShapeRect(int x , int y , int size){
        this(x,y,size,size,createRandomColor());
    }

    public ShapeRect(int x , int y, int size , Color color){
        this(x,y,size,size,color);
    }
    public ShapeRect(int x, int y, int width, int heigh, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigh = heigh;
        this.color = color;
    }
    public void chase(Player player){
        int dx = this.x - player.x;
        int dy = this.y - player.y;
        // 순간이동해서 바로 따라감 ㅠㅠ
        //this.x+=dx;
        //this.y+=dy;
        double distance = Math.sqrt(dx*dx+dy*dy);//루트 // 피타고라스 정릴로 두 객체의 거리구하기
        double mx =dx/distance*speed;
        double my =dy/distance*speed;
        this.x-=mx;
        this.y-=my;
    }
    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillRect(x,y,width,heigh);
    }

}

class Player{
    int x,y;
    int r=50;
    int speed=20; //이동속도
    Color color= new Color(30, 178, 180);
    public Player(int x,int y){
        this.x=x;
        this.y=y;
    }
    //key 이벤트를 받아서 움직임을 정의 (키 이벤트의 콜백함수)
    public void move(int keyCode){
        // a w s d
        switch (keyCode){
            case KeyEvent.VK_W : y-=speed; break;
            case KeyEvent.VK_S : y+=speed; break;
            case KeyEvent.VK_D : x+=speed; break;
            case KeyEvent.VK_A : x-=speed; break;

        }
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x,y,r,r);
    }
}


//복수의 도형을 그리고 움직이는 그래픽 예제
//1JFrame+JPanel(canvas)
public class L54MultiCanvas extends JFrame {
    class MyPanel extends JPanel{
        List<ShapeRect> shapeRectList = new ArrayList<>();
        Player player;
        Timer chaseTimer; // 블럭이 플레이어를 쫓아다니는 타이머 ㄷㄷ;;
        public MyPanel(){
            this.setBackground(new Color(38, 227, 20, 208));
            ShapeRect rect = new ShapeRect(20,20,100,70,Color.PINK);
            ShapeRect rect1 = new ShapeRect(150,150,120,Color.MAGENTA);
            ShapeRect rect2 = new ShapeRect(200,250,110);
            shapeRectList.add(rect);
            shapeRectList.add(rect1);
            shapeRectList.add(rect2);
            player=new Player(215,420);
            this.setFocusable(true);
            this.requestFocusInWindow();
            chaseTimer = new Timer(10,(a)->{
               for (ShapeRect shapeRect : shapeRectList){
                   shapeRect.chase(player);
               }
               repaint(); // 리페인트
            });
            chaseTimer.start();

            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    player.move(e.getKeyCode());
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            player.draw(g);
            for(ShapeRect shapeRect: shapeRectList){
                shapeRect.draw(g);
            }

//            g.setColor(new Color(200,100,100));
//            g.fillRect(0,0,50,50);
//            g.setColor(Color.WHITE); //255,255,255
//            g.fill3DRect(100,100,100,100,false); //사각형 버튼처럼 눌리거나 나와 있어보이게 출력
//            g.fill3DRect(210,100,100,100,true);
        }
    }
    private L54MultiCanvas(){
        super("멀티 도형 캔버스");
        MyPanel canvas=new MyPanel();
        this.setContentPane(canvas);
        this.setBounds(0,0,500,500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame = new L54MultiCanvas();
        });
    }
}
