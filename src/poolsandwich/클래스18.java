package poolsandwich;

import java.util.*;

interface Greeting{
    void sayHello();
}
interface HI{
    void sayHI();
}
interface Meet{
    void call();
}
interface Runnable{
    void run();
}

interface banana{
    void sweet();
}
interface Car2 {
    void Front();

            void Side();

                    void Rear();

                            void OtherExterior();

}
interface Car3 extends Car2{
    void FrontRear();
}
interface Car4 extends Car2{
    void SideOtherExterior();
}
interface kiwi{}
interface apple{}
public class 클래스18 {
    public static void main(String[] args) {
        Greeting greeting = new Greeting() {
            @Override
            public void sayHello() {
                System.out.println("익명클래스입니다");
            }
        };
        greeting.sayHello();

        HI hi = new HI() {
            @Override
            public void sayHI() {
                System.out.println("하이입니다");
            }
        };
        hi.sayHI();
        Meet meet = new Meet() {
            @Override
            public void call() {
                System.out.println("만나서 인사해요");
            }
        };
        meet.call();

        List<String> list = Arrays.asList("banana,kiwi,apple");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
        System.out.println(list);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        };
        r.run();
        banana B = new banana() {
            @Override
            public void sweet() {
                System.out.println("아유 달아");
            }
        };
        B.sweet();
        Car3 front = new Car3() {
            @Override
            public void Front() {
                System.out.println("프론트 :후드,본네트");
            }

            @Override
            public void Side() {
                System.out.println("사이드 : 사이드미러,본네트");
            }

            @Override
            public void Rear() {
                System.out.println("후면 : 트렁크, 뒷범퍼");
            }

            @Override
            public void OtherExterior() {
                System.out.println("기타 외부 : 지붕,썬루프");
            }
            @Override
            public void FrontRear() {
                System.out.println("전면 + 후면!");
            }
        };
        
        front.Front();
        front.OtherExterior();
        front.Rear();
        front.Side();
        Car3 car3 = new Car3() {
            @Override
            public void FrontRear() {
                System.out.println("전면과 후면!");
                front.Front(); //
                front.Rear(); //
            }

            @Override
            public void Front() {

            }

            @Override
            public void Side() {

            }

            @Override
            public void Rear() {

            }

            @Override
            public void OtherExterior() {

            }
        };
        Car4 car4 = new Car4() {
            @Override
            public void SideOtherExterior() {
                System.out.println("사이드 + 기타 외부");
            }

            @Override
            public void Front() {

            }

            @Override
            public void Side() {

            }

            @Override
            public void Rear() {

            }

            @Override
            public void OtherExterior() {

            }
        };
        System.out.println("=====================");
        car3.FrontRear();
        car4.SideOtherExterior();

}
    }
