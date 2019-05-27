package com.ayundao.service;

import com.ayundao.entity.Menu;
import com.ayundao.entity.UserRelation;

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
}
