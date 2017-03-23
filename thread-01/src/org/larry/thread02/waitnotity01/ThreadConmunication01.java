package org.larry.thread02.waitnotity01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Larry on 2017/3/23.
 */
public class ThreadConmunication01{

    private List<String> list = new ArrayList<>() ;

    private Object lock = new Object() ;

    private CountDownLatch countDownLatch = new CountDownLatch(1) ;

    public void addList(){
        synchronized (lock){

            for (int i = 0; i < 10; i++) {
                list.add("larry") ;
                System.out.println("当前线程："+Thread.currentThread().getName()+"\tlist.size()="+list.size());
                try {
                    Thread.sleep(1000);
                    if(list.size() == 5){
                        System.out.println("发出通知！");
                        lock.notify();
                        //countDownLatch.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void interruptList(){
        synchronized (lock){
                if (list.size() != 5){
                    System.out.println("当前线程："+Thread.currentThread().getName()+"进入。。。。");
                    try {
                        lock.wait();
                       // countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    throw new InterruptedException("线程中断") ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) throws Exception{
        final ThreadConmunication01 tc01 = new ThreadConmunication01() ;
        Thread t1 = new Thread(new Runnable() {
           @Override
           public void run() {
                tc01.addList();
           }
       },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                tc01.interruptList();
            }
        },"t2");
        t2.start();
        t1.start();
    }
}
