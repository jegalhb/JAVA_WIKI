package com.smu8.javautil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class L13SetMethod {
    static void main() {
        Set<String> fruitSet = new HashSet<>();
        fruitSet.add("바나나");
        fruitSet.add("바나나");
        fruitSet.add("사과");
        fruitSet.add("망고");
        fruitSet.add("애플망고");
        fruitSet.add("두리안");
        fruitSet.add("두리안");
        System.out.println(fruitSet); // 중복이 제거가 됨
        //fruitSet.get // set은 찾아서 가져오는 것이 힘들다
        boolean isDurian=fruitSet.contains("두리안");
        System.out.println("마 두리안 있드나? :" + isDurian );
        System.out.println(fruitSet.size()); // size == 길이!
        System.out.println(fruitSet.isEmpty()); // 비었나??

        //집합
        Set<Integer> a = new HashSet<>(Arrays.asList(1,2,3));
        Set<Integer> b = new HashSet<>(Arrays.asList(3,4,5));
        Set<Integer> c = new HashSet<>(Arrays.asList(5,6,7));

        //a.addAll(b); // a합집합b
        //System.out.println(a);
        Set<Integer> newA=new HashSet<>(a); // collection
        newA.addAll(b);
        System.out.println(newA);
        System.out.println(a);
        newA.addAll(c);
        System.out.println(newA); // 1234567
        a.retainAll(b);
        System.out.println(a); // 3 중복된 데이터! a교집합b
        // 집합을 하면 데이터 자체 값이 변화하기 때문에 조심해야함
        b.removeAll(c);
        System.out.println(b); // b - c!! 차집합! 3, 4
        //반복자~
        Iterator<String> it=fruitSet.iterator();
        System.out.println("이터레이터 반복문 while");
        while (it.hasNext()) {
            System.out.print(it.next()+",");
        }
        System.out.println("\n이터레이터 향상된 반복문 for");
        for (String fruit : fruitSet){
            System.out.print(fruit + ",");
        }
        System.out.println("\n이터레이터 반복문 each 내부 반복문");
        Consumer<String> callBackFunc=(fruit)->{
            System.out.print(fruitSet+",");
        };
        fruitSet.forEach(callBackFunc);
    }
}
