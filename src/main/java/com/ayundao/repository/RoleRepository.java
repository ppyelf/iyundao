package com.ayundao.repository;

import com.ayundao.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: RoleRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 23:43
 * @Description: 仓库 - 角色
 * @Version: V1.0
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

    //获取所有实体结合
    @Query("select r from Role r")
    List<Role> getList();

    //根据ID获取实体信息
    @Query("select r from Role r where r.id = ?1")
    Role findByRoleId(String id);

    //根据IDS获取实体集合信息
    @Query("select r from Role r where r.id in (?1)")
    List<Role> findByRoleIds(String[] roleIds);
}
