package com.smu8.game;

import java.util.Random;
import java.util.Scanner;

public class L01UpDownGame2 {
    public static void playGame(Scanner scanner) {
        System.out.println("업다운 게임입니다. 1~50까지의 수를 입력하세요(기회는 5번)");

        int randomNum = makeRandomNumber();
        System.out.println("힌트 : " + randomNum);

        final int MAX_COUNT = 5;
        boolean result = false;

        for (int count = 1; count <= MAX_COUNT; count++) {
            System.out.print(count + "번째 기회 : ");
            int userNum = scanner.nextInt();

            if (userNum == randomNum) {
                System.out.println("정답입니다.");
                result = true;
                break;
            } else {
                printUpDown(userNum, randomNum);
            }
        }

        printResult(result, randomNum);
    }

    // =========================
    // 랜덤 숫자 생성
    // =========================
    public static int makeRandomNumber() {
        Random random = new Random();
        return random.nextInt(1, 51); // 1 ~ 50
    }

    // =========================
    // 업 / 다운 출력
    // =========================
    public static void printUpDown(int userNum, int randomNum) {
        String msg = userNum > randomNum ? "Down" : "UP";
        System.out.println("오답입니다. " + msg);
    }

    // =========================
    // 결과 출력
    // =========================
    public static void printResult(boolean result, int randomNum) {
        if (result) {
            System.out.println("-------성공-------");
        } else {
            System.out.println("-----실패-------");
            System.out.println("정답은 " + randomNum);
        }
    }

    // =========================
    // 재도전 여부 확인
    // =========================
    public static boolean askRetry(Scanner scanner) {
        System.out.print("재도전 하시겠습니까? y/n : ");
        String retry = scanner.next();
        return retry.equalsIgnoreCase("y");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            playGame(scanner);

            if (!askRetry(scanner)) {
                System.out.println("게임을 종료합니다.");
                break;
            }
        }
    }

    // =========================
    // 한 판의 게임 실행
    // =========================

}

