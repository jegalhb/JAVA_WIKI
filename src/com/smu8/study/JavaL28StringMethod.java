package com.smu8.study;

import java.util.Arrays;

public class JavaL28StringMethod {
    public static void main(String[] args) {
        String s= "안농";
        char [] cArr = {'안','녕'};
        // 문자로된 열 == 문자열
        // c ==cArr : false
        // 자료형간의 == 비교 : 두개가 동일한 데이터냐? , 주소가 같냐?
        // System.out.println(s==cArr); 물리적으로는 같지만 주소가 달라 틀리다


        // equals == 문자열의 값을 비교 // 자료형은 equals 로 비교
        // 자료형의 == , 주소를 비교
        String s2 = "안녕";
        System.out.println(s==s2);
        String s3= new String("안녕");
        System.out.println(s==s3);

        System.out.println(s.equals(s3));
        s="hello";
        s3= new String("hello");
        System.out.println(s==s3);
        System.out.println(s.equals(s3));

        System.out.println("hello".equals("Hello"));
        System.out.println("hello".equalsIgnoreCase("Hello"));
        System.out.println("======================");

        // 대소문자 무시!!
        s="hello";
        System.out.println(s.toUpperCase());
        s=s.toUpperCase();
        System.out.println(s);
        System.out.println(s.toLowerCase());
        System.out.println("======================");

        String[] strArr = s.split("");
        System.out.println(Arrays.toString(strArr));
        s="010-4927-9412";
        String[] phoneNum= s.split("-");
        System.out.println(Arrays.toString(phoneNum));
        System.out.println("======================");
        // 특정 위치 문자 찾기.

        s= "안녕하세요 제갈입니다. 자바 수업이에오";
        System.out.println(s.contains("제갈"));
        System.out.println(s.indexOf("제갈"));
        System.out.println(s.contains("제구리")); // - flase
        System.out.println(s.indexOf("제구리")); // - 1
        System.out.println(s.substring(0,9));







    }
}
