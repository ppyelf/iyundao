package com.ayundao.repository;

import com.ayundao.entity.AssessmentRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentRangeRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/8 3:09
 * @Description: 仓库 - 考核范围
 * @Version: V1.0
 */
@Repository
public interface AssessmentRangeRepository extends CrudRepository<AssessmentRange, String> {

    //根据考核ID获取实体信息
    @Query("select ar from AssessmentRange ar where ar.assessmentId = ?1")
    List<AssessmentRange> findByAssessmentId(String id);
}
