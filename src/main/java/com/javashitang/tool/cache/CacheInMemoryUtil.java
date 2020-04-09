package com.javashitang.tool.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;


public class CacheInMemoryUtil {

    private static Cache cache;

    static {
        String cacheName = "inMemoryCache";
        Configuration config = new Configuration();
        // 最大内存为128M
        config.setMaxBytesLocalHeap(1024 * 1024 * 128L);
        CacheConfiguration defaultCache = new CacheConfiguration();
        // 设置缓存最多条数，0为不限制
        defaultCache.setMaxEntriesInCache(0);
        // 缓存满时用LRU
        defaultCache.setMemoryStoreEvictionPolicy("LRU");
        // 缓存的存活时间
        defaultCache.setTimeToLiveSeconds(86400);
        config.setDefaultCacheConfiguration(defaultCache);
        config.setName(cacheName);
        CacheManager cacheManager = CacheManager.create(config);
        cacheManager.addCache(cacheName);
        cache = cacheManager.getCache(cacheName);
    }

    /**
     * 放到缓存中
     */
    public static <T> void put(String key, T object) {
        Element element = new Element(key, object);
        cache.put(element);
    }


    /**
     * 放到缓存中，并设置超时时间
     */
    public static <T> void put(String key, T object, int expireSeconds) {
        Element element = new Element(key, object);
        element.setTimeToLive(expireSeconds);
        cache.put(element);
    }

    public static <T> T get(String key, Class<T> clazz) {
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return (T) element.getObjectValue();
    }
}
