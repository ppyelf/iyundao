package com.ayundao.repository;

import com.ayundao.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: FieldRoleRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 3:20
 * @Description: 仓库 - 字段
 * @Version: V1.0
 */
@Repository
public interface FieldRoleRepository extends CrudRepository<FieldRole, String> {

    @Query("select fr.field from FieldRole fr where fr.userGroupRelation in ?1 and fr.userRole in ?2")
    List<Field> findByPageAndUserGroupAndUserRole(List<UserGroupRelation> userGroupRelations, List<UserRole> userRoles);
}
