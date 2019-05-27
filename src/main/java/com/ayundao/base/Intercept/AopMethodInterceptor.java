package com.ayundao.base.Intercept;

import com.ayundao.base.exception.AuthenticationException;
import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.MethodInterceptor;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: AopMethodInterceptor
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/21 5:47
 * @Description: 环绕通知方法拦截器
 * @Version: V1.0
 */
public class AopMethodInterceptor implements MethodInterceptor {

    private List<AnnotationHandler> handlers;

    public AopMethodInterceptor() {
        // 添加权限操作
        List<AnnotationHandler> handler = new ArrayList<>(2);
        handler.add(new RoleAnnotationHandler());
        handler.add(new PermissionAnnotationHandler());
        setHandlers(handler);
    }

    public List<AnnotationHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<AnnotationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    /**
     * 验证权限
     * @param mi
     */
    public void assertAuthorized(MethodInvocation mi) {

        Collection<AnnotationHandler> aaHandlers = getHandlers();

        if (aaHandlers != null && !aaHandlers.isEmpty()) {

            for (AnnotationHandler handler : aaHandlers) {
                try {
                    ((AuthorizingAnnotationHandler) handler).assertAuthorized(getAnnotation(mi, handler));
                } catch (AuthenticationException ae) {
                    if (ae.getCause() == null) ae.initCause(new AuthenticationException("Not authorized to invoke method: " + mi.getMethod()));
                    throw ae;
                }
            }
        }

    }

    private Annotation getAnnotation(MethodInvocation mi, AnnotationHandler handler) {

        Class<? extends Annotation> clazz = handler.getAnnotationClass();

        Method m = mi.getMethod();

        Annotation a = AnnotationUtils.findAnnotation(m, clazz);
        if (a != null) return a;

        Class<?> targetClass = mi.getThis().getClass();
        m = ClassUtils.getMostSpecificMethod(m, targetClass);
        a = AnnotationUtils.findAnnotation(m, clazz);
        if (a != null) return a;
        return AnnotationUtils.findAnnotation(mi.getThis().getClass(), clazz);
    }

}
