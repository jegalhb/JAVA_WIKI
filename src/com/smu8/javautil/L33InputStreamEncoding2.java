package com.smu8.javautil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class L33InputStreamEncoding2 {
    static void main() {
        byte [] bytes = new byte[20];
        int i = 0;
        while (true){
            try {
                int input=System.in.read(); // input 스트림입니다! == byte단위로 처리한다!!!!
                bytes[i++] =(byte)input;
                if (input == '\n') break;
            } catch (IOException e) {
                e.printStackTrace(); // 화면에 오류 내용을출력
            }
        }
        System.out.println(Arrays.toString(bytes));
        String str = new String(bytes , Charset.defaultCharset()); // 터미널의 인코딩 디폴트(기본값)값을 그대로 사용한다는 것
        System.out.println("입력한 문자열 :" + str);
    }
}
