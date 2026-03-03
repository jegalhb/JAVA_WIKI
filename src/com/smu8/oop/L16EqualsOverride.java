package com.smu8.oop;

import java.util.Objects;

class 계산기{
    int a;
    int b;
    public 계산기(int a, int b){
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "계산기{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    @Override
    public boolean equals(Object o) { // 변수는 부모의 타입을 참조할 수 있다. (타입의 다형성)
        // 받아온 데이터가 계산기가 아니면 false
        // 받아온 데이터가 계산기가 라면 변수 c가 데이터를 참조
        if (!(o instanceof 계산기 c)) return false;
        return a == c.a && b == c.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
public class L16EqualsOverride {
    public static void main(String[] args) {
        계산기 c= new 계산기(10,20);
        계산기 c2=new 계산기(10,20);
        System.out.println(c);
        System.out.println(c2);
        String str = "안녕";
        System.out.println(c==c2);
        System.out.println(c.equals(c2));
    }
}
