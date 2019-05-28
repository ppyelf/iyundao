package com.ayundao.repository;

import com.ayundao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @ClassName: UserRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:09
 * @Description: 仓库 - 用户
 * @Version: V1.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

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

    /**
     * 用户搜索
     * @param key 查询条件
     * @return
     */
    @Query("select u from User u where u.account like ?1 or u.name like ?1 or u.createdDate like ?1")
    Page<User> findByKey(String key, Pageable pageable);

    @Query("select u from User u where u.id = ?1")
    User findByUserId(String id);

    @Query("select u from User u")
    Page<User> findAllForPage(Pageable pageable);
}
