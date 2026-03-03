package com.smu8.game;

import java.util.Random;
import java.util.Scanner;


public class L03UPDOWN {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int answer = random.nextInt(1, 101);
        int count = 0;
        int guess = 0;
        final int MAXCOUNT = 7;
        boolean d = false;

        System.out.println("업다운 게임 시작!!!");
        System.out.println("1~100 중 알맞는 숫자를 맞춰보셔요");

        while (count < MAXCOUNT) {
            count++;
            System.out.println(count + "번째숫자 입력 : ");
            guess = scanner.nextInt();

            if (guess == answer) {
                d = true;
                break;
            } else {
                String msg = (guess < answer ? "UP" : "DOWN");
                System.out.println(msg);
            }
        }
        if (d == true) {
            System.out.println("성공!");
        } else {
            System.out.println(" 실패 ");
        }
    }
}