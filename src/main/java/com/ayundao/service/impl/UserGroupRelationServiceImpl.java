package com.ayundao.service.impl;

import com.ayundao.entity.User;
import com.ayundao.entity.UserGroupRelation;
import com.ayundao.repository.UserGroupRelationRepository;
import com.ayundao.service.UserGroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserGroupRelationServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:57
 * @Description: 服务实现 - 用户组关系
 * @Version: V1.0
 */
@Service
public class UserGroupRelationServiceImpl implements UserGroupRelationService {

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;

    @Override
    public List<UserGroupRelation> findByUser(User user) {
        return userGroupRelationRepository.findByUser(user);
    }

    @Override
    public List<UserGroupRelation> findByUserId(String id) {
        return userGroupRelationRepository.findByUserId(id);
    }

    @Override
    public List<UserGroupRelation> findByUserGroupId(String userGroupId) {
        return userGroupRelationRepository.findByUserGroupId(userGroupId);
    }

}
