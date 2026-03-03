package com.smu8.oop;

class Ani{
    String name;
    public Ani(String name){
        this.name=name;
    }
    void sound(){
        System.out.println("동물이 내는 소리");
    }
}
class Lion extends Ani{
    public Lion(){
        super("사자");
    }
    @Override
    void sound(){
        super.sound();
        System.out.println("어흥!@!@#!$!");
    }
    void run(){
        System.out.println(super.name + "가 달린다!");
    }
}
class Penguin extends Ani{
    public Penguin(){
        super("펭귄");
    }
    void sound(){
        System.out.println("꿰에에엑꾹");
    }
    void swim(){
        System.out.println(super.name + "이 수영한다!");
    }
}

public class L18DownCasting {
    public static void main(String[] args) {
        //Lion lion = new Lion();
        Ani animal = new Lion();
        System.out.println(animal.name);
        animal.sound();
        // animal.run(); // 부모타입의 필드나 함수만 참조
        // Lion lion = animal; 애니멀은 라이언 이나 펭귄이 될 수 있어서 안댐
        // 강제형변환 == casting
        //Lion lion=(Lion) animal;
        //lion.run();
        if(animal instanceof Lion lion){ // 자동 다운 캐스팅 java 21
            lion.run();
        }

        animal = new Penguin();
        System.out.println(animal.name);
        animal.sound();

        if (animal instanceof Penguin){ // 펭귄이면 형변환 하겠다
            Penguin penguin = (Penguin) animal;
            penguin.swim();
        }
        // animal.swim();

        //Lion lion2 = (Lion) animal; // 런타임 오류
        // animal 팽귄 객체를 참조하는데 lion 으로 바꾸고 있어서 오류
        // up캐스팅~
        // 부모 타입의 변수가 자식 객체를 참조하는 것
    }
}
