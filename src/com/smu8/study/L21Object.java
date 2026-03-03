package com.smu8.study;
class Person{
    // public Person {}// 기본생성자 : 작성하지 않아도 컴파일러가 자동 생성
    // 필드 , 메소드 , 생성자
    String id = "981221-0000000"; // 필드
    String name = "제갈현빈"; // 필드
    int birth=1998; // 필드
    // . 필드 접근자(자식.속성)로 필드에 접근할 수 있나?- 가능
    public void say(){ // 함수=>동작(함수도 필드지만 함수와 필드를 구분해서 말함)
        System.out.println("나는"+name+"이야잇~");
    }
} // 오브젝트는 형이 필요함

public class L21Object {
    public static void main(String[] args) {
        new com.smu8.study.Person(); //객체 생성
        new Person();//같은 패키지에 있거나 import를 하면 패키지는 생략가능

       // 변수 : 데이터를 재사용
       Person p= new Person(); // 객체를 만들고 변수가 참조
        var p2=new Person(); // var 타입을 생략
        System.out.println(p.id+","+p.name+","+p.birth);
        p.name="제가릿";
        p.say();
        int i = 10;
        System.out.println(i);
        //System.out.println(5.0 >> 2);
        //기본형과 자료형의 차이
        //1.소문자로 시작
        //2.리터럴하게 표기 ; 눈에 보이는 그대로 표기
        //3. 필드가 없다(기본형=원시데이터 == 수만 존재)
    }
}
