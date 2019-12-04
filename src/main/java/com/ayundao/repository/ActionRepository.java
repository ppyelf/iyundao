package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Action;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ActionRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/28 11:19
 * @Description: 仓库 - 春风行动
 * @Version: V1.0
 */
@Repository
public interface ActionRepository extends BaseRepository<Action, String> {

    /**
     * 根据年份和机构ID获取实体集合
     * @param id
     * @param year
     * @return
     */
    @Query(value = "select ta.USERCODE   userCode, " +
            "       ta.USERNAME   userName, " +
            "       ta.GROUPCODE  groupCode, " +
            "       ta.GROUPNAME  groupName, " +
            "       sum(ta.MONEY) money, " +
            "       sum(ifnull(te.SCORE, 0))      score " +
            "from t_action ta " +
            "         left join t_evaluation te on te.ID = ta.EVALUATIONID " +
            "where ta.SUBJECTCODE = ?1 " +
            "  and ta.CREATEDATE like ?2 " +
            "group by ta.USERCODE", nativeQuery = true)
    List<Map<String, Object>> findBySubjectIdAndYear(String id, String year);
}
