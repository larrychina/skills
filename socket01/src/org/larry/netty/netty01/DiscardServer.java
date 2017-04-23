package org.larry.netty.netty01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Larry on 2017/4/23.
 */
public class DiscardServer implements Runnable {

    private static int port = 6789;

    public static void main(String[] args) {
        new DiscardServer().run();
    }

    @Override
    public void run() {
        // 1,用于接收客户端连接的多线程事件循环器
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        // 2,用于处理实际业务操作的多线程事件循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动NIO服务的辅助启动类
            ServerBootstrap sb = new ServerBootstrap();
            // 加入工作组
            sb.group(bossGroup, workerGroup)
                    // 声明使用NioServerSocketChannel类型的通道
                    .channel(NioServerSocketChannel.class)
                    // 通过childHandler去绑定具体的事件处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // option（）提供给NioServerSocketChannel用来接收进来的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // childOption()是提供给由父管道ServerChannel接收到的连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口和异步启动接收客户端进来的连接
            ChannelFuture cf = sb.bind(port).sync();

            // 一直等待，直到服务端socket关闭（在这个例子中，不会发生，但是你可以这样做来优雅的关闭你的服务器）
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
