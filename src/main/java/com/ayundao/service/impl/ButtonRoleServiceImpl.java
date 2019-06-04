package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.ButtonRoleRepository;
import com.ayundao.repository.UserGroupRelationRepository;
import com.ayundao.repository.UserRoleRepository;
import com.ayundao.service.ButtonRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ButtonRoleServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 3:30
 * @Description: 实现 - 按钮关系
 * @Version: V1.0
 */
@Service
@Transactional
public class ButtonRoleServiceImpl implements ButtonRoleService {

    @Autowired
    private ButtonRoleRepository buttonRoleRepository;

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<Button> findByUserAndField(User user, Field field) {
        List<UserGroup> userGroups = userGroupRelationRepository.findByUserIdForUserGroup(user.getId());
        List<Role> userRoles = userRoleRepository.findByUserId(user.getId());
        List<Button> buttons = buttonRoleRepository.findByFieldAndUserAndUserRole(
                CollectionUtils.isEmpty(userGroups) ? null : userGroups,
                CollectionUtils.isEmpty(userRoles) ? null : userRoles);
        if (CollectionUtils.isNotEmpty(buttons)) {
            List<Button> list = new ArrayList<>();
            for (Button button : buttons) {
                if (button.getField().getId().equals(field.getId())) {
                    list.add(button);
                }
            }
            return list;
        }
        return new ArrayList<>();
    }
}
