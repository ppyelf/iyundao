package com.ayundao.service.impl;

import com.ayundao.entity.RoleRelation;
import com.ayundao.repository.RoleRelationRepository;
import com.ayundao.service.RoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @ClassName: RoleRelationServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/10 11:27
 * @Description: 实现 - 用户权限
 * @Version: V1.0
 */
@Service
@Transactional
public class RoleRelationServiceImpl implements RoleRelationService {

    @Autowired
    private RoleRelationRepository repository;

    @Override
    public Set<RoleRelation> findRolesByUserId(String id) {
        return repository.findRolesByUserId(id);
    }
}
