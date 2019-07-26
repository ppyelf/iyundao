package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Permission;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: PermissionRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/26 8:58
 * @Description: 仓库 - 权限
 * @Version: V1.0
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission, String> {

}
