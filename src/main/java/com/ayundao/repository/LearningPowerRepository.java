package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.LearningPower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 学习强国
 * @Date: 2019/6/12 16:20
 */
@Repository
public interface LearningPowerRepository extends BaseRepository<LearningPower,String> {


    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from LearningPower e where e.user.id in (?1) order by e.score desc")
    Page<LearningPower> selectByDepart(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from LearningPower d")
    Page<LearningPower> selectAll(Pageable pageable);

}
