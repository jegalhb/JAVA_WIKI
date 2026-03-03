package com.smu8.oop;

import com.sun.source.tree.InstanceOfTree;


class TestA{
    int a=100;
    void printA(){
        System.out.println("TestA");
    }
}

class TestB extends TestA{
    int b=200;
    void printB(){
        System.out.println("TestB");
    }
}

class TestC extends TestB{
    int c=300;

    void printA(){
        System.out.println("TestC에서 재정의 한 A");
    }
}



public class L17TypePolymorphism {
    // 오버로드의 특성
    void sum(){}
    void sum(int a){}
    void sum(int a, int b){}

    // 오버라이드 : 부모 자식 둘다 같은 함수를 가짐
    @Override
    public String toString() {return super.toString();}

    //타입의 다형성
    public static void main(String[] args){
        TestC c= new TestC();
        TestB b = new TestC();
        TestA a = new TestC();
        Object obj = new TestC();
        //타입의 다형성 : 부모타입이 자식객체를 참조
        //Instanceof : 너 어떤 타입임?
        //getClass() : 너 어떤 타입임 문자열로?

        System.out.println(obj.getClass());
        System.out.println(obj instanceof TestC);
        //System.out.println(a.c);
        System.out.println(a.a);
        // 필드는 참조하는 부분만 사용
        a.printA();
        // 필드는 참조하는 부분만 사용
        // ** 오버라이드는 제외 ==> 부모의 함수를 재정의한 함수가 메서드 실행테이블에 명시되어서
        // a.printC();
        L17TypePolymorphism t = new L17TypePolymorphism();
        //객체를 참조하는 변수는 객체와 동일한 타입으로 참조
        Object o =new L17TypePolymorphism();
        //객체의 부모 타입 변수도 참조 가능(타입의 다형성)
        //다형성 == 오버로드,오버라이드,타입의 다형성
        //오버로드 : 함수이름은 1개인데 매개변수에 따라 여러개 존재
        //
    }
}