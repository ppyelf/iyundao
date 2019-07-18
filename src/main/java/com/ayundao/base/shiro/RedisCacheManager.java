package com.ayundao.base.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: RedisCacheManager
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/12 9:42
 * @Description: redis管理器
 * @Version: V1.0
 */
public class RedisCacheManager implements CacheManager {

    private final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    /**
     * fast lookup by name map
     */
    @Autowired
    private RedisManager redisManager;


    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("get cache, name={}",name);
        return new RedisCache<>(redisManager);
    }
}
