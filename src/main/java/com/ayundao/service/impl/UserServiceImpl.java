package com.ayundao.service.impl;

import com.ayundao.entity.User;
import com.ayundao.repository.UserRepository;
import com.ayundao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: UserServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:07
 * @Description: 服务实现 - 用户
 * @Version: V1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


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
        return user.getUserType().equals(User.USER_TYPE.amdin);
    }
}
