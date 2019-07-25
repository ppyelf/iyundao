package com.ayundao.repository;

import com.ayundao.entity.ExamInfoDepart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamInfoDepartRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface ExamInfoDepartRepository extends CrudRepository<ExamInfoDepart, String>{

    @Query("select a from ExamInfoDepart a where a.exam.id =?1")
    List<ExamInfoDepart> findByExamId(String id);
}
