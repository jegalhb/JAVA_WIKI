package com.smu8.javautil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class L43ScannerFile {
    public static void main(String[] args) {
        String path = "fileOut.txt";
        try (
                FileInputStream file = new FileInputStream(path);
                InputStreamReader isr = new InputStreamReader(file, StandardCharsets.UTF_8);
                // scanner는 리더와 버퍼를 한번에 사용하게 해준다
                BufferedReader br = new BufferedReader(isr); // 마지막 세미콜론은 써도 되고 안 써도 됨
        ) {
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                sb.append(line + "\n");
            }
            System.out.println(sb);
            /*while (true){
                String line = br.readLine();
                if (line==null)break;
                System.out.println(line);
            }                       */
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        path = "printWriterTest.txt";
        try(
                Scanner scanner = new Scanner(new FileInputStream(path),StandardCharsets.UTF_8) // 인코딩 가능 파일 불러올 준비완
                ) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()){
                sb.append(scanner.nextLine());
               /* String line = scanner.nextLine();
                System.out.println(line);

                */
            }
            System.out.println(sb);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
