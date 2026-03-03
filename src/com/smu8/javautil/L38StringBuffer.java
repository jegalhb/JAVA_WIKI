package com.smu8.javautil;
class StringTest extends Thread{
static Object ROCK = new Object();
    @Override
    public void run(){
        for (int i = 0; i < 1_000_00; i++) {
            /*synchronized (ROCK){
            }*/
            L38StringBuffer.str+="A";
            L38StringBuffer.sb.append("A");
            L38StringBuffer.sf.append("A");
        }
    }
}

public class L38StringBuffer {
    public static String str ="";
    public static StringBuilder sb=new StringBuilder();
    public static StringBuffer sf = new StringBuffer(); //synchronized 가 자동으로 댓


    static void main() throws InterruptedException {
        Thread t1 = new StringTest();
        Thread t2 = new StringTest();
            t1.start();
            t2.start();
            t1.join();
            t2.join();

        System.out.println(str.length());//102442
        System.out.println(sb.length());//198961
        System.out.println(sf.length());//200000
    }
}
