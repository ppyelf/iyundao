package com.ayundao.service;


import com.ayundao.entity.MenuRelation;

import java.util.List;

/**
 * @ClassName: MenuRelationService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:38
 * @Description: 服务 - 菜单关系
 * @Version: V1.0
 */
public interface MenuRelationService {


    /**
     * 根据菜单ID查找菜单关系集合
     * @param menuId
     * @return
     */
    List<MenuRelation> findByMenuId(String menuId);
}
