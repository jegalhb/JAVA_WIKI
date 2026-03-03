package com.smu8.oop;
//동물을 만들건디..... 동물은 걷고 말할껄? 근디 기능은 몰라 상상이안댐;;
interface Animalale{
    public static final int a=10;
    int b=20; // public static final : 어디서든 사용 가능한 정적멤버 (Animalable 객체와 관계 없음== 상속과 관련 없음)
    // interface 의 모든 함수는 자동으로 public abstract 작성된다!
    abstract public
    void walk();
    void sound();
    //void a(){}
    //Animalale(){}
    //interface는 객체가 될 수 없는 완전 추상화된 타입 ==> 필드 함수 생성자를 작성할 수 없다!!!!!!!!


}
class Tiger implements Animalale{
    int i = 0; // 객체에 포함
    static int j=0; // 얘는 따로 메소드 필드에 저장댐 (공유자원)

    @Override
    public void walk() {
        System.out.println("어슬렁~~어슬렁~~");
    }

    @Override
    public void sound() {
        System.out.println("어흥~~어흥~~");
    }
}

public class L20Interface {
    public static void main(String[] args) {
        // Animalale animalale = new Animalale(); 객체가 될 수 없음을 증명
        Animalale tiger = new Tiger();
        tiger.walk();
        tiger.sound();
        // tiger.a++; final은 상수이기에 못바꿩
        // a== 타이거의 필드
        // b == 타이거.b로 객체와는 관련이 없는 정적멤버
        Animalale tiger2 = new Tiger();

        Tiger t = new Tiger();
        t.i ++;
        //Tiger.j++;
        t.j++; // j필드가 아니다
        System.out.println(t.i);
        Tiger t2= new Tiger();
        t2.i ++;
        t2.j ++;
        System.out.println(t2.i);
    }
}
