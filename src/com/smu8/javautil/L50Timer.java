package com.smu8.javautil;

import javax.swing.*;
import java.awt.*;

public class L50Timer extends JFrame {
    private JLabel label;
    private JButton jButton;
    private int time = 10;

    public L50Timer(){
        super("타이머");
        // Timer EDT가 주기가 있는 반복실행
        label = new JLabel(Integer.toString(time),SwingConstants.CENTER);
        this.add(label);
        jButton=new JButton("시작");
        /*jButton.addActionListener((event)->{
            new Thread(()->{
                while (true){
                    threadSleep(1000);
                    time -- ;
                    // Swing의 그래픽과 이벤트는 EDT가 제어하기에!
                    // 새로운 스레드가 Swing을 제어하는것은 권장되지 않는다 ex)반영되지 않거나 or 깜박이는 현상이 나올 수 있음
                    //label.setText(Integer.toString(time));
                    SwingUtilities.invokeLater(()->{
                        label.setText(Integer.toString(time));
                    }); // EDT에게 실행을 위임하겟다
                    if (time==0)break;
                }
            }).start();
        }); */

        Timer timer = new Timer(1000,(e)->{
            label.setText(--time+""); // invokeLater 생략가능 ㄷㄷ 해당 스레드가 EDT이기 때문;;
            //if (time==0)time.stop(); // 생성이 완료되지 않았을 때 stop하면 오류 ㄸ
            if (time==0){
                Timer t =(Timer) e.getSource();
                t.stop();
            }
        });
        timer.start();// 스레드 슬립 1000 적용되어잇음
        this.add(jButton, BorderLayout.SOUTH);
        this.setBounds(0,0,200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    //타이머를만들껀데 4방향의 값이 초당 30번검사를 하는 타이머
    /**
     * 상하좌우를 나타내는 불리언 값 네 개가 있다.
     * up, down, left, right;
     * 방향키를 누를 때 해당 방향의 불리언 값이 true가 된다.
     * 방향키를 뗐을 때 해당 방향의 불리언 값이 false가 된다.
     * 기본 값은 false다.
     * 초당 n번씩 플레이어 위치 좌표에 다음과 같이 값을 갱신한다.
     * 만약 up이 true면:
     *     플레이어.y -= 속력;
     * 만약 down이 true면:
     *     플레이어.y += 속력;
     * 만약 left이 true면:
     *     플레이어.x -= 속력;
     * 만약 right이 true면:
     *     플레이어.x += 속력;
     */

    void threadSleep(long sec){
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{ // Window 생성을 EDT에게 위임하는 것이 더 안전해용
            L50Timer timer =new L50Timer();

        });

    }
}
//타이머를만들껀데 4방향의 값이 초당 30번검사를 하는 타이머
/**
 * 상하좌우를 나타내는 불리언 값 네 개가 있다.
 * up, down, left, right;
 * 방향키를 누를 때 해당 방향의 불리언 값이 true가 된다.
 * 방향키를 뗐을 때 해당 방향의 불리언 값이 false가 된다.
 * 기본 값은 false다.
 * 초당 n번씩 플레이어 위치 좌표에 다음과 같이 값을 갱신한다.
 * 만약 up이 true면:
 *     플레이어.y -= 속력;
 * 만약 down이 true면:
 *     플레이어.y += 속력;
 * 만약 left이 true면:
 *     플레이어.x -= 속력;
 * 만약 right이 true면:
 *     플레이어.x += 속력;
 */
