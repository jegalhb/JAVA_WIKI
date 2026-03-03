package com.smu8.javautil;

import java.nio.charset.Charset;
import java.util.Arrays;

public class L32InputStreamEncoding {
    static void main() throws Exception {
        // String (byte)로 된 배열을넣으면 문자열로 바꿔주는게 있다!
        byte [] bytes= new byte[20];
        //[234, 178, 189, 235, 175, 188]
        bytes [0] =(byte)234;
        bytes [1] =(byte)178;
        bytes [2] =(byte)189;
        bytes [3] =(byte)235;
        bytes [4] =(byte)175;
        bytes [5] =(byte)188;
        System.out.println(Arrays.toString(bytes));
        String str = new String(bytes, Charset.forName("EUC-KR"));
        System.out.println(str); //ABC널널
        String str2 = new String(bytes, Charset.forName("UTF-8"));
        System.out.println(str2);
        //String str3 = new String(bytes, Charset.forName("MS-949"));
        //System.out.println(str3);
    }
}
