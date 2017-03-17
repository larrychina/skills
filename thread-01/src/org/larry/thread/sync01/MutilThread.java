package org.larry.thread.sync01;

/**
 * Created by Larry on 2017/3/16.
 * 锁的概念
 */
public class MutilThread {
    // static
    private static int number ;

    // static
    public static synchronized void printStr(String name){
        try {
            if(name.equals("a")){
                System.out.println("tag a ,set number 100");
                number = 100 ;
                Thread.sleep(1000);
            }else {
                System.out.println("tag b ,set number 200");
                number = 200 ;
            }
            System.out.println("tag="+name+",number="+number);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        /**
        * 没有加static前，加了sysnchronized 结果也不是a执行完了以后b先执行的（或者b先执行），原因是因为我们实例了两个mutilThread对象，两个对象之间互不影响，线程只是获得的线程锁
         * 如果在静态方法上加sysnchronized ，表示锁定.class,类级别的锁(独占的)
        */
        final MutilThread mutilThread1 = new MutilThread() ;
        final MutilThread mutilThread2 = new MutilThread() ;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mutilThread1.printStr("a");
            }
        }) ;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                    mutilThread2.printStr("b");
            }
        }) ;
        t1.start();
        t2.start();
    }
}
