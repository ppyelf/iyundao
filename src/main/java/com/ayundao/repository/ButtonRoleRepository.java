package com.ayundao.repository;

import com.ayundao.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ButtonRoleRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 3:25
 * @Description: 仓库 - 按钮
 * @Version: V1.0
 */
@Repository
public interface ButtonRoleRepository extends CrudRepository<ButtonRole, String> {

    @Query("select br.button from ButtonRole br where br.userGroup in ?1 and br.role in ?2")
    List<Button> findByFieldAndUserAndUserRole(List<UserGroup> userGroup, List<Role> roles);
}
