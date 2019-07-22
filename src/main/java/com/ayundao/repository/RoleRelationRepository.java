package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.RoleRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @ClassName: RoleRelationRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/10 11:28
 * @Description: 仓库 - 用户权限
 * @Version: V1.0
 */
@Repository
public interface RoleRelationRepository extends BaseRepository<RoleRelation, String> {

    //根据用户ID查询实体集合
    @Query("select rr from RoleRelation rr where rr.user.id = ?1")
    Set<RoleRelation> findRolesByUserId(String id);
}
