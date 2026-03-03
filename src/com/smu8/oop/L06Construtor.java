package com.smu8.oop;

class student {
    int ID;
    int birth;
    String name;
    String email;

    public student(int id , int birth, String name) { // 기본생성자
        this.ID=id;
        this.birth=birth;
        this.name=name;
    }

    // to String(); : 클래스를 만들면 자동으로 존재, 객체를 설명 == 필드를 설명하는 용도
    // 대부분 개발툴에서 toString 자동 완성을 지원

    @Override // 기존에 toString 을 새로만든거로 대체
    public String toString() {
        return "student{" +
                "ID=" + ID +
                ", birth=" + birth +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    public student(){}
    public student(int id, int birth , String name ,String email){
        this(id, birth, name);
        this.email=email;
    }
    public student getMyself() {
        return this;
    }
}


public class L06Construtor {
    public static void main(String[] args) {
        //학생을 생성할 때 학생은 꼭 이름과 나이 그리고 학번이 있어야함.
        student s = new student();
        s.ID= 1234;
        s.birth= 2008;
        s.name= "철수";
        s.email="ij941212@naver.com";

        System.out.println(s); // 자동으로  toString()
        // 객체 설명의 기본값 : 타입+저장된 메모리 위치 => 필드로 바꿀수 있다.
        System.out.println(s.toString());


        student s2 = new student(1244,2001,"제갈");
        System.out.println(s2);
        student s3 = new student(1000,1998,"밍밍","ej918@naver.com");
        System.out.println(s3);
        // 1. 모든 필드를 초기화 하는 생성자를 만들어라
        // 2. 모든 필드를 초기화 하는 생성자에서 id birth name을 초기화 하는 생성자를 이용하세요!

        student s4 = s2.getMyself();
        System.out.println(s2.equals(s4));
        System.out.println(s2 == s4);
    }

}
