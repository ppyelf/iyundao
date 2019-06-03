package com.ayundao.repository;

import com.ayundao.entity.User;
import com.ayundao.entity.UserGroupRelation;
import com.ayundao.entity.UserRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserGroupRelationRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:58
 * @Description: 仓库 - 用户组关系
 * @Version: V1.0
 */
@Repository
public interface UserGroupRelationRepository extends CrudRepository<UserGroupRelation, String> {

    //查找用户所属用户组
    List<UserGroupRelation> findByUser(User user);

    //根据用户ID获取用户组集合
    List<UserGroupRelation> findByUserId(String id);

    //根据用户组ID获取用户组集合
    @Query("select ug from UserGroupRelation ug where ug.userGroup.id = ?1")
    List<UserGroupRelation> findByUserGroupId(String userGroupsId);

}
