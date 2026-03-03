package com.smu8.javautil;

import java.io.*;

public class L39FileWriter {
    static void main() {
        //file에는 이미지 , 음악 , 영상 등등 etc... 가장많은거는 문자열로 된 타입 텍스트(txt,hwp,doc,csv,md... etc)
        //FileWriter : 문자열을 문서로 저장하는 객체!!?
        //FileOutStream (byte) + BufferWriter(문자단위로 버퍼처리)
        try(
                // / : root (c:\)
                //./ :현재 jvm이 실행되고있는 프로그램 경로! // 현재 경로글 기준으로
                FileOutputStream fos = new FileOutputStream("out2.txt");
                OutputStreamWriter osw =new OutputStreamWriter(fos); // 문자단위로 바이트를 처리하겟다
                BufferedWriter bw = new BufferedWriter(osw); //버퍼로 문자열을 처리하겟다
                ) {
            bw.write("안녕하세요!");
            bw.newLine();
            bw.write("파일출력수업입니다 ㄷㄷㄷㄷ");
            bw.newLine();
            bw.write("writer는 바이트를 인코딩처리한당");
            bw.newLine();
            bw.write("버퍼는 임시저장공간으로 라인개행전까지의 문자열을 처리한다!");
            bw.newLine();
            bw.write("파일에 새로운 택스트를 넣어봐요");
        } catch (FileNotFoundException e) { // 파일이 없을 때 나오는 오류류
            throw new RuntimeException(e);
        } catch (IOException e) {// 입출력시 발생하는 오류류
            throw new RuntimeException(e);
        }
    }
}
