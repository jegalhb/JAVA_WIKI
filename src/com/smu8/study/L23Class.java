package com.smu8.study;
import java.util.Date;

public class L23Class {
    public static void main(String[] args) {
        //데이터 : 기본형(수,원시형),자료형(복합적,참조형,인스턴스)
        int i=10;
        double d=10.0;
        char c= 'c';
        boolean b=true;
        // 기본형: 리터럴하게 표기 (보이는 그대로),소문자로만 타입명시
        // 자료형 : new 연산자로 생성자를 호출,대문자로 타입명시
        // 파스칼 규칙
        Date date = new Date(); // new Date (); 객체(Object,Instance) =>객체의 타입이 자료형,참조형
        System.out.println(date.toLocaleString()); //2026. 1. 15. 오전 10:49:51
        // System.out.println(i.); // 기본형은 수만 존재하는 데이터기 떄문에 다른 자료를 참조하지 않는다.
        //참조형 : 여러 데이터를 참조하는 것
        //참조형 : 조수(식별자)만 존재
        new Student();
        // 데이터 재사용 => 변수
        var s= new Student();
        s.hello();
        System.out.println(s); //com.smu8.study.Student@3f91beef (해당 객체의 타입 주소
        System.out.println(i); // int i
        // 참조형은 참조하는 데이터가 많아서 객체를 설명하기 곤란함 => 객체의 식별자인 주소만 출력 (참조형)
        // 기본형은 저장된 데이터를 설명가능(수 즉 원시 데이터기 때문) => 주소가 아닌 데이터가 출력(기본형이다)
        Student s2= new Student();
        s2. name = "제갈이";
        s2. hello();



    }
}
// main 을 실행하면 main이 포함된 패키지를 모두 로딩 후 저장 (메소드영역에)
// 이후에 main코드를 하나씩 실행
class Student{ //클래스는 파스칼표기법 스투던트라는 타입을 만듦 = 객체를 만든것
    //3가지 요소
    // 필드 : 저장할 데이터 (성적,이름,id)
    // 함수 : 객체의 기능 (인사를 하는 기능)
    //생성자: 생성할 때 호출됨
    String grade = "A"; // 전역에 필드 생성 / 복수의 데이터가 생성된것 자료형
    String name = "제가리";
    String id = "A640001";
    public Student(){
        System.out.println("Student 생성자 호출됨!");
    } // 기본 생성자 안써도 존재
    public void hello(){ //함수는 카멜 표기법 사용 // 기능부분
        System.out.println(name+"이 인사합니다. 하이");

    }

}
