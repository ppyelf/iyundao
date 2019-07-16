package com.ayundao.base.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName: RedisCacheManager
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/12 9:42
 * @Description: redis管理器
 * @Version: V1.0
 */
public class RedisCacheManager implements CacheManager {

    /**
     * The Redis key prefix for caches
     */
    public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";
    public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "authCacheKey or id";
    /**
     * expire time in seconds
     */
    private static final int DEFAULT_EXPIRE = 1800;
    private final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    /**
     * fast lookup by name map
     */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    private RedisManager redisManager;
    private int expire = DEFAULT_EXPIRE;
    private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;
    private String principalIdFieldName = DEFAULT_PRINCIPAL_ID_FIELD_NAME;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("get cache, name={}",name);

        Cache cache = caches.get(name);

        if (cache == null) {
            cache = new RedisCache<K, V>(redisManager,keyPrefix + name + ":", expire, principalIdFieldName);
            caches.put(name, cache);
        }
        return cache;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }
}
