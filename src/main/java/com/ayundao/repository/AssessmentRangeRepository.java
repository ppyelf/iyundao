package com.ayundao.repository;

import com.ayundao.entity.AssessmentRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentRangeRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/10
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AssessmentRangeRepository extends CrudRepository<AssessmentRange,String>{

    @Query("select a from AssessmentRange a where a.assessment.id =?1")
    List<AssessmentRange> findRangeById(String id);
}
