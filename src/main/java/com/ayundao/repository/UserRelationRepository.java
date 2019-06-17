package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
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
public interface UserRelationRepository extends BaseRepository<UserRelation, String> {

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

    //根据机构,部门IDS,小组IDS查找用户关系
    @Query("select ur from UserRelation ur where ur.subject.id = ?1 and (ur.depart.id in (?2) or ur.groups.id in (?3))")
    List<UserRelation> findBySubjectAndDepartIdsOrGroupsIds(String subjectId, String[] departIds, String[] groupsIds);

    //根据部门ID获取成员关系
    @Query("select ur.user from UserRelation ur where ur.depart.id = ?1")
    List<User> findByDepartId(String id);

    //根据组织ID获取成员关系
    @Query("select ur.user from UserRelation ur where ur.groups.id = ?1")
    List<User> findByGroupId(String id);
}