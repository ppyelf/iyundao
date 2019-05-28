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

    @Query("select r from Role r")
    List<Role> getList();

    @Query("select r from Role r where r.id = ?1")
    Role findByRoleId(String id);
}
