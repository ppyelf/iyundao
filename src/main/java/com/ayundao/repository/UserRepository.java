package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.User;
import com.ayundao.entity.UserGroupRelation;
import com.ayundao.entity.UserRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: UserRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 14:09
 * @Description: 仓库 - 用户
 * @Version: V1.0
 */
@Repository
public interface UserRepository extends BaseRepository<User, String> {

    //查询用户名是否存在
    User findByAccount(String account);

    //根据账号,密码查询用户信息
    User findByAccountAndPassword(String account, String password);

    //用户搜索
    @Query("select u from User u where u.account like ?1 or u.name like ?1 or u.createdDate like ?1")
    Page<User> findByKey(String key, Pageable pageable);

    //根据用户ID查询实体信息
    @Query("select u from User u where u.id = ?1")
    User findByUserId(String id);

    //查询所有用户分页
    @Query("select u from User u")
    Page<User> findAllForPage(Pageable pageable);

    //获取未分配的部门/组织的人员
    @Query("select u from User u where u.id not in (?1)")
    List<User> findNotdistributionUser(Set<String> idSet);
}
