package com.ayundao.repository;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseRepository;
import com.ayundao.entity.DemocraticAppraisal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: DemocraticAppraisalRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/16
 * @Description: 实现 -
 * @Version: V1.0
 */
@Repository
public interface DemocraticAppraisalRepository extends BaseRepository<DemocraticAppraisal,String>{


    @Query(value = "SELECT name, usercode, SUM(SCORE) as scoresum,SUM(score)/COUNT(SCORE) as scoreavg\n" +
            "FROM t_democratic_appraisal  where `DATA` LIKE ?1%  GROUP BY name",nativeQuery = true)
    List<JSONObject> findDemForYear(String year);

    @Query(value = "SELECT name, usercode, SUM(SCORE) as scoresum,SUM(score)/COUNT(SCORE) as scoreavg\n" +
            "FROM t_democratic_appraisal  where usercode = ?1 AND `DATA` LIKE ?2%  GROUP BY name",nativeQuery = true)
    JSONObject findByUsercode(String usercode, String year);
}
