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

    @Query("select p from Page p where p.menu in ?1")
    List<Page> getPageByUserAndMenu(List<Menu> menu);

    Page getPageByMenu(Menu menu);

    Page getPageByMenuId(String id);
}
