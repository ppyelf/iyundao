package com.ayundao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: WechatController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/20 9:27
 * @Description: 控制层 - 微信
 * @Version: V1.0
 */
@RestController
@RequestMapping("/wx")
public class WechatController {

    @Value("${wechat.app.id}")
    private String id;

    @Value("${wechat.app.secret}")
    private String secret;


}
