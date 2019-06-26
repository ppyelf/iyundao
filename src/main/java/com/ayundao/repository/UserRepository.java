package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.User;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据用户ID查询实体信息
     * @param id
     * @return
     */
    @Query("select u from User u where u.id = ?1")
    User findByUserId(String id);

    //组织用户分页
    @Query(value = "SELECT u.* from t_user u left join t_user_relations ur on ur.USERID = u.ID where ur.GROUPSID = (?1)", nativeQuery = true)
    org.springframework.data.domain.Page<User> findByGroupIdForPage(String groupId, org.springframework.data.domain.Pageable pageable);

    //部门用户分页
    @Query(value = "select u from User u left outer join fetch u.userRelations ur left outer join fetch ur.depart d where d.id = :departId")
    Page<User> findByDepartIdForPage(@Param("departId") String departId);
}
