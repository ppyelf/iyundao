package com.ayundao.service;

import com.ayundao.entity.Permission;

import java.util.List;

/**
 * @ClassName: PermissionService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/8 9:07
 * @Description: 服务 - 权限
 * @Version: V1.0
 */
public interface PermissionService {

    /**
     * 根据ID集合查询实体集合
     * @param permissionIds
     * @return
     */
    List<Permission> findByIds(String[] permissionIds);
}
