package org.larry.socket.msn;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Larry on 2017/4/17.
 */
public class Server {

    private ServerHandler serverThread ;

    // 在线人数
    private List<ClientHandler> clients ;

    private final static int PORT = 6789 ;

    private ServerSocket serverSocket ;

    // 是否启动
    private boolean isStart = false ;

    public Server(){

    }

    // 启动服务器
    public void startServer(){
        try {
            serverSocket = new ServerSocket(PORT) ;
            clients = new ArrayList<>() ;
            ServerHandler sh = new ServerHandler(serverSocket,3,clients) ;
            new Thread(sh).start();
            isStart = true ;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 发送消息
    public void send(){
        byte [] readBytes = new byte[1024] ;
        try {
            System.in.read(readBytes) ;
            // 群发消息
            for (ClientHandler client : clients) {
                client.getSocket().getOutputStream().write(readBytes);
                client.getSocket().getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer() ;
        try {
            Thread.sleep(5000);
            server.send() ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
