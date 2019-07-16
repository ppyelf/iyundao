package com.ayundao.base.shiro;

import com.ayundao.base.utils.EncryptUtils;
import com.ayundao.entity.User;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: RetryLimitHashedCredentialsMatcher
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/11 15:40
 * @Description: 登录次数限制
 * @Version: V1.0
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "shiro:cache:retrylimit:";
    private static final Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
    private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;
    @Resource
    private UserService userService;
    private RedisManager redisManager;

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    private String getRedisKickoutKey(String username) {
        return this.keyPrefix + username;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //获取用户名
        String username = (String)token.getPrincipal();
        User user = StringUtils.isBlank(username) ? null : userService.findByAccount(username);
        //获取用户登录次数
        AtomicInteger retryCount = (AtomicInteger)redisManager.get(getRedisKickoutKey(username));
        if (retryCount == null) {
            //如果用户没有登陆过,登陆次数加1 并放入缓存
            retryCount = new AtomicInteger(0);
        }
        if (retryCount.incrementAndGet() > 5) {
            //如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段
            if (user != null && user.getStatus().ordinal() == 0){
                //数据库字段 默认为 0  就是正常状态 所以 要改为1
                //修改数据库的状态字段为锁定
                user.setStatus(User.ACCOUNT_TYPE.normal);
                userService.save(user);
            }
            logger.info("锁定用户" + user.getAccount());
            //抛出用户锁定异常
            throw new LockedAccountException();
        }
        //判断用户账号和密码是否正确
        String pw = String.valueOf(((UsernamePasswordToken) token).getPassword());
        boolean matches = EncryptUtils.getSaltverifyMD5(pw, user.getPassword());
        if (matches) {
            //如果正确,从缓存中将用户登录计数 清除
            redisManager.del(getRedisKickoutKey(username));
        }{
            redisManager.set(getRedisKickoutKey(username), retryCount);
        }
        return matches;
    }

    /**
     * 根据用户名 解锁用户
     * @param username
     * @return
     */
    public void unlockAccount(String username){
        User user = userService.findByAccount(username);
        if (user != null){
            //修改数据库的状态字段为锁定
            user.setStatus(User.ACCOUNT_TYPE.block);
            userService.save(user);
            redisManager.del(getRedisKickoutKey(username));
        }
    }

}
