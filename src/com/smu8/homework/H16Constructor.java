package com.smu8.homework;

class  book{
    String bookname;
    int price;
}


public class H16Constructor {
    public static class Student {
        String name;
        int score;

        public Student(String name , int score){
        this.name = name;
        this.score = score;
        }
        public void book(String bookname,int price){
            this.bookname();
        }

        private void bookname() {
            bookname();
        }

    }
    public static void main(String[] args) {
        Student s1 = new Student("제갈",90);
        System.out.println(s1.name);
        System.out.println(s1.score);

        Student s2 = new Student("어진",100);
        System.out.println(s2.name);
        System.out.println(s2.score);

        book b1 = new book();
        b1.bookname = "제갈북";
        b1.price = 20000;
        System.out.println(b1);


        book b2 = new book();
        b2.bookname = "정글북";
        b2.price = 40000;
        System.out.println(b2);


    }
}
