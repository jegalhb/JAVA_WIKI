package com.smu8.study;

public class L06Constant {
    public static void main(String[] args) {
        int i=10;
        i=20;
        i=30;
        // 변수 : 계속 다른 데이터를 참조할 수 있는
        // 상수 : 바뀌지 않는 : 불변
        final int a=100;
        // a=200; : 컴파일 오류
        final double PI=3.14; // 이건 상수라는 뜻으로 개발자에게 알려주기 위하여 대문자+스네이크문법을 사용
        // pi : 33.14; // 원주율은 바뀔 수 없다.

        //변수와 상수의 표기법 :

        int kmScore=95; // 카멜 표기법 낙타 혹~ "자바의 변수는 대부분 카멜 사용" 자바에서 이것만.
        int km_score=95; // 스네이크문법(소문자,파일이름,파이썬변수,폴더명 등등에 사용)
        final int KM_SCORE=95; // 대문자 스네이크 문법  (모든 프로그래밍 언어에서 상수로 사용)

        // 잘못 사용한 예시 (가능은 함 하지만 미움받음 하지마)
        final int Km_Score=95; // 절대안댐 : 스네이크 문법은 대문자로만 or 소문자로만 사용!!!!!!!!
        //윈도우에서 파일명이나 폴더명을 대소문자 + 언더바를 입력하면 (os가 대소문자 구별을 잘 못하기에 잘 찾지 못함)
        int KmScore=95; // 파스칼표기법 (class 이름에서만 사용)
        // int int=10; // int double class if while 등 예약어는 이름으로 사용 불가
        //int class=10; 예약어 사용 불가 예시 3개
        //int public=10;
        //int static=10;

        int 경민의성적=95; // 영어로만 사용하기를 권장!! 가능은함;

    }
}
