package com.smu8.oop;

class A{
    int x;
    private int p; // 상속받아도 접근 불가
    public void call(){
        System.out.println(this.x);
    }
    public A(){}
}
class B extends A{



}


public class L11Extends {
    public static void main(String[] args) {
        A a = new A();
        a.x = 100;
        a.call();

        B b = new B();
        b.x = 200;
        //b.p = 20; == 부모의 private은 접근 불가
        b.call();

        A c = new B(); // 객체는 부모타입의 변수가 참조가능 (객체가 여러 타입의 변수로 참조 타입의 다형성
    }
}
