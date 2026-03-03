package com.smu8.study;

public class L25ArrayError {
    public static void main(String[] args) {
        //오류 : 컴파일 오류 , 런타임 오류
        // 컴파일 오류 : javac가 오류를 발견해서 미리조치
        // 런타임 오류 : 컴파일러가 발견하지 못해서 실행 중 발생하는 오류
        // 런타임 오류가 발생하면 프로그램이 멈춘다.
        // int i ="" // 컴파일 오류는 배포(서비스)되지 않기 때문에 안전
        int [] nums={10,20,30,40}; // 길이가 4 순서 index가 0~3


        //nums[4]=50; //배열은 처음 생선한 길이가 변경되지 않음
        //배열 색인이 범위를 벗어났습니다. : ide intellij 가 개발자에게 위험을 알림


        //System.out.println(nums[4]); // 런타임 오류
        // Exception : 오류(예외)
        //ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4
        System.out.println("오류 발생 시 코드 실행이 안됨");
    }
}
