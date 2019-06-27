package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.DepartRepository;
import com.ayundao.repository.GroupsRepository;
import com.ayundao.repository.SubjectRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.UserRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserRelationServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:44
 * @Description: 服务实现 - 机构关系
 * @Version: V1.0
 */
@Service
@Transactional
public class UserRelationServiceImpl implements UserRelationService {

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Override
    public List<UserRelation> findByUser(User user) {
        return userRelationRepository.findByUser(user);
    }

    @Override
    public List<UserRelation> findByUserId(String id) {
        return userRelationRepository.findByUserId(id);
    }

    @Override
    public UserRelation findByUserIdAndSubject(User user, String subject) {
        Subject s = subjectRepository.find(subject);
        return userRelationRepository.findByUserIdAndSubject(user.getId(), s.getId());
    }

    @Override
    public List<UserRelation> getAll() {
        return userRelationRepository.getAll();
    }

    @Override
    public List<UserRelation> findBySubjectAndDepartOrGroups(String subjectId, String departId, String groupsId) {
        List<UserRelation> userRelations = userRelationRepository.findBySubjectAndDepartOrGroups(subjectId, departId, groupsId);
        return CollectionUtils.isEmpty(userRelations)
                ? new ArrayList<>()
                : userRelations;
    }

    @Override
    public List<UserRelation> findBySubjectAndDepartIdsOrGroupsIds(String subjectId, String[] departIds, String[] groupsIds) {
        List<UserRelation> userRelations = userRelationRepository.findBySubjectAndDepartIdsOrGroupsIds(subjectId, departIds, groupsIds);
        return CollectionUtils.isEmpty(userRelations)
                ? new ArrayList<>()
                : userRelations;
    }
}
