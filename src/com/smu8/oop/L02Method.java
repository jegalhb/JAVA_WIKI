package com.smu8.oop;
// 계산기 타입
public class L02Method {
    // 타입을 만드는 행위
    static class Cals{
        //com.smu.oop :클래스의 이름은 패키지(회사 도메인) +클래스명
        // 우리가 만든 class (지적재산)을 유일한 이름을 만들기 위해!
        // class {전체영역 == 전역,root영역}
        static int a; // 전역에 선언된 변수 == 필드! == 객체의 상태
        static int b; //
        public Cals(){} // 생성자 전역에 생성
        public int sum (){ // 함수 : 객체의 기능
            return a+b; // int : ( sum ()을 실행하면 int가 나온다
        }
        // public double sum(){}
        // 오류
        // 1. 함수 이름 앞에 void대신 타입을 쓰면 무조건 return을 작성해야함 : 함수가 반환을 약속했기 때문
        // 2. sum이 있기 때문에 똑같은 이름의 sum을 사용할 수 없다.
        // 3. 이름이 같은 함수인경우 == 매개변수가 다르면 이름이 같아도 허용된다 == 오버로드, 오버로딩
        public static double sum(double a, double b){
            double result= 0.0;
            result = a+b;
            return result;
        }
    }


// main : JVM을 호출하는 함수 -> java앱
    // main 앱을 실행한다 : JVM(java)을 호출해서  main 에 작성된 코드를 순서대로 실행!

    public static void main(String[] args) {
        System.out.println("계산기 어플 입니다");
        new Cals(); // 객체 (Calc 자료형 [타입]으로 만든 데이터)
        int sum= new Cals().sum();
        System.out.println(sum);

        Cals cals = new Cals();// new 연산자로 생성자를 호출하면 생성자를 포함하는 타입의 객체를 반환
        Cals.a=10;
        Cals.b=20;
        sum= cals.sum();
        System.out.println(sum);

        System.out.println(Cals.sum(11.1,22.2));
    }
    public void sum(int a, int b, int c){ //void는 반환하는게 없다 return이 필요가 없음
        System.out.println(a+b+c);
        return; // void 는 리턴을 작성할 수 있지만 아무것도 작성하지 않음!
        // 리턴 : 함수의 종료
        // System.out.println("입니다"); // 도달할 수 없는 구문
    }
}
