package com.smu8.homework;

public class H07if {
    public static void main(String[] args) {
        int x = 5;
        String result = "실행안됨";
        if (x % 2 == 0) {
            result = "짝수";
        }
        System.out.println(result);

        int score = 75;
        String grade = "";
        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60)
            grade = "D";
        System.out.println(grade);

        int n = 4;
        String INT = "";
        if (n % 2 == 0) {
            System.out.println("짝수");
        } else {
            System.out.println("홀수");
        }

        if (false) {
            // 참일 때
        } else {
            // 거짓일 때
        }
        int scort = 55;
        if (scort >= 90){
            System.out.println("우수");
        } else if (scort>=70) {
            System.out.println("보통");
        } else if (scort>=60) {
            System.out.println("미흡");
        } else{
            System.out.println("나가");
        }
        // 위랑 아래랑 같은 조건문입니다.
        if (scort >= 90){
            System.out.println("우수");
        } else {
            if (scort >= 70) {
                System.out.println("보통");
            } else {
                if (scort >= 60) {
                    System.out.println("미흡");
                }else {
                    System.out.println("나가");
                }
            }
        }

    }
}