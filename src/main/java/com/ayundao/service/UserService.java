package com.ayundao.service;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Role;
import com.ayundao.entity.User;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;

import java.util.List;


/**
 * @ClassName: UserService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:06
 * @Description: 服务层 -- 用户
 * @Version: V1.0
 */
public interface UserService {

    /**
     * 查询用户名是否存在
     * @param account
     * @return
     */
    User findByAccount(String account);

    /**
     * 根据账号,密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    User findByAccountAndPassword(String username, String password);

    //是否是管理员
    boolean isAdmin(String account);

    /**
     * 用户搜索
     * @return
     */
    Page<User> findByKey(Pageable pageable);

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * 添加用户
     * @param user
     */
    JsonResult save(User user, String subjectId, String departId, String groupsId, List<Role> roles, JsonResult jsonResult);

    /**
     * 获取用户详情的json
     * @param user
     * @return
     */
    JSONObject getUserInfoJson(User user);

    List<User> findAll();

    /**
     * 查询组织用户分页
     * @return
     */
    List<User> findByGroupIdForPage(String groupId);

    /**
     * 查询部门用户分页
     */
    List<User> findByDepartIdForPage(String departId);

    /**
     * 检测编号是否存在
     * @param code
     * @return
     */
    boolean existsCode(String code);

    /**
     * 根据编号查询实体
     */
    User findByCode(String code);

    /**
     * 查询机构用户分页
     */
    List<User> findBySubjectIdForPage(String id);

    /**
     * 查找用户群体
     * @param userids
     * @return
     */
    List<User> findbyIds(String[] userids);
}
