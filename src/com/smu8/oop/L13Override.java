package com.smu8.oop;

import poolsandwich.example.outer.C;

class Animal{
    String name= " 동물 ";
}
class Dog extends Animal {

}
class Cat extends Animal{
    String name = "고먕미";
}
class User {
    String name;
    // 생성자 작성시 기본 생성자는 삭제됨
    public User (String name) { // 생성자 규칙 이름을 꼭 초기값으로 진행하도록 수정
        this.name=name;
    }
    // 오버라이드
    public void say(){
        System.out.println("안녕하세요");
        // System.out.println("저는" + super.name);
        System.out.println("저는" + this.name); // 부모 것은 나의 것
    }
}
// class Customer extends User{ } // 오류 자식이 부모의 기본 생성자만 호출하기 때문에 오류
class Customer extends User{
    public Customer(){
        super("고객");
    }
    public Customer(String name){
        super(name);
    }
    // 오버라이드
    //@ : 컴파일 시 오류인지 검사 2 컴파일러가 자동완성
    @Override
    public void say(){
    System.out.println("안녕하세요");
    System.out.println("저는" + super.name);
    System.out.println("저는" + this.name); // 부모 것은 나의 것
}

}

public class L13Override { //  올라탄다 : 부모의것을 자식이 재정의한다
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();
        User user = new User("제갈");
        System.out.println(dog.name); //동물
        System.out.println(cat.name); // 고먐미
        System.out.println(user.name); // 제갈


        Customer customer = new Customer();
        Customer customer1 = new Customer("제갈고객");
        System.out.println(customer.name); // 고객
        customer.say();
        System.out.println(customer1.name); // 제갈고객
        customer1.say();
    }
}
