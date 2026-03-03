package com.smu8.study;

public class L04DataType {
    public static void main(String[] args){
        //data: 기본형, 자료형or 참조형이 있음.
        //기본형 데이터 : 기본적인 데이터를 의미. (컴퓨터 : 수로된 데이터, 문자=수 인 이유=(문자표의 번호를 참조하기 때문 )
        //참조형(자료형) : 참조(자식이 있는 ex- 필드접근자(.))하는 데이터가 있는 것을 의미.
        System.out.println("안녕하세요!"); // 자료형 , 문자열 String
        System.out.println('a'); // 문자 char(character)
        System.out.println((int)'a'); // 문자 char(character)
        System.out.println((int)'A');
        System.out.println((int)'안');
        // 자바는 문자 2byte -> 16bit utf(유니코드)-16 (utf-8,utf-16은 국제표준 문자표로 서로 호환가능)
        // 아스키코드 만든 이유 : 컴퓨터가 수만을 처리 하기 때문에 문자를 수로 표현
        // 이미지,영상,소리 등 세상의 모든 것을 데이터화할 수 있다.
        System.out.println(137); // int(정수) = 4byte
        System.out.println(1234567891234567891L); // 큰 정수 long = 8byte
        System.out.println(13.121212); // 실수 double = 8byte
        System.out.println(13.121212f);// 실수 float  = 4byte
        System.out.println(1==1); // 비교연산 ( 두개가 같은가)
        System.out.println(1!=1); // 1!= : 뜻은 둘은 다르지?
        // boolean = () true (1) , !false (0) , boolean은 1bit 데이터지만 크기는 1byte(메모리를 바이트로 나누기 때문)
        //  ||||||||||||
    }
}
