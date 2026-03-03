package poolsandwich.example.car;

public class CarMain {
    public static void main(String[] args) {
        Car car1 = new Car();
        car1.licensePlate();
        car1.setRear(new Rear() {
            @Override
            public void licensePlate() {
                System.out.println("도난당한 번호판");
            }
        });
        car1.licensePlate();

        car1.getRear();
        car1.setSide(new Side(){
            @Override
            public void door(){
                System.out.println("문이 박살났음");
            }
        });
        car1.door();
        System.out.println("=============================");
        car1.info();
        car1.setFront(new Front() {
            @Override
            public void hood() {
                System.out.println("""
                        와 우리는 자바를 배웠고 객체도 배웠고 앞으로도 많이 배울거에요.
                        와하하하하핳
                        """);
            }
        });
        car1.info();
    }
}
