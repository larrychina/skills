package org.larry.future;

/**
 * Created by Larry on 2017/3/29.
 */
public class FutureClient {
    public Data requestData(){
        final FutureData futureData = new FutureData() ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData() ;
                futureData.setData(realData);
            }
        }).start() ;
        return  futureData ;
    }
}
