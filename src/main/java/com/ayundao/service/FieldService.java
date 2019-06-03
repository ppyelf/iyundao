package com.ayundao.service;

import com.ayundao.entity.Field;

import java.util.List;

/**
 * @ClassName: FieldService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 2:40
 * @Description: 服务 - 字段
 * @Version: V1.0
 */
public interface FieldService {

    /**
     * 根据页面ID获取字段集合信息
     * @param pageId
     * @return
     */
    List<Field> findByPageId(String pageId);
}
