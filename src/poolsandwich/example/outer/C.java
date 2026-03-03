package poolsandwich.example.outer;

import poolsandwich.example.outer.side.B;
import poolsandwich.example.outer.side.inner.A;

public class C {
    public static void main(String[] args) {
        B b = new B(1);
        // System.out.println(b.b);// <-- error
        A a1 = new A();
        a1.setA(20);
        A a2 = new A();
        a2.setA(-10);
        System.out.println(a1.isThan(a2));
    }
}
