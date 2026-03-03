package poolsandwich;

import java.util.Random;
import java.util.Scanner;

public class 연습용14 {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int answer = random.nextInt(100) + 1; // 1~100
        int count = 0;
        int guess = 0;

        System.out.println("r 업다운 게임 시작!");
        System.out.println("1부터 100 사이의 숫자를 맞춰보세요.");

        while (true) {
            System.out.print("숫자 입력: ");
            guess = scanner.nextInt();
            count++;

            if (guess > answer) {
                System.out.println("DOWN ⬇️");
            } else if (guess < answer) {
                System.out.println("UP ⬆️");
            } else {
                System.out.println("🎉 정답입니다!");
                System.out.println(count + "번 만에 맞췄어요!");
                break;
            }
        }

        scanner.close();
    }
}