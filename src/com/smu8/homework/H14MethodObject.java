package com.smu8.homework;

public class H14MethodObject {

    public static int sumTo(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
    public static int symTO(int n){
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;

        }
return n;

    }
    public static int max(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }


    public static int add(int a, int b) {
        return a + b;
    }

    public static int add(int a, int b, int c) {
        return a + b + c;
    }  // 오버 로딩 예시



    public static void main(String[] args) {

        int sum = 0;

        H14MethodObject SUMTEST = new H14MethodObject();
        System.out.println(sumTo(3));
        System.out.println(sumTo(5));
        System.out.println("==================");

        /* public static int max(int a, int b) {
            if (a >= b) {
                return a;
            } else {
                return b;
            }
        }
         */
        H14MethodObject test = new H14MethodObject();
        System.out.println(test.max(2,2));
        System.out.println(H14MethodObject.max(2,4));

        System.out.println("==================");


// 코드 재사용 , 추상화
        int isEven = 0;
        H14MethodObject ISEVEN = new H14MethodObject();
        System.out.println(ISEVEN.max(7,8));
        System.out.println(isEven(3));
        System.out.println("==================");





    }
}
