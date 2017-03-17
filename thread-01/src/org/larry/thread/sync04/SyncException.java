package org.larry.thread.sync04;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Larry on 2017/3/17.
 *  线程在执行过程中，如果遇到异常时，可以通过interruptedException可以终止操作
 */
public class SyncException {

    private  int  x = 0 ;

    public synchronized void method1()throws Exception{
        while (true){
            try {
                x ++ ;
                if(x == 10 ){
                    Integer.parseInt("a") ;
                }
                System.out.println("x="+x);
            } catch (Exception e){
                e.printStackTrace();
                throw  new InterruptedException();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        final SyncException syncException = new SyncException() ;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncException.method1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }
}
