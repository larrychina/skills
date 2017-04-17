package org.larry.future;

/**
 * Created by Larry on 2017/3/29.
 */
public class FutureData implements Data {

    private RealData data  ;
    private boolean endSearch = false;

    public synchronized void setData(RealData data) {
        if(endSearch)
            return ;
        this.data = data;
        endSearch = true ;
        // 通知阻塞线程
        notify();
    }

    @Override
    public synchronized String getRequest() {
        while (!endSearch){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("");
        return data.getRequest();
    }
}
