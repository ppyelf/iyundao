package com.ayundao.service.impl;

import com.ayundao.entity.Permission;
import com.ayundao.repository.PermissionRepository;
import com.ayundao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: PermissionServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/8 9:07
 * @Description: 实现 - 权限
 * @Version: V1.0
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findByIds(String[] permissionIds) {
        return permissionRepository.findByIds(permissionIds);
    }
}
