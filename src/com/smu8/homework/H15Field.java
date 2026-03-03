package com.smu8.homework;

class Book{
    String title;
    int price;

}

public class H15Field {
    public static class Student{
        String name;
        int score;
    }

    public static void main(String[] args) {
        Student student = new H15Field.Student();
        String name = "경민";
        int score = 99;
        System.out.println(student.name);
        System.out.println(student.score);

        Book B1 = new Book();
        B1.title = "JeGals book";
        B1.price = 50000;
        System.out.println(B1.title);
        System.out.println(B1.price);

        Book B2 = new Book();
        B2.title = "AJINs book";
        B2.price = 100000;
        System.out.println(B2.title);
        System.out.println(B2.price);

    }
}
