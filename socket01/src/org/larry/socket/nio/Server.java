package org.larry.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by Larry on 2017/4/13.
 */
public class Server implements Runnable {


    // java 实现nio的核心是多路复用器

    // 1，多路复用器，（管理通信管道）
    private Selector selector;

    // 2，建立缓冲区
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public Server(int port) {
        try {
            // 1, 打开多路复用器
            selector = Selector.open();
            // 2, 打开服务器管道
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 3，设置管道为非阻塞
            channel.configureBlocking(false);

            // 4，绑定端口
            channel.bind(new InetSocketAddress(port));

            // 5，把管道注册到多路复用器上，key 为等待连接
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                // 1,多路复用器启动监听，当注册的事件到到时，方法返回，否则会一直阻塞
                selector.select();
                // 2, 获取selector选中的迭代项，
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                // 3, 遍历结果集
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next() ;
                    // 移除已选择的key ,避免重复处理
                    iterator.remove();
                    // 判断是否有效
                    if(key.isValid()){
                        // 客户端请求连接事件
                        if(key.isAcceptable()){
                            accept(key) ;
                        } else if (key.isReadable()){
                            read(key);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // 读事件驱动器
    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            // 清空缓存区
            this.readBuffer.clear() ;
            // 读取数据
            int count = channel.read(readBuffer)  ;
            if(count == -1){
                // 读取结束
                channel.close();
                key.cancel();
                return;
            }
            //5 有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
            this.readBuffer.flip();
            //6 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
            byte[] bytes = new byte[this.readBuffer.remaining()];
            //7 接收缓冲区数据
            this.readBuffer.get(bytes);
            //8 打印结果
            String body = new String(bytes).trim();
            System.out.println("Server : " + body);

            // 9..可以写回给客户端数据
            writeBuffer.clear();
            channel.write(writeBuffer.wrap("你个傻蛋子".getBytes())) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 客户端连接事件驱动器
    private void accept(SelectionKey key) {
        try {
            // 获取服务通道
            ServerSocketChannel  channel = (ServerSocketChannel) key.channel();
            // 获取连接的socket管道
            SocketChannel accept = channel.accept();
            // 设置非阻塞模式
            accept.configureBlocking(false) ;

            //在这里可以给客户端发送信息哦
            accept.write(ByteBuffer.wrap(new String("向客户端发送了一条信息\n").getBytes()));

            // 注册到多路复用器上，
            accept.register(selector,SelectionKey.OP_READ) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server(6789)).start();
    }
}
