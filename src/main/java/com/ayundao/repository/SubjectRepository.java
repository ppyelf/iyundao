package com.ayundao.repository;

import com.ayundao.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SubjectRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 16:31
 * @Description: 仓库 - 机构
 * @Version: V1.0
 */
@Repository
public interface SubjectRepository extends CrudRepository<Subject, String> {

    /**
     * 根据ID获取实体
     * @param subjectId
     * @return
     */
    @Query("select s from Subject s where s.id = ?1")
    Subject findBySubjectId(String subjectId);
}
