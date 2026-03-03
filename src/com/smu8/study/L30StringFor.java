package com.smu8.study;

public class L30StringFor {
    public static void main(String[] args) {
        String s = "hello java";
        char[] cArr = {'h', 'e', 'l', 'l', 'o', 'j', 'a', 'v', 'a'};
        System.out.println(cArr[4]);
        // System.out.println(s[4]); // js는 가능
        System.out.println(s.charAt(4)); // cArr [4]
        System.out.println(cArr.length);
        System.out.println(s.length());
        // 배열출력
        for (int i = 0; i < cArr.length; i++) {
            System.out.print(cArr[i] + ",");
            // 배열 출력
        }
        System.out.println("\n 문자열 출력");
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i));
        }
        // s에 대문자가 1개라도 있는지 검사하세요!
        boolean result = false;
        for (int i = 0; i < s.length(); i++) {

            // 'A' 'F' 'Z'
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                result = true;
                break; // 대문자 1개만 발견되믄 반복 탈출
            }
        }
        System.out.println("해당 문자열에 대문자가 있나? :" + result);

        String[] emailArr = {"a@g.com", "b@n.com", "c", "d@d.com"};
        // 다음 문자열 중 이메일 형식[@이 있음 이메일] 이 아닌것이 있나요?
        result = false;
        for (int i = 0; i < emailArr.length; i++) {
            String email = emailArr[i];
            boolean contain = email.contains("@"); // @ == at
            if (!contain) {
                result = true;
                break;
            }
        /*if)(emailArr[i].contains("@")){
        result=true;
        break;

         */

            System.out.println("이메일 형식이 다른 것이 존재: " + result);
        }
    }
}