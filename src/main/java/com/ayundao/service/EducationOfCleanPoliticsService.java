package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.EducationOfCleanPolitics;
import com.ayundao.entity.EducationOfCleanPoliticsAccessory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 廉政教育
 * @Date: 2019/6/12 14:30
 */
public interface EducationOfCleanPoliticsService {


    /**
     * 保存或修改实体
     * @param Recipe
     * @return
     */
    EducationOfCleanPolitics save(EducationOfCleanPolitics Recipe);

    JsonResult save(EducationOfCleanPoliticsAccessory params);

    /**
     * 查询所有
     * @return
     */
    Page<EducationOfCleanPolitics> selectAll(Pageable pageable);

    /**
     * 根据廉政教育id查询附件
     * @param id
     * @return
     */
    JsonResult findByEducationId(String id);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<EducationOfCleanPolitics> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<EducationOfCleanPolitics> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<EducationOfCleanPolitics> selectBySubject(String params,Pageable pageable);
}
