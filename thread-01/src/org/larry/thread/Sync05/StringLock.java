package org.larry.thread.Sync05;

/**
 * Created by Larry on 2017/3/17.
 */
public class StringLock {

    public void method1(){
        // 可以使用 ： new String("hello")
        synchronized ("hello"){// 这种情况就会产生死锁
            while (true){
                System.out.println(Thread.currentThread().getName()+"线程开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"线程结束");
            }
        }
    }

    private String lock  = "t1";

    public void method2(){
        synchronized (lock){
            // 如果对像修改后锁将改变
            lock = "change lock" ;
            try {
                System.out.println(Thread.currentThread().getName()+"线程开始");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"线程结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        final StringLock stringLock = new StringLock() ;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //stringLock.method1();
                stringLock.method2();
            }
        },"t1") ;

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //stringLock.method1();
                stringLock.method2();
            }
        },"t2") ;

        t1.start();
        t2.start();
    }
}
