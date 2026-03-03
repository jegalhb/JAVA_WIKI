package com.smu8.javautil;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class L26MultiThread {
    static void main() throws InterruptedException {
        // 일꾼 (Thread)를 한개 만들어서 시간출력을 시키고 나머지일꾼이 온도를 출력!!
        Thread thread = new Thread(() -> {//Runnable.run // 리턴없음
            while (true) {
                System.out.println(LocalTime.now());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }); // Thread는 만들 떄 어떤일(함수)을 시킬것인지 정해야함!
        // 버튼을눌럿을 때 어떤일을할건지 미리정하는것 (콜백함수)
        thread.start();

        while (true) {
            System.out.println("현재온도와 습도는27도/50%");
            Thread.sleep(1000);
        }
    }}
       /* Thread thread1 = new Thread(()-> {
            while (true) {
                System.out.println(LocalDateTime.now());

            }
        });
    }

        */
