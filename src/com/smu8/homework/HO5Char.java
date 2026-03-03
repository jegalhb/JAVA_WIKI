package com.smu8.homework;

public class HO5Char {
    public static void main(String[] args) {

        System.out.println((int)'A');
        System.out.println((int)'가');
        String ASCII= ("아스키 코드의 범위는 : 0~127 ");
        System.out.print(ASCII);
        String MS949= ("MS949 문자표의 특징을 한 줄로 설명하시오 : 한국 전용 문자표");
        System.out.println(MS949);
        String UnivsUTF8 = ("유니코드(Unicode)와 UTF-8의 차이를 설명하시오 : 유니코드는 문자를 번호로 정의, UTF-8은 그 해당 번호를 저장하는 방식 ");
        System.out.println(UnivsUTF8);
        // char c=65; // A
        System.out.println("char c=65의 출력 결과는 : A");
        System.out.println("char c=97의 출력 결과는 : a");
        //UAC00 = 가
        System.out.println("char c = '\uD83D\uDE0D';의 코드가 컴파일 오류가 나는 이유 : 문자 자료형은 2byte의 크기를 가지고 있기 때문에 4byte크기의 이모지는 넣을 수 없다 ");
        System.out.println("Integer.toHexString int 가의 출력 결과는 = ac00 ");
        System.out.println((int)'A');
        System.out.println((int)'Z');
        System.out.println((int)'a');
        System.out.println((int)'0');
        System.out.println((int)'가');
        System.out.println((char)65);
        System.out.println((char)90);
        System.out.println((char)97);
        System.out.println((char)48);
        System.out.println((char)44032);

        char c= 'A'; // 65 = A
        System.out.println(c+1); // 정수+1이랑 같은 소리인딩 66
        //System.out.println(c+1); = 1
        System.out.println((char)(c+1)); // 문자 65+1 66이니 B
        //System.out.println((char)(c+1)); = 2
        System.out.println("두 출력의 결과가 다른 이유 :1번은 형변환을 하지 않아서 정수+정수로 출력되고, 2번은 문자 형변환을 해서 아스키 코드 65번인 A->B가 된거기에 B가 출력된다.  ");
        char uac = '\uAC00';
        System.out.println(uac); // "가"
        System.out.println("유니코드 이스케이프 문자를 사용하여 문자 -가-를 출력하는 코드를 작성하시오 : char uac = '\\uAC00';\n System.out.println(uac);");
        //char t = 6000000; // char이 표현할 수 있는 범위를 벗어나서 컴파일 오류가 뜰 것.
        System.out.println("다음 코드에서 오류가 발생하는 이유 : char은 2byte(16bit)를 가지고 있는데 표현 범위를 벗어난 범위이기에 컴파일 오류가 발생함 해결은 int를 써주는 것");
        //int를 쓰는 이유는 int 4byte(32bit)이기에 표현할 수 있는 범위가 훨신 크기에 해당 숫자를 표현할 수 있음.
        //System.out.println(t);// Character.MAX_VALUE
    }
}
