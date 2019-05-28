package com.ayundao.repository;

import com.ayundao.entity.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserGroupRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 0:18
 * @Description: 仓库 - 用户组
 * @Version: V1.0
 */
@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, String> {

}
