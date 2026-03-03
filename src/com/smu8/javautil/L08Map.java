package com.smu8.javautil;

import java.util.*;

public class L08Map {
    static void main() {
        // hashSet , LinkedSetHash,TreeSet 짝
        // hashMap , LinkedMapHash,TreeMap 짝
        // key : value로 된 데이터가 map으로 key가 Set으로 되어 있어서 절대 중복되지않음.
        Map<String,Object>person = new HashMap<>();
        person.put("이름" ,"제갈");
        person.put("나이" ,40);
        person.put("이름" ,"제갈코딩");
        //키가 중복을 허용하면 이름이 2개 , 중복을 허용하지 않으면 (Set)이름이 바뀜
        //키는 Set이기 때문에 equals가 구현된 자료형이거나 기본형으로 작성하세요!!
        System.out.println(person);
        // {이름 = 경민코딩 , 나이 =40} or {이름 : 경민코딩 , 나이:40
        person.keySet();

        //key 키만 가져오는것
        Set<String> keys = person.keySet();
        System.out.println(keys); //[이름 , 나이]

        Collection<Object>values = person.values();
        System.out.println(values);

        //Entry : key value , key value . . . . .
        //맵의 키와 값을 배열로 반환
    }
}
