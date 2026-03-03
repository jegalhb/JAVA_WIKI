package com.smu8.javautil.javautilHomeWork;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class L35AutoClose {
    static void main() {
        // try(객체생성)해당 객체의 close를 finally에서 자동으로호출하는것
        //입출력 ,통신 -> closeable를구현하고 있는 모든 클래스가 가능
        try(InputStreamReader isr = new InputStreamReader(System.in)){
            int input=0;
            String str="";

            while((input = isr.read())!='\n'){
                str+=(char)input;
            }
            System.out.printf("입력한 문자열 : %s" , str);
        } catch (IOException e) {
            e.printStackTrace();
        }// 자동으로 finally isr.close()를 실행해준다
    }
}
