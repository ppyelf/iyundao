package com.ayundao.service.impl;

import com.ayundao.repository.FieldRoleRepository;
import com.ayundao.service.FieldRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: FieldRoleServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 9:00
 * @Description: 实现 - 字段关系
 * @Version: V1.0
 */
@Service
public class FieldRoleServiceImpl implements FieldRoleService {

    @Autowired
    private FieldRoleRepository fieldRoleRepository;
}
