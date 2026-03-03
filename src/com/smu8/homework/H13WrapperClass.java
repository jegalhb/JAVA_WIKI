package com.smu8.homework;

public class H13WrapperClass {
    public static void main(String[] args) {
        int a = 10;
        Integer b = a;
        System.out.println(b);

        Integer c = 10;
        int d = a;
        Integer e = null;
        int f = a;
        Integer g = Integer.valueOf(10);

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);

        String S = Integer.toString(500);

        String input = "1234";
        int num = Integer.parseInt(input);
        System.out.println(num * 2);


    }
}
