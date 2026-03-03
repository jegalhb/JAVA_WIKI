package poolsandwich;

class GenericTEST<T>{
    public T a;
    // 현재 이상태는 Object이기에 형변환을 계속 해줘야함 + 잘못된 타입을 기재 시
    // 컴파일오류가 발생치 않고 런타임 오류가 발생하여 치명적
}
public class 연습용19Generic {
    /*class NumberBox <T extends Number> {
        private T value;
        private String str;
        public NumberBox(T value){
            T value1 = this.value;
        }
    }*/
    static void main() {
        GenericTEST g =new GenericTEST();
        g.a = "안농";
        g.a = 15;
        g.a = true;

        // Generic은 타입의 다향성의 단점을 보완해주는 역할!
        // 기본형은 안대구 랩퍼클래스만 가능!

        GenericTEST<Integer> g2 = new GenericTEST<Integer>();
        // g2.a = "안농!"; 현재 선언 매개변수가 Integer이기에 안댐
        g2.a = 29;
        System.out.println(g2.a);
    }

}
