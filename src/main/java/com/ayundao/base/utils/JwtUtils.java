package com.ayundao.base.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ayundao.base.shiro.SecurityConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @ClassName: JwtUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/16 16:22
 * @Description: JAVA-JWT工具类
 * @Version: V1.0
 */
@Component
public class JwtUtils {

    /**
     * LOGGER
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static JwtUtils jwtUtils;

    @Value("${server.token.tokenExpireTime}")
    public Integer tokenExpireTime;

    @Value("${server.token.refreshTokenExpireTime}")
    public Integer refreshTokenExpireTime;

    @Value("${server.token.shiroCacheExpireTime}")
    public Integer shiroCacheExpireTime;

    @Value("${server.token.secretKey}")
    public String secretKey;

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        String secret = getClaim(token, SecurityConsts.ACCOUNT) + jwtUtils.secretKey;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param account
     * @param currentTimeMillis
     * @return
     */
    public static String sign(String account, String currentTimeMillis) {
        // 帐号加JWT私钥加密
        String secret = account + jwtUtils.secretKey;
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + jwtUtils.tokenExpireTime*60*1000L);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(SecurityConsts.ACCOUNT, account)
                .withClaim(SecurityConsts.CURRENT_TIME_MILLIS, currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    @PostConstruct
    public void  init() {
        jwtUtils = this;
        jwtUtils.tokenExpireTime = this.tokenExpireTime;
        jwtUtils.refreshTokenExpireTime = this.refreshTokenExpireTime;
        jwtUtils.shiroCacheExpireTime = this.shiroCacheExpireTime;
        jwtUtils.secretKey = this.secretKey;
    }

}
