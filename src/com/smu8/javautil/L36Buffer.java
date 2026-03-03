package com.smu8.javautil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class L36Buffer {
    static void main() {
        //Scanner scanner = new Scanner(System.in);
        //scanner.nextLine();
        // 밑의 코드를 스캐너가 구현하는 것! -> 사용하기 편하게하기위해서
        try(
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new  BufferedReader(isr);
                // 버퍼로 임시저장공간에 문자열을 라인개행까지 받을 수 있다
                ) {
            String str = br.readLine(); // 라인개행이 있을 때 까지의 문자열을 받겟다!!
            System.out.printf("입력한 문자열 : %s",str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
