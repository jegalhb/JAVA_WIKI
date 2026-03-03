package com.smu8.homework;

public class H09forWhile {
    public static void main(String[] args) {
        /*for (int i = 0; i <= 4; i++) {
            System.out.println(i);
        }

        int i = 0;
        while (i < 5) {
            i++;
            System.out.println("문제 2: while반복문 5>1");
        }

         */


       /* System.out.println("\n 문제3: for 출력 5~1");
        for (int t = 10; t > 0; t--) {
            System.out.println(t + ",");
        }
        for (int w = 1; w <= 10; ++w) {
            if (w % 2 == 0) {
                System.out.print(w + " ");
            }
            int a = 1;
            while (a <= 10) {
                if (a % 2 == 1) ;
                System.out.println(a + ",");
                a++;

            }
        }
        /*int q = 0;
        while (q <= 10) {
            if (q == 5) {
                continue;
            }
            q++;
            System.out.println(q + ",");

         */
        /*for (int j = 1; j <=10 ; j++) {
            if (j==5){
                continue;
            }
            System.out.println(j+",");
        }
        for (int j = 1; j <=20; j++) {
            if (j==7){
                break;
            }
            System.out.println(j+",");
        }

         */
       /* int v = 1;
        while (v <= 10) {
            if (v == 3 || v == 6) {
                v++;
                continue;
            }
            System.out.println(v + " ");
            v++;

            int i = 1;
            while (i <= 10) {
                if (i == 3 || i == 6) {
                    i++;
                    continue;
                }
                System.out.print(i + " ");
                i++;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                continue;
            }
            System.out.println(i);
        }
        int i = 0;
        while (true) {
            if (i == 3) {
                break;
            }
            System.out.println(i);
            i++;
        }

        */
        //1부터 숫자를 출력하다가
        //총합이 20을 초과하면 1+2+3+4+5+ = 20 펑~
        //반복을 종료하도록 작성하시오.
        //(while + break 사용)
     int i =1;
     int sum=0;
     while (true){
         sum+=i;
         System.out.println(i+",");
         if(sum>20){
             break;
         }
         i++;
     }


    }
    }