package org.larry.thread.sync04;

/**
 * Created by Larry on 2017/3/17.
 * sysnchronized 可以使用在连续调用的方法中
 */
public class SyncRepeat {
    public synchronized void method1(){
        System.out.println("method1....");
        method2();
    }
    public synchronized void method2(){
        System.out.println("method2....");
        method3();
    }
    public synchronized void method3(){
        System.out.println("method3....");
    }

    public static void main(String[] args) {
        final SyncRepeat sr = new SyncRepeat() ;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sr.method1();
            }
        });
        t1.start();
    }
}
