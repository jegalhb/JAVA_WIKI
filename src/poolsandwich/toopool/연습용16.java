package poolsandwich.toopool;
//Payment 추상 클래스에 다음을 구성하시오.
//log() 일반 메서드: “결제 로그 기록” 출력
//pay(int amount) 추상 메서드
//CardPayment, BankPayment 클래스를 만들어 pay를 각각 구현하고, Main에서 업캐스팅으로 실행하시오.
abstract class Payment {
    abstract public void pay(int amount);

    public void save (int amount) {System.out.println(amount+ "결재 저장");}
    public void print (int amount) {
        System.out.println(amount + "결재 되었습니다");
        //this.pay(100);
    }
}
class CardPayment extends Payment{
    @Override
    public void pay(int amount) {
        //this.save(amount); // 결제 로직이라고 가정
        //this.print(amount); // 로그
        System.out.println(amount + "원이 CardPayment로 결재 되었습니다");
    }
}
class BankPayment extends Payment{
    @Override
    public void pay(int amount) {
        //this.save(amount);
        //this.print(amount);
        System.out.println(amount +"원이 BankPayment로 결재 되었습니다.");
    }
}
public class 연습용16 {
    public static void main(String[] args) {
        CardPayment cardPayment = new CardPayment();
        cardPayment.pay(50000);

        BankPayment bankPayment = new BankPayment();
        bankPayment.pay(90000);

        Payment p = new CardPayment();
        p.pay(50000);
        Payment p2 = new BankPayment();
        p2.pay(80000);

    }
}
