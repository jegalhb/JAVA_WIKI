package com.smu8.study;

public class L08Float {
    public static void main(String[] args) {
        //실수 종류와 저장되는 원리
        float f= 12345678901234567890234567890.0f;
        System.out.println(f);
        // 1,2345679E28 => 부동소수점 표기법
        // 1.12345678901234567890234567890.0f->1.2345679E28
        // 가수부가 표현할 수 없는 길이를 넘었기에 반올림함.
        double d=12345678901234567890234567890.0;
        System.out.println(d);
        //double의 가수부가 더 크기에 정밀도가 높다
        //면접 : 왜 자바는 실수의 기본형으로 double을 사용하나요?
        // A=> Float 보다 double의 가수부가 커서 정밀도가 높다.
        System.out.println(0.2+0.4); // 1/5 + 2/5
        System.out.println(0.5+0.25); // 1/2 + 1/4
        //면접 : 0.1을 더하기하면 무한 소수가 나오는 이유?
        // A : 실수 변환 과정에서 0.1이 2진수로 정확히 떨어지지 않기 때문, 무한소수기 때문
        System.out.println(0.3==(0.1+0.2)); // false
        f=1E35F;
        d=1E300;
        // 실수는 지수부가 있기 때문에 천문학적 숫자를 다룰 수 있음.



    }
}
