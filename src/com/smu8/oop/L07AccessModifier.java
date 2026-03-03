package com.smu8.oop;
public class L07AccessModifier {
    public int a; // 누구나 다 접근함
    int b; // default : 같은 패키지에 있는 클래스가 접근 가능
    protected int c; // protected : 상속관계에서 접근 가능(상속시간에 배울 예정)
    private int d; // 같은 클래스 내부에서 접근 가능

    // 같은 클래스 내부에서 접근 가능한가??????
    // == 같은 클래스 내부에서는 모두모두모두모두 접근 가능 내꺼니까.
    // 내부지정자 == 어디까지 접근을 가능하게 할 것인가를 정하는 문법
    void set(){
        this.a = 100;
        this.b = 200;
        this.c = 300;
        this.d = 400;
    }
}


class AccessModifier{
    // com.smu8.oop.AccessModifier 에서 같은 패키지에 있는 L07AccessModifier의 필드 중
    // 어떤 것을 접근할 수 있냐??
    public static void main(String[] args) {
        L07AccessModifier m = new L07AccessModifier();
        m.a = 10;
        m.b = 20;
        m.c = 30;
        // m.d = 40; : 컴파일 오류
    }


}
