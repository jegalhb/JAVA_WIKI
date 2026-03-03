package com.smu8.study;

public class L33WrapperClass {
    public static void main(String[] args) {
        // 랩퍼클래스 : 기본형의 자료형
        String str = "123123";
        String str2 = "12313";
        //문자열을 정수로 바꾼 후 더한 결과
        int a = Integer.parseInt(str);
        int b = Integer.parseInt(str2);
        System.out.println(a+b);

        //Byte , Short  , Integer , Long , Float , Character , Boolean
        // Integer 가 문자열을 정수로
        a = 45678;
        b = 90;
        // System.out.println(a+""+b);
        System.out.println(Integer.toString(a) + Integer.toString(b));
        // Integer 가 정수를 문자열로

        // int 의 최대 , 최솟값 확인
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);

        System.out.println(Double.parseDouble("10.11") + Double.parseDouble("11.11"));

        System.out.println("==================");
        // parse *(*== 전부라는 뜻 ) : 강제형변환 (오류동반)
        // int i = Integer.parseInt("A");
        // 런타임 오류 : jvm이 종료되기 때문에 위험한 오류 ( 예외 처리 해야함)

        // 기본형을 자료형으로 형변환
        Integer obj = 10; // auto boxing 자동 형변환
        Integer obj2 = Integer.valueOf(10); // 정상적인 방법 : 같은 값인 객체가 있으면 객체를 재사용
        Integer obj3 = 10;
        // Integer obj3 = new Integer(10); 값과 상관 없이 매번 객체 생성
        System.out.println(obj.toString());
        System.out.println(obj2.shortValue());
        // System.out.println(obj.hashCode()); // hashCode == 저장된주소
        System.out.println(obj+obj2); // 자료형이 기본형으로 형변환
// 기본형 = 0 자료형 == null

    }
}
