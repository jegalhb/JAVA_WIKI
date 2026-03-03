package poolsandwich;

interface Bananana{
    int So_Sweet(int a, int b);
}
interface para{

}

public class 연습용18람다다람다 {
    public static void main(String[] args) {
        Bananana B =(int a , int b)->{return a+b;};
        System.out.println(B.So_Sweet(40,60));

        // @오버라이드중
    }

}
