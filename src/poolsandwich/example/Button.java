package poolsandwich.example;

public abstract class Button {
    private int size;
    /*
    *
    * 버튼이면 모두 클릭하는 기능이 있습니다.
    * 근데 버튼마다 클릭하면 하는 행동이 다릅니다.
    * */
    public abstract String click(); // 공통된 규격만 정의하고 구현은 상속받은 클래스가 담당 <-- 추상화
    public String info() {
        return "This is a Button";
    }
    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {
        if (size < 1) {
            System.out.println("Invalid Size");
        } else {
            this.size = size;
        }
    }
}
