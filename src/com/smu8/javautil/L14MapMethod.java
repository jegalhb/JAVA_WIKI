package com.smu8.javautil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class L14MapMethod {
    static void main() {
        //key value
        Map<String,Object> person = new HashMap<>();
        person.put("id" , "제갈코딩");
        person.put("name" , "제갈이");
        person.put("age" , 28);
        System.out.println(person);
        System.out.println(person.get("id"));
        System.out.println(person.containsKey("id"));// key있드나?
        System.out.println(person.containsValue(28));// value가 있드나?
        person.remove("name");
        System.out.println(person);
        person.put("name" , "smu8기");
        person.putIfAbsent("co","kosmo"); // 키가 없을 때만 추가?
        System.out.println(person);

        Set<String> keys = person.keySet(); // 맵이 반복문 사용 불가라 많이 사용!!
        System.out.println(keys);
        System.out.println("Set을 이용한 Map의 반복문");
        for (String key : keys){
            Object value = person.get(key);
            System.out.print(key + "=" +value + ",");
        }
        System.out.println("value 만 보기 (잘사용되지 않음)");
        Collection<Object> values = person.values();
        System.out.println("\n" + values);

        System.out.println(" EntrySet : 맵의 반복을 위해 나온 타입!!");
        Set<Map.Entry<String,Object>>entrySet =person.entrySet();
        for(Map.Entry<String,Object> entry : entrySet){
            String key = entry.getKey();
            Object value=entry.getValue();
            System.out.println(key+"=" + value);
            /*| 구분         |순서 | 중복| 인덱스 | 구조 |
                    | ---------- | -- | ----- | -----| -- |
                    | ArrayList  | O  | O     | O    | 배열 |
                    | LinkedList | O  | O     | O    | 노드 |
                    | Set        | X  | X     | X    | 해시 |
                    | Map        | X  | Key X | X    | 해시 |
                                 key는 안댕(value)는 중복가능
             */

        }
    }
}
