package datastructure;

public class ListMain {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.insert(0, "First");
        list.insert(1, "Second");
        System.out.println(list.select(0)); // First
        list.delete(0);
        System.out.println(list.select(0)); // Second
    }
}
