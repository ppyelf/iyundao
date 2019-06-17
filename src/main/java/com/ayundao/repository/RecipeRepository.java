package com.ayundao.repository;


import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 处方
 * @Date: 2019/6/12 8:50
 */
@Repository
public interface RecipeRepository extends BaseRepository<Recipe, String> {

    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from Recipe e where e.user.id in (?1) order by e.score desc ")
    Page<Recipe> selectByDepart(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from Recipe d")
    Page<Recipe> selectAll(Pageable pageable);
}
