package org.larry.socket.test;

import java.util.StringTokenizer;

/**
 * Created by Larry on 2017/4/17.
 */
public class StringTokenizerTest {

    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("hello world/@我爱这个世界","/@") ;

        while (st.hasMoreTokens()){
            System.out.println(st.nextToken());
        }
    }
}
