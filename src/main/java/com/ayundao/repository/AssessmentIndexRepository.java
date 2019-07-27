package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.AssessmentIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentIndexRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/11
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AssessmentIndexRepository extends BaseRepository<AssessmentIndex, String> {

    @Query("select a from AssessmentIndex a where a.assessment.id = ?1")
    List<AssessmentIndex> findIndexByAssessmentId(String id);

    @Query("select a from AssessmentIndex  a where a.sortedid =?1")
    AssessmentIndex findbyparcode(String parcode);
}
