package com.smu8.javautil;

import java.util.ArrayList; // 도구! lib
import java.util.Arrays;
import java.util.List;

public class L06List {
    public static void main(String[] args) {
        //List는 Array를 보완하기 위해 나온 컬렉션
        int [] numArr = new int[4]; // 0 0 0 0 문자열이면 널널널~
        int [] numArr2= {11,22,33,44};
        System.out.println(numArr2[0]);
        System.out.println(numArr2[1]);
        System.out.println(numArr2[2]);
        System.out.println(numArr2[3]);
        //numArr2[4] =55;
        //Array는 가볍고 기본형이 가능하지만 길이변경이 불가
        //=>고정길이 데이터에 적합

        List<Integer>numList = new ArrayList<>();
        numList.add(111); // add
        numList.add(222);
        numList.add(333);
        numList.add(444);
        numList.add(555);
        numList.add(0,777); // 특정위치에 넣기도 가능
        System.out.println(numList);
        System.out.println(numList.size());
        //[111, 222, 333, 444, 555] : ArrayList가 to String을 재정의해서
        numArr2[2]=66;
        System.out.println(numArr2);
        System.out.println(Arrays.toString(numArr2));
        System.out.println(numArr2.length);


        // 리스트 삭제가 가능
        //numList.remove(66); // 인덱스
        //numList.remove(2);
        //numList.remove(2);
        numList.remove(Integer.valueOf(777)); // 오브젝트
        // List는 모든 요소를 자료형으로 갖는다.
        System.out.println(numList);
    }
}

