package org.larry.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

public class QueryService {

    BloomFilter<String> bloomFilter = null ;

    private final static int POOL_SIZE = 2000 ;

    public void init(){
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")),1000) ;

        for(Integer i =0 ; i < POOL_SIZE ; i++){
            bloomFilter.put(String.valueOf(i.hashCode()));
        }
    }


    public void query(String partNumber){

        boolean flag = bloomFilter.mightContain(partNumber);

        if(!flag){
            System.out.println("not pass guava filter");
            return;
        }


    }




    public static void main(String[] args) {
        QueryService service = new QueryService() ;
        service.init();

        Integer integer = new Random().nextInt(100000);
        System.out.println(integer);
        service.query(String.valueOf(integer.hashCode()));
    }

}
