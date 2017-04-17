package org.larry.masterworker;

import javax.net.ssl.ManagerFactoryParameters;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Random;

/**
 * Created by Larry on 2017/3/30.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
        // 初始化可用worker数量
        Master master = new Master(new Worker(),Runtime.getRuntime().availableProcessors()) ;
        Random r = new Random();
        // 模拟100个任务
        for(int i = 1; i <= 100; i++){
            Task t = new Task();
            t.setId(i);
            t.setPrice(r.nextInt(1000));
            master.submit(t);
        }

        long start = System.currentTimeMillis() ;
        master.execute();
        /*while (!master.isComplate()){

        }
        System.out.println(master.getResult());
        long executeTimes = System.currentTimeMillis() - start ;
        System.out.println("最终结果："+master.getResult()+",执行时间："+executeTimes);*/
        while(true){
            if(master.isComplate()){
                long end = System.currentTimeMillis() - start;
                System.out.println("最终结果：" + master.getResult() + ", 执行时间：" + end);
                break;
            }
        }
    }
}
