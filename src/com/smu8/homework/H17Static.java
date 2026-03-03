package com.smu8.homework;
class Counter{
    static int count = 0;
    int x = 0;
    Counter(){
        count++;
    }
}
public class H17Static {
    public static void main(String[] args) {
        Counter a = new Counter();
        Counter b = new Counter();

        System.out.println(a.x); // 0
        System.out.println(b.x); // 0
        System.out.println(Counter.count); // 2

    }
}
