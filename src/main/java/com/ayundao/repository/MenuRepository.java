package com.ayundao.repository;

import com.ayundao.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MenuRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:39
 * @Description: 仓库 - 菜单关系
 * @Version: V1.0
 */
@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {

    //获取所有菜单集合
    @Query("select m from Menu m")
    List<Menu> getList();

    //根据菜单ID获取菜单实体
    @Query("select m from Menu m where m.id = ?1")
    Menu findByMenuId(String id);
}
