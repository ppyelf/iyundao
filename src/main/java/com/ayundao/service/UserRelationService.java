package com.ayundao.service;

import com.ayundao.entity.User;
import com.ayundao.entity.UserRelation;

import java.util.List;

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
}
