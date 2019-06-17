package com.ayundao.service;

import com.ayundao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
     * @param key 查询条件
     * @return
     */
    Page<User> findByKey(String key, Pageable pageable);

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
    void save(User user, String subjectId, String departId, String groupsId);

    /**
     * 查询用户分页
     * @return
     */
    Page<User> findAllForPage(Pageable pageable);
}
