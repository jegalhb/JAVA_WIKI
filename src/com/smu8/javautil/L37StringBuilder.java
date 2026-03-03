package com.smu8.javautil;

public class L37StringBuilder {
    static void main() {
        // 문자열이 불변성을 가지기 때문에 문자열 누적시 문제가 발생
        String str = "";
        long start = System.nanoTime();
        for (int i = 0; i <1_000_00 ; i++) {
            str+=i;
        }
        long End = System.nanoTime();
        System.out.println(End-start);
        //System.out.println(str);
        start = System.nanoTime();
        StringBuilder sb=new StringBuilder(); // "" // 누적시키는 문자열일 시 빌더를 쓰면좋음
        for (int i = 0; i < 1_000_000; i++) {
            sb.append(i); // == str+=i;
        }

        End = System.nanoTime();
        System.out.println(End-start);


    }
}
