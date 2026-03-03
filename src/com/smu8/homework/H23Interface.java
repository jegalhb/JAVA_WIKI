package com.smu8.homework;
//Developer 인터페이스: work(), coding()
//Designer 인터페이스: work(), design()
//Person 클래스는 Developer, Designer를 동시에 구현
//work()는 “출근 한다” 출력
//Main에서 Developer 타입과 Designer 타입으로 업캐스팅하여 work()를 각각 호출
interface Developer{
    void work();
    void coding();
}
interface Designer{
    void work();
    void Design();
}
class Person implements Developer , Designer{
    @Override
    public void Design() {
        System.out.println("디자인한다!");
    }

    @Override
    public void work() {
        System.out.println("일한다!");
    }

    @Override
    public void coding() {
        System.out.println("코딩한다!");
    }
}
public class H23Interface {
    public static void main(String[] args) {

        Developer d = new Person();
        Designer  s = new Person();
        d.work();
        s.work();
    }
}
