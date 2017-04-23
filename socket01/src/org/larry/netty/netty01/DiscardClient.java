package org.larry.netty.netty01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Larry on 2017/4/23.
 */
public class DiscardClient {

    private static int port = 6789 ;
    private static String host = "127.0.0.1" ;

    public static void main(String[] args) {
        new DiscardClient().run();
    }

    public void run(){
        // 用于处理实际业务操作的多线程事件循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动NIO服务的辅助启动类
            Bootstrap sb = new Bootstrap();
            // 加入工作组
            sb.group(workerGroup)
                    // 声明使用NioServerSocketChannel类型的通道
                    .channel(NioSocketChannel.class)
                    // 通过childHandler去绑定具体的事件处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientSocketHandler());
                        }
                    })
                    // childOption()是提供给由父管道ServerChannel接收到的连接
                    .option(ChannelOption.SO_KEEPALIVE, true);
            // 启动客户端进行服务器连接
            ChannelFuture cf = sb.connect(host,port).sync();

            // 一直等待，知道连接关闭
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
