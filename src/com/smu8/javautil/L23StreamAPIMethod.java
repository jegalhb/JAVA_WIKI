package com.smu8.javautil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class L23StreamAPIMethod {
    static void main() {
        // 최종연산자 : 더이상 스트림을 반환하지 않아서 체이닝 할 수 없는 것을 의미
        // forEach : 출력과 로그를 하기위한 최종연산용도
        List<String> strList = new ArrayList<>(Arrays.asList("1","1","2","3","-5"));
        strList.stream().
                forEach(System.out::println);
        long count=strList.stream().count(); // length , size ,count를 받는다!
        System.out.println(count);

        //reduce() : 어떤 하나의 결과로 만들 때 사용한다! EX )  전체의 합 ,전체의 곱
        Optional<String> result =strList.stream().reduce((s, s2)->s+s2);
        //reduce는 null을 반환할 수 있어서 Optional 해줘야해!
        //문제 ㄸ 해당 문자열을 문자열을 정수로 바꿔서 누적합 ㄷㄷ
        int sum=strList.stream()
                .distinct()
                .map(Integer::parseInt)
                .filter(n->n>=0)
                .reduce((n1,n2)->n1+n2)
                .orElse(0); // 없으면 0 있으면 sum이 이상적
        System.out.println(sum);
        //검사 anyMatch ,allMatch , nonMatch => boolean 반환
        // 전부다 양이냐!
        boolean is=strList.
                stream()
                .map(Integer::parseInt)
                .allMatch(i->i>=0);
        System.out.println("모두 양이가? :" + is);

        //"1","1","2","3","-5"
        Optional<String> strOpt=strList.stream().findAny(); // 하나만찾으면 반환한다 ㄷㄷ;
        strOpt.ifPresent(System.out::println);
        // findAny는 임의의 값을 가져오는데 대체로 첫번째 값을 찾아 반환해요
        // 임의 값을 가져오는 경우에는 스트림이 여러개가 병렬로 있는 경우 첫번째 값을 장담할 수 없어서 애매꾸리;


        //collect-> list ,set ,map으로 스트림을 반환하는 것!!!!!!!!
        List nums =Stream.of("12","13","1","-5").collect(Collectors.toList());
        System.out.println(nums.get(1)); // 13

        Set numSet = Stream.of(1,1,2,2,3,4,5).collect(Collectors.toSet());
        System.out.println(numSet); // 1 2  3 4 5 중복제거 Set ㄷㄷ

        //strList에 있는 문자열을 다시 정수로 변환한뒤다시 리스트로 변환
        List<Double> doubleList =strList
                .stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        System.out.println(doubleList);
        System.out.println(doubleList.get(4));
    }
}
