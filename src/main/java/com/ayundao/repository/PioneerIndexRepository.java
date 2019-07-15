package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PioneerIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PioneerIndexRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/13
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface PioneerIndexRepository extends BaseRepository<PioneerIndex,String>{

    @Query("select p from PioneerIndex p where p.data between (?1) and (?2)")
    List<PioneerIndex> findListByCreatedTimeOrderByWarn(String startTime, String endTime);
}
