package com.smu8.study;

public class L18WhiliBreak {
    public static void main(String[] args) {
        // 0부터 2의 배수를 더하고 싶은데 10000보다 커질때 까지.= Break
        int num = 0;
        int sum = 0;
        while (true){
            num+=2;// 2씩증가
            sum+=num; // sum=sum+num : sum에 2의 배수를 누적
            if (sum>=10000) break; // 반복문 종료 ㄷㄷ!
        }
        System.out.println(sum);
        // 반복문의 반복수와 상관없이 종료될 때 = break;
        num = 0;
        sum = 0;
        while (true){
            num+=3; // 3  6 9 12
            sum+=num; // 3 9 21
            if (sum>=20000) break;
        }
        System.out.println(sum);
        // 3의 배수의 누적합을 구하는데 20000이상일 때 종료
        num = 0;
        sum = 0;
        while (true){
            num+=3;
            sum+=num;
            if (sum>20000) break;
        }
        System.out.print(sum+"\n3의 배수의 누적합을 구하는데 20000 종료 값 :");


        // 20000이상일때 종료할때까지 몇번 걸렷나 알고싶을 때
        num = 0;
        sum = 0;
        int i=0;
        while (true){
            i++;
            num+=3;
            sum+=num;
            if (num>20000) break; // if 문은 실행문이 1줄일 때 {}생략가능 ㄷㄷ

        }
        System.out.println(num);
        System.out.println("20000까지 누적하는데 반복수 :"+i);

        i=1;
        sum=0;
        while (true){
            i+=3;
            sum+=i;
            if (sum>5000) break;
            num+=2;
        }
        System.out.println(sum);
    }
}
