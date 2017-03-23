package org.larry.thread.volatile01;



/**
 * Created by Larry on 2017/3/21.
 */
public class VolatileThread implements Runnable{

    private static int count  ;

    // volatile 如果不加，run方法不中不会打印出来，
    private static volatile boolean isRunning = true ;

    public static void main(String[] args) throws Exception{
        /*String str = "hello {0} ,hahaha" ;
        String value = MessageFormat.format("oh, {1} is 'a' pig,{1},------>{2}", "ZhangSan","hahha","zhangsav");

        System.out.println(value);  // 输出：oh, ZhangSan is a pig

        System.out.println(str.contains("{1}"));*/

        VolatileThread volatileThread = new VolatileThread() ;

        new Thread(volatileThread).start();
        Thread.sleep(3000);
        isRunning = false ;
        System.out.println("isRunning设置了false!");
        Thread.sleep(1000);
        System.out.println(isRunning);
    }

    @Override
    public void run() {

        while (isRunning){

        }
        System.out.println("thread is stop running!");
    }

}
