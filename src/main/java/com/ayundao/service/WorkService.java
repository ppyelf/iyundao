package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Work;

import java.util.List;

/**
 * @ClassName: WorkService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 17:29
 * @Description: 服务 - 中心工作
 * @Version: V1.0
 */
public interface WorkService {

    /**
     * 获取所有实体的集合
     * @return
     */
    List<Work> findAll();

    /**
     * 新建实体
     * @param year
     * @param name
     * @param workType
     * @param startTime
     * @param endTime
     * @param remark
     * @param subjectId
     * @param departId
     * @param groupId
     * @return
     */
    JsonResult save(String year, String name, int workType, String startTime, String endTime, String remark, String subjectId, String departId, String groupId, JsonResult jsonResult);

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    Work find(String id);

    /**
     * 根据ID删除实体
     * @param id
     */
    void delete(String id);
}
