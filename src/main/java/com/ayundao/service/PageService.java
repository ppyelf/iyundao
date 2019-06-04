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

    /**
     * 获取所有页面的集合 - list
     * @return
     */
    List<Page> getAllForList();

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    Page find(String id);

    /**
     * 保存实体
     * @param page
     * @param menu
     * @param father
     * @return
     */
    Page save(Page page, Menu menu, Page father);

    /**
     * 根据菜单ID获取页面集合
     * @param id
     * @return
     */
    List<Page> findPageByMenuId(String id);

    /**
     * 根据页面ID删除实体
     * @param id
     */
    void delete(String id);

    /**
     * 根据父级ID获取所有子集的集合
     * @param id
     * @return
     */
    List<Page> findSonsByFatherId(String id);

    /**
     * 根据IDS获取实体集合
     * @param pageIds
     * @return
     */
    List<Page> findByIds(String[] pageIds);
}
