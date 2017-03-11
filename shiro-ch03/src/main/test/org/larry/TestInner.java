package org.larry;

import net.sf.ehcache.Ehcache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.larry.shiro.spring.SpringCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.*;

/**
 * Created by Larry on 2017/3/10.
 * 测试内部类
 */
public class TestInner {
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {

       // return new SpringCache(springCahce);
        return null ;
    }

    class SpringCache implements Cache{

        private org.springframework.cache.Cache springCache ;

        public SpringCache(org.springframework.cache.Cache springCache) {
            this.springCache = springCache;
        }

        public Object get(Object o) throws CacheException {
            Object value = springCache.get(o);
            if (value instanceof SimpleValueWrapper) {
                return ((SimpleValueWrapper) value).get();
            }
            return value;
        }

        public Object put(Object o, Object o2) throws CacheException {
            springCache.put(o,o2);
            return o2;
        }

        public Object remove(Object o) throws CacheException {
            springCache.evict(o);
            return null;
        }

        public void clear() throws CacheException {
            springCache.clear();
        }

        public int size() {
            // 首先需要判断是哪个cahce实现，从对应的实现cache寻找相应的方法
            if(springCache instanceof Ehcache){
                Ehcache cache = (Ehcache) springCache.getNativeCache();
                return cache.getSize() ;
            }
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported!") ;
        }

        public Set keys() {
            if(springCache instanceof Ehcache){
                Ehcache cache = (Ehcache) springCache.getNativeCache();
                new HashSet(cache.getKeys())  ;
            }
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported!") ;
        }

        public Collection values() {
            return null;
        }

    }
}
