package com.ayundao.repository;

import com.ayundao.entity.AssessmentFile;
import com.ayundao.entity.AssessmentRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AssessmentFileRepository
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/7 21:00
 * @Description: 仓库 - 考核文件
 * @Version: V1.0
 */
@Repository
public interface AssessmentFileRepository extends CrudRepository<AssessmentFile, String> {

//    //根据IDS获取文件集合
//    @Query("select af from AssessmentFile af where af.id in (?1)")
//    List<AssessmentFile> findByIds(String[] ids);
//
//    //根据考核ID获取集合信息
//    @Query("select af from AssessmentFile af where af.assessment.id = ?1")
//    List<AssessmentFile> findByAssessmentId(String id);
}
