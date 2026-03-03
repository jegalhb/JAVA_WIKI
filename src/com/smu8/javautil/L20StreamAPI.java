package com.smu8.javautil;

import java.util.stream.Stream;

public class L20StreamAPI {
    static void main() {
        //API == 약속과 규칙! == 어플의 규칙
        //StreamAPI 반복문의 규칙
        Stream<Integer> intStream=Stream.of(10,20,30,40);
        // 10 20 30 40 배열과 유사한 데이터 타입 + 반복처리를 빠르게할 흐름(stream)이 존재
        intStream.forEach((i)->{
            System.out.println(i);
        });
        //스트림은 한번 사용하면 더이상 사용불가 == 반복자 Iterator도 한번만 가능
        intStream.forEach((i)->{
            System.out.println(i);
        });
        // 흐름이 있는 데이터는 보통 사용후 close로 닫는다 (입출력도 stream으로불림!)
    }
}
