package org.larry.thread.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Larry on 2017/3/23.
 * 通过wait notify 关键字实现自定义queue , 模拟BlockingQueue
 */
public class QueueDefault {

    // 对象锁
    private  final static Object lock = new Object() ;

    // 自定义队列
    private List<Object> queue = new LinkedList<>() ;

    // 保证原子操作
    private AtomicInteger count = new AtomicInteger(0) ;

    private int minSize = 0 ;

    private int maxSize = 5 ;

    public void put(Object object){
        synchronized (lock){
            try {
                while (queue.size() == maxSize){
                    lock.wait();
                }
                queue.add(object) ;
                count.incrementAndGet();
                System.out.println("元素："+object+"被添加！");
                lock.notify();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public Object take(){
        Object temp = null ;
        synchronized (lock){
            try {
                if (queue.size() == minSize){
                    lock.wait();
                }
                count.incrementAndGet();
                temp = queue.remove(0) ;
                System.out.println("元素："+temp+"被去除！");
                lock.notify();
            }catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }
        return temp ;
    }
    public  int getSize(){
        return queue.size() ;
    }
    public static void main(String[] args)throws Exception {
        final QueueDefault qd = new QueueDefault() ;
        qd.put("A");
        qd.put("B");
        qd.put("C");
        qd.put("D");
        qd.put("E");
        System.out.println("当前队列长度："+qd.getSize());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                qd.put("f");
                qd.put("g");
            }
        }) ;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("取到当前元素："+qd.take() );
                    Thread.sleep(1000);
                    System.out.println("取到当前元素："+qd.take() );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}
