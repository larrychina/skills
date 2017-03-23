package org.larry.thread.volatile01;



/**
 * Created by Larry on 2017/3/21.
 */
public class VolatileThread02 implements Runnable{

    private int count  ;

    @Override
    public void run() {
        addCount();
    }

    public void addCount(){
        for (int i = 0; i < 1000; i++) {
            count ++ ;
        }
        System.out.println(count);
    }

    public static void main(String[] args) throws Exception{
        VolatileThread02 volatileThread02 = new VolatileThread02();
        for (int i = 0; i < 10; i++) {
            new Thread(volatileThread02).start();
        }
        Thread.sleep(5000);
        System.out.println(volatileThread02.count);
    }
}
