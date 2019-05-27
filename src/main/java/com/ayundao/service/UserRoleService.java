package com.ayundao.service;

import com.ayundao.entity.User;
import com.ayundao.entity.UserRole;

import java.util.List;

/**
 * @ClassName: UserRoleService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:51
 * @Description: 服务 - 角色关系
 * @Version: V1.0
 */
public interface UserRoleService {

    /**
     * 查找用户拥有的角色
     * @param user
     * @return
     */
    List<UserRole> findByUser(User user);
}
