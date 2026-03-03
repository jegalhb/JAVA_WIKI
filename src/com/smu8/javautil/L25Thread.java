package com.smu8.javautil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class L25Thread {
    static void main() throws InterruptedException {
        // 시계를 만든다! == 시간출력 + 현재 온도를 함께출력하고자 한다.

        LocalDate nowDate=LocalDate.now();
        System.out.println(nowDate.toString());  // yyyy MM dd 2026 02 09
        LocalDateTime nowDateTime=LocalDateTime.now(); //2026-02-09T11:01:52.557526200
        System.out.println(nowDateTime); // yyyy- mm- dd 'T' HH:mm:ss.SSS == 시간의 문자열 포멧
        while (true){
            Thread.sleep(1000);
            System.out.println(LocalDateTime.now());
        }
        // 일꾼Thread가 한개뿐이라서 위 반복문이 종료되지 않는 한 절대 아래 코드에 도달할수없어 ㅠㅠ
        /*while (true){
            Thread.sleep(1000);
            System.out.println("현재온도는 :27도");
        }         */

    }
}
