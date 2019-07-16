package com.ayundao.base.resolver;

import com.ayundao.base.annotation.CurrentSubject;
import com.ayundao.base.exception.AuthenticationException;
import com.ayundao.entity.Subject;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.security.auth.login.LoginException;

/**
 * @ClassName: CurrentSubjectResolver
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/13 16:48
 * @Description: 当前机构解析器
 * @Version: V1.0
 */
public class CurrentSubjectResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        return parameter.getParameterType().isAssignableFrom(Subject.class)
                && parameter.hasParameterAnnotation(CurrentSubject.class)
                && subject.isAuthenticated();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest req, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Subject subject = (Subject) SecurityUtils.getSubject().getSession().getAttribute("currentSubject");
        if (subject != null) {
            return subject;
        }
        return new AuthenticationException("无法获取当前机构,用户异常");
    }
}
