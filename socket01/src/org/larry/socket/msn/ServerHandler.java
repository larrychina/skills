package org.larry.socket.msn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Larry on 2017/4/17.
 */
public class ServerHandler implements Runnable{

    private ServerSocket serverSocket ;
    // 设置最大连接数
    private int max ;
    // 客户端
    private List<ClientHandler> clients ;

    public ServerHandler(ServerSocket serverSocket, int max, List<ClientHandler> clients) {
        this.serverSocket = serverSocket;
        this.max = max;
        this.clients = clients ;
    }

    @Override
    public void run() {
        try {
            while (true){
                Socket socket = this.serverSocket.accept() ;
                if(clients.size() == max){ // 连接达到最大数
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String inf = br.readLine() ;
                    StringTokenizer st = new StringTokenizer(inf,"@") ;
                    User user = new User(st.nextToken(),st.nextToken()) ;

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;

                    bw.write("来自服务器的系统消息：" + user.getName() + ",ip:" + user.getIp() + "，\t服务器上连接已达到上限！");
                    bw.flush();

                    br.close();
                    bw.close();
                    socket.close();
                    continue;
                }
                // 如果正常连接，我们告诉服务器该客户端已上线
                /**
                 * 1,当前客户端立即反馈（告诉客户端连接成功）
                 * 2,通知所有客户端（当前客户端上线了）
                 */
                ClientHandler clientHandler = new ClientHandler(socket,clients) ;
                // 开启对此线程的服务端线程
                new Thread(clientHandler).start();
                clients.add(clientHandler) ;
                System.out.println("系统消息:" + clientHandler.getUser().getName() + "已上线！");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
