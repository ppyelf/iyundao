package com.ayundao.service;

import com.ayundao.entity.Page;
import com.ayundao.entity.UserGroup;

import java.util.List;

/**
 * @ClassName: UserGroupService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 0:16
 * @Description: 服务 - 用户组
 * @Version: V1.0
 */
public interface UserGroupService {

    /**
     * 获取用户组列表
     * @return
     */
    List<UserGroup> getList();

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    UserGroup findById(String id);

    /**
     * 保存用户组实体
     * @param userGroup
     */
    UserGroup save(UserGroup userGroup);


    List<UserGroup> findByIds(String[] userGroupIds);
}
