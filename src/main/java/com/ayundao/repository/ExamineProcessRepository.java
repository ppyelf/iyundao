package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ExamineProcess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamineProcessRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 9:04
 * @Description: 仓库 - 审批流程
 * @Version: V1.0
 */
@Repository
public interface ExamineProcessRepository extends BaseRepository<ExamineProcess, String> {

    //根据用户ID查询实体集合
    @Query("select ep from ExamineProcess ep where ep.user.id = ?1")
    List<ExamineProcess> findByUserId(String userId);
}