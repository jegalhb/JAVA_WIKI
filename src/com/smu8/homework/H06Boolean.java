package com.smu8.homework;

public class H06Boolean {
    public static void print() {
        char ch = '0';
        for (int i = 0; i < 10; i++) {
            System.out.printf("%c %d\n", (char)(ch + i), ch + i);
        }
    }
    public static void main(String[] args) {

        int a = 3;
        System.out.println(a>=1 || a<5);
        char ch = '7'; // 0~127
        print();
        System.out.println();

        System.out.println(a>=48 && a<=57);
        System.out.println();
        System.out.println(a>='0' && a<='9');
// 0 1 2 3 4 5 6  7 8 9

    }
}
