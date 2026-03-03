package poolsandwich.example.statictest;

public class StaticTest {
    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(Car.carTotal);
        new Car();
        System.out.println(Car.carTotal);
    }
}
