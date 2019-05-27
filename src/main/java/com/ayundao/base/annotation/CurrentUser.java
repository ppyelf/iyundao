package com.ayundao.base.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: CurrentUser
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 15:49
 * @Description: 当前用户
 * @Version: V1.0
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

}
