package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Pioneer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PioneerRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 16:57
 * @Description: 先锋人物
 * @Version: V1.0
 */
@Repository
public interface PioneerRepository extends BaseRepository<Pioneer, String> {

    //根据起止时间查询并按照分数排序
    @Query("select p from Pioneer p where p.createdDate between (?1) and (?2) order by p.score desc")
    List<Pioneer> findListByCreatedTimeOrderByScore(String startTime, String endTime);
}
