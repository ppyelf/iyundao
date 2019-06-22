package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Role;
import com.ayundao.entity.User;
import com.ayundao.entity.UserGroupRelation;
import com.ayundao.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName: UserRoleRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 14:55
 * @Description: 仓库 - 角色关系
 * @Version: V1.0
 */
@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, String> {

    /**
     * 查找用户拥有的角色
     * @param user
     * @return
     */
    List<UserRole> findByUser(User user);

    @Query("select ur.role from UserRole ur where ur.user.id = :id")
    List<Role> getRoleByUserId(@Param("id") String id);

    //根据用户ID获取所有角色
    @Query("select ur.role from UserRole ur where ur.user = ?1")
    List<Role> findByUserId(String userId);
}
