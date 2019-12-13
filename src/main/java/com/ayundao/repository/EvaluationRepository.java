package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Evaluation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EvaluationRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 14:27
 * @Description: 仓库 - 考评
 * @Version: V1.0
 */
@Repository
public interface EvaluationRepository extends BaseRepository<Evaluation, String> {

    /**
     * 获取指标列表
     *
     * @return
     */
    @Query(value = "select tei.ID   id, " +
            "       case " +
            "           when tei.TYPE = 0 then '加分指标' " +
            "           when tei.TYPE = 1 then '减分指标' " +
            "           else '一票否' " +
            "           end  type, " +
            "       tei.NAME name, " +
            "       tei.MIN  min, " +
            "       tei.MAX  max, " +
            "       tei.TYPE val " +
            "from t_evaluation_index tei " +
            "order by tei.TYPE asc, tei.MAX desc", nativeQuery = true)
    List<Map<String, Object>> getIndexList();

    /**
     * 获取待审核考核列表
     * @param startTime 1
     * @param endTime 2
     * @param code 3
     * @param subjectId 4
     * @param departId 5
     * @param indexId 6
     * @param status 7
     * @param num 8
     * @param size 9
     * @return
     */
    @Query(value = "select te.ID                                                                           id, " +
            "       user.code                                                                       userCode, " +
            "       user.name                                                                       userName, " +
            "       tei.ID                                                                          indexId, " +
            "       tei.NAME                                                                        indexName, " +
            "       case when tei.TYPE = 0 then '加分指标' when tei.TYPE = 1 then '减分指标' else '一票否' end type, " +
            "       te.SCORE                                                                        score, " +
            "       operator.name                                                                   operatorName, " +
            "       operator.sname                                                                  subjectName, " +
            "       te.CREATEDATE                                                                   operatorTime, " +
            "       case when te.STATUS = 0 then '等待中' when te.STATUS = 1 then '同意' else '拒绝' end   status, " +
            "       te.SURETIME                                                                     sureTime, " +
            "       te.REMARK                                                                       remark " +
            "from t_evaluation te " +
            "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
            "         left join (select tu.ID id, tu.CODE code, tu.NAME name, td.NAME sname " +
            "                    from t_user tu " +
            "                             left join t_user_relations tur on tur.USERID = tu.ID " +
            "                             left join t_depart td on td.ID = tur.DEPARTID " +
            "                    where tu.CODE like ?3 " +
            "                      and td.ID like ?4) user " +
            "                   on user.id = te.USERID " +
            "         left join (select tu.ID id, tu.CODE code, tu.NAME name, ifnull(td.NAME, ts.NAME) sname " +
            "                    from t_user tu " +
            "                             left join t_user_relations tur on tur.USERID = tu.ID " +
            "                             left join t_subject ts on tur.SUBJECTID = ts.ID " +
            "                             left join t_depart td on tur.DEPARTID = td.ID " +
            "                    where td.ID like ?5) operator on operator.code = te.USERCODE " +
            "where (te.YEAR between ?1 and ?2) " +
            "  and te.STATUS in (?7) " +
            "  and te.EVALUATIONINDEXID like ?6 " +
            "  and user.code is not null " +
            "  and user.name is not null " +
            "  and operator.name is not null " +
            "order by te.CREATEDATE asc " +
            "limit ?8, ?9", nativeQuery = true)
    List<Map<String, Object>> getList(String startTime, String endTime, String code, String subjectId, String departId, String indexId, int[] status, int num, int size);

    /**
     * 统计列表
     * @param startTime 1
     * @param endTime 2
     * @param code 3
     * @param subjectId 4
     * @param departId 5
     * @param indexId 6
     * @param status 7
     * @return
     */
    @Query(value = "select ifnull(count(te.ID), 0) count " +
            "from t_evaluation te " +
            "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
            "         left join (select tu.ID id, tu.CODE code, tu.NAME name, td.NAME sname " +
            "                    from t_user tu " +
            "                             left join t_user_relations tur on tur.USERID = tu.ID " +
            "                             left join t_depart td on td.ID = tur.DEPARTID " +
            "                    where tu.CODE like ?3 " +
            "                      and td.ID like ?4) user " +
            "                   on user.id = te.USERID " +
            "         left join (select tu.ID id, tu.CODE code, tu.NAME name, ifnull(td.NAME, ts.NAME) sname " +
            "                    from t_user tu " +
            "                             left join t_user_relations tur on tur.USERID = tu.ID " +
            "                             left join t_subject ts on tur.SUBJECTID = ts.ID " +
            "                             left join t_depart td on tur.DEPARTID = td.ID " +
            "                    where td.ID like ?5) operator on operator.code = te.USERCODE " +
            "where (te.YEAR between ?1 and ?2) " +
            "  and te.STATUS in (?7) " +
            "  and te.EVALUATIONINDEXID like ?6 " +
            "  and user.code is not null " +
            "  and user.name is not null " +
            "  and operator.name is not null " +
            "order by te.CREATEDATE asc", nativeQuery = true)
    long countList(String startTime, String endTime, String code, String subjectId, String departId, String indexId, int[] status);

