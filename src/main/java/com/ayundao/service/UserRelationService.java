package com.ayundao.service;

import com.ayundao.entity.User;
import com.ayundao.entity.UserRelation;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: UserRelationService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:43
 * @Description: 服务-机构关系
 * @Version: V1.0
 */
public interface UserRelationService {

    /**
     * 查找用户本人的机构关系
     * @param user
     * @return
     */
    List<UserRelation> findByUser(User user);

    /**
     * 根据ID超找用户机构关系
     * @param id
     * @return
     */
    List<UserRelation> findByUserId(String id);

    /**
     * 根据机构和用户获取菜单等信息
     * @param user
     * @param subject
     * @return
     */
    UserRelation findByUserIdAndSubject(User user, String subject);

    /**
     * 获取所有机构关系
     * @return
     */
    List<UserRelation> getAll();

    /**
     * 根据机构,部门,小组查找用户关系
     * @param subjectId
     * @param departId
     * @param groupsId
     * @return
     */
    List<UserRelation> findBySubjectAndDepartOrGroups(String subjectId, String departId, String groupsId);

    /**
     * 根据机构,部门IDS,小组IDS查找用户关系
     * @param subjectId
     * @param departId
     * @param groupsId
     * @return
     */
    List<UserRelation> findBySubjectAndDepartIdsOrGroupsIds(String subjectId, String[] departId, String[] groupsId);


    List<UserRelation> saveAll(Set<UserRelation> userRelations);

    /**
     * 获取所有实体集合List
     * @return
     */
    List<UserRelation> findAll();

    /**
     * 根据部门ID获取成员关系
     * @param id
     * @return
     */
    List<User> findByDepartId(String id);

    /**
     * 根据组织ID获取成员关系
     * @param id
     * @return
     */
    List<User> findByGroupId(String id);
}
