package com.ayundao.service.impl;

import com.ayundao.base.Pageable;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        return user.getUserType().equals(User.USER_TYPE.admin);
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
    @Transactional
    public void save(User user, String subjectId, String departId, String groupsId) {
        if (StringUtils.isBlank(subjectId)
                 || StringUtils.isBlank(departId)
                    || StringUtils.isBlank(groupsId)) {
            return ;
        }
        Subject subject = subjectRepository.findBySubjectId(subjectId);
        Depart depart = departRepository.findByDepartId(departId);
        Groups groups = groupsRepository.findByGroupsId(groupsId);
        UserRelation userRelation = new UserRelation();
        userRelation.setSubject(subject);
        userRelation.setDepart(depart);
        userRelation.setGroups(groups);
        userRelation.setUser(user);
        userRepository.save(user);
        userRelationRepository.save(userRelation);
    }

    @Override
    public Page<User> findAllForPage(Pageable pageable) {
        return userRepository.findAllForPage(pageable);
    }
}
