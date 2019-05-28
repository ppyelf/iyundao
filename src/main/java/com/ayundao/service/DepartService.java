package com.ayundao.service;

import com.ayundao.entity.Depart;

import java.util.List;

/**
 * @ClassName: DepartService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:24
 * @Description: 服务 -- 部门
 * @Version: V1.0
 */
public interface DepartService {

    /**
     * 根据机构ID获取部门集合
     * @param subjectId
     * @return
     */
    List<Depart> findBySubjectId(String subjectId);
}
