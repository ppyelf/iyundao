package com.ayundao.service;

import com.ayundao.entity.Subject;

import java.util.List;

/**
 * @ClassName: SubjectService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:28
 * @Description: 服务 -- 机构
 * @Version: V1.0
 */
public interface SubjectService {

    /**
     * 获取所有机构实体
     * @return
     */
    List<Subject> findAll();
}
