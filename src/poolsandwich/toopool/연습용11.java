package poolsandwich.toopool;

public class 연습용11 {
    public static void main(String[] args) {
        char ch = 'A';

        if (ch >= 'A' && ch <= 'Z') {
            System.out.println("대문자");
        } else if (ch >= 'a' && ch <= 'z') {
            System.out.println("소문자");
        } else {
            System.out.println("기타");
        }

        int i = 80;
        String msg = "";
        if (i >= 90){
            System.out.println("우수");
    } else if (i>=80) {
            System.out.println("합격");
        } else if (i>70) {
            System.out.println("불합격");
        }else{
            System.out.println("과락");
        }
         i = 80;
        switch (i){
            case 90 :
                System.out.println("우수 합격");
                break;
            case 80 :
                System.out.println("합격");
                break;
            case 70 :
                System.out.println("불합격");
                break;
            case 60 :
                System.out.println("과락");
                break;
        }
        char ha = 65;
        if (ha>= '0' && ha<= '9' ) {
            System.out.println("숫자");
        } else if (ha>='가' && ha<='힣' ) {
            System.out.println("한글");
        } else if (ha>='A' && ha<='Z' ) {
            System.out.println("영문 대문자");
        } else if (ha>='a' && ha<='z' ) {
            System.out.println("영문 소문자");
        } else {
            System.out.println("기타");
        }
        System.out.println((int)'a');
    }
}