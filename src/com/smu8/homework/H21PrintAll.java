package com.smu8.homework;

import poolsandwich.example.outer.C;

public class H21PrintAll {
    //static 독립적 존재 데이터 정적멤버
    public static void printAll(Object o){
        String str = o.toString();
        System.out.println("무엇이든 출력하라");
    }
    public static void main(String[] args) {
        Dog d= new Dog();
        Cat c= new Cat();
        H21PrintAll h11 = new H21PrintAll();

        printAll(d);
        printAll(c);
        printAll(h11);
    }
}
