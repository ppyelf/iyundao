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
    @Query(value = "SELECT ep.* from t_examine_process ep left join t_examine e on e.ID = ep.EXAMINEID where e.EXAMINETYPE = ?2 and ( ep.USERID = ?1 or ep.`STATUS` = 1) group by ep.USERID, ep.EXAMINEID", nativeQuery = true)
    List<ExamineProcess> findProcessByUserIdAndType(String userId, int type);

    //根据用户ID和审核ID查询实体信息
    @Query("select ep from ExamineProcess ep where ep.examine.id = ?1 and ep.user.id = ?2")
    ExamineProcess findProcessByExamineIdAndUserId(String id, String userId);

    //根据审核ID和流程等级查询实体
    @Query("select ep from ExamineProcess ep where ep.examine.id = ?1 and ep.level = ?2 and ep.status = ?3")
    ExamineProcess findByExamineIdAndLevel(String id, int level, ExamineProcess.PROCESS_STATUS status);

    //根据请假/请示ID获取审核流程集合
    @Query("select ep from ExamineProcess ep where ep.examine.id = ?1 and ep.id <> ?2")
    List<ExamineProcess> findByExamineIdExcludeCurrent(String id, String epId);

    //获取审核中最大等级
    @Query(value = "select max(ep.level) from t_examine_process ep where ep.EXAMINEID = ?1 and ep.PERSONTYPE = 1", nativeQuery = true)
    int maxLevel(String id);
}