package poolsandwich.example.toohard;

class A {
    public A() {
        this.call();
    }
    public void call() {
        System.out.println("A Method Call");
    }
}
class B extends A {
    public B() {
        super();
    }
    public void call() {
        System.out.println("B Method Call and A Method Overriding");
    }
}
public class Constructor {
    public static void main(String[] args) {
        new B();
    }
}
