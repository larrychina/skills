package org.larry.future;

/**
 * Created by Larry on 2017/3/29.
 */
public class Main {

    public static void main(String[] args) {
        FutureClient futureClient = new FutureClient() ;
        Data data = futureClient.requestData() ;
        System.out.println("调用查询接口。。。。。。。。。");
        String result = data.getRequest() ;
        System.out.println(result);
    }
}
