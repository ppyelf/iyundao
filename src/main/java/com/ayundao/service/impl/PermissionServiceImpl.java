package com.ayundao.service.impl;

import com.ayundao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: PermissionServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/8 9:07
 * @Description: 实现 - 权限
 * @Version: V1.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

}
