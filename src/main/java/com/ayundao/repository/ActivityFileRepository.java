package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ActivityFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ActivityFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/6 10:57
 * @Description: 仓库 - 活动文件w
 * @Version: V1.0
 */
@Repository
public interface ActivityFileRepository extends BaseRepository<ActivityFile, String> {

    //根据活动ID获取集合信息
    @Query("select af from ActivityFile af where af.activity.id = ?1")
    List<ActivityFile> findByActivityId(String id);
}
