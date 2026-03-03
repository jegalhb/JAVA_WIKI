package com.smu8.javautil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class L12LinkedList {
    static void main() {
        List<String> strList= new LinkedList<>();
        strList.add("안농");
        strList.add("잘가");
        System.out.println(strList);
        // 링크드리스트는 사용법이 다르지 않음

        List<Integer> numArrList = new ArrayList<>();
        List<Integer> numLinkedList = new LinkedList<>();
        long Start=System.nanoTime(); // 1/1_000_000초! == 나노초~
        for (int i = 0; i < 1_000_000; i++) {
            numArrList.add(i);
            numLinkedList.add(i);
        }
        long end = System.nanoTime();
        System.out.println((end-Start) /1_000_000.0 + "초");
        //System.out.println(numLinkedList);
        //System.out.println(numLinkedList);
        Start = System.nanoTime();
        numArrList.remove(0);
        end = System.nanoTime();
        System.out.println((end-Start) /1_000_000.0 + "초");

        Start = System.nanoTime();
        numLinkedList.remove(0);
        end = System.nanoTime();
        System.out.println((end-Start) /1_000_000.0 + "초");

    }
}
