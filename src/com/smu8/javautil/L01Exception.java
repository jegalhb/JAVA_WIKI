package com.smu8.javautil;

public class L01Exception {
    public static void main(String[] args) {
        int [] nums = {100 ,200, 300,};
        nums [3] = 400;

        try {
            nums[4]=500;
            System.out.println("오류가 발생하면 다음 코드는 실행되지않음");
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
            System.out.println("배열의 범위가 아닙니다.");
        }
        System.out.println("예외를 처리하면 jvm이 멈추지않는다");
    }
}
