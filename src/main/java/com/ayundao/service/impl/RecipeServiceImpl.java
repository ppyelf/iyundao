package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Recipe;
import com.ayundao.entity.RecipeRational;
import com.ayundao.repository.RecipeRationalRepository;
import com.ayundao.repository.RecipeRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ink-feather
 * @Description:
 * @Date: 2019/6/12 13:59
 */
@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository RecipeRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Autowired
    private RecipeRationalRepository recipeRationalRepository;

    @Override
    public Recipe save(Recipe Recipe) {
        return RecipeRepository.save(Recipe);
    }

    @Override
    public JsonResult save(RecipeRational params) {

        if (params.equals("") || params == null) {
            return JsonResult.failure(801,"传入数据为空");
        } else {
            try {
                RecipeRational save = recipeRationalRepository.save(params);
                return JsonResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return JsonResult.failure(802,e.getMessage());
            }
        }
    }

    @Override
    public Page<Recipe> selectAll(Pageable pageable) {
        return RecipeRepository.selectAll(pageable);
    }

    @Override
    public Page<Recipe> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return RecipeRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<Recipe> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return RecipeRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<Recipe> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return RecipeRepository.selectByDepart(list,pageable);
    }


    @Override
    public JsonResult findByRecipeId(String recipeId) {
        try {
            List<RecipeRational> list = recipeRationalRepository.findByRecipeId(recipeId);
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }
}
