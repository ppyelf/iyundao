package com.ayundao.repository;

import com.ayundao.entity.Button;
import com.ayundao.entity.ButtonRole;
import com.ayundao.entity.UserGroupRelation;
import com.ayundao.entity.UserRole;
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

    @Query("select br.button from ButtonRole br where br.userGroupRelation in ?1 and br.userRole in ?2")
    List<Button> findByFieldAndUserAndUserRole(List<UserGroupRelation> userGroupRelations, List<UserRole> userRoles);
}
