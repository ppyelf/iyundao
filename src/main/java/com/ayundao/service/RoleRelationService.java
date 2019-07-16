package com.ayundao.service;

import com.ayundao.entity.RoleRelation;

import java.util.Set;

/**
 * @ClassName: RoleRelationService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/10 11:27
 * @Description: 服务 - 用户权限
 * @Version: V1.0
 */
public interface RoleRelationService {

    /**
     * 根据用户ID查询实体集合
     * @param id
     * @return
     */
    Set<RoleRelation> findRolesByUserId(String id);
}
