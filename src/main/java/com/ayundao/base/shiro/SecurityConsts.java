package com.ayundao.base.shiro;

/**
 * @ClassName: SecurityConsts
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/18 6:30
 * @Description: 请求头中信息常量
 * @Version: V1.0
 */
public class SecurityConsts {

    /**
     * request请求头属性
     */
    public static final String IYUNDAO_ASSESS_TOKEN="IYunDao-AssessToken";

    /**
     * 账号
     */
    public static final String ACCOUNT = "account";

    /**
     * Shiro redis 前缀
     */
    public static final String PREFIX_SHIRO_CACHE = "iyundao:cache:";

    /**
     * redis-key-前缀
     * iyundao:refresh_token
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "iyundao:refresh_token:";

    /**
     * JWT-currentTimeMillis
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";
}
