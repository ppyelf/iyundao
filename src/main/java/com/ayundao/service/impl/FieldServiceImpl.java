package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.FieldRepository;
import com.ayundao.repository.FieldRoleRepository;
import com.ayundao.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName: FieldServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 2:40
 * @Description: 实现 - 字段
 * @Version: V1.0
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldRoleRepository fieldRoleRepository;

    @Override
    public List<Field> findByPageId(String pageId) {
        return fieldRepository.findByPageId(pageId);
    }

    @Override
    public List<Field> findAllForList() {
        return fieldRepository.findAllForList();
    }

    @Override
    public Field findById(String id) {
        return fieldRepository.findByFieldId(id);
    }

    @Override
    @Modifying
    @Transactional
    public Field save(Field field, List<UserGroup> userGroups, List<Role> roles) {
        field = fieldRepository.save(field);
        Set<FieldRole> set = new HashSet<>();
        for (UserGroup userGroup : userGroups) {
            for (Role role : roles) {
                FieldRole fr = new FieldRole();
                fr.setCreatedDate(new Date());
                fr.setLastModifiedDate(new Date());
                fr.setUserGroup(userGroup);
                fr.setRole(role);
                fr.setField(field);
                fieldRoleRepository.save(fr);
                set.add(fr);
            }
        }
        field.setFieldRoles(set);
        return field;
    }

    @Override
    @Transactional
    public Field update(Field field, List<UserGroup> userGroups, List<Role> roles) {
        List<FieldRole> fieldRoles = fieldRoleRepository.findByFieldId(field.getId());
        fieldRoleRepository.deleteAll(fieldRoles);
        field = save(field, userGroups, roles);
        return field;
    }

    @Override
    public void delete(Field field) {
        List<FieldRole> fieldRoles = fieldRoleRepository.findByFieldId(field.getId());
        fieldRoleRepository.deleteAll(fieldRoles);
        fieldRepository.delete(field);
    }

}
