package com.smu8.javautil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class L44SocketServer {
    //스레드가 참조하는 필드는 전역이거나 static 으로 선언
    List<Socket> socketList= Collections.synchronizedList(new ArrayList<>());
    //Collections.synchronizedList : 멀티스레드에서 동기화하는 콜렉션 객체 생성
    public static void main(String[] args) {
        try (ServerSocket server=new ServerSocket(7777);){
            while (true){
                Socket socket=server.accept(); // 접속하면 소켓을 반환
                System.out.println(socket.getInetAddress());
                Scanner in=new Scanner(socket.getInputStream()); //유저가 보내는 메세지 받기
                Thread msgThread=new Thread(()->{
                    while (in.hasNext()){
                        String line=in.nextLine();
                        System.out.println(line);
                    }
                });

                msgThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}