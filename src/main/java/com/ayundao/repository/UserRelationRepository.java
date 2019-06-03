package com.ayundao.repository;

import com.ayundao.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserRelationRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 15:08
 * @Description: 仓库 - 用户关系
 * @Version: V1.0
 */
@Repository
public interface UserRelationRepository extends CrudRepository<UserRelation, String> {

    //获取用户关系
    List<UserRelation> findByUser(User user);

    //根据用户ID获取用户关系
    List<UserRelation> findByUserId(String id);

    //根据用户ID和机构ID获取用户关系
    @Query("select ur from UserRelation ur where ur.user.id = ?1 and ur.subject.id = ?2")
    UserRelation findByUserIdAndSubject(String userId, String subjectId);

    //获取所有的用户关系
    @Query("select ur from UserRelation ur")
    List<UserRelation> getAll();

    //根据机构ID,部门ID,小组ID获取用户关系集合
    @Query("select ur from UserRelation ur where ur.subject.id = ?1 and (ur.depart.id = ?2 or ur.groups.id = ?3)")
    List<UserRelation> findBySubjectAndDepartOrGroups(String subjectId, String departId, String groupsId);
}