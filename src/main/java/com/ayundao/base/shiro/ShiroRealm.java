package com.ayundao.base.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ayundao.base.utils.EncryptUtils;
import com.ayundao.base.utils.JwtUtils;
import com.ayundao.entity.*;
import com.ayundao.service.RoleRelationService;
import com.ayundao.service.UserRelationService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: ShiroRealm
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/8 16:45
 * @Description: shiro - 用户域
 * @Version: V1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private RoleRelationService roleRelationService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        String account = StringUtils.isBlank(token.getAccount()) ? null : token.getAccount();
        account = StringUtils.isBlank(account) ? JwtUtils.getClaim(token.getToken(), SecurityConsts.ACCOUNT) : account;
        String assessToken = JwtUtils.sign(account, redisManager.CURRENT_TIME_MILLIS);
        String password = String.valueOf(token.getPassword());
        // 从数据库获取对应用户名密码的用户
        User user = StringUtils.isBlank(account) ? null : userService.findByAccount(token.getAccount());
        if (user == null) {
            throw new UnknownAccountException("用户名/密码不正确");
        }
        if (user.getStatus().equals(User.ACCOUNT_TYPE.block)) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        if (user.getStatus().equals(User.ACCOUNT_TYPE.disable)) {
            throw new LockedAccountException("账号已禁用,无法登陆");
        }
        //过期时间内的处理方式
        if (JwtUtils.verify(assessToken)
                && redisManager.hasKey(redisManager.getPREFIX_SHIRO_REFRESH_TOKEN() + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = redisManager.get(redisManager.getPREFIX_SHIRO_REFRESH_TOKEN() + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (redisManager.get(redisManager.getPREFIX_SHIRO_REFRESH_TOKEN() + account).equals(currentTimeMillisRedis)) {
                if (EncryptUtils.getSaltverifyMD5(password, user.getPassword())) {
                    refreshToken(account);
                    return new SimpleAuthenticationInfo(user, token.getPassword(), this.getName());
                }else{
                    throw new AccountException("用户名/密码不正确");
                }
            }
        }
        //过期时间后的处理方式
        if (JwtUtils.verify(assessToken)
                && !redisManager.hasKey(redisManager.getPREFIX_SHIRO_REFRESH_TOKEN() + account)
                && EncryptUtils.getSaltverifyMD5(password, user.getPassword())) {
            refreshToken(account);
            return new SimpleAuthenticationInfo(user, token.getPassword(), this.getName());
        } 
        throw new TokenExpiredException("Token已过期");
    }

    private void refreshToken(String account) {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        // 清除可能存在的Shiro权限信息缓存
        String tokenKey=SecurityConsts.PREFIX_SHIRO_CACHE + account;
        if (redisManager.hasKey(tokenKey)) {
            redisManager.del(tokenKey);
        }

        //更新RefreshToken缓存的时间戳
        String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        if (redisManager.hasKey(refreshTokenKey)) {
            redisManager.set(refreshTokenKey, currentTimeMillis, redisManager.getRefreshTokenExpireTime()*60*60L);
        }else{
            redisManager.set(refreshTokenKey, currentTimeMillis, redisManager.getRefreshTokenExpireTime()*60*60L);
        }
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserAuthorizationInfo info =  new UserAuthorizationInfo();

        //获取用户机构
        List<UserRelation> userRelations = userRelationService.findByUser(user);
        Set<String> subjectType = new HashSet<>();
        for (UserRelation ur : userRelations) {
            subjectType.add(ur.getSubject().getSubjectType().name());
        }
        info.addOrgans(subjectType);

        //获取用户角色
        Set<RoleRelation> roleRelation = roleRelationService.findRolesByUserId(user.getId());
        //添加角色
        Set<Permission> permissions = new HashSet<>();
        for (RoleRelation rr : roleRelation) {
            info.addRole(rr.getRole().getCode());
            info.addStringPermission(rr.getPermission().getCode());
            permissions.add(rr.getPermission());
        }

        //添加权限
        for (Permission permission:permissions) {
            info.addStringPermission(permission.getCode());
        }
        return info;
    }
    
    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的认证缓存和授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
