package poolsandwich.example.statictest;

public class Car {
    private String pw;
    int wheels;
    public String id;
    public static int carTotal = 0;
    public Car() {
        carTotal += 1;
    }
    public void printInfo() {
        System.out.println("id: " + id);
        System.out.println("wheels: " + wheels);
    }
    public static void info() {
        System.out.println("This is a Car.");
    }
}
