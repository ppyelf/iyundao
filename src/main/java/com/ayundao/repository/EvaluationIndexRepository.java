package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.EvaluationIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: EvaluationIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 15:25
 * @Description: 仓库 - 考评指标
 * @Version: V1.0
 */
@Repository
public interface EvaluationIndexRepository extends BaseRepository<EvaluationIndex, String> {

    /**
     * 获取所有指标的名称
     * @return
     */
    @Query(value = "select tei.* " +
            "from t_evaluation_index tei " +
            "order by tei.TYPE asc, tei.NAME desc", nativeQuery = true)
    List<EvaluationIndex> getAllNames();

    /**
     * 根据类型和名称获取指标
     * @param type
     * @param name
     * @return
     */
    @Query(value = "select * " +
            "from t_evaluation_index tei " +
            "where tei.TYPE = ?1 " +
            "  and tei.NAME = ?2 group by tei.NAME", nativeQuery = true)
    EvaluationIndex findByTypeAndName(int type, String name);
}
