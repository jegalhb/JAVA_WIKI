package poolsandwich.toopool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 연습용20ListMethod {
    static void main() {
        List<String>animal= new ArrayList<>();
        animal.add("호랑이");
        animal.add("사슴");
        animal.add("노루");
        System.out.println(animal.size());
        // System.out.println(animal.indexOf(2));
        //animal.set(4,"개"); //IndexOutOfBoundsException
        animal.set(1,"제갈"); // 2번째를 제갈로 바꿧네유
        animal.remove(2); // 2번인 노루가 삭제!
        System.out.println(animal);
        System.out.println(animal.get(1)); // 제갈
        System.out.println(animal.contains("제갈")); // 제갈이라는게 있누? true
        System.out.println(animal.contains("호랑이"));
        // 계속 변수가 달라지는거니 조심할것 지금 호랑이 제갈뿐
        animal.clear(); // 리스트 전부 삭제
        System.out.println(animal); // 삭제 후 남은거~ []뿐
        System.out.println(animal.contains("제갈"));
        // 전부 삭제한게
        animal.add(0,"제갈");
        animal.add(1,"랑이");
        animal.add(2,"노루");
        System.out.println(animal);

        // 리스트 백업 방법!
        List<String> bUp =new ArrayList<>();
        //bUp = List.copyOf(animal); // bup 리스트 객체에 animal객체의 내용을 카피해서 집어넣음
        //animal = List.copyOf(bUp);
        for (String item : animal) {
            bUp.add(item);
        }

        // 기존 리스트 객체를 고정시켜서 백업해두는 것
        // 삭제를 해도 bUp에 들어있는 animal 리스트 객체는 사라지지않는다.
        bUp.add(1,"제가링링");
        System.out.println(bUp);
        //bUp = animal;
        animal.size();
        System.out.println(animal.size());
        System.out.println(animal);
        System.out.println("======");
        System.out.println(animal);
        System.out.println(bUp);
        animal.clear();
        System.out.println("=====");
        System.out.println(animal);
        System.out.println(bUp);


    }
}
