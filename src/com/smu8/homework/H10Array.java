package com.smu8.homework;

public class H10Array {
    public static void main(String[] args) {
        int[] data = {3, 6, 9, 12};
        for (int i = 0; i < data.length; i++) {
            System.out.println("배열 모두 출력!"+data[i]);
            }

        int [] nums= new int[3];
        nums[0] = 10;
        nums[1] = 20;
        nums[2] = 30;
        System.out.println(nums[1]);
        // = 20 출력 ㄷㄷ
        int [] arr = new int[3];
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println(arr[2]);

        int[] ar= {5,10,15,20};
        System.out.println(ar.length); // length = 전 배열의 갯수를 표기함

        //int[] data ={3,6,9,12};
        for (int i = 0; i < data.length; i++) {
            System.out.println("배열 모두 출력"+data);
        }



    }

}