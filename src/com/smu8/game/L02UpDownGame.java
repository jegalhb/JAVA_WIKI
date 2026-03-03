package com.smu8.game;

import java.util.Random;
import java.util.Scanner;

public class L02UpDownGame {
    public static void main(String[] args) {

        Random random = new Random();
        int ranNum = random.nextInt(1,101);
        System.out.println("힌트는 :" + ranNum);

        Scanner scanner = new Scanner(System.in);
        System.out.println("수만 입력해라잉");

        String inputmango =scanner.next();
        int inputNum = Integer.parseInt(inputmango);
        System.out.println("입력한 수 : " + inputmango);

        if (ranNum == inputNum){
            System.out.println( "사온 망고 갯수가 맞습니다!");
        }else {
            System.out.println("아닙니다!");
        }
    }
}
