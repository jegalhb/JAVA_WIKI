package com.smu8.study;

import java.util.Arrays;

public class L24Array {
    public static void main(String[] args) {
        // 5명 성적 (0~100)
        int[] scores=new int[5]; // {0.0.0.0.0}
        // new : 객체를 만든것 (배열은 객체)
        scores[0] = 65;
        scores[1] = 77;
        scores[2] = 89;
        scores[3] = 70;
        scores[4] = 100;
        System.out.println(scores);//주소가 뜸 [I@b4c966a
        //[I@b4c966a : 데이터가 저장된 메모리 주소
        //[ : Array
        //I : int
        System.out.println(Arrays.toString(scores));
        // java.util.Arrays : [] Array를 도와주는 클래스(==유틸 클래스)
        // 모든 학생의 성적합,평균
        int sum =scores[0]+scores[1]+scores[2]+scores[3]+scores[4];
        System.out.println(sum);
       // System.out.println(scores.length); // 배열의 길이 or 배열에 존재하는 아이템의 수
        System.out.println(sum/5);

        //배열의 리터럴 표기법 배열은 자료형이지만 리터럴 표기법 존재
        // {데이터,데이터,데이터 ....//
        // 배열의 동일한 타입의 데이터만 저장가능
        double [] nums={10.1,20.2,30,40.5};
        System.out.println(nums.length); // 4
        System.out.println(nums[3]); // 40.5
        System.out.println(nums[2]); // 30.0 형변환!
        nums[0] = 100.0;
        System.out.println(Arrays.toString(nums));

    }
}
