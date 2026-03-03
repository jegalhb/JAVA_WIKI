package com.smu8.javautil;

import com.sun.jdi.IntegerValue;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class L22StreamAPIMethod {
    static void main() {
        // 다시스트림을 반환하는 것이 == 중간연산자 filer , map
        // 스트림을 반환하지 않고 다른것을 반환하는 것 == 최종연산자 forEach
        IntStream numStream = IntStream.of(10,-7,20,39);
        // 음수를 제외한 스트림을 만들고 싶어!
        // 검사 후 새로운 스트림을 반환하는 것 : filter
        IntStream numStream2=numStream.filter((n)->{ // 계속 옵셔널을 반환하기 떄문에 중간연산자라고 함
            return  n>0;
        }); // == Predicate.test
        numStream2.forEach(System.out::println);

        //리스트에 있는 모든 수를 음수로 바꾸고싶다!!!!!!!!!!!!!!!!!!!!!!
        // Map Function.apply
        // Math : 연산을 도와주는 도구! (클래스)
        //abs() : 절대값으로 바꿔줌!
        System.out.println(Math.abs(-13));
        List<Number> numList =new ArrayList<>(Arrays.asList(10,-10.0,20.12,13));
        Stream<Double> doubleStream =numList.stream().
                map((n)->(Math.abs(n.doubleValue())));
        doubleStream.forEach(System.out::println);

        // 모든 수를 정수 (int)로바꾼후 출력하고싶다!!!!!!
        numList.stream()
                .map((n)->n.intValue())
                .forEach((i)->{
                    System.out.println(i);
                });
        // 모든 수를 double로 바꿔!!!!!!!!!!! 함수참조를 겁나게해!
        numList.stream()
                .map(Number::doubleValue)
                .forEach(System.out::println);

        List<String> strList=new ArrayList<>(Arrays.asList("13","13","11","11","-34","88"));
        //받은 문자열을 수로 변환
        //양수만 필터!
        //출력해용
        strList.stream().map((s)->Integer.parseInt(s)).filter((i)->i>0).forEach(System.out::println);

        strList.stream().distinct().forEach(System.out::println);
        // strlist의 중복을 제거하고 정수로 변환,제곱의 값으로 바꿔!!!!!!!!!

        // Limit , skip!!!!!!!
        strList.stream()
                .distinct()
                .map(Integer::parseInt)
                .map((i)->i*i)
                .forEach(System.out::println);
        //"13","13","11","11","-34","88"
        strList.stream()
                .limit(5) //"13","13","11","11","-34"
                .skip(2)  // 11 , 11, -34
                .map(Integer::parseInt)
                .sorted()
                .forEach(System.out::println);
    }
}
