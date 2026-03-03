package com.smu8.oop;

// class 데이터가 아니다 (객체의 형 type)
class StaticTest{
    int a = 10;
    void call(){
        System.out.println("안녕");
    }
}

class StaticTest2{ // class 는 데이터가 아니지만
    static int a=10000; // static으로 선언된 필드나 함수는 class와 별개로 데이토로 존재
    static void call(){
        System.out.println("잘가~!");
    }
}

public class L09Static {
    // main : jvm 호출
    // public : jvm 오픈
    // static : jvm 이미 생성됨
    // void : 어플은 실행이 반환
    // String [] args : 프로그램이 실행하기 위한 필요 초기 조건
    public static void main(String[] args) {
        // System.out.println(StaticTest.a); // 클래스는 데이터가 아니기 때문!!
        // System.out.println(StaticTest.call);
        StaticTest s = new StaticTest();// Class로 객체 생성 (데이터)
        System.out.println(s.a);
        s.call();


        System.out.println(StaticTest2.a);
        StaticTest2.call();
    }
}
