package com.smu8.javautil;

public class L30Synchronized {
    // static : 컴파일러가 코드를 분석하면서 만들어놓고 삭제하지않음 -> 공유자원
    public static int count = 0;
    public static Object ROCK = new Object(); // 스레드가 참조하는 동안 다른 스레드가 참조하지 못하도록 잠거버리는것

    static void main(String[] args) throws InterruptedException {
        Thread  T1 =  new Thread(()->{
            for (int i = 0; i <10_000_000 ; i++) {
                synchronized (ROCK){
                    count++;
                }
            }
        });
        Thread T2 = new Thread(()->{
            for (int i = 0; i < 10_000_000; i++) {
                synchronized (ROCK){
                    count++;
                }
            }
        });
     T1.start();
     T2.start();

     T1.join(); // 위 스레드가 끝날 때 까지 기다립니당 == 스레드 동기화
     T2.join();
        System.out.println(count); // 원하는 숫자가 나오지 않음 == 스레드가 동시에 참조하여 증가시키기 때문에 문제가 발생

    }
}
