package com.smu8.javautil.javautilHomeWork;

import java.util.Scanner;

public class H04EXCEPTION {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("정수 입력! : ");
        String input = scanner.next();

        try {
            int nums = Integer.parseInt(input);
            int result = nums * nums;
            System.out.print(result + "입니다");
        } catch (NumberFormatException e) {
            System.out.println("숫자만 쳐입력해");
        }


        System.out.println("=============");

        System.out.println("나이를 입력하세요 :");
        String inunput = scanner.next();
        try {
            int age = Integer.parseInt(inunput);
            if (age <= 0) {
                throw new IllegalArgumentException("나이는 1이상이여야함;;");
            }
            System.out.println("입력한나이" + age);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        int[] arr = {10, 20, 30};
        System.out.println("인덱스 번호를 입력하세요~");
        String InPut = scanner.next();

        try {
            int index = Integer.parseInt(InPut);
            System.out.println("값 :" + arr[index]);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("배열의 범위에서 벗어낫엉");
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해~");
        }

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("비밀번호를 입력하세요~ :");
        String password = scanner.next();
        System.out.println(password + "입력");

        try {
            if (password.length() < 5) {
                throw new IllegalArgumentException("비밀번호는 5자 이상이여야함;;");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
