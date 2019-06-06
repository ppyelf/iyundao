package com.ayundao.repository;

import com.ayundao.entity.ReleaseDepart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ReleaseDepartRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 16:33
 * @Description: 仓库 - 发布部门
 * @Version: V1.0
 */
@Repository
public interface ReleaseDepartRepository extends CrudRepository<ReleaseDepart, String> {

}
