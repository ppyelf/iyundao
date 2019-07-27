package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ActivityImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ActivityImageRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/6 11:32
 * @Description: 仓库 - 活动图片
 * @Version: V1.0
 */
@Repository
public interface ActivityImageRepository extends BaseRepository<ActivityImage, String> {

    //根据IDS获取活动图片集合
    @Query("select ai from ActivityImage ai where ai.id in (?1)")
    List<ActivityImage> findByIds(String[] activityImageIds);

    //根据活动ID获取集合信息
    @Query("select ai from ActivityImage ai where ai.activity.id = ?1")
    List<ActivityImage> findByActivityId(String id);
}
