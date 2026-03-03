package poolsandwich;

import poolsandwich.example.outer.C;

abstract class Shape{
    abstract public double area();
}
class Rectangle extends Shape{
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    @Override
    public double area() {
        return width*height;
    }
}
class Circle extends Shape {
    private double radius;
    public static final double PI=3.1415;
    public Circle(double radius) {
        this.radius = radius;
    }
    public double area(){
        return this.radius*this.radius*Circle.PI;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

public class 연습용17 {
    public static void main(String[] args) {
        Circle circle = new Circle(10.0);
        Circle circle2 = new Circle(12.0);
        Circle circle3 = new Circle(14.0);
        Rectangle rectangle = new Rectangle(9,7);
        Rectangle rectangle2 = new Rectangle(9,9);
        Rectangle rectangle3 = new Rectangle(11,11);
        Rectangle rectangle4 = new Rectangle(13,13);

        Shape [] shapeArr ={circle,circle2,circle3,rectangle,rectangle2,rectangle3,rectangle4};
        for (int i = 0; i < shapeArr.length; i++) {
            System.out.println(shapeArr[i].getClass() + "." + shapeArr[i].area());
        }
    }
}
