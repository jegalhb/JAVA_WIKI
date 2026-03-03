package poolsandwich.toopool;

import java.util.Optional;

public class 연습용20Optional {
    static void main() {
        Optional<String> name=Optional.ofNullable("abcde");
        Optional<Integer> age=Optional.of(28);
        Optional<String> say=Optional.of("늙엇엉");
        name.ifPresent((nam)->{
            System.out.println(nam.toUpperCase());
        });
        System.out.println(name); // 본 name은 바뀌지않음




        Optional<String>OPT=Optional.of("123");
        Optional<Optional<Integer>>NESTED;
        OPT.map((s)-> say.isPresent());

        Optional<Optional<Integer>> nested;
        Optional<Integer> flat = OPT.flatMap(s -> parseIntSafe(s));
        Optional<Integer> flat2=OPT.flatMap(s->parseIntSafe(s));
        OPT.map(s->parseIntSafe(s));


    }

    static Optional<Integer> parseIntSafe(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
