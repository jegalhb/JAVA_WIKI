package com.smu8.javautil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class L45SocketClient {
    public static void main(String[] args) {
        //서버에 접속
        //String ip="127.0.0.1"; //내컴퓨터에 실행중인 서버에 접속
        String ip="192.168.0.65"; //내컴퓨터에 실행중인 서버에 접속
        int port=7777;
        try (Socket socket=new Socket(ip,port)){
            OutputStream out=socket.getOutputStream();
            PrintWriter print=new PrintWriter(out,true);
            print.println("경민 안녕!!!");

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}