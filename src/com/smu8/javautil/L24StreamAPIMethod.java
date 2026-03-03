package com.smu8.javautil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class L24StreamAPIMethod {
    static void main() {
        List<Integer> numList = new ArrayList<>(Arrays.asList(1,2,3,-6,4,5,6,-1,-2,-3));
        //Stream =>map Collect (Collect.*)으로 바꾸기
        //partitioningBy : map인데 두가지 그룹으로 반환 true false냐로
        Map<Boolean,List<Integer>> result =numList.stream()
                .collect(Collectors.partitioningBy((num)->{
                    return num>=0;
                }));
        System.out.println(result);

        // groupingBy: map 여러개의 그룹으로 반환 (그룹 == List를 뜻함)으로반환
        Map<String,List<Integer>> result2 =numList.stream()
                .collect(Collectors.groupingBy((num)->{
                    if (num>0){
                        return "양수";
                    } else if (num<0) {
                        return "음수";
                    }else
                        return "제로";
                }));
        System.out.println(result2);
        }

    }
