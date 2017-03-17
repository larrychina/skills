package org.larry.thread.Sync05;

/**
 * Created by Larry on 2017/3/17.
 *
 */
public class ObjectLock {

    public void method1(){
        synchronized (this){
            System.out.println("对象锁");
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }
    public void method2(){
        synchronized (this){
            System.out.println(this.getClass());
            try {
                System.out.println("类锁");
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }

    public void method3(){
        Object lock = new Object() ;
        synchronized (lock){
            System.out.println("任何对象锁");
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }

}
