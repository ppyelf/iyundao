package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.*;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * 获取用户关系
     * @param user
     * @return
     */
    List<UserRelation> findByUser(User user);

    /**
     * 根据用户ID获取用户关系
     * @param id
     * @return
     */
    List<UserRelation> findByUserId(String id);

    /**
     * 根据用户ID和机构ID获取用户关系
     * @param userId
     * @param subjectId
     * @return
     */
    @Query("select ur from UserRelation ur where ur.user.id = ?1 and ur.subject.id = ?2")
    UserRelation findByUserIdAndSubject(String userId, String subjectId);

    /**
     * 获取所有的用户关系
     * @return
     */
    @Query("select ur from UserRelation ur")
    List<UserRelation> getAll();

    /**
     * 根据机构ID,部门ID,小组ID获取用户关系集合
     * @param subjectId
     * @param departId
     * @param groupsId
     * @return
     */
    @Query("select ur from UserRelation ur where ur.subject.id = ?1 and (ur.depart.id = ?2 or ur.groups.id = ?3)")
    List<UserRelation> findBySubjectAndDepartOrGroups(String subjectId, String departId, String groupsId);

    /**
     * 根据机构,部门IDS,小组IDS查找用户关系
     * @param subjectId
     * @param departIds
     * @param groupsIds
     * @return
     */
    @Query("select ur from UserRelation ur where ur.subject.id = ?1 and (ur.depart.id in (?2) or ur.groups.id in (?3))")
    List<UserRelation> findBySubjectAndDepartIdsOrGroupsIds(String subjectId, String[] departIds, String[] groupsIds);

    /**
     * 根据部门id查询用户id
     * @param subjectId
     * @return
     */
    @Query("select t.id from UserRelation t where t.depart.id = ?1")
    List<String> selectByDepart(String subjectId);

    /**
     * 根据组织id查询用户id
     * @param subjectId
     * @return
     */
    @Query("select t.id from UserRelation t where t.groups.id = ?1")
    List<String> selectByGroup(String subjectId);

    /**
     * 根据机构id查询用户id
     * @param subjectId
     * @return
     */
    @Query("select t.id from UserRelation t where t.subject.id = ?1")
    List<String> selectBySubjectId(String subjectId);

    /**
     * 根据用户ID和所属机构/组织ID查询所属关系
     * @param userId
     * @param departId
     * @param groupsId
     * @return
     */
    @Query("select ur from UserRelation ur where ur.user.id = ?1 and (ur.depart.id = ?2 or ur.groups.id = ?3)")
    UserRelation findByUserIdAndDepartIdOrGroupId(String userId, String departId, String groupsId);

    /**
     * 根据用户IDS获取实体集合信息
     * @param userIds
     * @return
     */
    @Query("select ur from UserRelation ur where ur.user.id in (?1)")
    List<UserRelation> findByUserIds(String[] userIds);

}