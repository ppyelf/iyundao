package com.ayundao.base.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: CurrentSubject
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/11 11:32
 * @Description: 当前机构
 * @Version: V1.0
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentSubject {

}
