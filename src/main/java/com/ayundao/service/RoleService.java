package com.ayundao.service;

import com.ayundao.entity.Role;

import java.util.List;

/**
 * @ClassName: RoleService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 23:42
 * @Description: 服务 - 角色
 * @Version: V1.0
 */
public interface RoleService {

    /**
     * 获取权限列表
     * @return
     */
    List<Role> getList();

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    Role findById(String id);

    /**
     * 保存实体
     * @param role
     * @return
     */
    Role save(Role role);

    /**
     * 根据ID获取实体信息
     * @param roleId
     * @return
     */
    Role findByRoleId(String roleId);

    /**
     * 根据IDS集合获取实体集合信息
     * @param roleIds
     * @return
     */
    List<Role> findByRoleIds(String[] roleIds);
}
