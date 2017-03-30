package org.larry.future;

/**
 * Created by Larry on 2017/3/29.
 */
public class RealData implements Data {

    private  String result ;

    public RealData() {
        System.out.println("开始数据查询，此操作很耗时！");
        try{
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("数据查询结束，返回查询结果！");
        result = "result right" ;
    }

    @Override
    public String getRequest() {
        return result;
    }
}
