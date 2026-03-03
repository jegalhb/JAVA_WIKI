package com.smu8.study;

public class L11BooleanOperater {
    public static void main(String[] args) {
        // 논리연산자
        // == 같다
        // 같지않다 !=
        // 크기를 비교하는 연산자 (값을 비교=>수만가능)
        // 크다 >
        // 작다 <
        // 크거나 같다 >=
        // 작거나 같다 <=
        // 반대 : !

        int a = 0;
        double d= 0.0;
        String s= "0";
        // System.out.println(s>a); / 크기를 비교하는 연산은 기본형만 가능
        // ystem.out.println(a==s); / ==은 자료형도 비교 가능
        // 기본형과 자료형 비교는 당연히 거짓이기에 오류
        System.out.println(s=="0");
       // System.out.println(s<="1");
        System.out.println(a==d);
        // 기본형의 비교는 값(크기)을 비교한다. (데이터가 각각 달라도 비교하지 않는다)
        System.out.println(8==8.0);
        boolean b=10>3;
        System.out.println(!b);

        System.out.println("1"=="1"); // true
        a=10;
        d=9.0;
        System.out.println(a==d); // false
        System.out.println(a>d); // true
        System.out.println(a!=d); // true

        d=10.0;
        a=10;
        System.out.println(a==d); // true
        System.out.println(a!=d); // false
        // 자바는 논리형의 수학적 연산을 하지 못하게 한다.
        // true : 1
        // false : 0

        // 논리 연산 = &&(곱) ||(합)
        System.out.println(true && true); // true
        System.out.println(false && true); // false
        System.out.println(false && false); // false

        // || : 하나라도 true가 있으면 true
        System.out.println(true || true); // true \ 1+1 = 1
        System.out.println(true || false); // true
        System.out.println(false|| false); // false
        // && : 하나라도 false가 있으면 false
        System.out.println(false||false||false||false||true);
        System.out.println(false&&false&&false&&false&&true);

        System.out.println(false || false || true || false || false);
        a=0;
        System.out.println(++a>10 || ++a>10 || ++a>1 ||++a>0 || ++a>0);
        // 결과와 a는 몇일까요 true 3~
        System.out.println(a);
        System.out.println((int)'a');
        System.out.println(Integer.toHexString(40032));
        System.out.println(Integer.toHexString(44032));
        System.out.println("두려워요");
        char u ='\uAAAA';

    }
}
