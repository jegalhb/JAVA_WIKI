package com.smu8.study;

public class L10BitOperetor {
    public static void main(String[] args) {
        // 정보처리기사 시험에 꼭나옴
        // 비트연산자
        // >> << 쉬프트 연산
        int a = 8; // 0000 1000 (2진수)
        a=a<<1; // 0001 0000 (2진수 Binary)
        System.out.println(a);
        a<<=2;  // 0010 0000
        System.out.println(a);
        a>>=6;
        System.out.println(a);

        a=29; //0001 1101
        System.out.println(Integer.toBinaryString(a));
        // Integer = int를 도와주고 int의 자료형 데이터(랩퍼클래스)
        a>>=2; // 0000 0111?
        System.out.println(a);

        a=-2;
        System.out.println(Integer.toBinaryString(a));
        // 11111111111111111111111111111110
        a>>=2;
        System.out.println(a); // 음수의 나머지는 1

        // "~" 보수 not연산
        // 1111->0000 , 0100 -> 1011

        System.out.println(~5); // 0101 -> 1010
        System.out.println(~0); // 0000 0000 -> 1111 1111
        System.out.println(~578945612); //-578945613

        //비트 논리연산 &(곱) |(합)
        // 1&1 = 1
        // 1&0 = 0

        // 1+1 = 2
        // 1+0 = 1

        // 1|1 = 1
        // 1|0 = 1
        // 0|0 = 0

        //1^1 => 0
        //0^0 => 0
        //0^1 => 1
        //1^0 => 1
        // 베타적 논리합 = 같아야 무조건 0 다르면 1 // 서로 배타적으로 다르면 참이다.

        int i=7; // 0111
        int j=8;  // 1000
        System.out.println(i|j); // 15 = 0000 1111

        j=11; // 1011
        System.out.println(j|i); // 0000 1111

        System.out.println(i&j);
        System.out.println(7&8);
        System.out.println(Short.MAX_VALUE);

    }
}
