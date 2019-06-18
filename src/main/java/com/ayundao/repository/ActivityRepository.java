package com.ayundao.repository;

import com.ayundao.entity.Activity;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ActivityRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 13:43
 * @Description: 仓库 - 活动
 * @Version: V1.0
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, String> {

    //根据ID查找实体
    @Query("select a from Activity a where a.id = ?1")
    Activity find(String id);

    //获取活动列表集合
    @Query("select a from Activity a")
    List<Activity> findAllForList();

    //查询活动分页
    @Query("select a from Activity a")
    Page<Activity> findAllForPage(Pageable pageable);
}
