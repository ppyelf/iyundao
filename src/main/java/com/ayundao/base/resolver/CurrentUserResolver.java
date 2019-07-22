package com.ayundao.base.resolver;

import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.exception.AuthenticationException;
import com.ayundao.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @ClassName: CurrentUserResolver
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/13 16:48
 * @Description: 当前用户参数解析器
 * @Version: V1.0
 */
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Subject subject = SecurityUtils.getSubject();
        return parameter.getParameterType().isAssignableFrom(User.class)
                && parameter.hasParameterAnnotation(CurrentUser.class)
                && subject.isAuthenticated();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest req, WebDataBinderFactory webDataBinderFactory) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
            return user;
        }
        return new AuthenticationException("无法获取当前用户,认证异常");
    }
}
