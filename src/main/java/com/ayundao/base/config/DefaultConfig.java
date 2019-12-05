package com.ayundao.base.config;

import com.ayundao.base.resolver.CurrentSubjectResolver;
import com.ayundao.base.resolver.CurrentUserResolver;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }
    
    private static final Map<String, String> VALIDATION_ANNATATION_DEFAULT_MESSAGES =
            new HashMap<String, String>(20) {{
                put("NotNull", "common.message.notNull");
                put("Max", "common.message.max");
            }};

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setMessageInterpolator(new MessageInterpolator());
        return localValidatorFactoryBean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserResolver());
        argumentResolvers.add(currentSubjectResolver());
        WebMvcConfigurer.super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public CurrentUserResolver currentUserResolver() {
        return new CurrentUserResolver();
    }

    @Bean
    public CurrentSubjectResolver currentSubjectResolver() {
        return new CurrentSubjectResolver();
    }

    /**
     * 消息拦截器
     */
    private class MessageInterpolator extends ResourceBundleMessageInterpolator {
        @Override
        public String interpolate(String message, Context context, Locale locale) {
            String annotationTypeName = context.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();

            String annotationDefaultMessageCode = VALIDATION_ANNATATION_DEFAULT_MESSAGES.get(annotationTypeName);
            if (null != annotationDefaultMessageCode && !message.startsWith("javax.validation")
                    && !message.startsWith("org.hibernate.validator.constraints")) {
                message = "{" + annotationDefaultMessageCode + "}";
            }

            return super.interpolate(message, context, locale);
        }
    }
}
