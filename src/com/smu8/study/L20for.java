package com.smu8.study;

public class L20for {
    public static void main(String[] args) {
        for (int i = 0; i<5; i++){
            System.out.println(i);
        }
        for (int t = 0; t < 5; t++) {
          //  System.out.println(t);
        }

        int n=0;
        while (n<10){
            n++;
            System.out.print(n);
            if(n!=10)System.out.print(",");
        }
        for (int i = 1; i <11 ; i++) {
            System.out.println(i+"");

        }
        //10~1 for 거꾸로 출력
        System.out.println("\n for 출력 10~1");
        for (int i = 10; i>0 ; i--) {
            System.out.println(i+",");

        }
        //1~20까지 출력하다가 6의 배수는 제외
        System.out.println("\n for 출력하는데 6의 배수 제외");
        for (int i =1; i<=20; i++) {
            if (i%6==0) continue;
            System.out.println(i+",");
        }
        System.out.println("\n1~10000까지의 누적합 구하던 중 20000이상일때 중지");
        // 1~10000까지 합을 구하다가 총합이 20000 이상이 될 때 멈추세요
        int sum = 0;
        for (int i=1 ; i<10000; i++){
            sum+=i;
            if (sum>20000){
                System.out.println(i+"번째에 끝");
                break;
            }
        }
        System.out.println("\n 1~10000까지의 누적합" + sum);
        }

    }
