package org.larry.netty.netty01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Larry on 2017/4/23.
 */
public class ClientSocketHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String request = "连接上了，哇咔咔" ;
        ctx.writeAndFlush(Unpooled.copiedBuffer(request.getBytes())) ;
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] data = new byte[byteBuf.readableBytes()];
        while (byteBuf.isReadable()) {
            byteBuf.readBytes(data);
              /*
                System.out.print((char) result.readByte());
                System.out.flush();*/

        }
        System.out.println("Server:" + new String(data, "utf-8"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String request = "你好，服务器，我要连接你了哦，哇咔咔。。" ;
        ctx.writeAndFlush(Unpooled.copiedBuffer(request.getBytes())) ;
    }
}
