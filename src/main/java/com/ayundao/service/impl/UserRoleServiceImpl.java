package com.ayundao.service.impl;

import com.ayundao.entity.User;
import com.ayundao.entity.UserRole;
import com.ayundao.repository.UserRoleRepository;
import com.ayundao.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserRoleServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:54
 * @Description: 服务实现 - 角色关系
 * @Version: V1.0
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findByUser(User user) {
        return userRoleRepository.findByUser(user);
    }
}
