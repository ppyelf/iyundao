package com.ayundao.service.impl;

import com.ayundao.entity.Depart;
import com.ayundao.entity.Groups;
import com.ayundao.repository.GroupsRepository;
import com.ayundao.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: GroupsServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 15:15
 * @Description: 实现 - 小组
 * @Version: V1.0
 */
@Service
@Transactional
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;

    @Override
    public List<Groups> findBySubjectId(String subjectId) {
        return groupsRepository.findBySubjectId(subjectId);
    }

    @Override
    public List<Groups> getList() {
        return groupsRepository.getList();
    }

    @Override
    public Groups findById(String id) {
        return groupsRepository.findByGroupsId(id);
    }

    @Override
    public Groups save(Groups groups) {
        return groupsRepository.save(groups);
    }
}
