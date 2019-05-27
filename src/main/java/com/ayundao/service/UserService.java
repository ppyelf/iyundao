package com.ayundao.service;

import com.ayundao.entity.User;

import java.util.Arrays;

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
}
