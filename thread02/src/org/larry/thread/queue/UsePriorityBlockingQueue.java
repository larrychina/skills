package org.larry.thread.queue;

import org.larry.thread.dto.Task;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Larry on 2017/3/26.
 */
public class UsePriorityBlockingQueue {

    public static void main(String[] args) {
        PriorityBlockingQueue queue = new PriorityBlockingQueue() ;
        Task t1 = new Task(4,"t1") ;
        Task t2 = new Task(2,"t2") ;
        Task t4 = new Task(5,"t4") ;
        Task t3 = new Task(6,"t3") ;
        Task t5 = new Task(8,"t5") ;
        queue.add(t1) ;
        queue.add(t2) ;
        queue.add(t4) ;
        queue.add(t3) ;
        queue.add(t5) ;

        Iterator iterator = queue.iterator() ;
        while (iterator.hasNext()){
            System.out.println(queue.poll() );
            iterator.next() ;
        }
    }
}
