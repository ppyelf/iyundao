package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ReleaseGroups;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ReleaseGroupRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 16:38
 * @Description: 仓库 - 发布小组
 * @Version: V1.0
 */
@Repository
public interface ReleaseGroupRepository extends BaseRepository<ReleaseGroups, String> {

}
