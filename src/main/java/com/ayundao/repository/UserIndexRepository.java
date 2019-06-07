package com.ayundao.repository;

import com.ayundao.entity.AssessmentRange;
import com.ayundao.entity.UserIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/8 3:22
 * @Description: 仓库 - 用户指标
 * @Version: V1.0
 */
@Repository
public interface UserIndexRepository extends CrudRepository<UserIndex, String> {

    //根据考核ID获取集合信息
    @Query("select ui from UserIndex ui where ui.assessmentId = ?1")
    List<UserIndex> findByAssessmentId(String id);
}
