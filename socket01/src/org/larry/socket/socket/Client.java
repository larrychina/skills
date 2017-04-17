package org.larry.socket.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by Larry on 2017/4/11.
 */
public class Client {

    private static  int port = 8888 ;

    public static void main(String[] args) {
        Socket socket = null ;
        try {
            // 1、创建客户端Socket，指定服务器地址和端口
            socket = new Socket("127.0.0.1",port) ;
            // 2,获取输出流，向服务端发起信息
            OutputStream os = socket.getOutputStream() ;
            PrintWriter pw = new PrintWriter(os) ;
            pw.println("你好，我是小客："+Client.class.getPackage()+Client.class.toString());
            pw.flush();
            socket.shutdownOutput();

            // 3，获取输入流，读取服务端响应信息
            InputStream is = socket.getInputStream() ;
            BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;
            String info  ;
            System.out.println(br.readLine());
            while ((info = br.readLine())!=null){
                System.out.println("接收到服务器端响应的消息是："+info);
                break;
            }

            pw.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
