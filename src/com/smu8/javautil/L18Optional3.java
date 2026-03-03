package com.smu8.javautil;

import java.util.Optional;

public class L18Optional3 {
    static void main() {
        // map : 기존의 데잍터를 바꾼다.
        // 문자열 => 정수로
        Optional<String> strOpt=Optional.of("113");
        Optional<Integer> intOpt=strOpt.map((str)->Integer.parseInt(str)); // 문자열 113을 정수 113으로 바꾸겟다
        int num = intOpt.orElse(0);
        System.out.println(num);

        strOpt = Optional.of("2233");
        num= strOpt
                .map((str)-> Integer.parseInt(str)). // empty일때  map은 실행안되고 Optional반환
                orElse(0);
        System.out.println(num);
        strOpt=Optional.empty();
        //체이닝 함수 : .으로 계속 연결하는 것 , 같은 타입을 계속 반환(중간함수) , JQuery
        num=strOpt
                .map((str)->Integer
                        .parseInt(str))
                .orElse(0);
        System.out.println(num);

        //flatMap 평평하게 하는 flat : Optional에 중첩시 한개를 지운다

        strOpt = Optional.of("-113.13");
        Optional<Optional<Integer>> intOpt2=strOpt.map((str)->parseInt(str) );
        Optional<Integer> intOpt3=strOpt.flatMap((str)->parseInt(str) );

        //filter (null이 아니고 내가 원하는 데이터를 추출할때 사용)
        //age>0 age<=140 -7이 왔을때
        Optional<Integer>ageOpt=Optional.of(7);
        int age = ageOpt
                .filter((n)->n>=0 && n<=140) // 조건에만족하면 age에 7을 넣어준다
                .orElse(0); // 조건에 부합하지 않으면 0을 age에 넣어준다
        System.out.println(age);
        Optional<String> ageStrOpt=Optional.of("40");
        age=ageStrOpt
                .map((s)->Integer.parseInt(s)) //값이 null이 아니라면
                .filter((n)->n>=0) // 조건에 맞으면 실행
                .orElse(0);
        System.out.println(age);
    }

    public static Optional<Integer> parseInt(String s){
        try {
            return Optional.of(Integer.parseInt(s)); // 오류가 발생하지 않는다면 상자에 있는 문자열을 강제로 정수로 바꾼다.
        }catch (Exception e){ // Exception오류를 catch하면 상자(Optional)에다 null을 넣는다
            return Optional.empty();

        }
    }
}