package com.ayundao.service.impl;

import com.ayundao.entity.Button;
import com.ayundao.entity.ButtonRole;
import com.ayundao.entity.Role;
import com.ayundao.entity.UserGroup;
import com.ayundao.repository.ButtonRepository;
import com.ayundao.repository.ButtonRoleRepository;
import com.ayundao.service.ButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: ButtonServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 3:27
 * @Description: 实现 - 按钮
 * @Version: V1.0
 */
@Service
public class ButtonServiceImpl implements ButtonService {

    @Autowired
    private ButtonRepository buttonRepository;

    @Autowired
    private ButtonRoleRepository buttonRoleRepository;

    @Override
    public List<Button> findAllList() {
        return buttonRepository.findAllList();
    }

    @Override
    public Button find(String id) {
        return buttonRepository.findByButtonId(id);
    }

    @Override
    @Modifying
    @Transactional
    public Button save(Button button, List<UserGroup> userGroups, List<Role> roles) {
        button = buttonRepository.save(button);
        Set<ButtonRole> set = new HashSet<>();
        for (UserGroup userGroup : userGroups) {
            for (Role role : roles) {
                ButtonRole br = new ButtonRole();
                br.setCreatedDate(new Date());
                br.setLastModifiedDate(new Date());
                br.setUserGroup(userGroup);
                br.setRole(role);
                br.setButton(button);
                buttonRoleRepository.save(br);
                set.add(br);
            }
        }
        button.setButtonRoles(set);
        return button;
    }

    @Override
    public Button update(Button button, List<UserGroup> userGroups, List<Role> roles) {
        List<ButtonRole> buttonRoles = buttonRoleRepository.findByButtonId(button.getId());
        buttonRoleRepository.deleteAll(buttonRoles);
        return save(button, userGroups, roles);
    }

    @Override
    public void delete(Button button) {
        List<ButtonRole> buttonRoles = buttonRoleRepository.findByButtonId(button.getId());
        buttonRoleRepository.deleteAll(buttonRoles);
        buttonRepository.delete(button);
    }
}
