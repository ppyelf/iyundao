package com.ayundao.repository;

import com.ayundao.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: MenuRelationRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 17:52
 * @Description: 仓库 - 菜单关系
 * @Version: V1.0
 */
@Repository
public interface MenuRelationRepository extends CrudRepository<MenuRelation, String> {

    @Query("select m from MenuRelation m where m.userRelation = ?1 and m.role in ?2")
    List<MenuRelation> findByUserRelationAndRoleId(UserRelation userRelations, List<Role> role);

    @Query("select mr.menu from MenuRelation mr where mr in ?1")
    List<Menu> getMenuByMenuRelation(List<MenuRelation> list);

    @Query("select mr from MenuRelation mr where mr.menu.id = ?1")
    List<MenuRelation> findByMenuId(String menuId);
}
