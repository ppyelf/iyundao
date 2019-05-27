package com.ayundao.service;

import com.ayundao.entity.Field;
import com.ayundao.entity.Menu;
import com.ayundao.entity.Page;
import com.ayundao.entity.User;

import java.util.List;

/**
 * @ClassName: PageService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 1:26
 * @Description: 服务 - 页面
 * @Version: V1.0
 */
public interface PageService {

    /**
     * 根据菜单获取页面
     * @param menu
     * @return
     */
    List<Page> getPageByUserAndMenu(List<Menu> menu);

    /**
     * 根据菜单获取页面
     * @param menu
     * @return
     */
    Page getPageByMenu(Menu menu);

    /**
     * 根据用户和页面获取字段属性
     * @param user
     * @param page
     * @return
     */
    List<Field> getFieldsByUserAndPage(User user, Page page);
}
