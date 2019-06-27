package com.ayundao.base.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Excel
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 9:57
 * @Description: 注解 - excel
 * @Version: V1.0
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {

    String name() default "";

    int sort() default 0;
}
