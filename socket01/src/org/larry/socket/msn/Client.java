package org.larry.socket.msn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Larry on 2017/4/17.
 */
public class Client {

    private final static String HOST = "127.0.0.1" ;

    private final static int PORT = 6789 ;

    private Socket socket ;

    private boolean isConnection = false ;

    private boolean isConnected = false;

    private PrintWriter writer;
    private BufferedReader reader;
    private Map<String, User> onLineUsers = new HashMap<String, User>();// 所有在线用户

    // 负责接收消息的线程
    private MessageHandler messageHandler ;

    public void startClient(String name,String clientIp){
        try {
            socket = new Socket(HOST,PORT) ;

            messageHandler = new MessageHandler(socket) ;
            writer = new PrintWriter(socket.getOutputStream()) ;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            // 开启接收消息的线程
            new Thread(messageHandler).start();
            sendMessage(name + "@" + clientIp);
            isConnection = true ;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        writer.println(message);
        writer.flush();
    }

    public static void main(String[] args) {
        new Client().startClient("李四2","10.21.221.10");
    }
}
