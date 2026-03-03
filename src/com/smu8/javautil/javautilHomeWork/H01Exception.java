package com.smu8.javautil.javautilHomeWork;

import java.io.IOException;

public class H01Exception {

    public class test{
        static void read() throws IOException{
            throw new IOException("io");
        }
    }

    public static void main(String[] args) {
        try {
            test.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end");
    }
}
