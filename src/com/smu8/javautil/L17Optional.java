package com.smu8.javautil;

import java.util.Optional;

public class L17Optional {
    static void main() {
        //orElse(기본값이 간단할때)orElseGet(기본값을 생성해야 할 때 == 비용이든다)
        //Optional<String> strOpt=Optional.of("안녕");
        //String str = strOpt.get(); // 물어보지않고 가져오기 (권장x) == null일 가능성이 있기때문 NoSuchElementException
        //System.out.println(str);

        Optional<String> strOpt= Optional.empty(); // 정수를 담는 상자 strOpt라는 상자를 만들엇는딩 아직 empty야

        String str=strOpt.orElse("안농"); // strOpt의 값이 empty면 "안농"을 str에 넣는다
        System.out.println(str);

        Optional<Integer> intOpt = Optional.empty(); // 정수를 담을 수 있는 상자 intOpt를만들었는데 값은 아직 없음
        //System.out.println(intOpt.get());
        Integer i = intOpt.orElseGet(()->{
            //통신, 파일 , 계산 . .. . . -> 기본값을 생성
            return 0; // 값이 empty면 0을 반환해서 i에 넣겟다
        });
        System.out.println(i);


        /// orElseThrow : 없으면 오류! throw오류를 생성 , throws 오류 처리를 위임
        /// 404 : NOT FOUND 페이지가 없다. orElseThrow 구현됨
        Optional objOpt=Optional.empty();
        // Object obj = objOpt.orElseThrow();NoSuchElementException
        //
        try {
            Object obj = objOpt.orElseThrow(()->{
                return new IllegalArgumentException("받아온 데이터가 잘못됨");
            });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}
