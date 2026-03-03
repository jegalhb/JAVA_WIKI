package com.smu8.oop;

import java.util.Arrays;

class Validator {
    // 배열검사
    // 1. 수가 모두 even(짞쑤)인지 검사
    public boolean isEven(int [] arr){
        boolean isEven = true;
        // 배열탐색 : 배열 중 홀수가 있으면 isEven = false
        // {2,4,6,9,10}
        for (int i = 0; i < arr.length; i++) {
        if (arr[i]%2==1){
            isEven = false;
            break;// 홀수가 발견되면 더 이상 반복탐색을 할 이유가 없음 == 반복문 종료 ㄷㄷ
        }
        }
        return isEven;
    }
public boolean isOdd (int ... arr){
    //    boolean isOdd = true;
    for (int i = 0; i < arr.length; i++) {
        if (arr[i]%2==0)return false; // 리턴을 하게 되면 함수가 종료 자동으로 반복문 또한 종료됨. == > 코드의 길이가 줄어듬 유지 보수 용이
    }
    return  true;
}

}
public class L04MethodReturn {

    public static void main(String[] args) {
        //return 과 종료시점
    int[] nums = {2,4,6,8,9,10}; // 리터럴 표기법 new연산자 생략 , 변수선언 필수
    int[] nums2=new  int[5]; // 일반적 배열 선언 //
    nums2 [0]=2;
    nums2 [1]=2;
    nums2 [2]=2;
    nums2 [3]=2;
    nums2 [4]=2;
         //new  int[]= {10,20,30,40};
        System.out.println(Arrays.toString(new int[]{10,20,30}));
        Validator validator = new Validator();
        System.out.println(validator.isEven(nums));
        System.out.println(validator.isEven(nums2));
        System.out.println(validator.isEven(new int[]{10,20,30}));

        System.out.println("1,3,5,7,9는 모두 홀수임?" +validator.isOdd(1,3,5,7,9));
        System.out.println("1,3,5,7,9는 모두 홀수임?" +validator.isOdd(1,3,5,6,7,9));
    }
}
