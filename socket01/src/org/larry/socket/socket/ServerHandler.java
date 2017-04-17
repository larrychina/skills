package org.larry.socket.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by Larry on 2017/4/11.
 */
public class ServerHandler implements Runnable {

    private Socket socket ;
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        /**
        * 代理服务端处理客户端请求的数据
        */
        try {
            // 接收客户端发送的数据
            InputStream is = socket.getInputStream() ;
            BufferedReader br = new BufferedReader(new InputStreamReader(is) ) ;
            String info = null ;
            while ((info = br.readLine()) != null){
                System.out.println("客户端发来信息："+info);
            }

            // 响应客户端请求
            OutputStream os = socket.getOutputStream() ;
            PrintWriter pw = new PrintWriter(os) ;
            pw.println("我是小服，收到你的请求，我响应的数据是：你是个逗比，哈哈哈。。。");
            pw.flush();
            socket.shutdownOutput();
            br.close();
            is.close();
            pw.close();
            os.close();
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                try {
                   socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null ;
        }
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Larry\\Desktop\\11.txt" ;
        try {
            InputStream is = new FileInputStream(new File((path))) ;
           /* byte [] bytes = new byte[1024] ;
            String info = null ;
            int leng = 0 ;
            while ((leng = is.read(bytes)) != -1 ){
                System.out.println(bytes.toString());
            }*/
            BufferedReader br = new BufferedReader(new InputStreamReader(is) ) ;
            String info = null ;
            while ((info = br.readLine()) != null){
                System.out.println(info);
            }
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
