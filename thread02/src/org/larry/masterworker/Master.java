package org.larry.masterworker;

import org.larry.thread.dto.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Larry on 2017/3/30.
 */
public class Master {

    // 1，存放任务的队列 ，此处采用
    private ConcurrentLinkedDeque<Task> taskQueue = new ConcurrentLinkedDeque<>() ;

    // 2，承载所有worker对象的容器
    private HashMap<String,Thread> wokerMap = new HashMap<>() ;

    // 3，需要一个盛放每个worker处理的结果集
    private ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap() ;

    // 4，构造方法，初始化worker数，后期可以改造成线程池来做
    public Master(Worker worker,int workerCount){
        worker.setResultMap(resultMap);
        worker.setWorkerQueue(taskQueue);

        for (int i = 0; i < workerCount; i++) {
            wokerMap.put("woker"+i,new Thread(worker));
        }
    }

    // 5，需要一个提交任务的操作
    public void submit(Task task){
        this.taskQueue.add(task) ;
    }

    // 6 ，需要一个执行的方法，启动所有的worker线程
    public void execute(){
        for (Map.Entry<String, Thread> entry: this.wokerMap.entrySet()){
            entry.getValue().start();
        }
    }

    // 7，判断任务是否执行结束
    public boolean isComplate(){
        for (Map.Entry<String, Thread> entry: this.wokerMap.entrySet()){
           if(!entry.getValue().getState().equals(Thread.State.TERMINATED))
               return false ;
        }
        return true ;
    }

    // 8，计算方法结果
    public Object getResult(){
        int result = 0 ;
        for(Map.Entry<String,Object> entry : this.resultMap.entrySet()){
            result += (Integer) entry.getValue() ;
        }
        return result ;
    }
}
