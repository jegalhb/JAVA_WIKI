package com.smu8.study;

public class L12Char {
    public static void main(String[] args) {
        char c= 'a'; // "" ==큰따음표 , '' == 작은 따음표
        System.out.println(c);
        c= 77;
        System.out.println(c); // 77은 아스키에서 M
        c=17891;
        System.out.println(c);
        c=14415;
        System.out.println(c);
        c=1;
        System.out.println(c);

        // 17891은 UTF 16에서 䗣
        // c=111111; 문자는 16비트 정수기 때문에 더 큰 숫자 표기 불가능
        // c='🤢';
        // 자바는 고정길이 utf-16이기 때문에 4byte 크기의 이모지는 문자로 사용불가
        // 문자열로는 사용가능
        String s="이모지는 4byte기 때문에 문자열로 사용 : 🤢 "; //surrogate Pair
        System.out.println(s);
        c='\u0041'; // 0041 == 65(10) , u= 유니코드
        System.out.println(c);
        c='\u9999';
        System.out.println(c);
        c='\uA9FC';
        System.out.println(c);


    }
}
