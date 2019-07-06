package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.AdvicesInfoDeparRepository;
import com.ayundao.repository.AdvicesRepository;
import com.ayundao.repository.UserRepository;
import com.ayundao.service.AdvicesService;
import com.ayundao.service.DepartService;
import com.ayundao.service.GroupsService;
import com.ayundao.service.SubjectService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: AdvicesServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class AdvicesServiceImpl implements AdvicesService{

    @Autowired
    private AdvicesRepository advicesRepository;

    @Autowired
    private AdvicesInfoDeparRepository advicesInfoDeparRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private DepartService departService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Advices> findAll() {
        return advicesRepository.findAllForList();
    }

    @Override
    @Transactional
    public Advices save(Advices advices, String subjectId, String departId, String groupId) {
        advices = advicesRepository.save(advices);
        AdvicesInfoDepar aid = new AdvicesInfoDepar();
        aid.setCreatedDate(new Date());
        aid.setLastModifiedDate(new Date());
        aid.setAdvices(advices);
        if(StringUtils.isNotBlank(subjectId)){
            Subject subject = subjectService.find(subjectId);
            aid.setSubject(subject);
        }
        if (StringUtils.isNotBlank(departId)){
            Depart depart = departService.findById(departId);
            aid.setDepart(depart);
        }
        if (StringUtils.isNotBlank(groupId)){
            Groups groups = groupsService.findById(groupId);
            aid.setGroups(groups);
        }
        advicesInfoDeparRepository.save(aid);


        return advices;
    }

    @Override
    public Advices findById(String id) {

        return advicesRepository.find(id);
    }

    @Override
    @Transactional
    public void delete(Advices advices) {
            User user = advices.getUser();
            user.setAdvices(null);
            userRepository.save(user);
            advicesRepository.delete(advices);
    }

    @Override
    @Transactional
    public List<Advices> findAdvicesByDeptionId(String id) {

        return advicesRepository.findAdvicesByDeptionId(id);
    }


}
