package com.smu8.oop;
import java.lang.*; // String , Integer , Exception , Object
// 자바의 기본패키지 : 자바 문법에 필요한 기본 클래스 제공
class ObjTest extends Object{

}
// 모든 클래스는 자동으로 Object를 상속받는다.
public class L15Object{
    public static void main(String[] args) {
     ObjTest objTest = new ObjTest();
     ObjTest objTest2 = new ObjTest();
        System.out.println(objTest.toString());// com.smu8.oop.ObjTest@2f4d3709 (타입 + 식별자 16진수)
        System.out.println(objTest.hashCode());// 793589513 : 객체의 식별자(10진수)
        // 자료형 동등비교연산 : 주소비교
        System.out.println(objTest2.hashCode());
        System.out.println(objTest==objTest2);
        //object.eqyals() : 객체 두개가 논리적으로 같나? (같은타입 , 상태가 같다 )  = > 무조건 재정의
        System.out.println(objTest.equals(objTest2)); // false
        //String.eculas
        String str = "안녕하세요";
        String str2 = new String("안녕하세요");
        System.out.println(str==str2);
        System.out.println(str.equals(str2));


    }
}
