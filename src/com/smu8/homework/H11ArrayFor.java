package com.smu8.homework;

public class H11ArrayFor {
    public static void main(String[] args) {
        int[] score = {80, 75, 90, 100, 65, 88};
        int sum = 0;
        int avg = 0;
        for (int i = 0; i < score.length; i++) {
            //     System.out.println(score[i]); == 탐색 용도!
            sum += score[i];
        }
        avg = sum / score.length;
        System.out.println("점수 합 :" + sum + ", 점수 평균 :" + avg);

        /*
        다음은 상품 코드 목록이다.
        int[] productCodes = {101, 203, 305, 410, 512};
        사용자가 찾고 싶은 상품 코드가 305일 때,
        배열 안에 해당 값이 존재하면
        "상품이 존재합니다"
        존재하지 않으면
        "상품이 없습니다"
        를 출력하시오.
        요구 조건
        반복문과 조건문을 함께 사용할 것
        break 사용 가능
         */
        int [] productCodes = {101,203,305,410,512};
        boolean isFind = false;
        final int FIND_CODE = 305;
        for (int i = 0; i < productCodes.length; i++) {
            // System.out.println(productCodes[i]);
            if (productCodes[i] == FIND_CODE){
                // System.out.println("305 찾음");
                isFind=true;
                break;
            }
        }
        String mag = isFind ? FIND_CODE + "찾음" : FIND_CODE + "없음";
        System.out.println(mag);
        /*문제 3. 잘못 입력된 값 찾기 (유효성 검사)
           다음은 사용자들이 입력한 나이 데이터이다.
            int[] ages = {25, 30, -3, 45, 200, 18};
            정상적인 나이는 0 이상 120 이하이다.
            잘못 입력된 나이만 출력하시오.
            잘못된 값이 몇 개인지도 함께 출력하시오.
            출력 예시
            잘못된 나이: -3
            잘못된 나이: 200
            총 2개 */
        int ages [] = {25,30,-3,200,18};
        int cnt = 0;
        for (int i = 0; i <ages.length; i++) {
            if (ages[i] >120 || ages [i]<0){
                System.out.println("잘못된 나이 " +ages[i]);
                cnt ++;

            }
        }
        System.out.println("잘못된 나이 총수 : " +cnt);
        /*
        다음은 회원 이름 목록이다.
        String[] names = {"kim", "lee", "park", "choi"};
        이름이 "park" 인 사람이 존재하는지 확인하시오.
        존재하면 "회원 존재"
        존재하지 않으면 "회원 없음" 출력
        요구 조건
        문자열 비교는 == 사용 금지
        반드시 문자열 비교 메서드를 사용할 것
         */
        String[] names = {"kim", "lee", "park", "choi"};
        final String FIND_NAME = "park";
        boolean isFindName = false;
        for (int i = 0; i < names.length; i++) {
            // System.out.println(names[i].equals(FIND_NAME));
           // isFindName=names[i].equals(FIND_NAME);
            isFindName=names[i].contains(FIND_NAME);
            if (isFindName) break;
        }
          //     System.out.println(isFindName);
        mag = isFindName ? FIND_NAME + "회원 있음" : FIND_NAME + "회원 없음";
        System.out.println(mag);

        /*다음은 단어 목록이다.
                String[] words = {"java", "array", "loop", "string"};
        각 문자열의 길이를 출력하시오.
        모든 문자열 길이의 합을 출력하시오.
                출력 예시
        java : 4
        array : 5
        총 길이: 20
         */
        String[] words = {"java", "array", "loop", "string"};
        int lengthSum = 0;
        for (int i = 0; i < words.length; i++) {
            System.out.println(words[i] + ":" + words[i].length());
            lengthSum+= words[i].length();
        }
        System.out.println("words의 총 길이 :" + lengthSum);


        /*다음은 이메일 입력 데이터이다.
        String[] emails = {
                "user@test.com",
                "admin@test.com",
                "guesttest.com",
                "hello@site",
                "a@mail.com"
        };
        이메일에는 반드시 @ 문자가 포함되어야 한다.
        잘못된 이메일만 출력하시오.
                정상 이메일 개수와 비정상 이메일 개수를 각각 출력하시오.

         */
        String[] emails = {
                "user@test.com",
                "admin@test.com",
                "guesttest.com",
                "hello@site",
                "a@mail.com",
                "amail.com"
        };
        int ValidEmailCnt = 0;
        int inValidEmailCnt = 0;
        for (int i = 0; i < emails.length; i++) {
            // System.out.println(emails[i]);
            if (emails [i].contains("@")){
                ValidEmailCnt ++;
            }else {
                inValidEmailCnt ++;
            }
        }
        System.out.println("검증된 이메일 수 :" + ValidEmailCnt);
        System.out.println("검증 ㄴㄴ 이메일 수 : " + inValidEmailCnt);

    }
}