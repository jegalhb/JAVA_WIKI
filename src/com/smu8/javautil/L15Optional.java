package com.smu8.javautil;

import java.util.Optional;

public class L15Optional {
    static void main() {
        String str =null;
        //System.out.println(str.indexOf("A"));NullPointerException
        //unchecked exception 체크하지 않아서 생기는 오류
        if (str!=null){
            System.out.println(str.indexOf("A"));
        }else {
            System.out.println("str에 데이터 없엉;;");
        }

        //checked 오류 : try catch가 아니면 찾을 수 없는 오류;;
        try {
            System.out.println(str.toUpperCase()); //NPE

        }catch (NullPointerException e){
            System.out.println("str 존재하지않다 null");
        }
        Optional<String> strOpt = Optional.of("java"); // of는 데이터가 무조건 있어야함
        //Optional 은 IF로 null인지 검사를 권장한다
        if (strOpt.isPresent()){ // str! =null
            String s = strOpt.get();
            System.out.println(s.toUpperCase());
        }else {
            System.out.println("strOpt데이터가 없어요");
        }
        strOpt.ifPresentOrElse((s)->{
            System.out.println(s.toUpperCase()+"ifPresent");
        },()->{
            System.out.println("strOpt데이터가 null: OrElse:");
        });

    }
}
