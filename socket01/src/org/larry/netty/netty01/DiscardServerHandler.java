package org.larry.netty.netty01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by Larry on 2017/4/23.
 * ServerChannelHandler
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // ByteBuf 是一个引用计数对象，用完必须通过显示的调用release()方法进行手动释放。
            ByteBuf result = (ByteBuf) msg;
            byte[] data = new byte[result.readableBytes()];
            // 这个低效的循环事实上可以简化为:System.out.println(result.toString(io.netty.util.CharsetUtil.US_ASCII))
            while (result.isReadable()) {
                result.readBytes(data);
              /*
                System.out.print((char) result.readByte());
                System.out.flush();*/

            }
            String request = new String(data, "utf-8");
            System.out.println("Server:" + request);

            // 写给客户端
            String response = "Hello,马小琥，服务器收到你的请求，并成功与你建立了连接！";
            /*ctx.write(response) ;
            ctx.flush() ;*/
            ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
            // 为此通信管道添加事件监听
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    assert channelFuture == future ;
                    channelFuture.channel().close() ;
                }
            }) ;
        } finally {
            // 或者，你可以在这里调用in.release()。
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
