package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.RecipeRational;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 处方点评
 * @Date: 2019/6/15 10:50
 */
@Repository
public interface RecipeRationalRepository extends BaseRepository<RecipeRational,String> {

    /**
     * 根据处方查询处方点评
     * @param recipeId
     * @return
     */
    @Query("select r from RecipeRational r where r.recipe.id = ?1")
    List<RecipeRational> findByRecipeId(String recipeId);
}
