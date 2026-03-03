package com.smu8.javautil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

interface CallBack{
    void test();
}
//CallBack 인터페이스를 객체로 사용하는 3가지 방법
//1.class를 만들어서 구현
//2.익명클래스
//3.CallBack에 추상메서드가 한개일 시 람다식 사용 (함수형 인터페이스)

public class L16FunctinalInterface {
    //1
    static class CallBackFunc implements CallBack{
        @Override
        public void test() {
            System.out.println("class로 구현된 메서드");
        }
    }



    static void main() {
        CallBack c= new CallBackFunc(); // <- 1

        CallBack c2=new CallBack() { // <-2
            @Override
            public void test() {
                System.out.println("익명클래스로 구현된 메서드");
            }
        };
        //3
        CallBack c3=()->{System.out.println("람다식으로 구현된 메서드");}; // test ()->{}
        c.test();
        c2.test();
        c3.test();
        //java에서 사용하기 위해 미리 만들어 놓은 함수형 인터페이스;
        //Consumer 소비자 (T)->{} // 소비하기 때문에 반환이 없다
        Consumer consumer = (o) -> {};
        Consumer <String> consumer2=(String s)->{};
        Consumer<Integer> consumer3=(Integer i)->{};

        //Collection.forEach(Consumer)
        //음수가 있는지 검사
        List<Integer> numList = new ArrayList<>(Arrays.asList(10,22,-33,50,70)); // 요소들 마다 num에 들어가서 반복문
        numList.forEach((num)->{
            if (num<0)
            System.out.println(num);
        });
        Optional<Integer> intOpt= Optional.of(13); // intOpt라는 박스에 13을 넣겠다
        intOpt.ifPresent((num)->{//intOpt박스에 값이 있다면 13을 intOpt에 넣겟다
            System.out.println(num+"이 존재합니다");
        });

        Runnable r = ()->{}; // run
        Optional<String> strOpt=Optional.empty();
        Optional<String> strOpt2=Optional.empty();
        strOpt2.ifPresentOrElse((s)->{System.out.println(s);},()->{System.out.println("데이터가 없음");});
        // 값이 null이면 함수를 실행한다

        Function<String,Integer> fun= (str)->{return str.length();};
        Function<String,Integer> fun2= (str)-> str.length(); // 리턴생략
        Function<String,Integer> fun3= String::length; // 람다 메서드 참조방식

        Predicate<String> predicate=(str)->{return str.length()>0;}; //boolean
        Predicate<String> predicate2=(str)-> str.length()>0; // 리턴생
        Supplier<String> supplier=()->"공급자";
        // Supplier 아니면 하는애들 사용
        // Predicate boolean 참거짓 필터에서 사용
        // 함수형 인터페이스
    }
}
