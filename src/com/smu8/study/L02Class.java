package com.smu8.study;
class classTest{
    //java 문서에 작성된 다른 클래스는 컴파일시 .class로 분리
    //같은 패키지에 같은 이름의 class가 2개 존재할 수 없기 때문에 같은 이름의 자바문서(.java)를 만들 수 없다.

}
// public : 공유, 오픈된, 발간
// public class : 다른 사람이 가져다 쓸 수 있는 or 공유가 가능한 리소스, java문서의 주인
public class L02Class { // Root 조상 최상위 부모 영역
    /* = 여러 줄 주석
    {} : 영역 Scope
    {} : 제일 밖에 있는 영역 root
    {{}} : 자식
    {{}{}} : 형제-> 부모 가능 , 형제-형제 불가능
    " 자바의 기본 규칙 "
    1. 모든 코드는 class 내부에 존재 (class가 root다= 최상이라 는 뜻)
    2. 실행되는 코드 함수 내부에 존재
    3. 실행되는 코드 종료 시점에 ";"를 세미클론을 작성
    4. {} 영역은 꼭!! 열고 닫아야 한다.
    5. 빨간줄은 컴파일 오류 -> 실행 불가
     */
    public static void main(String[] args) {
        {
            System.out.println("안녕"); // 실행은 무조건 함수 내부에서 작성
//            System.out.println("안녕") // 무조건 코드가 종료되는 시점에 ";" 세미클론 사용함

        }
        {

        }

    }
}
