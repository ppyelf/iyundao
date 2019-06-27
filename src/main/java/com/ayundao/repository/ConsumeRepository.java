package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Consume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ConsumeRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 15:30
 * @Description: 仓库 - 耗材预警
 * @Version: V1.0
 */
@Repository
public interface ConsumeRepository extends BaseRepository<Consume, String> {

    //根据起止时间查询并按照分数排序
    @Query("select c from Consume c where c.createdDate between (?1) and (?2) order by c.warn desc")
    List<Consume> findListByCreatedTimeOrderByWarn(String startTime, String endTime);
}
