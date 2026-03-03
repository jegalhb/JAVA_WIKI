package com.smu8.homework;
import java.util.Scanner; // string, int , boolean 기본 라이브러리 외의 라이브러리 사용시 import(다른 패키지)
// java.util : 개발자에게 유용한 라이브러리 집합
public class H06whileFor {
    // 반복문 2개 while for
    public static void main(String[] args) {
        boolean f= true;

        int n= 0;
        while (f){
            // 안녕 5번
            n++;
            System.out.println("안녕");
            if(n==5){
                //break;
                f=false;
            }
        }
        //for(int i) : for 의 내부에서만 사용되는 변수 ==지역변수
        for (int i = 0; i < 5; i++) {
            System.out.print(i);

        }
        // System.out.print(i); // i는 지역변수기 때문에 전역에서 쓸 수 없다.
        System.out.println("\n5~1까지 출력");
        for (int i = 5; i >=0 ; i--) {  // 상단의 i가 지역변수기 때문에 서언가능
            System.out.println(i);

        }
        System.out.println("\n1~10중 짝수만 출력 : i를 짝수로 증가");
        for (int i = 0; i <=10 ; i+=2) {
            System.out.println(i);
        }
        System.out.println("\n1~10중 짝수만 출력 : 짝수가 아닌 i는 건너 뜀 continue");
        for (int i = 0; i <=10 ; i+=2) {
            if (i%2==1)continue; // if (i%2 !=0)
            System.out.println(i);
        }

        System.out.println("\n 정수를 입력하세요");
        //반복문 이유 : 입력대기 네이버검색
        //Scanner : 입력을 대기하는 반복문
        // 객체 NEW Scanner(입력 받을 곳)
        Scanner sc=new Scanner(System.in); // 콘솔에서 입력받을준비
        int inputNum=sc.nextInt(); // 콘솔에서 입력하는 것을 대기하는 반복문 생성

        System.out.println("입력완료!"+inputNum);
        for (int i = 0; i < inputNum; i++) {
            System.out.print("안녕"+i);

        }
    }
}
