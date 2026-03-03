package com.smu8.oop;
@FunctionalInterface
interface  ACapture{
    void test();
    //void a();
}

public class L25Capture {
    public static void main(String[] args) {
        // 캡쳐는 지역변수의 참조하는 값을 복사해 가는 것
        // 함수가 실행되면서 지역변수가 생성됨 => 함수가 종료되면 지역변수는 소멸 (int a)
        // 객체는 만들어지면 계속 존재
        int a = 0;
        ACapture c = ()->{
            System.out.println("a :" + a);

        };
        c.test();
    }
}
