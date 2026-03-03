package com.smu8.oop;

class Person{
    String name;
    int age;
    double score;
    // 전역에 선언된 변수, 전역변수 , 필드 (객체의상태) , 속성
    public void test(){
        String name = "테스터"; // 테스트가 호출되면 만들어지는 지역변수
        System.out.println("name :" + name);
        System.out.println("this.name :" + this.name);

    }
}

public class L05Field {
    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(p.name); // null
        System.out.println(p.age);  // 0
        System.out.println(p.score);// 0.0

        p.name = "제가링";
        p.age = 28;
        p.score = 94.0;
        System.out.println(p.name);
        System.out.println(p.age);
        System.out.println(p.score);

        int a; // 지역변수 main의 지역변수임
        // a 가 생성되었지만, 초기화되지 않았다 => 정의되지않은(UndeFined) == 절!!!!!!!!!!대 사용금지
        int b = 10; // b가 생성되면서 10으로 초기화 되었다.
        // System.out.println(a); 컴파일 오류 ㄷㄷ
        // p.test.name = "제갈"; 객체의 지역변수는 접근하거나 바꿀수 없다.
        p.test(); // name : 테스터
        // this.name : 제가링
    }
}
