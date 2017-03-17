package org.larry.thread.sync02;

/**
 * Created by Larry on 2017/3/16.
 */
public class MutilThread {

    public synchronized void method1(){
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    // synchronized
    public synchronized void method2(){
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        final MutilThread m = new MutilThread() ;


        // t1线程获得对象锁， t2线程可以以异步的方式调用对象中非synchronized修饰的方法,这就是异步
        // t1线程先获得对象锁，t2线程再去调用对象中synchronized修饰的方法，需要等待，这就是同步
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.method1();
            }
        },"t1") ;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.method2();
            }
        },"t2") ;

        t1.start();
        t2.start();
    }
}
