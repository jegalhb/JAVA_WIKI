package poolsandwich.example.car;
// 객체지향 총망라
// 전략 패턴
public class Car implements Front, Rear, Side {
    private Front front;
    private Side side;
    private Rear rear;

    public Car() {
        this.front = new Front() {
            @Override
            public void hood() {
                System.out.println("정보가 없음");
            }
        };
        this.side = new Side() {
            @Override
            public void door() {
                System.out.println("문 정보 없음");
            }
        };
        this.rear = new Rear() {
            @Override
            public void licensePlate() {
                System.out.println("번호판 없음");
            }
        };
    }
    public Car(Front front, Side side, Rear rear) {
        this.front = front;
        this.side = side;
        this.rear = rear;
    }

    public Front getFront() {
        return front;
    }

    public void setFront(Front front) {
        this.front = front;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Rear getRear() {
        return rear;
    }

    public void setRear(Rear rear) {
        this.rear = rear;
    }

    @Override
    public void hood() {
        this.front.hood();
    }

    @Override
    public void licensePlate() {
        this.rear.licensePlate();
    }

    @Override
    public void door() {
        this.side.door();
    }
    public void info() {
        this.front.hood();
        this.side.door();
        this.rear.licensePlate();
    }
}
