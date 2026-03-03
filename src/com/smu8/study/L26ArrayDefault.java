package com.smu8.study;

import java.util.Arrays;
import java.util.Date;

public class L26ArrayDefault {
    public static void main(String[] args) {
        // 배열의 기본값
        double [] nums= new double[3]; // {0..0}
        System.out.println(Arrays.toString(nums));

        int [] intnums = new int[3];
        System.out.println(Arrays.toString(intnums));
        boolean[] bArr=new boolean[3];
        System.out.println(Arrays.toString(bArr));
        char[] cArr = new char[3];
        System.out.println(Arrays.toString(cArr));
        // 기본형의 배열 기본값은 0

        int i= 0;
        String s="";
        s=null; // 참조하는 것이없다.
        String [] strArr = new String[3];
        System.out.println(Arrays.toString(strArr));

        Date [] dateArr = new Date[3];
        System.out.println(Arrays.toString(dateArr));

        // 자료형의 기본 배열값은 null
    }
}
// 필드의 기본값?
class A{
    int a; // 0
    String s; // null
}