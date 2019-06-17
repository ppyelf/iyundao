package com.ayundao.service.impl;

import com.ayundao.entity.User;
import com.ayundao.entity.UserGroup;
import com.ayundao.repository.UserGroupRepository;
import com.ayundao.service.UserGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserGroupServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 0:17
 * @Description: 实现 - 用户组
 * @Version: V1.0
 */
@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public List<UserGroup> getList() {
        return userGroupRepository.getList();
    }

    @Override
    public UserGroup findById(String id) {
        return userGroupRepository.findByUserGroupId(id);
    }

    @Override
    public UserGroup save(UserGroup userGroup) {
        return userGroupRepository.save(userGroup);
    }

    @Override
    public List<UserGroup> findByIds(String[] userGroupIds) {
        List<UserGroup> userGroups = userGroupRepository.findByIds(userGroupIds);
        return CollectionUtils.isEmpty(userGroups)
                ? new ArrayList<>()
                : userGroups;
    }

    @Override
    public UserGroup saveDepartUsers(UserGroup userGroup, List<User> users) {
        return null;
    }

}
