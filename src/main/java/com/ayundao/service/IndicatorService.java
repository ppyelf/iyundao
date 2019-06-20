package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Indicator;
import com.ayundao.entity.Work;

import java.util.List;

/**
 * @ClassName: IndicatorService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 11:02
 * @Description: 服务 - 工作指标
 * @Version: V1.0
 */
public interface IndicatorService {

    /**
     * 根据父级ID获取获取父级实体
     * @param fatherId
     * @return
     */
    List<Indicator> findByFatherId(String fatherId);

    /**
     * 新建实体
     * @param name
     * @param situation
     * @param work
     * @param father
     * @param jsonResult
     * @return
     */
    JsonResult create(String name, String situation, Work work, Indicator father, JsonResult jsonResult);

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    Indicator find(String id);

    /**
     * 获取所有实体结合 -- List
     * @return
     */
    List<Indicator> findAll();

    /**
     * 根据father为空获取所有实体集合
     * @return
     */
    List<Indicator> findAllAndFatherIsNull();

    /**
     * 根据ID删除实体及外键关联
     * @param id
     */
    JsonResult delete(String id, JsonResult jsonResult);
}
