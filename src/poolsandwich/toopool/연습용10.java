package poolsandwich.toopool;

public class 연습용10 {
    public static void main(String[] args) {
        /*
         */
        int score = 80;

        if (score >= 90) {
            System.out.println("학점 A");
        } else if (score >= 80) {
            System.out.println("학점 B");
        } else if (score >= 70) {
            System.out.println("학점 C");

        } else if (score >= 60) {
            System.out.println("재수강");

        } else if (score >= 50) {
            System.out.println("재재수강");
        } else {  //else 그 외 모든것
            System.out.println("나가");
        }

    }
}