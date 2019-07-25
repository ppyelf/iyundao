package com.ayundao.base.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: WxUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/25 8:31
 * @Description: 微信工具类
 * @Version: V1.0
 */
@Component
public class WxUtils {

    /**
     * 请求网址
     */
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     * 固定参数
     */
    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";
    @Value("${wechat.app.id}")
    private String id;
    @Value("${wechat.app.secret}")
    private String secret;
    @Value("${wechat.app.url}")
    private String url;

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getUrl() {
        return url;
    }

}
