package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.User;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * 组织用户分页
     * @param groupId
     * @return
     */
    @Query(value = "SELECT u.* from t_user u left join t_user_relations ur on ur.USERID = u.ID where ur.GROUPSID = (?1)", nativeQuery = true)
    List<User> findByGroupIdForPage(String groupId);

    /**
     * 部门用户分页
     * @param departId
     * @return
     */
    @Query(value = "select u from User u left outer join fetch u.userRelations ur left outer join fetch ur.depart d where d.id = :departId")
    List<User> findByDepartIdForPage(@Param("departId") String departId);

    /**
     * 根据编号查询实体
     * @param code
     * @return
     */
    @Query("select u from User u where u.code = ?1")
    User findByCode(String code);

    /**
     * 机构用户分页
     * @param id
     * @return
     */
    @Query(value = "select u.* from t_user u left join t_user_relations ur on ur.USERID = u.ID where ur.SUBJECTID = (?1)", nativeQuery = true)
    List<User> findBySubjectIdForPage(String id);

    /**
     * 根据编号获取用户集合
     * @param codes
     * @return
     */
    @Query(value = "select * from t_user where CODE in ?1 order by field(CODE, ?1);", nativeQuery = true)
    List<User> findByCodes(String[] codes);

    /**
     * 根据编号和姓名获取实体信息
     * @param value
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Query(value = "select u.* from t_user u where to_pinyin(u.NAME) like ?1 order by u.NAME limit ?2, ?3", nativeQuery = true)
    List<User> getNameForPage(String value, int pageNumber, int pageSize);

    /**
     * 统计 - 根据编号和姓名获取实体信息
     * @param value
     * @return
     */
    @Query(value = "select ifnull(count(*), 0) from t_user u where to_pinyin(u.NAME) like ?1", nativeQuery = true)
    long countNameForPage(String value);
}
