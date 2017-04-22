package org.larry.socket.aio;


import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by Larry on 2017/4/17.
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {
    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        //当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
        attachment.assc.accept(attachment, this);
        // 读取结果
        read(result);
    }

    private void read(AsynchronousSocketChannel asc) {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024) ;
        asc.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip() ;
                System.out.println("当前字节数为：" + result);
                String resultData = new String(attachment.array()).trim() ;
                write(asc,resultData) ;
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });

    }

    private void write(AsynchronousSocketChannel asc, String resultData) {
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024) ;
        writeBuffer.put("收到数据了，回你个砸蛋！。。".getBytes()) ;
        writeBuffer.flip() ;
        asc.write(writeBuffer) ;
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }
}
