package org.larry.future;

import jdk.internal.jfr.events.ExceptionThrownEvent;

/**
 * Created by Larry on 2017/3/29.
 */
public class Main {

    public static void main(String[] args) {
        FutureClient futureClient = new FutureClient() ;
        Data data = futureClient.requestData() ;
        System.out.println("请求结束");
        try {
            System.out.println("做其他的任务start。。。。。。。");
            Thread.sleep(5000);
            System.out.println("做其他的任务end。。。。。。。");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String result = data.getRequest() ;
        System.out.println(result);
    }
}
