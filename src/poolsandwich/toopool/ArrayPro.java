package poolsandwich.toopool;

import java.util.Scanner;

public class ArrayPro {
    public static void main(String[] args) {
        /*
        * 정수를 하나 입력받는다.
        * 입력받은 정수만큼 정수를 입력받는다.
        * 입력한 순서대로 정수를 출력한다.
        * *제한조건*
        * 입력을 전부 받은 후 출력을 해야 한다.
        * 문자열로 풀 수 없는 문제다.
        * */

        Scanner scanner = new Scanner(System.in);

        int count = scanner.nextInt();
        int[] numbers = new int[count];

        for (int i = 0; i < count; i++) {
            numbers[i] = scanner.nextInt();
        }
        for (int i = 0; i < count; i++) {
            System.out.println(numbers[i]);
        }
    }
}
