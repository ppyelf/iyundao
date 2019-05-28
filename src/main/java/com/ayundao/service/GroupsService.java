package com.ayundao.service;

import com.ayundao.entity.Groups;

import java.util.List;

/**
 * @ClassName: GroupsService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 15:14
 * @Description: 服务 - 小组
 * @Version: V1.0
 */
public interface GroupsService {

    /**
     * 根据机构ID获取小组集合
     * @param subjectId
     * @return
     */
    List<Groups> findBySubjectId(String subjectId);
}
