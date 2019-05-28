package com.ayundao.repository;

import com.ayundao.entity.UserGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserGroupRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 0:18
 * @Description: 仓库 - 用户组
 * @Version: V1.0
 */
@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, String> {

    @Query("select ug from UserGroup ug")
    List<UserGroup> getList();

    @Query("select ug from UserGroup ug where ug.id = ?1")
    UserGroup findByUserGroupId(String id);
}