    /**
     * 查询统计分页
     *
     * @param code
     * @param year
     * @param num
     * @param size
     * @return
     */
    @Query(value = "select te.ID                                  id, " +
            "       te.YEAR                                year, " +
            "       tu.CODE                                code, " +
            "       tu.NAME                                name, " +
            "       if(tu.SEX = 0, '男', '女')               sex, " +
            "       tui.BIRTHDAY                           birthday, " +
            "       case " +
            "           when tui.POSTTYPE = 0 then '医生' " +
            "           when tui.POSTTYPE = 1 then '护士' " +
            "           when tui.POSTTYPE = 2 then '医技' " +
            "           else '其他' end                      postType, " +
            "       tui.post                               post, " +
            "       tui.title                              title, " +
            "       sum(te.SCORE)                          score, " +
            "       td.ID                                  departId, " +
            "       td.NAME                                departName, " +
            "       tg.ID                                  groupId, " +
            "       tg.NAME                                groupName " +
            "from t_evaluation te " +
            "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
            "         left join t_user tu on te.USERID = tu.ID " +
            "         left join t_user_info tui on tui.USERID = te.USERID " +
            "         left join t_user_relations tur on te.USERID = tur.USERID " +
            "         left join t_depart td on td.ID = tur.DEPARTID " +
            "         left join t_groups tg on tg.ID = tur.GROUPSID " +
            "where te.YEAR like ?2 " +
            "  and tu.CODE like ?1 " +
            "group by te.USERID " +
            "order by ?5 " +
            "limit ?3, ?4", nativeQuery = true)
    LinkedList<Map<String, Object>> getSumList(String code, String year, int num, int size, @Param("order") Object order);

    /**
     * 统计统计分页的总和
     *
     * @param code
     * @param year
     * @return
     */
    @Query(value = "select count(te.USERID) count " +
            "from t_evaluation te " +
            "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
            "         left join t_user tu on te.USERID = tu.ID " +
            "         left join t_user_info tui on tui.USERID = te.USERID " +
            "         left join t_user_relations tur on te.USERID = tur.USERID " +
            "         left join t_groups tg on tg.ID = tur.GROUPSID " +
            "where te.YEAR like ?2 " +
            "  and tu.CODE like ?1 " +
            "group by te.USERID " +
            "order by score desc", nativeQuery = true)
    List<Long> countSumList(String code, String year);

    /**
     * 获取年份列表
     *
     * @return
     */
    @Query(value = "select YEAR year from t_evaluation group by YEAR", nativeQuery = true)
    List<String> getYearList();

    /**
     * 获取年度里个人的考评记录
     *
     * @param id
     * @param year
     * @return
     */
    @Query(value = "select te.YEAR            year, " +
            "       case " +
            "           when tei.TYPE = 0 then '加分指标' " +
            "           when tei.TYPE = 1 then '减分指标' " +
            "           else '一票否' end type, " +
            "       tei.NAME           name, " +
            "       te.SCORE           score, " +
            "       tei.TYPE           value " +
            "from t_evaluation te " +
            "         left join t_user tu on te.USERID = tu.ID " +
            "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
            "where te.USERID = ?1 " +
            "  and te.YEAR like ?2 " +
            " and te.STATUS = 1 " +
            "order by year, type", nativeQuery = true)
    List<Map<String, Object>> findEvaluationByUserIdAndYear(String id, String year);

    /**
     * 获取个人年度的医德医风
     *
     * @param code
     * @return
     */
    @Query(value = "select te.YEAR                year, " +
            "       case " +
            "           when tei.TYPE = 0 then '加分指标' " +
            "           when tei.TYPE = 1 then '减分指标' " +
            "           else '一票否决' end    type, " +
            "       tei.NAME               name, " +
            "       sum(case " +
            "               when tei.TYPE = 0 then te.SCORE " +
            "               when tei.TYPE = 1 then te.SCORE " +
            "               else -999 end) score , " +
            "       te.REMARK remark " +
            "from t_evaluation te " +
            "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
            "         left join t_user tu on te.USERID = tu.ID " +
            "where tu.CODE = ?1 " +
            "  and te.STATUS = 1 " +
            "group by name, year " +
            "order by year asc", nativeQuery = true)
    List<Map<String, Object>> getEvaluationByUserIdAndYear(String code);
}
