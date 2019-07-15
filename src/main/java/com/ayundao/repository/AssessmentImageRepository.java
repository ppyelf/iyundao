package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.AssessmentImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentImageRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/11
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AssessmentImageRepository extends BaseRepository<AssessmentImage,String> {
    @Query("select a from AssessmentImage a where a.assessment.id=?1")
    List<AssessmentImage> findImageById(String id);
}
