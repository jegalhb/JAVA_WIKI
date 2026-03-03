package com.smu8.javautil.javautilHomeWork;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class H03Exception {
    class A{ }
    class B extends A{}
    class C extends A{}
    void castTest(){
        A b = new B();
        A c = new C();
        C castC = (C)b;
    }
    static void main() {
        //배열 , 형변환 , casting
        H03Exception ex = new H03Exception();
        //ex.castTest();
        System.out.println("오류 발생시 jvm종료");
        // 오류인데 if로 처리할 수 있는 것 : 개발자가 처리할 수 있는 오류
        Object o = "문자열";
        if (o instanceof Date){
            Date d = (Date) o;
        }
        // 통신객체 : 개발자가 처리할 수 없는 오류 (예외를 강제)
        Socket socket = new Socket();
        try {
            socket.getInputStream(); // throws IOException
        } catch (IOException e) {
            System.out.println("통신 중 예외가 발생"); // 로그 , DB , 유저에게 알림
        }
        /*int nums [] = {10,20,30};
        nums [4] = 50;
         */
        System.out.println("10/0");
    }
}
