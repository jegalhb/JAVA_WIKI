package com.smu8.game;

import java.util.Random;
import java.util.Scanner;

public class TestRankDiscrimination {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("당신의 점수를 입력하세요 :");

        String A = "당신의 등급은: ";


        int inputScore = scanner.nextInt();
        if (inputScore >= 0 && inputScore <= 100) {
            if (inputScore >= 90) {
                System.out.println(A+"A");
            } else if (inputScore >= 80) {
                System.out.println(A+"B");
            } else if (inputScore >= 70) {
                System.out.println(A+"C");
            } else
                System.out.println(A+"F");
        } else {
            String MSG = "잘못된 점수입니다";
            System.out.println(MSG);
        }
    }
}