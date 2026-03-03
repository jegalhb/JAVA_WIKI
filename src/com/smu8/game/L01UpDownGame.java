package com.smu8.game;

import java.util.Random;
import java.util.Scanner;

public class L01UpDownGame {
    public static void main(String[] args) {
        // 1~10 중 숫자 맞추기!
        Random random = new Random();
        int randomNum = random.nextInt(1,11); // 랜덤한 숫자를 만들어줌
     //   System.out.println(randomNum);
        System.out.println("hint 랜덤하게 뽑힌 수 :" + randomNum);

        Scanner scanner = new Scanner(System.in);
        //System.in 으로도 문자열 입력을 받을 수 있는데 (입력은 1byte => 2byte )
        // Scanner "안녕" <-엔터(라인개행) 까지의 문자열으 입력받도록 도와주는 객체
        // Scanner는 반복문으로 입력인 엔터가 있을때까지 유저의 입력을 대기=> 프로그램이 멈춤
        System.out.print("수만 입력하세요 : ");
        String inputStr=scanner.next();
        int inputNum = Integer.parseInt(inputStr);
        System.out.println("입력한 문자열 :"+ inputStr);

        if (inputNum==randomNum){
        System.out.println("정답 ㄷㄷ");
    }else {
            System.out.println("오답!!!!!!!!!!!!!");
        }
}
    }