package com.smu8.javautil.javautilHomeWork;

import java.time.LocalTime;

public class H05Thread {
    static int count = 0;
    static final Object lock = new Object();


    static void main() throws Exception {
        //// 문제 1번
        Thread T = new Thread(()->{
            System.out.println("작업");
        });
        T.start();

        // 문제 2번
        Thread T2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (lock) {
                    count++;
                }
            }
        });
        Thread  T3 = new Thread(()->{
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (lock){
                    count++;
                }
            }
        });
        T2.start();
        T3.start();
        T2.join(); // 다른 스레드 끝날 때까지 기다리는 거
        T3.join();
        System.out.println("count :" + count);
        // 문제 3번
        Thread clock = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000);
                    System.out.println(LocalTime.now());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        clock.start();
        System.out.println("다음 코드 실행");
    }
}