package org.larry.socket.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Larry on 2017/4/11.
 */
public class SocketServer {

    private static int port =  8888 ;

    public static void main(String[] args) {
        try {
            // 1,创建一个socket，指定绑定端口，并开启监听这个端口
            ServerSocket serverSocket = new ServerSocket(port) ;
            // 2,调用accept方法，等待客户端连接
            Socket socket = serverSocket.accept() ;
            // 3,获取输入流，读取客户端信息
            // 4，获取输出流，响应客户端信息
            new Thread(new ServerHandler(socket)).start();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
