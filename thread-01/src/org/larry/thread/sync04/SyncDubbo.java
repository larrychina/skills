package org.larry.thread.sync04;

/**
 * Created by Larry on 2017/3/17.
 * synchronized 在继承中依然可以使用
 */
public class SyncDubbo {

    static class Supper{
        protected int i = 10 ;
        public  void operationSupper(){
            try {
                i -- ;
                System.out.println("main print i="+i);
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    static class Subber extends Supper{

        public  void operationSubber(){
            try {
                while (i > 0 ){
                    i -- ;
                    System.out.println("subber print i="+i);
                    //Thread.sleep(1000);
                    operationSupper();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            final Subber sb = new Subber() ;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    sb.operationSubber();
                }
            });
            t1.start();
        }
    }
}
