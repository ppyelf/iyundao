package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
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
public interface FieldRoleRepository extends BaseRepository<FieldRole, String> {

    //根据用户组和角色集合获取字段信息
    @Query("select f from FieldRole f where f.userGroup in (?1) and f.role in (?2)")
    List<Field> findByPageAndUserGroupAndRole(List<UserGroup> userGroups, List<Role> role);

    //根据字段ID获取字段关系合集
    @Query("select fr from FieldRole fr where fr.field.id = ?1")
    List<FieldRole> findByFieldId(String id);
}
