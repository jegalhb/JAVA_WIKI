package com.smu8.javautil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class L09Iterator {
    static void main() {
        List<String> strList = new ArrayList<>();
        strList.add("자바");
        strList.add("컬렉션");
        strList.add("리스트");
        strList.add("셋");
        strList.add("맵");
        strList.add("이터러블");
        //반복탐색 : 요소의 값을 변경하거나 검사하기 위해서 한다.
        // 인덱스 기반 반복탐색! : 매번 인덱스를 계산하기 떄문에 비효율적
        for (int i = 0; i <strList.size() ; i++) {
            System.out.print(strList.get(i)+ ",");
        }
        //Iterator :반복자는 직접 이동하면서 값을 탐색
        System.out.println("\n 반복자 출력");
        Iterator<String> it =strList.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext()); // 다음요소가 있냐????
        System.out.println(it.next());
        System.out.println(it.hasNext()); // 다음요소있엉?
        // System.out.println(it.next());에러!:NoSuchElementException

        Iterator<String> it2=strList.iterator();
        System.out.println("while iterator");
        while (it2.hasNext()){
            System.out.print(it2.next() + ",");
        }

        System.out.println("\n자바가 제공하는 이터레이터 반복문! == 향상된 반복문");
        // 자바가 제공하는 이터레이터 반복문! == 향상된 반복문
        for (String str : strList){
            System.out.print(str+ ",");
        }


    }
}
