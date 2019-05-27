package com.ayundao.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Identity
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/22 10:01
 * @Description: 身份注解
 *                  方法,类
 *                  运行时
 * @Version: V1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Identity {

}
