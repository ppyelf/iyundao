package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.StyleOfWork;
import com.ayundao.entity.StyleOfWorkStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 行风效能
 * @Date: 2019/6/12 9:00
 */
@Repository
public interface StyleOfWorkRepository extends BaseRepository<StyleOfWork, String> {

    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from MaterialsWarning e where e.user.id in (?1) order by e.score desc")
    Page<StyleOfWork> selectByDepart(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from Recipe d")
    Page<StyleOfWork> selectAll(Pageable pageable);

    /**
     * 分组织统计科室行风效能平均分 倒序
     * @return
     */
    @Query(nativeQuery = true,value = "select d.Name,SUM(sf.SCORE)/COUNT(ur.USERID) average from t_depart d " +
            "INNER JOIN t_user_relations ur " +
            "on ur.DEPARTID = d.ID " +
            "INNER JOIN t_styleofwork sf " +
            "ON sf.USER_ID = ur.USERID " +
            "GROUP BY ur.DEPARTID " +
            "ORDER BY SUM(sf.SCORE)/COUNT(ur.USERID) DESC")
    List<StyleOfWorkStatistics> statistics();
}
