package org.larry.thread.volatile01;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Larry on 2017/3/21.
 */
public class VolatileThread03 extends Thread{

    private  static  int count  ;
    private static AtomicInteger atomicInteger = new AtomicInteger(0) ;
    @Override
    public void run() {
        addCount();
    }

    public void addCount(){
        for (int i = 0; i < 1000; i++) {
            count ++ ;
            atomicInteger.incrementAndGet() ;
        }
        System.out.println("count:"+count);
        System.out.println("atomicTnteger:"+atomicInteger);
    }

    public static void main(String[] args) throws Exception{
        //VolatileThread03 volatileThread02 = new VolatileThread03();
        for (int i = 0; i < 10; i++) {
            //new Thread(volatileThread02).start();
            new VolatileThread03().start();
        }
        Thread.sleep(5000);
        System.out.println("count---->:"+count);
    }
}
