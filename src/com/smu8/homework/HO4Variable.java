package com.smu8.homework;

public class HO4Variable {
    public static void main(String[] args) {
        // 실습 1
        System.out.println("-----실습1번-----");
        System.out.println(1); // 정수 int
        System.out.println(1.1); // 실수 double , float
        System.out.println('a'); // 문자 char
        System.out.println("안녕"); // 문자열 String
        System.out.println(1==2); // 비교연산
        // 실습 2
        int i=10; //정수 변수 선언
        System.out.println(i);
        i=20;
        System.out.println(i);
        i=30;
        System.out.println(i);
        // 실습3
         var a=100; // var 변수 선언 시 타입을 명시치않고, 대입한 데이터의 타입으로 변경됨.
         var b="char";  //다시확인
        System.out.println(a+b);

        // 실습4

        String l="Hello";
        String p="Java";
        String z= "";
        int h= 10; // 정수는 수학적 연산
        int f= 20;
        System.out.println(l+p);
        System.out.println(l+h);
        System.out.println(h+z+f+l);
        System.out.println(h+(f+l)); //문자열+정수 -> + h = 정수 + 문자열

        // System.out.println('a'+'b'); "ab"가 아닌 => 195

        // 실습 5
        final double PI = 3.14;
        System.out.println(2*5*PI);
        System.out.println(2*10*PI);
        // final double PI =3.14159;
        // int r = 5 -> Syetem.out.println(2*r*PI);

        // 실습 10
        System.out.println(3*3*PI);
        System.out.println(4*4*PI);
        System.out.println(5*5*PI);
        //실습 6
        int jgScore = 95;
        final int JG_SCORE = 100;

        boolean login = true;
        System.out.println(login);

        //실습 7
        System.out.println(Integer.MAX_VALUE);
        //byte i = 1; short t = 4; int r = 21_4748_3647; long k = 22_0000_0000L;
        // System.out.println(i);
        // System.out.println(t);
        // System.out.println(r);
        // System.out.println(k);
        //System.out.println(a+b+jgScore);
        //실습 8
        int q = 10;
        int w = 12;
        int e = 14;
        int r = 16;
        System.out.println(q+w);
        System.out.println(e-r);
        System.out.println(r*w);
        System.out.println(q/r);
        System.out.println(q%r);
        byte n = (byte)128;
        //실습 9 <-- 연산자 잘못 썼었어요
        System.out.println(4/2); // + , - , * ,/ , %
        System.out.println(22/2.2);
        //실습 10
        double pi = 3.14159;
        r=3;
        System.out.println(r*r*pi);
        r=4;
        System.out.println(r*r*pi);
        r=5;
        System.out.println(r*r*pi);
// 3(4x3) 3*(4*3)(s+2(d+6)a+3)
        //실습 11
        int HbScore = 50;
        final int jgjbScore = 100;
        boolean u = (HbScore>jgjbScore); // boolean = 참 거짓
        System.out.println(u);
        System.out.print(HbScore);
        System.out.println(u);
       // String u="학생의 점수:" +jgjbScore+"비교결과 :" +u;
      //  System.out.println(u);







    }

    {

    }
    }
