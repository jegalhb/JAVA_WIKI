package com.smu8.study;

public class L17While2 {
    public static void main(String[] args) {
        // while의 조건식{}
        // 화면에 10번 안녕출력
        int i = 0; // index => 순서 or 목차
        while (i<10){
            i++;
            System.out.println("안녕"+i);

        }
        i=10;
        while (i>0) {
            --i;
            System.out.println("잘가" + i);
        }
        // 반복문의 출력 수를 제어! (변수(선언문),조건식,증감문)
        i=0;
        int sum=0;
        // 1~10까지 합
        boolean flag = true;
        while (i!=100){
            sum=sum+(++i);
            if (i==100) flag=false;
            System.out.println("1~100까지의 합 :" +sum);
            //break;
            // if (i==100) flag = false;
             if (i==100)break; // switch break 반복문에서 벗어남

        }


}
}