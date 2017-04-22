package org.larry.socket.msn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by Larry on 2017/4/19.
 * 线程消息处理类
 */
public class MessageHandler implements Runnable {

    private BufferedReader reader ;

    private Socket socket ;

    public MessageHandler(Socket socket) {
        this.socket = socket ;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message = "" ;
        while (true) {
            try {
                message = reader.readLine() ;
                StringTokenizer tokenizer = new StringTokenizer(message,"/@") ;
                String command = tokenizer.nextToken() ;
                System.out.println("有人说：" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
