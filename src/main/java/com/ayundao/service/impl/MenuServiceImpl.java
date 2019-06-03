package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.*;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Menu save(String name, String remark, boolean isPublic, String uri, String fatherId, int level, List<UserRelation> userRelations, Role role, List<UserGroupRelation> userGroupRelations) {
        Date date = new Date(System.currentTimeMillis());
        Menu menu = new Menu();
        menu.setCreatedDate(date);
        menu.setLastModifiedDate(date);
        menu.setPublic(isPublic);
        menu.setName(name);
        menu.setRemark(remark);
        menu.setUri(uri);
        if (StringUtils.isNotBlank(fatherId)) {
            Menu father = menuRepository.findByMenuId(fatherId);
            menu.setFather(father);
        }
        menu.setLevel(level);
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
            mr.setRole(role);
            menuRelationRepository.save(mr);
        }
        return menu;
    }

}
