package poolsandwich;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class IOSimpleClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            socket = new Socket("192.168.0.6", 6000); // 포트번호를 기준으로 다른  프로그램마다 포트번호를 부여받는다
            is = socket.getInputStream();
            os = socket.getOutputStream();
            isr = new InputStreamReader(is);
            osw = new OutputStreamWriter(os);
            br = new BufferedReader(isr);
            bw = new BufferedWriter(osw);
            bw.write(scanner.nextLine() + "\n");
            bw.flush();
            String message = br.readLine();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (osw != null) osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (isr != null) isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
