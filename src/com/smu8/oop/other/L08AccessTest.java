package com.smu8.oop.other;
// com.smu8.oop.LO7AccessModfier : 다른 패키지의 클래스 호출
import com.smu8.oop.L07AccessModifier;

class ProtectedTest extends L07AccessModifier{
    public ProtectedTest(){
        L07AccessModifier m=new L07AccessModifier();
        m.a=111;
        //m.b=111;
        //m.c=111;
        //m.d=111;
        // 객체를 만들지 않아도 상속받은, 상속받은 부모가 객체로 존재
        // 이때 부모객체를 super 로 참조
        super.a=1111;
        //super.b=1111;
        super.c=1111; // protected
        //super.d=1111;
    }

}


public class L08AccessTest {
    public static void main(String[] args) {
        L07AccessModifier m = new L07AccessModifier();
        m.a=11; // public : 어디서든
        // m.b=22; // default : 다른 패키지라 오류
        // m.c=33; // protected // 상속관계가 아니라 오류
        // m.d=44; // private // 같은 클래스내부에서만 가능하기에 오류
    }
}
