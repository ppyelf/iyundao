package com.ayundao.service;

import com.ayundao.entity.ExamInfoDepart;

import java.util.List;

/**
 * @ClassName: ExamInfoDepartService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 考试与部门组织用户关系
 * @Version: V1.0
 */
public interface ExamInfoDepartService {

    /**
     * 根据考试id找下部门组织用户关系
     * @param id
     * @return
     */
    List<ExamInfoDepart> findByExamId(String id);
}
