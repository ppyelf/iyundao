package com.ayundao.service;

import com.ayundao.entity.LearningPower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 学习强国
 * @Date: 2019/6/12 16:26
 */
public interface LearningPowerService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    LearningPower save(LearningPower params);

    /**
     * 查询所有
     * @return
     */
    Page<LearningPower> selectAll(Pageable pageable);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<LearningPower> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<LearningPower> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<LearningPower> selectBySubject(String params,Pageable pageable);
}
