package com.ayundao.service.impl;

import com.ayundao.entity.Depart;
import com.ayundao.entity.Subject;
import com.ayundao.entity.User;
import com.ayundao.entity.UserRelation;
import com.ayundao.repository.DepartRepository;
import com.ayundao.repository.SubjectRepository;
import com.ayundao.service.DepartService;
import com.ayundao.service.UserRelationService;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: DepartServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:25
 * @Description: 实现 - 部门
 * @Version: V1.0
 */
@Service
@Transactional
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRelationService userRelationService;

    @Override
    public List<Depart> findBySubjectId(String subjectId) {
        return departRepository.findBySubjectId(subjectId);
    }

    @Override
    public List<Depart> getList() {
        return departRepository.getList();
    }

    @Override
    public Depart findById(String id) {
        return departRepository.findByDepartId(id);
    }

    @Override
    public Depart save(Depart depart) {
        return departRepository.save(depart);
    }

    @Override
    public List<Depart> findByIds(String[] departIds) {
        return departRepository.findByIds(departIds);
    }

    @Override
    public List<Depart>  saveAll(List<Depart> departs) {
        return departRepository.saveAll(departs);
    }

    @Override
    public Depart saveDepartUsers(Depart depart, List<User> users) {
        Subject subject = subjectRepository.find(depart.getSubject().getId());
        Set<UserRelation> userRelations = new HashSet<>();
        for (User user : users) {
            UserRelation ur = new UserRelation();
            ur.setCreatedDate(new Date());
            ur.setLastModifiedDate(new Date());
            ur.setSubject(subject);
            ur.setUser(user);
            ur.setDepart(depart);
            userRelations.add(ur);
        }
        userRelationService.saveAll(userRelations);
        return depart;
    }

}
