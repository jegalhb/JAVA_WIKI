package com.smu8.game;

import java.util.Scanner;

public class L07GUGUDAN {
    public static void print(Scanner scanner) {
        System.out.print("단 입력 렛츠고 : ");
        int DAN = scanner.nextInt();
        int sum = 0;

        if (DAN == 0) {
            return;
        }

        if (DAN >= 0 && DAN <= 9) {
            for (int i = 1; i <= 9; i++) {
                System.out.println(DAN + " X " + i + " =" + (DAN * i));
                sum += DAN*i;
            }
        } else {
            System.out.println("잘못된 구구구 단 입니다");
        }
        System.out.println("총합은" + sum);
        print(scanner);
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // print(scanner);
        int sum = 0;
        while (true) {
            System.out.print("단 입력 렛츠고 : ");
            int DAN = scanner.nextInt(); // sum = 45
            // sum = sum; //
            // sum+= sum; // sum+sum
            sum += 0;

            if (DAN == 0){
             break;
            }

            System.out.println(DAN >= 0);
            if (DAN >= 0 && DAN <= 9) {
                for (int i = 1; i <= 9; i++) {
                    System.out.println(DAN + " X " + i + " = " + (DAN * i));
                    sum += DAN * i;
                }
            } else {
                System.out.println("잘못된 구구구 단 입니다");
            }
            System.out.println("총합은" + sum);
        }
    }
}