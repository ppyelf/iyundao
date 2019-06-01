package com.ayundao.service.impl;

import com.ayundao.entity.Role;
import com.ayundao.repository.RoleRepository;
import com.ayundao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 23:42
 * @Description: 实现 - 角色
 * @Version: V1.0
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getList() {
        return roleRepository.getList();
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findByRoleId(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleId(String roleId) {
        return roleRepository.findByRoleId(roleId);
    }
}
