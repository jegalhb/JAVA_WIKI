package com.smu8.oop;
// 계산기!!
// class Cals (){ } 이미 존재하기 때문에 오류

import java.util.Date;
import java.util.Random;

class Calculator{
    //오버로딩 , 이름은 한개인데 역할이 여러개 -> 다형성 (객체지향문법특징)
 public int sum(int a, int b){
     return a+b;
 }
 public int sum(int a, int b, int c){
     return a+b;
 }
 int a; //  전역변수
 // 동일한 매개변수를 여러개 처리하고 싶다면 매개변수를 배열로 처리
    public int sum(int [] arr) {
     //{10,20,30,40}
        int sum = 0;// 지역변수 : 함수가 호출되어야 만들어짐 함수명과 무관
        for (int i = 0; i < arr.length; i++) {
         sum+=arr[i];
        }
     return sum;
    }
    public int multiple ( int ... nums){ // == int [] nums
        int mult = 1; // 0은 무엇이랑 곱해도 0 때문에 곱하기 초기값은 1
        // System.out.println(nums); //[I@2f4d3709 [: Array , I : int , @2f4d3709 : 주소
        for (int i = 0; i < nums.length; i++) {
            mult*=nums[i];
        }
        return mult;
    }

}
public class L03MethodArgs {

    public static void main(String[] args) {
        Calculator c = new Calculator();
        int result = c.multiple(10,20);
        System.out.println("10*20의결과 :" + result);
        result=c.multiple(11,22);
        System.out.println("11*22의결과 :" + result);
        result=c.multiple(11,22,33);
        System.out.println("11*22*33의결과 :" + result);
        result=c.multiple(11,22,33,44);
        System.out.println("11*22*33*44의결과 :" + result);

        c.multiple();

        int [] arr = {100,200,300,400,};
        int sum = c.sum(arr);
        System.out.println("arr의 아이템의 총합: " + sum);
        sum = c.sum(new int[] {11,22,33});
        // sum = c.sum(new int[]{11,22,33}) x
        Date now = new Date(); // java.util
        System.out.println(now.toString()); //Fri Jan 23 10:57:58 KST 2026
        String nowStr = now.toLocaleString();
        System.out.println(nowStr); //2026. 1. 23. 오전 10:59:21

        Random random = new Random(); // 랜덤수
        int num = random.nextInt();
        System.out.println(num);
        num= random.nextInt(0,51);
        // 1, 51 == 전달인자(파라미터)
        System.out.println(num);


    }



}
