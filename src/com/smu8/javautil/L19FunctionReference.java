package com.smu8.javautil;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class L19FunctionReference {
    static void main() {
        // 함수참조 :: 함수를 매개변수처럼 보이게 작성 (상상할 수 있는 만큼 생략)
        Consumer c=(s)->{System.out.println(s);};
        Consumer c2=System.out::println;

        Optional<Integer> opt=Optional.of(11); //opt라는 상자 안에 int 11넣는다
        int num=opt.orElse(0); // 값이 없으면 0을 출력한다
        opt.ifPresent(n->{System.out.println("숫자는 :" + n);
        });//opt라는 상자 안에 값이 있다면 숫자는 11을 출력하겟다
        opt.ifPresent(System.out::println);

        Optional<String> strOpt = Optional.of("777"); //strOpt라는 문자열을 받을수있는 상자에 문자열 777을 넣어준다
        int su=strOpt
        //        .map((s)->Integer.parseInt(s))
                .map(Integer::parseInt) // strOpt 상자에 값이 있다면 문자열"777"이 정수형777로 강제 형변환해서 su에 넣는다.
                .orElse(0);// 아니면 su에 0을 넣는다

        System.out.println(su);

        strOpt=Optional.empty(); // 없을때는 빈객체 new String() //strOpt가 참조하고있는 "777"을 null로 바꿔준다
        String str=strOpt
                //.orElseGet(()->new String()); // 없다면 빈객체 new String()을 str에 참조시키겟다~
                        .orElseGet(String::new);
                                //.orElse("");
        System.out.println(str);

    }
}
