package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.TieredMergePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @ClassName: UserServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:07
 * @Description: 服务实现 - 用户
 * @Version: V1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;


    @Override
    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User findByAccountAndPassword(String username, String password) {
        return userRepository.findByAccountAndPassword(username, password);
    }

    @Override
    public boolean isAdmin(String account) {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            return false;
        } 
        return user.getUserType().equals(User.USER_TYPE.amdin);
    }

    @Override
    public Page<User> findByKey(String key, Pageable pageable) {
        key = "%" + key + "%";
        return userRepository.findByKey(key, pageable);
    }

    @Override
    public User findById(String id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public void delete(String id) {
        User user = userRepository.findByUserId(id);
        userRepository.delete(user);
    }

    @Override
    public User save(User user, String subjectId, String departId, String groupsId) {
        if (StringUtils.isBlank(subjectId)
                 || StringUtils.isBlank(departId)
                    || StringUtils.isBlank(groupsId)) {
            user = userRepository.save(user);
            return user;
        }else {
            Subject subject = subjectRepository.findBySubjectId(subjectId);
            Depart depart = departRepository.findByDepartId(departId);
            Groups groups = groupsRepository.findByGroupsId(groupsId);
            UserRelation userRelation = new UserRelation();
            userRelation.setSubject(subject);
            userRelation.setDepart(depart);
            userRelation.setGroups(groups);
            userRelation.setUser(user);
            Set<UserRelation> set = new HashSet<>();
            set.add(userRelation);
            user.setUserRelations(set);
            userRelationRepository.save(userRelation);
        }
        user = userRepository.save(user);
        return user;
    }

    @Override
    public Page<User> findAllForPage(Pageable pageable) {
        return userRepository.findAllForPage(pageable);
    }

    @Override
    public List<User> findNotdistributionUser() {
        List<UserGroupRelation> userGroupRelations = userGroupRelationRepository.findAll();
        List<UserRelation> userRelations = userRelationRepository.findAll();
        Set<String> set = new HashSet<>();
        for (UserGroupRelation userGroupRelation : userGroupRelations) {
            set.add(userGroupRelation.getUser().getId());
        }
        for (UserRelation userRelation : userRelations) {
            set.add(userRelation.getUser().getId());
        }
        return userRepository.findNotdistributionUser(set);
    }

    @Override
    public List<User> findByIds(String[] userIds) {
        List<User> list = userRepository.findByIds(userIds);
        return CollectionUtils.isEmpty(list)
                ? new ArrayList<>()
                : list;
    }

    @Override
    public boolean existsAccount(String account) {
        User user = userRepository.findByAccount(account);
        return user == null ? false : true;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
