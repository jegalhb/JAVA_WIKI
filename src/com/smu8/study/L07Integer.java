package com.smu8.study;

public class L07Integer {
    public static void main(String[] args) {
        //정수 : 소수점이 없는 수 - 22 , 0 , 122
        //정수 기본형 데이터 타입 (정수 데이터를 참조하는 타입)
        // 정수의 대표적 : int , long , byte , short
        // byte : 1byte 크기의 정수
        // short : 2byte 크기의 정수 -32768~32767
        // int : 4byte 크기의 정수 -21억~21억 , -2147483648 +2147483647 *** 정수를 입력하면 무조건 int
        // long : 8byte 크기의 정수
        byte b=111; // 1byte = 256개의 경우의 수를 가짐.
        // b=256;
        b=127; // 양수 127개 음수  128개를 각각 나눠가진다, 양수에서 0을 가져갔기에 127+0인것
        b=-128;
        short s= 32767;
        int i=2147483647;
        long l=1234567890L;
        // long l=12_3456_7890L; // 긴 숫자 _로 나눠줄 수 있음.
        System.out.println(333); // 크기는 short지만 정수를 입력하면 무조건 int ****

        //정수는 수학적 연산이 가능 (+-*/)
        System.out.println(i*s);
        System.out.println(i-s);
        System.out.println(10%3); // 10/3 10을 나눈 나머지
        System.out.println(10/4); // 10/2 = 5인데 2 (정수간 연산을 정수로 반환)
        System.out.println(10/4.0);
        System.out.println(1.1+1.2 == 1.3); // 부동소수점오차 예시
        System.out.println(1.1+1.2 >1.1);
        System.out.println(4/0);
    }
}
