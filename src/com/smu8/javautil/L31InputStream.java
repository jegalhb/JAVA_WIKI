package com.smu8.javautil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class L31InputStream {
    static void main() throws IOException {

        // 콘솔에서 입력을 받는다 : 입력받은 문자열은 인코딩 없이 BYTE단위로 처리
        // 콘솔에서 입력되는 한글 문자는 utf - 8 은 3바이트~
        System.out.println(Charset.defaultCharset()); // 콘솔창에서 어떤 인코딩을 사용하는지 알 수 있다
        //Scanner scanner = new Scanner(System.in);
        //int wordInt =System.in.read(); // 글자하나에서 바이트 한개를 가져옴
        // 입출력은 항상 오류를 동반하기에 "예외 처리"를해줘야함
        //System.out.println(wordInt);
        //System.out.println(Integer.toBinaryString(wordInt)); // 정수를 이진수의 문자열의 형태로 보여주는
        List<Integer> wordInts = new ArrayList<>();
        int word = 0;
        String str="";
        while ((word=System.in.read())!='\n'){  // 엔터가있을때까지 무한반복 -> 라인개행개입 -> 반복문 종료
            wordInts.add(word);
            char c= (char)word;
            str+=c;
        }
        System.out.println("입력한 문자열 : " + str);
        System.out.println(wordInts);
        wordInts.stream().forEach((n)->{
            System.out.print(Integer.toBinaryString(n)+",");
        });


    }
}
