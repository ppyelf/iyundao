package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Recipe;
import com.ayundao.entity.RecipeRational;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 处方点评
 * @Date: 2019/6/12 13:54
 */
public interface RecipeService {

    /**
     * 保存或修改实体
     * @param Recipe
     * @return
     */
    Recipe save(Recipe Recipe);

    JsonResult save(RecipeRational params);

    /**
     * 查询所有
     * @return
     */
    Page<Recipe> selectAll(Pageable pageable);

    /**
     * 根据处方id查询点评
     * @param recipeId
     * @return
     */
    JsonResult findByRecipeId(String recipeId);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<Recipe> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<Recipe> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<Recipe> selectBySubject(String params,Pageable pageable);

}
