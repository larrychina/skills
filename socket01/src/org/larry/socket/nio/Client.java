package org.larry.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Larry on 2017/4/13.
 * NIO客户端
 */
public class Client {

    // 定义管道
    private SocketChannel channel;
    // 建立缓冲区
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    private Selector selector ;

    public  Client(String host,int port){
        // 创建连接地址
        InetSocketAddress address = new InetSocketAddress(host,port) ;
        try {
            // 打开管道
            channel = SocketChannel.open() ;
            // 设置管道为非阻塞
            channel.configureBlocking(false) ;
            // 建立与服务端的连接
            channel.connect(address) ;

            // 客户端
            /*************************************************/

            selector = Selector.open() ;
            // 管道注册到多路复用器上
            channel.register(selector, SelectionKey.OP_CONNECT) ;
            /*************************************************/

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listener(){
        try {
            while (true) {
                // 等待注册事件到达时，否则一直等待
                selector.select() ;


                /*******************************/
                // 2, 获取selector选中的迭代项，
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                // 3, 遍历结果集
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 移除已选择的key ,避免重复处理
                    iterator.remove();
                    // 判断是否有效
                    if (key.isValid()) {
                        // 客户端请求连接事件
                        if (key.isReadable()) {
                            read(key);
                        }
                        // 连接事件发生
                        else if (key.isConnectable()){
                            connection(key) ;
                        }
                    }
                }
                /*******************************/
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(channel != null)
                    channel.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }

    }

    private void connection(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        // 正在连接中，
        try {
            if(channel.isConnectionPending()){
                // 设置连接完成
                channel.finishConnect() ;
                // 设置管道非阻塞
                channel.configureBlocking(false) ;
                //在这里可以给服务端发送信息哦

                // 定义一个数组，使用系统录入功能
                byte[] bytes = new byte[1024];
                System.in.read(bytes);

                // 数据放入缓冲区
                buffer.put(bytes);
                // 复位
                buffer.flip();

                String input = new String(bytes);
                if ("C".equals(input.trim())) {
                    // 退出
                    channel.shutdownOutput();
                }
                // 发送数据
                channel.write(buffer);
                // 清空缓冲区数据
                buffer.clear();
                //channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
                // 注册读事件
                channel.register(selector,SelectionKey.OP_READ) ;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer_ = ByteBuffer.allocate(1024) ;
            int count = channel.read(buffer_) ;
            if(count == -1){
                channel.close();
                key.cancel();
                return;
            }
            buffer_.flip();
            byte [] bytes_ = new byte[buffer_.remaining()] ;
            buffer_.get(bytes_) ;
            String read = new String(bytes_) ;
            System.out.print("收到服务端的信息："+read);
        }catch (IOException e){

        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1",6789).listener() ;
    }
}
