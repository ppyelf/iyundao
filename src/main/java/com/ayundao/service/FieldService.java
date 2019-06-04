package com.ayundao.service;

import com.ayundao.entity.Field;
import com.ayundao.entity.Page;
import com.ayundao.entity.Role;
import com.ayundao.entity.UserGroup;

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

    /**
     * 获取字段所有集合 -- list
     * @return
     */
    List<Field> findAllForList();

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    Field findById(String id);

    /**
     * 保存实体
     * @param field
     * @param userGroups
     * @param roles
     * @return
     */
    Field save(Field field, List<UserGroup> userGroups, List<Role> roles);

    /**
     * 修改实体
     * @param field
     * @param userGroups
     * @param roles
     * @return
     */
    Field update(Field field, List<UserGroup> userGroups, List<Role> roles);

    /**
     * 删除实体
     * @param field
     */
    void delete(Field field);
}
