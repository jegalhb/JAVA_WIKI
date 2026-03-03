package com.smu8.homework;

class Animall {
    void sound() { System.out.println("동물"); }
}
class Dog extends Animal {
    @Override
    String sound() { System.out.println("멍멍");
        return null;
    }
    void fetch() { System.out.println("물어오기"); }
}
class Cat extends Animal {
    @Override
    String sound() { System.out.println("야옹");
        return null;
    }
}
class Zoo {
    void test(Animal a) {
        a.sound();
        if (a instanceof Dog d) {
            d.fetch();
        }
    }
}
public class Main {
    static void test(Animal a) {
        a.sound();
        if (a instanceof Dog d) {
            d.fetch();
        }
    }

    public static void main(String[] args) {
        Zoo z=new Zoo();
        z.test(new Dog());
        z.test(new Cat());
    }
}
