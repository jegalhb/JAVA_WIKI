package com.smu8.oop;
//Car 클래스를 만드세요. (name,move())
//Car 클래스는 생성할때 name 을 초기화 함
class Car{
    String name;

    public Car(String name){
        this.name=name;  // 생성자 == 필드를 초기화!!
    }
    public void move(){
        System.out.println(this.name+"움직인다");
    }
}
class ElectricCar extends Car{
    private int battery =100;
    public ElectricCar(String name) {
        super(name);
    }
    public int getBattery(){
        return this.battery;
    }

    @Override
    public void move() {
        battery--;
        super.move();
    }
}


public class L14ExtendsEx {
    public static void main(String[] args) {
        Car car = new Car("그랜져");
        car.move();
        ElectricCar electricCar=new ElectricCar("테슬라x");
        electricCar.move();
        electricCar.move();
        electricCar.move();
        electricCar.move();
        electricCar.move();
        System.out.println(electricCar.getBattery());
    }
}
