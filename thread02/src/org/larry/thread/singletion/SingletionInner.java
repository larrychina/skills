package org.larry.thread.singletion;

import java.util.Queue;

/**
 * Created by Larry on 2017/3/26.
 */
public class SingletionInner {
    private static class Inner{
        private static SingletionInner singletionInner = new SingletionInner();
    }

    public static SingletionInner getInstance(){
        return Inner.singletionInner ;
    }

    public static void main(String[] args) {

    }
}
