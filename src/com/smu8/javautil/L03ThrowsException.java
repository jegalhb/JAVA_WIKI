package com.smu8.javautil;

import java.util.Scanner;

public class L03ThrowsException {

    public static void setAge(int age) throws Exception{ // 예외를 강제하라!(예외 위임)
        // 나이는 0부터 140까지 ;;
        int birth = 0;

        if (age-birth>=0 && age-birth <=140){
            System.out.println("당신의 나이는 : " + age  + "입니다");
        }else {
            throw new IllegalArgumentException("나이는 0~140만 가능혀;; ");
        }
        System.out.println("당신의 나이는 : " + age  + "입니다");
    }

    public static void tester() throws Exception{
        try {
            setAge(200000000);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("오류 처리!!");
            throw  new Exception("다시 오류발생 ㅠㅠ");
        }
    }
    public static void main(String[] args) {
        // 메인은 최종사용자이기에 예외 위임은 비추
        try {
            setAge(2000000000);
        } catch (Exception e) {
           // throw new RuntimeException(e);
            System.out.println("오류처리!!!");
        }
        try {
            tester();
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("오류처리!");
        }

    }
}
