package com.smu8.study;

public class L13if {
    public static void main(String[] args) {
        // 어플 : 양수와 음수를 다르게 처리함
        int a = 199;
        String msg = "a는 음수입니다";
        if (a < 0) {
            msg = "a는 양수입니다";

        }
        System.out.println(msg);

        a = 188;
        msg = "";
        if (a>0) {
            msg = "양수";

        } else {
            msg = "음수";
        }
        System.out.println(msg);
        // a==0일때를 찾지 못함
        // 분기가 2개를 초가하는 복잡한 상태를 else if로 처리

        a=0; // +0 -0 둘다 0
        msg="";
        if (a>0) {
            msg = "양수";
        } else if (a==0) {
            msg = "제로";
        }
        else {
            msg ="음수";
        }
        System.out.println(0.0==-0.0);

        int birth=2008;
        int year=2026;
        int age = 0;
        age = year-birth;
        msg=""; // 만 19세 이상은 주류 구입 가능, 미만은 불가
        if (age>=19){
            System.out.println("주류 구입 가능");
        } else{
            System.out.println("주류 구입 불가능");
        }
        int n = 7777;
        msg= "";
        if (n%2==0){
            msg = "n은 짝수";
        }else {
            msg = "n은 홀수";
        }
        int score = 88;
        msg= "";
        if (score>=90){
            msg = "score가 우수";
        } else if (score>=70) {
            msg = "score가 보통";
        }else {
            msg = "score가 미흡";
        }

        int month = 3;
        if (month>=3 && month<=5);{
        msg = "봄";
            }
    }
}