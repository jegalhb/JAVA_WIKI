package com.smu8.study;

import java.util.Arrays;

public class L29ArrayFor {
    public static void main(String[] args) {
        // 선생님이 점수 관리 (정수)
        //int [] : Arr 정수 배열 타입
        int [] scoreArr = new int[7]; // new int = 객체를 생성, 길이가 7
        scoreArr [0]= 88;
        scoreArr [1]= 90;
        scoreArr [2]= 75;
        scoreArr [3]= 100;
        scoreArr [4]= 78;
        scoreArr [5]= 65;
        scoreArr [6]= 87;

        System.out.println(Arrays.toString(scoreArr)); // [88, 90, 75, 100, 78, 65, 87]
        // 90점 이상은 몇명이야?
        int cnt =0;
        if (scoreArr[0]>=90) cnt ++;
        if (scoreArr[1]>=90) cnt ++;
        if (scoreArr[2]>=90) cnt ++;
        if (scoreArr[3]>=90) cnt ++;
        if (scoreArr[4]>=90) cnt ++;
        if (scoreArr[5]>=90) cnt ++;
        if (scoreArr[6]>=90) cnt ++;
        System.out.println(cnt); // 2
        System.out.println(scoreArr.length); // 길이 7

        cnt = 0;
        for (int i =0; i<scoreArr.length;i++) {
            // System.out.println(i);
            if (scoreArr[i]>=90) cnt ++;

        }
        System.out.println("90점 이상을 받은 학생의 수 : " + cnt);

        int [] numArr = {33,-12,88,-22,100,56,-88};
        //numArr 에 음수가 몇개 들어왔나요?
        cnt = 0;
        for (int i = 0; i <numArr.length ; i++) {
            if (numArr[i]<0) cnt++;
        }
        System.out.println("음수는" + cnt + "개 입니다");
        // 리터럴 표기 시 변수를 선할때만 가능
        // 변수를 재사용하고 싶으면 객체생성 후 사용
        String [] strArr = {}; // strArr 변수를 선언(만들 때) 리터럴 표기 가능
        strArr = new String[] {}; // 변수를 재사용시는 객체 생성 후 리터럴 표기 사용
        scoreArr = new int[] {55,99,100,77,120,-7,20}; // 100점 넘는 사람과 음수인 사람이 있음
        // 점수의 모든 합=sum 을 구하는데 0~100사이의 정수만 합하세요
        int sum =0;
        for (int i = 0; i < scoreArr.length; i++) {
            //sum+=scoreArr[i];
            //if (scoreArr[i]>100 || scoreArr[i]<0)
              //  sum+=scoreArr[i];
            if (scoreArr[i]>0 && scoreArr[i]<=100) sum +=scoreArr[i];

            System.out.println("전체합 :"+sum);
        }


    }
}
