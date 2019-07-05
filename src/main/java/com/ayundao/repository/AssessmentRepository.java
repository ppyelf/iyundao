package com.ayundao.repository;

import com.ayundao.entity.Assessment;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: AssessmentRepository
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/7 20:15
 * @Description: 仓库 - 考核
 * @Version: V1.0
 */
@Repository
public interface AssessmentRepository extends CrudRepository<Assessment, String> {

//    //获取分页
//    @Query("select a from Assessment a")
//    Page<Assessment> findAllForPage(Pageable pageable);
//
//    //根据ID查找实体
//    @Query("select a from Assessment a where a.id = ?1")
//    Assessment find(String assessmentId);
}
