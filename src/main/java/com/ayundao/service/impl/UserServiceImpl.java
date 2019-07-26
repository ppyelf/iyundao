package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ayundao.base.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @ClassName: UserServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:07
 * @Description: 服务实现 - 用户
 * @Version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Autowired
    private RoleRelationRepository roleRelationRepository;

    @Override
    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User findByAccountAndPassword(String username, String password) {
        return userRepository.findByAccountAndPassword(username, password);
    }

    @Override
    public boolean isAdmin(String account) {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            return false;
        } 
        return user.getUserType().equals(User.USER_TYPE.admin);
    }

    @Override
    public Page<User> findByKey(Pageable pageable) {
        return userRepository.findPage(pageable);
    }

    @Override
    public User findById(String id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public void delete(String id) {
        User user = userRepository.findByUserId(id);
        userRepository.delete(user);
    }

    @Override
    public JsonResult save(User user, Subject subject, String departId, String groupsId, List<Role> roles, List<Permission> permissions, JsonResult jsonResult) {
        if (StringUtils.isBlank(departId)
                    || StringUtils.isBlank(groupsId)) {
            JsonResult.failure(601, "用户必须有所属的机构/部门/组织");
        }
        Depart depart = departRepository.findByDepartId(departId);
        Groups groups = groupsRepository.findByGroupsId(groupsId);
        if (subject == null) {
            return JsonResult.failure(604, "机构不存在");
        }
        if (depart == null && groups == null) {
            return JsonResult.failure(603, "部门/组织不存在");
        }
        UserRelation userRelation = new UserRelation();
        userRelation.setCreatedDate(new Date());
        userRelation.setLastModifiedDate(new Date());
        userRelation.setSubject(subject);
        userRelation.setDepart(depart);
        userRelation.setGroups(groups);
        userRelation.setUser(user);
        user = userRepository.save(user);
        if (CollectionUtils.isNotEmpty(roles)) {
            Set<RoleRelation> set = new HashSet<>();
            for (Role role : roles) {
                for (Permission permission : permissions) {
                    RoleRelation ur = new RoleRelation();
                    ur.setCreatedDate(new Date());
                    ur.setLastModifiedDate(new Date());
                    ur.setUser(user);
                    ur.setRole(role);
                    ur.setPermission(permission);
                    ur = roleRelationRepository.save(ur);
                    set.add(ur);
                }
            }
            user.setRoleRelations(set);
        }
        userRelationRepository.save(userRelation);
        Set<UserRelation> userRelations = new HashSet<>();
        userRelations.add(userRelation);
        user.setUserRelations(userRelations);
        user = userRepository.save(user);
        jsonResult.setData(getUserInfoJson(user));
        return jsonResult;
    }

    @Override
    public JSONObject getUserInfoJson(User user) {
        JSONObject userJson = JsonUtils.getJson(user);
        if (CollectionUtils.isNotEmpty(user.getUserRelations())) {
            com.alibaba.fastjson.JSONArray arr = new com.alibaba.fastjson.JSONArray();
            for (UserRelation ur : user.getUserRelations()) {
                JSONObject json = new JSONObject();
                JSONObject subJson = new JSONObject();
                subJson.put("id", ur.getSubject().getId());
                subJson.put("name", ur.getSubject().getName());
                json.put("subject", subJson);
                if (ur.getDepart() != null) {
                    JSONObject deJson = new JSONObject();
                    deJson.put("id", ur.getDepart().getId());
                    deJson.put("name", ur.getDepart().getName());
                    json.put("depart", deJson);
                }
                if (ur.getGroups() != null) {
                    JSONObject groupJson = new JSONObject();
                    groupJson.put("id", ur.getGroups().getId());
                    groupJson.put("name", ur.getGroups().getName());
                    json.put("groups", groupJson);
                }
                arr.add(json);
            }
            userJson.put("userRelations", arr);
        }
        if (CollectionUtils.isNotEmpty(user.getRoleRelations())) {
            JSONArray arr = new JSONArray();
            for (RoleRelation ur : user.getRoleRelations()) {
                JSONObject json = new JSONObject();
                json.put("id", ur.getRole().getId());
                json.put("name", ur.getRole().getName());
                arr.add(json);
            }
            userJson.put("userRoles", arr);
        }
        return userJson;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<User> findByGroupIdForPage(String groupId) {
        return userRepository.findByGroupIdForPage(groupId);
    }

    @Override
    public List<User> findByDepartIdForPage(String departId) {
        return userRepository.findByDepartIdForPage(departId);
    }

    @Override
    public boolean existsCode(String code) {
        User user = userRepository.findByCode(code);
        return user == null ? false : true;
    }

    @Override
    public User findByCode(String code) {
        return userRepository.findByCode(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findbyIds(String[] userids) {
        return userRepository.findByIds(userids);
    }

    @Override
    public List<User> findBySubjectIdForPage(String id) {
        return userRepository.findBySubjectIdForPage(id);
    }
}
