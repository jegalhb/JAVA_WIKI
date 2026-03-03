package com.smu8.homework;

public class H08Switch {
    public static void main(String[] args) {

        int x = 5;
        String result = "실행 안됨";
        if (x % 2 == 0) {
            result = "짝수";
        }
        System.out.println(result);
        // 실행안됨
        /*int score = 75;
        String grade = "";

        if (score>=90) {
            grade= "A";
        }else if (score>=80) {
            grade="B";

        } else if (score>=70) {
            grade="C";
        }else {
            grade = "F";

        }
        System.out.println(grade);

         */

        int n = 2;

        switch (n) {
            case 1:
                System.out.println("A");
                break;
            case 2:
                System.out.println("B");
            case 3:
                System.out.println("C");
                break;
            case 4:
                System.out.println("D");

        }
        int score = 65;
        switch (score/10) {
            case 10,9:
                System.out.println('A');
                break;
            case 8:
                System.out.println('B');
                break;
            case 7:
                System.out.println('C');
                break;
            default:
                System.out.println('F');
                //score /5 로 a+b+점수 구현 ㄷㄷ
        }
        int age = 17;
        String ming = (age >= 20) ? "성인" : "미성년자";
        // (조건) ?(맞아?) "그럼 이거 참조해" :(아니면?) "이거 참조해"
        System.out.println(ming);
        n = 0;
        String siu = (n > 0) ? "양수" : (n < 0) ? "음수" : "0";
        System.out.println(n);

        score = 55;
        String boy = (score >= 60) ? "합격" : "불합격";
        System.out.println(boy);

        int day = 2;
        String Moon;
        switch (day) {
            case 1 -> Moon = "월";
            case 2 -> Moon = "화";
            case 3 -> Moon = "수";
            default -> Moon = "기타";
        }
        System.out.println(Moon);
        int sung = 4;
        String T;
        switch (sung) {
            case 1:
                System.out.println(10);
                break;
            case 2:
                System.out.println(20);
                break;
            case 3:
                System.out.println(30);
                break;
            default:
                System.out.println("나가");
        }
        /*int y = 3;
        String YUNG;
        switch (y){
            case 1 :
                System.out.println("일");
            case 2:
                System.out.println("이");
            case 3:
                System.out.println("삼");
// Switch
         */
        /*int y = 3;
        String YUNG;
        if (y == 3){
            System.out.println("삼");
    } else if (y==2) {
            System.out.println("이");
        } else if (y==1) {
            System.out.println("일");
        }
// if else 방식
         */
        int y = 3;
        String YUNG = (y==3) ? "삼" : (y==2) ? "이" :"일";
        System.out.println(YUNG);
        // 삼향연산자
        int a= -10;
        if(a>0){
            System.out.println("안녕");
        }if (a<0){
            System.out.println("잘가");
        }
    }
    }