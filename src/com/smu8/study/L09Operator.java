package com.smu8.study;

public class L09Operator {
    public static void main(String[] args) {
        int a = 10;
        int b = 3;
        System.out.println(a+b); //13
        System.out.println(a-b); //7
        System.out.println(a*b); //30
        System.out.println(a/b); //3
        System.out.println(a%b); //1

        // 증감연산자
        System.out.println(++a);
        // 하나 증가
        System.out.println(--b);
        // 하나 감소

        //++a를 하면 기존의 데이터 10을 11로 바꾸는 것이 아님.
        // 기존 10 데이터에서 10+1의 결과가 새로 만들어진다.
        // 이후 a가 새로 생긴 결과 11을 참조하게 되는것

        // 대입연산자 (+,-,*,/,%)
        a=a+3;
        System.out.println(a); // 14
        a+=3; // 증강 대입 연산자
        System.out.println(a); // 17
        a+=1; // == ++a
        System.out.println(a); // 18
        a-=8;
        System.out.println(a);
        a*=5;
        System.out.println(a);
        a/=50;
        System.out.println(a);
        a%=1;
        System.out.println(a);

        //증감연산자의 순서!!
        // 증감연산자가 앞(++a+2)에있으면 먼저 증가
        // 증감연산자가 뒤(a++*2)에있으면 타 연산을 먼저하고 증가
        System.out.println(a++); // println하는 것두 연산이다
        System.out.println(a);

        b=1;
        int result=++b*2; // 2 ++ = 1올라요
        System.out.println(b + " " + result);

        b=1;
        result=b++*2;  // 1
        System.out.println(b);
        System.out.println(b + " "+ result);
        System.out.println(b);

    }
}
