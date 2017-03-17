package org.larry.thread.sync03;

/**
 * Created by Larry on 2017/3/17.
 * 数据脏读，解决方法：在方法上都加上synchronized
 *
 */
public class MutilThread {

    private String name  = "z3";
    private String age = "123";
    public synchronized void setValue(String name ,String age){
        try {
            Thread.sleep(2000);
            this.name = name ;
            this.age = age ;
            System.out.println("setValue方法：name="+name+",age="+age);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void getValue(){
        System.out.println("getValue方法：name="+name+",age="+age);
    }

    public static void main(String[] args) throws InterruptedException{
        final MutilThread mutilThread = new MutilThread() ;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mutilThread.setValue("z3","456");
            }
        }) ;

        t1.start();
        Thread.sleep(1000);
        mutilThread.getValue();
    }
}
