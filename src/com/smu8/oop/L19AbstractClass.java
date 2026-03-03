package com.smu8.oop;
// 결재 시스템 구현
// 카카에페이 , 계좌이체 , 카드 ...etc
// 결제시스템 결제(결제는 시스템 마다 방식이 다름) , save , print
// Payment : 미완성 클래스 => 사용불가
abstract class Payment{
    abstract public void pay(int money); // {본문 , 바디} == 실제 코드!
    public void save (int money){
        System.out.println(money+ " 결제 저장!");
    }
    public void print(int money){
        System.out.println(money + " 결제 되었습니다. 감사합니다!");
    }
}
class NaverPay extends Payment{
    @Override
    public void pay(int money){
        System.out.println(money + "이 네이버 페이로 결재됩니다.");
    }
}
class KakaoPay extends Payment {
    @Override
    public void pay(int money) {
        System.out.println(money + "이 카카오페이로 결제됩니당");
    }
}
class Card extends Payment{
    @Override
    public void pay(int money) {
        System.out.println(money + "이 카드로 결재됩니다");
    }
}



public class L19AbstractClass {
    public static void main(String[] args) {
       // Payment payment = new Payment(); : 추상 클래스는 미완성 클래스라 사용못함
        Payment naver = new NaverPay();
        naver.pay(30000);
        Payment kakao = new KakaoPay();
        kakao.pay(40000);
        Payment card = new Card();
        card.pay(50000);
    }
}
