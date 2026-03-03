package com.smu8.study;

public class LO3Function {
//    public static void a(){} // 함수의 이름은 고유하기에 중복될 수 없다. (단, 오버로드 제외)
    // main 함수(실행 담당==어플을 실행하기 도 함)
    public static void sum(int a,int b, int c,double d){ // a.b.c는 매개변수
        System.out.println("sum : a+b+c+d의 결과"); // 객체
        System.out.println(a+b+c+d);

    }
    public static void a(int a) { // 개발자 작성 함수 이름 객체를 사용 하기 위해서
        System.out.println("a 함수 입니다");
        System.out.println("안녕 ~");
        System.out.println("잘가 ~");

    }
    public static void main(String[] args) { //무언가 실행하기 위하여 main은 필수.
            a(); // L03Fuction 클래스 내부에서 a를 호출하기 때문에 생략가능
        System.out.println("------------------------------------------------------------------------------------");
        LO3Function.a(); // class 내부
        //"문자열" 1, 1.3 (연산 +-*/)
        System.out.println(111*111); // 정수 int
        System.out.println(111.1*111.1); // 실수 float

        // LO3Function.sum(); // sum 함수는 정수 데이터를 3개 받아야 실행 가능
        LO3Function.sum(10,20,30,40.11); // parameter(전달인자) == 매개변수
        // sum 함수를 실행하는데 10,20,30을 전달했다
        // 데이터는 여러 종류가 있다. -> 데이터(초급,고급),변수
        System.out.println("a"); // 문자 char
        System.out.println("Srting"); // 문자열

    }

    public static void a() { // a : 개발자가 작성한 함수의 이름(시그니처)
        // 함수는 실행의 집합
        System.out.println("a 함수 입니다");
        System.out.println("안녕 ~");
        System.out.println("잘가 ~");

    }
}
