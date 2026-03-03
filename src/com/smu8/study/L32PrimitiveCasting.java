package com.smu8.study;

public class L32PrimitiveCasting {
    public static void main(String[] args) {
        //기본형의 형변환과 캐스팅
        char c = '안';
        System.out.println(c);
        System.out.println((short)c);
        System.out.println((int)c);
        // Casting : 캐스팅 형변환은 자연스럽지 않은 형변환 (강제성)
        int i = 2000000000;
        long l =2000000000000000000l;
        float f=200000000000000000000000000000000000000f;
        Double d = 1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000d;

        long CastLong =i; // long이 i보다 더 큰수를 표현해서 자연스럽게 형변환
        // int CastInt = l; //
        int CastInt=(int)l;
        System.out.println(CastLong); // 1321730048
        CastLong = (long)f;
        System.out.println(CastLong);
        // long 보다 큰 수의 실수를 long으로 바꾸면 overfLOW가 아닌 최대 수
        CastInt=(int) f;
        System.out.println(CastInt);

        double castDouble=f;
        float castFlat = (float)f;
        System.out.println(castFlat);

        // 캐스팅
        // 큰 정수 -> 작은정수 : 버림
        // 실수 -> 정수 : 최대, 최소값
        // 큰실수 -> 작은수 : +- Infinity
    }
}
