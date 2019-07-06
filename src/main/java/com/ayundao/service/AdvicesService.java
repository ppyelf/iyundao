package com.ayundao.service;

import com.ayundao.entity.Advices;
import com.ayundao.entity.AdvicesInfoDepar;

import java.util.List;

/**
 * @ClassName: AdvicesService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface AdvicesService {

    List<Advices> findAll();

    Advices save(Advices advices, String subjectId, String departId, String groupId);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Advices findById(String id);

    /**
     *删除实体
     */
    void delete(Advices advices);

    /**
     * 根据部门组织id查询消息实体
     * @param id
     * @return
     */
    List<Advices> findAdvicesByDeptionId(String id);
}
