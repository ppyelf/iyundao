package com.ayundao.repository;

import com.ayundao.entity.AssessmentIndex;
import com.ayundao.entity.AssessmentRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/7 21:43
 * @Description: 仓库 - 指标
 * @Version: V1.0
 */
@Repository
public interface AssessmentIndexRepository extends CrudRepository<AssessmentIndex, String> {


    //根据ID获取实体信息
    @Query("select ai from AssessmentIndex ai where ai.id = ?1")
    AssessmentIndex find(String id);

    //查询最大值
    @Query(value = "SELECT IFNULL(max(ai.`CODE`),0) from t_assessment_index ai", nativeQuery = true)
    int findLastCode();

    //根据IDS集合查询实体集合信息
    @Query("select ai from AssessmentIndex ai where ai.id in (?1)")
    List<AssessmentIndex> findByIds(String[] ids);

    //根据考核ID获取实体集合
    @Query("select ai from AssessmentIndex ai where ai.assessment.id = ?1")
    List<AssessmentIndex> findByAssessmentId(String id);
}
