package com.smu8.javautil;

import java.util.*;

public class L10ListMethod {
    static void main() {
        List<String> strList = new ArrayList<>();
        strList.add("A");
        strList.add("B");
        strList.add("C");
        strList.add("D");
        strList.add("E");
        System.out.println(strList);
        strList.add(2,"Z");
        System.out.println(strList);
        strList.set(2,"ㄱ");
        System.out.println(strList);
        //add : 데이터를 추가! 추가를 하니 길이도 변경!
        //set : 데이터를 변경!
        //strList.remove(2);
        strList.remove("ㄱ");
        System.out.println(strList);

        System.out.println(strList.get(3));
        System.out.println(strList.contains("D"));
        System.out.println(strList.contains("F")); // contains == ""가 있나
        System.out.println(strList.indexOf("D")); // "" 가 어디에있나
        System.out.println(strList.indexOf("f")); // -1
        System.out.println(strList.lastIndexOf("D")); // 3 뒤에서부터 요소를 찾는다
        // 요소가 많을 때 뒤에있는 요소를 찾는게 유리함
        strList.clear();
        System.out.println(strList);
        strList= new ArrayList<>(Arrays.asList("F" , "G" , "Abc", "D", "C", "B" , "Acc"));
        strList.add("Bac"); // 배열 추가!
        strList.sort(Collections.reverseOrder()); // 내림차순 정렬기능
        System.out.println(strList);
        Collections.sort(strList); // 정렬기능!
        System.out.println(strList);

        System.out.println("+++++++++++++++++++++++++++++");

        List<Integer> numList = new ArrayList<>(Arrays.asList(1111,0,-22,33,55,2,88));
        System.out.println(numList);
        numList.sort(Comparator.naturalOrder()); // 오름차순 정렬!
        System.out.println(numList);
        numList.sort(Comparator.reverseOrder()); // 역순정렬!
        System.out.println(numList);








          //Arrays : 길이불가 상태로 만들어진다!
//        strList= Arrays.asList("A","B","C","D","E"); // 리스트 선언에 도움 == Arrays.asList
//        System.out.println(strList);
//        //데이터 추가
//        //strList.add(2,"Z"); UnsupportedOperationException
//        //add라는 함수는 길이 변경이 불가하기 때문에 에러!
//        strList.set(2,"Z");
//        System.out.println(strList);

    }
}
