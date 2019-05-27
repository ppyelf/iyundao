package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.MenuService;
import com.ayundao.service.UserRelationService;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MenuServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 21:49
 * @Description: 服务实现 - 菜单
 * @Version: V1.0
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRelationRepository menuRelationRepository;

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<Menu> getMenuByUserId(String id, UserRelation ur) {
        List<UserGroupRelation> userGroupRelations = userGroupRelationRepository.findByUserId(id);
        List<Role> role = userRoleRepository.getRoleByUserId(id);

        List<MenuRelation> menuRelations = menuRelationRepository.findByUserRelationAndRoleId(ur, role);
        List<MenuRelation> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(menuRelations)) {
            for (MenuRelation mr : menuRelations) {
                if (mr.getUserGroupRelation() != null) {
                    for (UserGroupRelation userGroupRelation : userGroupRelations) {
                        if (mr.getUserGroupRelation().getId().equals(userGroupRelation.getId())) {
                            list.add(mr);
                        }
                    }
                }else {
                    list.add(mr);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(menuRelations)) {
            List<Menu> menus = menuRelationRepository.getMenuByMenuRelation(list);
            return CollectionUtils.isNotEmpty(menus)
                    ? menus
                    : new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
