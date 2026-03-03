package poolsandwich.example.outer.side.inner;

import poolsandwich.example.outer.side.B;

public class A {
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
    public boolean isThan(A a) {
        return this.a > a.a; // return this.a > a.getA();
    }

    private int a;

    public static void main(String[] args) {
        B b = new B(2);
        // System.out.println(b.b); // <-- error
        A a = new A();
        a.a = -2;
        System.out.println(a.a);
    }

}
