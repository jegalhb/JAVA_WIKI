package poolsandwich.example;

public class ButtonMain {
    static Button[] getButtonArray() {
        Button[] buttons = new Button[3];

        buttons[0] = new HardButton();
        buttons[1] = new BigButton();
        buttons[2] = new NormalButton();

        return buttons;
    }
    public static void main(String[] args) {
        Button[] buttons = getButtonArray();
        // buttons[0] = new Button();
        /*
        * 버튼을 전부 누릅니다.
        * */
        for (int i = 0; i < buttons.length; i++) {
            System.out.println(buttons[i].click()); // 각 버튼마다 다른 동작 <-- 다형성
        }
//        System.out.println(new HardButton().info()); // <-- 상속받은 메소드 호출
        System.out.println("buttons[0].size is reset");
        buttons[0].setSize(10);
        System.out.println("buttons[0].size == " + buttons[0].getSize()); // private 멤버변수는 다른 클래스에서 접근 불가
        System.out.println("buttons[0].size is reset");
        buttons[0].setSize(-4); // 유효하지 않은 값을 설정하는 경우
        System.out.println("buttons[0].size == " + buttons[0].getSize()); // 유효하지 않은 값으로부터 보호 <-- 캡슐화
    }
}
