package poolsandwich.toopool;

import java.util.Locale;
import java.util.Optional;

public class 연습용21MethodReference {
    static void main() {
        Optional<String>opt = Optional.of("제갈");
        opt.ifPresent(System.out::println);
        //opt.ifPresent(s->System.out.println(s));

        Optional<String>opt2=Optional.of("jagalHyeanBin");
        System.out.println(opt2.map(String::toUpperCase));

        Optional<String>opt3=Optional.ofNullable(null);

    }
}
