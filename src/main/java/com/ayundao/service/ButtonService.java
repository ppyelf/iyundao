package com.ayundao.service;

import com.ayundao.entity.Button;
import com.ayundao.entity.Role;
import com.ayundao.entity.UserGroup;

import java.util.List;

/**
 * @ClassName: ButtonService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 3:26
 * @Description: 服务 - 按钮
 * @Version: V1.0
 */
public interface ButtonService {

    /**
     * 按钮列表 - list
     * @return
     */
    List<Button> findAllList();

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    Button find(String id);

    /**
     * 保存实体
     * @param button
     * @param userGroups
     * @param roles
     * @return
     */
    Button save(Button button, List<UserGroup> userGroups, List<Role> roles);

    /**
     * 更新实体
     * @param button
     * @param userGroups
     * @param roles
     * @return
     */
    Button update(Button button, List<UserGroup> userGroups, List<Role> roles);

    /**
     * 删除实体
     * @param button
     */
    void delete(Button button);
}
