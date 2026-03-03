package com.smu8.homework;

import java.io.*;

public class H26InputOutput {
    public static void main(String[] args) {
        // 그러기 위해서  바이트를 문자로 디코딩해주는 스트림리더로 해결
        // 디코딩이 되지 않아 깨진다
        InputStreamReader in =new InputStreamReader(System.in);
        InputStream IN = System.in; // 인풋스트림은 바이트스트림에서 1바이트씩 읽어 char로 변환하기에
        int a = 0;
        String str = "";
        while (true){
            try {
                if (!((a=in.read())!=-1)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            str+=(char)a;
            if (a=='\n')break;
        }
        System.out.println(str);

        try (BufferedInputStream INN = new BufferedInputStream(new FileInputStream("a,bin"));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("b.bin"))) {
            int B;
            while ((B = in.read()) != -1) { // 1바이트씩 읽기
                out.write(B);              // 1바이트씩 쓰기
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream fis = new FileInputStream("data.txt")) { //파일을 읽어올 fis라는 통로에 data.txt를 불러오겟다

            int x = fis.read(); // 정수형 x 에 fis의 비트 데이터를 1개씩 읽어서 넣겠다
            System.out.println(x); // 그 x를 출력할게용

        } catch (Exception e) { // 만약 불러올게 없다면 에러를 출력하겠다
            System.out.println("error");
        }
    }
}


//while (true) {
//                try {
//                    if (!(b=in.read()!=-1)) break;
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
//            }