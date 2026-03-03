package com.smu8.study;

public class L16While {
    public static void main(String[] args) throws InterruptedException {
        // while 반복문 (반복으로 수행)
        /* "안녕"을 너무 빠르게 출력해서 메모리가 넘칠 수 있다
        while (true){
            System.out.println("안녕");
        }

         */
        while (true){
            //일 == Thread
            Thread.sleep(1000); // 밀리초 == 1/1000 초
            // 일꾼이 쉬면 오류가 발생할수도 있기 때문에 오류처리를 해야함!(예외강제)
            System.out.println("잘가");
        }
    }
}
