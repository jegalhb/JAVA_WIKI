package poolsandwich;

import java.util.Random;
import java.util.Scanner;

public class poolsandwich {

    // ===== 게임 상태 =====
    enum State { PLAYING, GAME_OVER, WIN }

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        // ===== 플레이어 상태 =====
        boolean doorClosed = false;
        boolean cameraOn = false;

        // ===== 시스템 =====
        int power = 100;
        int hour = 12;
        State state = State.PLAYING;

        // ===== 프레디 =====
        int freddyPos = 0; // 0: 무대, 1: 복도, 2: 문앞, 3: 사무실
        int freddyAI = 5;

        System.out.println("🎮 Five Nights at Freddy's (Console)");
        System.out.println("6AM까지 살아남아라...\n");

        // ===== 게임 루프 =====
        while (state == State.PLAYING) {

            System.out.println("--------------------------------");
            System.out.println("⏰ 시간: " + hour + " AM");
            System.out.println("⚡ 전력: " + power);
            System.out.println("🚪 문: " + (doorClosed ? "닫힘" : "열림"));
            System.out.println("📷 카메라: " + (cameraOn ? "ON" : "OFF"));
            System.out.println("🐻 Freddy 위치: " + freddyPos);
            System.out.println("--------------------------------");

            System.out.println("행동 선택");
            System.out.println("1: 문 열/닫기");
            System.out.println("2: 카메라 켜기/끄기");
            System.out.println("0: 아무것도 안함");

            int input = sc.nextInt();

            if (input == 1) doorClosed = !doorClosed;
            if (input == 2) cameraOn = !cameraOn;

            // ===== Freddy AI =====
            if (!cameraOn) {
                if (random.nextInt(20) < freddyAI && freddyPos < 3) {
                    freddyPos++;
                    System.out.println("👀 ...무언가 움직였다");
                }
            }

            // ===== 공격 판정 =====
            if (freddyPos == 3 && !doorClosed) {
                state = State.GAME_OVER;
                break;
            }

            // ===== 전력 소모 =====
            power -= 1;
            if (doorClosed) power -= 2;
            if (cameraOn) power -= 1;

            if (power <= 0) {
                state = State.GAME_OVER;
                break;
            }

            // ===== 시간 진행 =====
            hour++;
            if (hour == 6) {
                state = State.WIN;
                break;
            }

            Thread.sleep(800); // 긴장감
        }

        // ===== 결과 =====
        System.out.println("\n==============================");
        if (state == State.GAME_OVER) {
            System.out.println("😱 GAME OVER");
            System.out.println("프레디에게 잡혔다...");
        } else {
            System.out.println("🎉 6 AM");
            System.out.println("살아남았다!");
        }
        System.out.println("==============================");
    }
}
