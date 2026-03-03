package com.smu8.game;

import java.util.Random;
import java.util.Scanner;

public class L05ROCKINGandrolling {

    /*static  String COM(int num){
        switch (num) {
            case 0 : return "바위";
            case 1 : return "가위";
            case 2 : return "보";
            default: return  "알수없는수";
        }

    }

     */

    static void COM (int num){
        if (num==0){
            System.out.println("바위");
        } else if (num==1) {
            System.out.println("가위");
        }else
            System.out.println("보");
    }




    public static void main(String[] args) {

        final int ROCK = 0;
        final int SCISSORS = 1;
        final int PAPER = 2;

        int userWinCnt = 0;
        int comWinCnt = 0;


        System.out.println("가위바위보 게임 3승 먼저 하믄 이겨");
        System.out.println("바위=0 , 가위=1 , 보=2");

        while (userWinCnt < 3 && 3 > comWinCnt) {
            int randomNum = new Random().nextInt(0, 3);
            Scanner scanner = new Scanner(System.in);
            int userNum = scanner.nextInt();

            if (userNum >= 0 && userNum <= 2) {
                if (userNum == randomNum){
                    //System.out.println("유저 :" + COM(userNum));
                    //System.out.println("컴퓨터 :" + COM(randomNum));

                    //System.out.println(userNum+ "vs" + randomNum);

                    //System.out.println(COM(userNum)+ "vs" + COM(randomNum));
                    //System.out.println(COM(randomNum));
                    System.out.print("유저의 패" + " : ");COM(userNum);
                    System.out.print("상대의 패" + " : ");COM(randomNum);
                    System.out.println("비겻엉");
                } else if (userNum-randomNum == -1 || userNum-randomNum ==2) {
                    userWinCnt ++;
                    System.out.print("유저의 패" + " : ");COM(userNum);
                    System.out.print("상대의 패" + " : ");COM(randomNum);
                    System.out.println(userWinCnt + "번째 이김");
                } else{
                    comWinCnt ++;
                    System.out.print("유저의 패" + " : ");COM(userNum);
                    System.out.print("상대의 패" + " : ");COM(randomNum);
                    System.out.println(comWinCnt + "번째 짐");
                }
            }else {
                System.out.println("0~2까지 입력하세요");
            }

        }
        String msg = (userWinCnt>comWinCnt ? "유저승리" : "유저패배");
        System.out.println(msg);
    }
}
