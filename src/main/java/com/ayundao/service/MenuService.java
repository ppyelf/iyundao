package com.ayundao.service;

import com.ayundao.entity.*;

import java.util.List;

/**
 * @ClassName: MenuService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 21:48
 * @Description: 服务 - 菜单
 * @Version: V1.0
 */
public interface MenuService {

    /**
     * 根据用户ID获取详细菜单信息
     * @param id
     * @return
     */
    List<Menu> getMenuByUserId(String id, UserRelation ur);

    /**
     * 获取菜单列表
     * @return
     */
    List<Menu> getList();

    /**
     * 根据菜单关系获取菜单列表
     * @param menuRelationId
     * @return
     */
    List<Menu> getListByMenuRelationId(String menuRelationId);

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    Menu findById(String id);

    /**
     * 后台管理模块 - 添加菜单
     * @param menu
     * @param userRelations
     * @param role
     * @param userGroupRelations
     * @return
     */
    Menu save(Menu menu, List<UserRelation> userRelations, List<Role> role, List<UserGroupRelation> userGroupRelations);

    /**
     * 后台管理 - 删除菜单
     * @param id
     */
    void delete(String id);

    /**
     * 修改实体
     * @param menu
     * @param userRelations
     * @param roles
     * @param userGroupRelations
     * @return
     */
    Menu modify(Menu menu, List<UserRelation> userRelations, List<Role> roles, List<UserGroupRelation> userGroupRelations);
}
