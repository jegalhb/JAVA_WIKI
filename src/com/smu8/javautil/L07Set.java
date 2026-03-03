package com.smu8.javautil;

import java.util.*;

public class L07Set { //정렬에 유리
    static void main() {
        Set<Integer> numSet = new HashSet<>();
        numSet.add(789);
        numSet.add(123);
        numSet.add(456);
        numSet.add(333);
        numSet.add(444);
        numSet.add(789);
        System.out.println(numSet);
        // Set은 중복된 데이터를 확인후 저장! (== , equals)
        // Set,HashSet은 중복을 제거하되 순서가 없는것
        System.out.println(789==789);
        System.out.println("안녕".equals("안녕"));

        //순서는 보장되지만 인덱스 기반으로 데이터를 찾을 수 없다. 중복된 데이터도 제거
        Set <Integer>numSet2 = new LinkedHashSet<>();
        numSet2.add(111);
        numSet2.add(222);
        numSet2.add(333);
        numSet2.add(444);
        numSet2.add(555);
        numSet2.add(555);
        System.out.println(numSet2);
        System.out.println(numSet2);
        System.out.println("밍");

        List<Integer> numList = new ArrayList<>();
        numList.add(111);
        numList.add(222);
        numList.add(333);
        numList.add(444);
        numList.add(555);
        numList.add(555);
        System.out.println(numList);
        System.out.println(numList.get(2));

        //작은것 왼쪽 큰것 오른쪽으로 뻗어나가는 구조
        Set<Integer> numTree = new TreeSet<>();
        numTree.add(13);
        numTree.add(4);
        numTree.add(20);
        numTree.add(3);
        numTree.add(1);
        numTree.add(5);
        numTree.add(7);
        numTree.add(1);
        System.out.println(numTree);

        //자료형은 equals가 구현된 타입만  set으로 중복 제거됨;;
        Set <String> strSet= new HashSet<>();
        strSet.add("제갈");
        strSet.add("제길");
        strSet.add("재갈");
        strSet.add("제갈코딩");
        strSet.add("제갈");
        strSet.add("제갈코딩입문자");
        strSet.add("제갈");
        System.out.println(strSet);
    }
}
