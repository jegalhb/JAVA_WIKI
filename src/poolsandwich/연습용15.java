package poolsandwich;

// cal 클래스 생성
// name 과 move를 생성
// Car 클래스는 생성할 때 name을 초기화 함
// car.move를 재정의 할껀데 car.move + battery -- 를 구현
class Car{
    String name;
    public Car(String name){
        this.name=name;
    }
    public void move(){
        System.out.println("움직인다");
    }
}
class ElectricCal extends Car{
    private int battery=100;
    public ElectricCal(String name){
        super(name);
    }
    public int getBattery(){
        return this.battery;
    }
    @Override
    public void move() {
        battery --;
        super.move();

    }
}

public class 연습용15 {
    public static void main(String[] args) {
        Car car = new Car("KIA :"); // poolsandwich.Car@2f4d3709
        System.out.print(car.name);
        car.move();

        ElectricCal electricCal = new ElectricCal("테슬라 :");
        System.out.print(electricCal.name);
        electricCal.move();
        electricCal.move();
        electricCal.move();
        System.out.print("남은 잔여 배터리 :");
        System.out.println(electricCal.getBattery());
        System.out.println(car.name);

    }
}
