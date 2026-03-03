package com.smu8.study;

import java.util.Scanner;

public class ScannerEx {
    public static int sal(int c, int d){
        return c*d;
    }
    public static int mul(int a, int b) {
        return a - b;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int a = scan.nextInt();
        for (int i = 0; i < a  ; i++) {
            System.out.println("Hello");
        }

        scan.close();
    }
}


