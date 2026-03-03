package com.smu8.oop;
class anonymous1 implements Payable{ // 이하 코드를 컴파일러가 컴파일 할 때 자동완성하는 클래스!
    @Override
    public void pay() {
        System.out.println("계좌 이체를 진행합니두");
    }
};

interface Payable{
    void pay();
}
class CardPay implements Payable{

    @Override
    public void pay() {
        System.out.println("카드결젱");
    }
}

public class L22AnonymousClass {
    public static void main(String[] args) {
        //Payable payable = new P
        Payable CardPay = new CardPay();
        CardPay.pay();
        //클래스를 만들고 인터페이스를 구현하고~ 객체를 생성하는 것이.... 귀찮다!
        // == 이렇게 나오게 된것이 익명클래스 ㄷㄷ
        // 인스턴스 생성 : new 생성자();
        // 익명클래스   :   new 생성자(){};
        Payable bankPay = new Payable(){

            @Override
            public void pay() {
                System.out.println("계좌 이체를 진행합니두");
            }
        };
        bankPay.pay();
    }
}
