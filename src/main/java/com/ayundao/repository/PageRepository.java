package com.ayundao.repository;

import com.ayundao.entity.Menu;
import com.ayundao.entity.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName: PageRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 1:27
 * @Description: 仓库 - 页面
 * @Version: V1.0
 */
@Repository
public interface PageRepository extends CrudRepository<Page, String> {

    //获取菜单所有的页面
    @Query("select p from Page p where p.menu in ?1")
    List<Page> getPageByUserAndMenu(List<Menu> menu);

    //根据菜单获取所有页面分页
    Page getPageByMenu(Menu menu);

    //根据菜单ID获取所有页面分页
    Page getPageByMenuId(String id);
}
