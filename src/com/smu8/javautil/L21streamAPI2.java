package com.smu8.javautil;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class L21streamAPI2 {
    static void main() {
        //배열=>스트림!
        String[] strArr= {"자바" , "스트림" , "체이닝"};
        // Array 배열은 이터레이터를 포함하고 있지않음
        // => 향상된 반복문 사용시 반복자가 개입한다!
        for(String s:strArr){
            System.out.println(s+"");
        }
        Stream<String> strStream=Arrays.stream(strArr);
        //변수에서 참조할 수있지만 스트림은 재사용 불가이기 떄문에 바로 사용하는 것이 일반적이다
        System.out.println("\n스트림 forEach");
        Arrays.stream(strArr).forEach((s)->{
            System.out.print(s+",");
        });

        List<Integer> numList = new ArrayList<>();
        numList.add(10);
        numList.add(22);
        numList.add(33);
        numList.add(45);
        System.out.println("\n"+numList);

        // 모든 Collection은 stream을 생성할 수 있게 구현됨
        numList.stream().forEach(System.out::println);
        Set<Integer> numSet= new HashSet<>(Arrays.asList(1,1,2,3,4,5));
        System.out.println(numSet);
        numSet.stream().forEach(System.out::println);

        Map<String,Object>customer= new HashMap<>();
        customer.put("id","wprkf");
        customer.put("name","제갈코딩");
        customer.put("grade",1);
        customer.put("isMarried",true);
        System.out.println(customer);
        System.out.println(customer.get("isMarried"));
        // map은 컬랙션의 하위 클래스가 아니다 : Set을 키로 사용함 , EntrySet으로 Set반환
        customer.keySet().stream().forEach((key)->{
            Object value = customer.get(key);
            System.out.println(key+" : " + value);
        });
        // Set타입! [Entry(key,value),Entry(name , 제갈코딩),Entry.....
        customer.entrySet().stream().forEach((entry)->{
            String key = entry.getKey();
            Object value= entry.getValue();
            System.out.println(key+"=="+value);
        });
        //기본형을 지원하는 스트림 Stream + 기본형을 지원! int long double 세가지가 있따!!!!!!!!!
        // 콜렉션의 단점인 기본형 사용 불가를 해결!
        IntStream intStream = IntStream.of(10,20,30,40);
        DoubleStream doubleStream=DoubleStream.of(10.0,22.13);
        LongStream longStream=LongStream.of(10L,20L,222222222222222L);
        Stream<Integer> integerSystem=Stream.of(10,20,30,40);

        OptionalInt intOpt = OptionalInt.of(13);
        Optional<Integer> numOpt=Optional.of(13);


    }
}
