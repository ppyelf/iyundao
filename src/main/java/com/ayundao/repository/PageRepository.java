package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
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
public interface PageRepository extends BaseRepository<Page, String> {

    //获取菜单所有的页面
    @Query("select p from Page p where p.menu in ?1")
    List<Page> getPageByUserAndMenu(List<Menu> menu);

    //根据菜单获取所有页面分页
    Page getPageByMenu(Menu menu);

    //根据菜单ID获取所有页面分页
    Page getPageByMenuId(String id);

    //获取所有页面的集合 - list
    @Query("select p from Page p")
    List<Page> getAllForList();

    //根据ID获取实体信息
    @Query("select p from Page p where p.id = ?1")
    Page find(String id);

    //根据菜单ID获取页面集合
    @Query("select p from Page p where p.menu.id = ?1")
    List<Page> findPageByMenuId(String id);

    //根据父级ID获取所有子集的集合
    @Query("select p from Page p where p.father.id = ?1")
    List<Page> findSonsByFatherId(String id);

    //根据IDS获取实体集合信息
    @Query("select p from Page p where p.id in (?1)")
    List<Page> findByIds(String[] pageIds);

    //获取没有父级的菜单集合
    @Query("select p from Page p where p.father is null")
    List<Page> getPageByFatherIsNull();
}
