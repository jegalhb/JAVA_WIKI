package com.smu8.game;

import java.util.Random;
import java.util.Scanner;

public class BLCAKJAXGAME {

    public static void main(String[] args) {

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int userSum = 0;
        int userAceCount = 0;

        int computerSum = 0;
        int computerAceCount = 0;


        for (int i = 0; i < 2; i++) {
            int card = drawCard();
            userSum += getCardValue(card);
            if (card == 1) userAceCount++;
        }
        userSum = adjustAce(userSum, userAceCount);


        for (int i = 0; i < 2; i++) {
            int card = drawCard();
            computerSum += getCardValue(card);
            if (card == 1) computerAceCount++;
        }
        computerSum = adjustAce(computerSum, computerAceCount);

        System.out.println("유저 카드 합계: " + userSum);
        System.out.println("컴퓨터 카드: ?, ?");


        while (true) {
            System.out.print("선택 (1:카드 받기, 2:멈추기): ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                int card = drawCard();
                int value = getCardValue(card);

                userSum += value;
                if (card == 1) userAceCount++;

                userSum = adjustAce(userSum, userAceCount);

                System.out.println("카드 추가! 현재 합계: " + userSum);

                if (userSum > 21) {
                    System.out.println("[결과] 유저 버스트! 패배");
                    return;
                }
            } else {
                break;
            }
        }


        while (computerSum <= 16) {
            int card = drawCard();
            computerSum += getCardValue(card);
            if (card == 1) computerAceCount++;

            computerSum = adjustAce(computerSum, computerAceCount);
        }


        System.out.println("\n[최종 결과]");
        System.out.println("유저 합계: " + userSum);
        System.out.println("컴퓨터 합계: " + computerSum);


        if (computerSum > 21) {
            System.out.println("결과: 유저 승리");
        } else if (userSum > computerSum) {
            System.out.println("결과: 유저 승리");
        } else if (userSum < computerSum) {
            System.out.println("결과: 컴퓨터 승리");
        } else {
            System.out.println("결과: 무승부");
        }
    }

    static Random random = new Random();

    static int drawCard() {
        return random.nextInt(13) + 1;
    }


    static int getCardValue(int card) {
        if (card >= 2 && card <= 10) {
            return card;
        } else if (card >= 11 && card <= 13) {
            return 10;
        } else {
            return 11; // A
        }
    }


    static int adjustAce(int sum, int aceCount) {
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }
}

