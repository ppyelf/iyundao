package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.AssessmentFile;
import com.ayundao.entity.AssessmentIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentFileRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/11
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AssessmentFileRepository extends BaseRepository<AssessmentFile,String> {


    @Query("select a from AssessmentFile a where a.assessment.id =?1")
    List<AssessmentFile> findFileById(String id);
}
