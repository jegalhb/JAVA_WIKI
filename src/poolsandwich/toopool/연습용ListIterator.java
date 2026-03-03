package poolsandwich.toopool;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 연습용ListIterator {
    static void main() {
        List<String>fruit = new ArrayList<>(Arrays.asList("딩고","밍고","쟁고","봉고"));
        System.out.println(fruit.get(0));
        System.out.println(fruit.get(1));
        System.out.println(fruit.get(2));
        System.out.println(fruit.get(3));

        for (int i = 0; i < fruit.size(); i++) {
            System.out.println(fruit.get(i));
        }
        System.out.println("---------------");
        for(String fruitit : fruit){
            System.out.print(fruitit);
        }
    }
}
