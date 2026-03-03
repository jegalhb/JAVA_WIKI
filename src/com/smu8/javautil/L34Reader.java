package com.smu8.javautil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class L34Reader {
    static void main()   {
        // Reader + close
        // 입출력은 스트림이 있어서 사용완료시 close로 닫아줘야한다!
        InputStreamReader isr =null;

        try {
            isr = new InputStreamReader(System.in, Charset.defaultCharset());
            //int input =isr.read(); // 한글자씩 처리! 인코딩으로 바이트의 수를 정해서 처리한다.
            // inputstream 은 1바이트씩 처리 , InputStreamReader는 한글자! 1~4바이트
            //System.out.println(input); // 안 : 50504 경 : 44221
            //System.out.println((char) input);
            //System.out.printf("입력한 정수 %d , 문자 %s",input,(char)input);
            String str ="";
            while (true){
                int word=isr.read();
                System.out.print(word+",");
                str+=(char)word;
                if (word=='\n')break; // 통신의 끝에는 항상 -1
            }
            System.out.println();
            System.out.println("입력한 문자열 : " +str);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (isr!=null) isr.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
