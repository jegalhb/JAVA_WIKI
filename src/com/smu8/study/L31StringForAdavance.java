package com.smu8.study;

import java.util.Arrays;

public class L31StringForAdavance {
    public static void main(String[] args) {
        // 2차원 배열
        int [][] numArr = {{0,1},{2,3},{4,5}};
        System.out.println(numArr[0] [1] ); // 1
        System.out.println(numArr[2] [0]);  // 4

        for (int i = 0; i <numArr.length ; i++) {
            int [] arr = numArr[i];
            System.out.println(Arrays.toString(arr));
            for (int j = 0; j <numArr[i].length; j++) {
                System.out.println(numArr[i][j]);
            }
        }
        // { {"ab},",{"vd"},{"ef"},{"gh"}}
        boolean result = true;
        String [] strArr = {"ab,","vd","efA","gh"}; // 2차원 배열
        // 반복문에 이름주기
        outer :for (int i = 0; i < strArr.length; i++) {
            String s= strArr [i];
            System.out.println(s);
            for (int j = 0; j < s.length(); j++) {
                char c= s.charAt(j);
                if(c>='A' && c<='Z') {
                    result = false;
                    break outer;

                }
            }

        }
        System.out.println("strArr 의 문자열들은 모두 소문자 : " + result);


    }
}
