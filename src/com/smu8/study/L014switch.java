package com.smu8.study;

public class L014switch {
    public static void main(String[] args) {

        int weekNum = 5;
        String week = "";
        if (weekNum == 0) {
            week = "월요일";
        } else if (weekNum == 1) {
            week = "화요일";
        } else if (weekNum == 2) {
            week = "수요일";
        } else if (weekNum == 3) {
            week = "목요일";
        } else if (weekNum == 4) {
            week = "금요일";
        } else if (weekNum == 5) {
            week = "토요일";
        } else if (weekNum == 6) {
            week = "일요일";
        }
        System.out.println("오늘의 요일은" + week + "입니다");
        // if문이 가독성이 떨어지는 이유
        // 1. 복잡한 조건 (비교연산,논리연산)
        // 2. {} 블럭스코프,가 너무 많다.... ㅠㅠ

        // switch => 가독성이 떠렁지는 if 문 대신 사용!!!!(동등비교만 가능, ==)
        // sitch() 동등비교할 변수 : 변수는 기본형,문자열,enum 타입만 올수 있다.
        weekNum = 7;
        week = "";
        switch (weekNum) {
            case 0: // weekNum== 0일때
                week = "월요일";
                break; // 다음 코드는 실행하지 않겟다.
            case 1: week="화요일"; break;
            case 2: week="수요일"; break;
            case 3: week="목요일"; break;
            case 4: week="금요일"; break;
            case 5: week="토요일"; break;
            case 6: week="일요일"; break;
            default: week= "요일은 0~6까지만 존재해요잉";
        }
        System.out.println("switch : 오늘은" + week + "입니다");
        //switch가 if 동등비교버디 명시적이기 떄문에 개발자들이 많이 사용 ㄷㄷ!

        // break가 없을 때
        //  case가 같은 것부터 브레이크가 없을 때 까지 실행
        int num = 4;
        switch (num){
            case 1 : System.out.println("num은 1입니다.");
            case 2 : System.out.println("num은 2입니다.");
            case 3 : System.out.println("num은 3입니다.");
            case 4 : System.out.println("num은 4입니다.");
            case 5 : System.out.println("num은 5입니다.");
        }


    }
    }
