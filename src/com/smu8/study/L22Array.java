package com.smu8.study;

public class L22Array {
    public static void main(String[] args) {
        // ex) 선생님이 학생의 성적을 관리하고 싶다.
        int s=99;
        int s2=80;
        int s3=88;
        // 복수의 데이터를 효과적으로 관리하는 타입 ==Array
        // 순서(index 0~ 시작)와 길이가 있으면 더 좋다!
        int [] scores={99,80,88,78,100,60}; // []대 괄 호 = Array
        System.out.println(scores[0]);
        scores[3]=90;
        System.out.println(scores[3]);
        //System.out.println(scores[6]); //
        // System.out.println(scores[-1]);//존재하지 않는 index는 참조 불가


    }
}
