package com.ayundao.service.impl;

import com.ayundao.entity.Depart;
import com.ayundao.entity.Groups;
import com.ayundao.entity.Subject;
import com.ayundao.repository.SubjectRepository;
import com.ayundao.service.DepartService;
import com.ayundao.service.GroupsService;
import com.ayundao.service.SubjectService;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.hql.spi.id.inline.IdsClauseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.acl.Group;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SubjectServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:29
 * @Description: 服务层--机构
 * @Version: V1.0
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findList();
    }

    @Override
    public Subject find(String id) {
        return subjectRepository.find(id);
    }

    @Override
    @Transactional
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject saveDepartAndGroup(Subject subject, String[] departIds, String[] groupIds) {
        List<Depart> departs = departService.findByIds(departIds);
        List<Groups> groups = groupsService.findbyIds(groupIds);
        if (CollectionUtils.isNotEmpty(departs)) {
            for (Depart depart : departs) {
                depart.setSubject(subject);
            }
            departService.saveAll(departs);
        } 
        
        if (CollectionUtils.isNotEmpty(groups)) {
            for (Groups group : groups) {
                group.setSubject(subject);
            }
            groupsService.saveAll(groups);
        }
        Set<Depart> departSet = new HashSet<>();
        departSet.addAll(departs);
        Set<Groups> groupSet = new HashSet<>();
        groupSet.addAll(groups);
        subject.setDeparts(departSet);
        subject.setGroups(groupSet);
        subject = subjectRepository.save(subject);
        return subject;
    }

    @Override
    public boolean exists(String id) {
        return subjectRepository.exists(id);
    }

    @Override
    public boolean existsCode(String code) {
        Subject subject = subjectRepository.findByCode(code);
        return subject == null ? false : true;
    }

    @Override
    public Subject findByCode(String code) {
        return subjectRepository.findByCode(code);
    }

    @Override
    public List<Subject> findbyIds(String[] subjectIds) {

        return subjectRepository.findByIds(subjectIds);
    }

    @Override
    public List<Subject> findMySubjectByUserId(String userId) {
        return subjectRepository.findMySubjectByUserId(userId);
    }


}
