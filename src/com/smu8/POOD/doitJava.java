package com.smu8.POOD;

import poolsandwich.example.outer.side.inner.A;

public class doitJava {
    class score{
        score A = new score();
    }
    static class Array{
       int [] a = new int[10];
    }

    static void main(String[] args) {
        int Value=5;
        System.out.println(Value|2);
        Boolean A = true;
        Boolean B = false;
        Boolean C;
        if (B==!A){
            System.out.println(B);
        }else {
            System.out.println("A");
        }

        C = (B==!A ? true:false);
        System.out.println(C);
        if (C == false){
            System.out.println(A==!B);
        }else {
            System.out.println(C+"True");
        }
        int score = 6;
        switch (score){
            case 10-> System.out.println("pass");
            case 9-> System.out.println("pass");
            case 8-> System.out.println("pass");
            case 7-> System.out.println("pass");
            default -> System.out.println("fail");
        }
        System.out.println();

        for (int i = 0; i < 200; i++) {
            if (i==100){
                System.out.println("100의 분기점");
            }else {
                System.out.println(i);
            }
        }
        System.out.println();
        while (A){
            System.out.println("왕마장여");
            break;
        }
        System.out.println(A);
        while (score<0){
            score=7;
            switch (score){
                case 10-> System.out.println("pass");
                case 9-> System.out.println("pass");
                case 8-> System.out.println("pass");
                case 7-> System.out.println("pass");
                default -> {
                    System.out.println("fail");
                    score++;
                }
            }
            System.out.println();
        }
        Array array = new Array();

       }
    }
