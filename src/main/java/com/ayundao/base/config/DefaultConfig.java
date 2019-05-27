package com.ayundao.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: DefaultConfig
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 8:41
 * @Description: 默认配置
 * @Version: V1.0
 */
@Configuration
public class DefaultConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

}
