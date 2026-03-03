package com.smu8.oop;

import java.awt.event.ActionListener;
import java.util.function.Function;

@FunctionalInterface
interface LambdaTest{
    void a();
    static void b(){}; // 필드와 관련이 없는  static
}
@FunctionalInterface
interface ParamTest{
    int sum(int a, int b);
}
public class L24Lambda {
    public static void main(String[] args) {
    LambdaTest l = new LambdaTest() {
        @Override
        public void a() {
        }
    };
    LambdaTest l2 =()->{};
    // ParamTest p=()->{}; ParamTest안에 있는 sum(a,b)이 오버라이드댓음
        ParamTest p=(int a, int b)->{return a+b;};
        System.out.println(p.sum(10,20));
        ParamTest p2=(int a, int b)-> a+b;// 리턴과 중괄호 생략 가능!
        System.out.println(p.sum(20,30));

        ActionListener a=(e)-> {};
        Runnable run =()->{};
    }
}
