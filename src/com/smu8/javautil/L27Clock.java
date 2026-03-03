package com.smu8.javautil;

import java.time.LocalTime;

class ClockTread extends Thread{ // 상속
    @Override
    public void run() { // 스레드가 할 일을 미리 정한다 콜백함수! 호출되서 실행되는 함수
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(LocalTime.now());
        }
        }
}
class Thermo implements Runnable{ // 인터페이스
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("현재온도는 -67도");
        }
    }
}
public class L27Clock {
    static void hygro(){
        while (true){
            try {
                Thread.sleep(1000);
                System.out.println("현재 습도는 98%");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void main() throws InterruptedException {
        Thread clockThread = new ClockTread();
        clockThread.start(); // 스레드 생성 후 재정의한 run을 실행시킴
        //System.out.println("스레드가2개면 실행됨");
        Thread thermo= new Thread(new Thermo()); // 스레드 만들어서 해보기
        thermo.start();

        Thread Hygro = new Thread(()->hygro());
        Hygro.start();



        /*while (true){ // Thread클래스로 재정의
            System.out.println(LocalTime.now());
            Thread.sleep(1000);
        }
        while (true){ // Run
            System.out.println("현재 습도는 ..");
        }               */




    }
}
