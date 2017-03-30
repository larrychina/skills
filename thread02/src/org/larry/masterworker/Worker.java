package org.larry.masterworker;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Larry on 2017/3/30.
 * woker
 */
public class Worker implements Runnable{

    // master的queue引用
    private ConcurrentLinkedDeque workerQueue  ;

    // 每一个worker都有一个对concurrentHashmap的引用，用于存放结果集
    private ConcurrentHashMap<String,Object> resultMap ;

    public ConcurrentLinkedDeque getWorkerQueue() {
        return workerQueue;
    }

    public void setWorkerQueue(ConcurrentLinkedDeque workerQueue) {
        this.workerQueue = workerQueue;
    }

    public ConcurrentHashMap<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while (true){
            Task task = (Task) this.getWorkerQueue().poll();
            if (task == null) break;
            Object obj = handle(task) ;
            this.resultMap.put(Integer.toString(task.getId()),obj) ;
        }
    }

    // 处理每个task逻辑，假设每个task耗时0.5秒
    public Object handle(Task task){
        int output = 0 ;
        try{
            Thread.sleep(500);
            output = task.getPrice() ;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return output ;
    }
}
