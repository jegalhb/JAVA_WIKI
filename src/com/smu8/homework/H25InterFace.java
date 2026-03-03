package com.smu8.homework;
//결재 시스템 초안 작성
interface PAYABLE{
    void pay(int amount);
}
class CardPAY implements Payable{
    @Override
    public void pay(int amount) {
        System.out.println("카드결제 완료!"+ amount+"원");
    }
}
class BankPAY implements Payable{
    @Override
    public void pay(int amount) {
        System.out.println("계좌이체 완료!" + amount+"원");
    }
}
class MAin{
    //void process(CardPay pay , int amount){} // 카드 뱅크 동시 결재 가능
    //void process(BankPay pay , int amount){} // 카드 뱅크 동시 결재 가능
    public void process(Payable pay,int amount){
        System.out.println("결제시스템에 접속합니다!");
        System.out.println(amount +"원을 결제하시겠습니까?");
        pay.pay(amount);
    }
}


public class H25InterFace {
    public static void main(String[] args) {
        MAin main = new MAin();
        main.process(new CardPAY(),50000);
        main.process(new BankPAY(),30000);
    }
}
