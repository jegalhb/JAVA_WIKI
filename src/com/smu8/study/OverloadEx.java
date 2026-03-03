package com.smu8.study;

public class OverloadEx {
    public static int add(int a, int b) {
        return a + b;
    }
    public static int add(double a, double b) {
        return (int) (a + b);
    }
    public static void main(String[] args) {
        System.out.println(add(4, 3));
        System.out.println(add(4.5, 3.2));

    }
}
