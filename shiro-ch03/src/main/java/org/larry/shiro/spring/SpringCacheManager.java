package org.larry.shiro.spring;

import net.sf.ehcache.Ehcache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.*;

/**
 * Created by Larry on 2017/3/10.
 * shiro提供了类似spring的cahce抽象，shiro本身不是实现缓存，但是又提供了cache的抽象，方便不同的cache实现方式（如redis..）
 */
public class SpringCacheManager implements CacheManager {

    /**
     * 设置spring cache manager
    */
    private org.springframework.cache.CacheManager cacheManager ;

    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        org.springframework.cache.Cache springCahce =this.cacheManager.getCache(s) ;
        return new SpringCache(springCahce);
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
            if(springCache instanceof Ehcache){
                Ehcache cache = (Ehcache) springCache.getNativeCache();
                List keys = cache.getKeys() ;
                if(!CollectionUtils.isEmpty(keys)){
                    List list = new ArrayList(keys.size()) ;
                    Iterator it = keys.iterator() ;
                    while (it.hasNext()){
                       Object key = it.next() ;
                        Object value = cache.get(key) ;
                        if(null != value)
                            list.add(value) ;
                    }
                    return Collections.unmodifiableCollection(list) ;
                }else {
                    return Collections.emptyList();
                }
            }
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported!") ;
        }
    }

}
