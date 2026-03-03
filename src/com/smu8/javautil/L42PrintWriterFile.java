package com.smu8.javautil;

import java.io.*;

public class L42PrintWriterFile {
    public static void main(String[] args) {
        //바이트 입력 => 문자열로 변경 (출력)
        //OutputStream out=new OutputStream(); or FileOutStream 바이트 출력
        //OutputStreamWriter writer=new OutputStreamWriter(out,인코딩); : 바이트->문자 출력
        //BufferedWriter br=new BufferedWriter(writer) : 문자 -> 라인개행까지의 문자열 출력
        String Path = "fileOut.txt";
        try (
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Path)))
        ) {
            bw.write("파일 쓰기 복습이에오");
            bw.newLine();
            bw.write("객체를 중첩해서 생성하는 것은 어렵다...");
            bw.newLine();
            bw.write("바이트를 문자열로 저장하는 것도 어려워...");
            bw.flush(); // 나머지 버퍼를 지우면서 작업 정보를 보낸다 // 쓰는게 좋아요
            // 비워주지않는다면 파일 작성이 안될 수 있다
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //PrintWriter : scanner처럼 문자열 출력처리를 자동으로 하는 유틸클래스
        Path = "printWriterTest.txt";
        try (FileOutputStream file = new FileOutputStream(Path);
             //PrintWriter out = new PrintWriter(file, true, StandardCharsets)
        ) {
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
