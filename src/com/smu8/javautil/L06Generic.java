package com.smu8.javautil;

import java.time.LocalDate;

class GenericTest<T>{ // <T extends Number> : 수 랩퍼클래스만 가능.
    public T o; // null
}


public class L06Generic {
    static void main() {
        GenericTest g = new GenericTest(); // <?> : T가 Object
        g.o="안녕";
        g.o=13; // Integer
        g.o=true; // boolean
        g.o= LocalDate.now(); // LocalDate
        // 타입의 다형성
        // 장점 : 어떤 타입의 객체든 부모 타입의 변수로 참조 가능
        // 단점 : 어떤 객체를 참조하고 있는지 파악하기 힘들다!

        // 제네릭: 타입의 다형성을 단점을 보안해준다
        // 제네릭: 기본형은 될수없다. 랩퍼클래스만 가능
        GenericTest<Integer> g2 = new GenericTest<>();
        //g2.o ="안녕";
        //g2.o = LocalDate.now();
        g2.o = 55;
        //g2.o= true;
        g2.o = (int)10.0;
        System.out.println(g2.o);

    }
}
