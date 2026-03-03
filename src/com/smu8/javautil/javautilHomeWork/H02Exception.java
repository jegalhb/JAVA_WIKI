package com.smu8.javautil.javautilHomeWork;


import java.util.Scanner;

public class H02Exception {
    static void setScore(int score){
        if (score<0 || score>100){
            throw new IllegalArgumentException(score + "는 0~100사이여야 한다");
        }else
            System.out.println(score + "입니다~");
    }
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            int x = 10/0;
        }catch (ArithmeticException e){
            System.out.println("child");
        }catch (Exception e){
            System.out.println("parent");
        }
        try {
            setScore(95);
        }catch (Exception e){
            System.out.println("오류!!!");
        }
    }
}
