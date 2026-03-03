package com.smu8.oop;

class X{ // 부모
    public X (){
        super(); // 생성자에서 부모생성자를 호출하지 않으면 컴파일러가 자동완성!? 강제성!
        System.out.println("X기본 생성자 호출");
    }
    int a=10;
}
class Y extends X{ // 부모
    int b=20;
    public Y(){
        super();
        System.out.println("Y기본 생성자 호출");
    }
public Y(int b){ // 자식
        this.b=b;
    System.out.println("Y(int b 생성자 호출" );
}
}
// extends X Y : 오류 다중상속 불가 : 두 클래스의 필드나 함수가 같은 이름일 때 어떤것을 사용할지 알 수 없어서
// 두 클래스의 우선순위를 알 수 없어서.
class Z extends Y {
    public Z(){ // 기본 생성자
        super(200);
        System.out.println("Z 기본생성자 호출");
        //super(200); // 언제나 부모가 객체로 만들어져야하기 때문에 맨위에서만 작성가능
        // 부모가 만들어지기 전에 자식이 어떤 코드도 실행할 수 없다.
    }
    int c = 30;

}



public class L12ExtendsConstructor {
    public L12ExtendsConstructor(){
    }

    public static void main(String[] args) {
        Z z = new Z();
        System.out.println(z.a); //  X -> a
        System.out.println(z.b); //  Y -> b
        System.out.println(z.c); //  Z -> c
         // Z는 부모인 Y, X 객체로 가진다 (이 객체를 super로 참조)
        // 객체를 생성하려면 무조건 생성자가 호출되어야합니다.
    }
}
