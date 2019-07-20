package com.ayundao.base.shiro;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JwtUtils;
import com.ayundao.entity.User;
import com.ayundao.service.UserGroupService;
import com.ayundao.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName: JwtFilter
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/16 16:08
 * @Description: JWT过滤
 * @Version: V1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * LOGGER
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    @Autowired
    private RedisManager redisManager;
    @Value("${server.token.tokenExpireTime}")
    private Integer tokenExpireTime;
    @Value("${server.token.refreshTokenExpireTime}")
    private Integer refreshTokenExpireTime;
    @Value("${server.token.shiroCacheExpireTime}")
    private Integer shiroCacheExpireTime;
    @Value("${server.token.secretKey}")
    private String secretKey;

    @Autowired
    private UserService userService;

    /**
     * 检测Header里Authorization字段
     * 判断是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SecurityConsts.IYUNDAO_ASSESS_TOKEN);
        return authorization != null;
    }

    /**
     * 登录验证
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String assessToken = httpServletRequest.getHeader(SecurityConsts.IYUNDAO_ASSESS_TOKEN);
        Subject subject = SecurityUtils.getSubject();
        String account = JwtUtils.getClaim(assessToken, SecurityConsts.ACCOUNT);
        User user = subject.getPrincipal() == null
                ? userService.findByAccount(account)
                : (User) subject.getPrincipal();
        JwtToken token = null;
        if (user != null) {
            token = new JwtToken(user.getAccount(), user.getPassword(), true, assessToken);
        } 
        boolean needLogin = !subject.isAuthenticated()
                || redisManager.hasKey(redisManager.getPREFIX_SHIRO_REFRESH_TOKEN() + account);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try{
            if (needLogin) {
                subject.login(token);
                return true;
            }
            return false;
        } catch (UnknownAccountException ex) {
            return false;
        } catch (LockedAccountException ex) {
            return false;
        } catch (AccountException ex) {
            return false;
        } catch (TokenExpiredException ex) {
            return false;
        } catch (IncorrectCredentialsException ex){
            return false;
        }
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 获取AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前Token的帐号信息
        String account = JwtUtils.getClaim(token, SecurityConsts.ACCOUNT);
        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        // 判断Redis中RefreshToken是否存在
        if (redisManager.hasKey(refreshTokenCacheKey)) {
            // 获取RefreshToken时间戳,及AccessToken中的时间戳
            // 相比如果一致，进行AccessToken刷新
            String currentTimeMillisRedis = redisManager.get(refreshTokenCacheKey).toString();
            String tokenMillis=JwtUtils.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS);

            if (tokenMillis.equals(currentTimeMillisRedis)) {

                // 设置RefreshToken中的时间戳为当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                Integer refreshTokenExpireTime = JwtUtils.jwtUtils.refreshTokenExpireTime;
                redisManager.set(refreshTokenCacheKey, currentTimeMillis,refreshTokenExpireTime*60L);

                // 刷新AccessToken，为当前最新时间戳
                token = JwtUtils.sign(account, currentTimeMillis);

                // 使用AccessToken 再次提交给ShiroRealm进行认证，如果没有抛出异常则登入成功，返回true
                JwtToken jwtToken = new JwtToken(token);
                this.getSubject(request, response).login(jwtToken);

                // 设置响应的Header头新Token
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(SecurityConsts.IYUNDAO_ASSESS_TOKEN, token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConsts.IYUNDAO_ASSESS_TOKEN);
                return true;
            }
        }
        return false;
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    // AccessToken已过期
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                this.response401(request, response, msg);
                return false;
            }
        }
        return true;
    }

    /**
     * 401非法请求
     * @param req
     * @param resp
     */
    private JsonResult response401(ServletRequest req, ServletResponse resp,String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");

        JsonResult result = JsonResult.success();
        result.setCode(200);
        result.setMessage(msg);
        return result;
    }
}
