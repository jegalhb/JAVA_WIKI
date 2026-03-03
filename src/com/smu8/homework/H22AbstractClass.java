package com.smu8.homework;

abstract class Shape {
    public abstract double area();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return radius * radius * 3.141592653589793;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

public class H22AbstractClass {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[2];
        shapes[0] = new Circle(3);
        shapes[1] = new Rectangle(4, 5);

        for (int i = 0; i < shapes.length; i++) {
            System.out.println(shapes[i].area());
        }
    }
}
