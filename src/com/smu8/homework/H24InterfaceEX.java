package com.smu8.homework;

/**
 * 결제 인터페이스
 */
interface Payable{
    /**
     * 결제를 하는 메소드
     * @param amount
     * 결제할 금액
     */
    void pay(int amount);
}
class CardPay implements Payable {
    @Override
    public void pay(int amount) {
        System.out.println("카드 결제: " + amount);
    }
}
class BankPay implements Payable{
    @Override
    public void pay(int amount) {
        System.out.println("뱅크 결제 :" + amount);
    }
}

public class H24InterfaceEX {
    static void process(Payable p , int amount){
        p.pay(amount);
    }
    public static void main(String[] args) {
    //process를 이용해 카드 결제 1회, 계좌 이체 1회를 실행
        CardPay c = new CardPay();
        BankPay b = new BankPay();
        Payable p = c;
        p.pay(5000);
        process(c,5000);
        process(b,5000);
    }
}
