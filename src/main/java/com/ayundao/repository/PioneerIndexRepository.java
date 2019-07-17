package com.ayundao.repository;

import com.alibaba.fastjson.JSONObject;
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


    @Query(value = "SELECT name, usercode, SUM(SCORE) as scoresum,SUM(score)/COUNT(SCORE) as scoreavg\n" +
            "FROM t_pioneer_index  where `DATA` LIKE ?1%  GROUP BY name",nativeQuery = true)
    List<JSONObject> findPioForYear(String year);

    @Query(value = "SELECT name, usercode, SUM(SCORE) as scoresum,SUM(score)/COUNT(SCORE) as scoreavg\n" +
            "FROM t_pioneer_index  where usercode = ?1 AND `DATA` LIKE ?2%  GROUP BY name",nativeQuery = true)
    JSONObject findByUsercode(String usercode, String year);


//    @Query(value = "SELECT t.name as name,t.usercode as usercode,SUM(t.score)/COUNT(*) as tscore,\n" +
//            "sum(d.score)/count(*) as dscore\n" +
//            "from t_pioneer_index t \n" +
//            "INNER JOIN t_democratic_appraisal d \n" +
//            "on t.name = d.name\n" +
//            "GROUP BY t.name" ,nativeQuery = true)
//    List<JSONObject> findAllForPeople();
}
