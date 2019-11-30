package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Drugs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DrugsRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/23 11:52
 * @Description: 仓库 - 药品预警
 * @Version: V1.0
 */
@Repository
public interface DrugsRepository extends BaseRepository<Drugs, String> {

    /**
     * 获取分页
     * @param code
     * @param time
     * @param num
     * @param size
     * @return
     */
    @Query(value = "select * " +
            "from t_drugs td " +
            "where td.USERCODE like ?1 " +
            "  and td.YEAR like ?2 " +
            "order by td.YEAR " +
            "limit ?3, ?4", nativeQuery = true)
    List<Map<String, Object>> findByTimeAndCodeForPage(String code, String time, int num, int size);

    /**
     * 统计分页
     * @param code
     * @param time
     * @return
     */
    @Query(value = "select ifnull(count(*), 0) " +
            "from t_drugs td " +
            "where td.USERCODE like ?1 " +
            "  and td.YEAR like ?2 " +
            "order by td.YEAR ", nativeQuery = true)
    long countByTimeAndCodeForPage(String code, String time);
}
