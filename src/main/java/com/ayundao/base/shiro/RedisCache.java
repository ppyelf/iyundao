package com.ayundao.base.shiro;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.utils.JwtUtils;
import com.ayundao.entity.User;
import org.apache.commons.collections.bag.UnmodifiableSortedBag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @ClassName: RedisCache
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/12 9:02
 * @Description: Redis缓存管理器
 * @Version: V1.0
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {


    private  Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private RedisCache redisCache;

    private  RedisManager redisManager;

    @CurrentUser
    private User user;

    public RedisCache(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @PostConstruct
    public void  init() {
        redisCache = this;
        redisCache.redisManager = this.redisManager;
        redisCache.user = this.user;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object get(Object key) throws CacheException {
        String tempKey= this.getKey(key);
        if(redisManager.hasKey(tempKey)){
            return redisManager.get(tempKey);
        }
        return null;
    }

    /**
     * 保存缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        return redisManager.set(this.getKey(key), value);
    }

    /**
     * 移除缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        String tempKey= this.getKey(key);
        if(redisManager.hasKey(tempKey)){
            redisManager.del(tempKey);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {}

    @Override
    public int size() {
        //@TODO
        return 20;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        Set keys = this.keys();
        List<V> values = new ArrayList<>();
        for (Object key : keys) {
            values.add((V)redisManager.get(this.getKey(key)));
        }
        return values;
    }

    /**
     * 根据名称获取
     * @param key
     * @return
     */
    private String getKey(Object key) {
        user = getUser();
        if (user != null) {
            key = user.getAccount();
            return SecurityConsts.PREFIX_SHIRO_CACHE + key;
        }
        return SecurityConsts.PREFIX_SHIRO_CACHE + JwtUtils.getClaim(key.toString(), SecurityConsts.ACCOUNT);
    }

    private User getUser() {
        Subject subject = SecurityUtils.getSubject();
        user = (User) subject.getPrincipal();
        return user;
    }
}
