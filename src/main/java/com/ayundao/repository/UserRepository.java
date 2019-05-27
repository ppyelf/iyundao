package com.ayundao.repository;

import com.ayundao.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @ClassName: UserRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:09
 * @Description: 仓库 - 用户
 * @Version: V1.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    /**
     * 查询用户名是否存在
     * @param account
     * @return
     */
    User findByAccount(String account);

    /**
     * 根据账号,密码查询用户信息
     * @param account
     * @param password
     * @return
     */
    User findByAccountAndPassword(String account, String password);
}
