package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.*;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private MenuRepository menuRepository;

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private RoleService roleService;

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

    @Override
    public List<Menu> getList() {
        return menuRepository.getList();
    }

    @Override
    public List<Menu> getListByMenuRelationId(String menuRelationId) {
        return null;
    }

    @Override
    public Menu findById(String id) {
        return menuRepository.findByMenuId(id);
    }

    @Override
    @Transactional
    public Menu save(Menu menu, List<UserRelation> userRelations, List<Role> role, List<UserGroupRelation> userGroupRelations) {
        Date date = new Date(System.currentTimeMillis());
        menu = menuRepository.save(menu);
        //用户关系
        //用户组关系
        List<MenuRelation> menuRelations = new ArrayList<>();
        for (UserRelation userRelation : userRelations) {
            MenuRelation mr = new MenuRelation();
            mr.setCreatedDate(date);
            mr.setLastModifiedDate(date);
            mr.setUserRelation(userRelation);
            for (UserGroupRelation userGroupRelation : userGroupRelations) {
                mr.setUserGroupRelation(userGroupRelation);
                menuRelations.add(mr);
            }
        }
        //角色关系
        for (MenuRelation mr : menuRelations) {
            mr.setMenu(menu);
            for (Role r : role) {
                mr.setRole(r);
                menuRelationRepository.save(mr);
            }
        }
        return menu;
    }

    @Override
    @Modifying
    @Transactional
    public void delete(String id) {
        Menu menu = menuRepository.findByMenuId(id);
        List<MenuRelation> menuRelations = menuRelationRepository.findByMenuId(menu.getId());
        if (CollectionUtils.isNotEmpty(menuRelations)) {
            menuRelationRepository.deleteAll(menuRelations);
        }
        menuRepository.deleteById(id);
    }

    @Override
    @Modifying
    @Transactional
    public Menu modify(Menu menu, List<UserRelation> userRelations, List<Role> roles, List<UserGroupRelation> userGroupRelations) {
        List<MenuRelation> menuRelations = menuRelationRepository.findByMenuId(menu.getId());
        if (CollectionUtils.isNotEmpty(menuRelations)) {
            menuRelationRepository.deleteAll(menuRelations);
        }
        return save(menu, userRelations, roles, userGroupRelations);
    }

}
