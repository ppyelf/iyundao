package com.ayundao.service;

import com.ayundao.entity.ExamInfoTextpaper;

import java.util.List;

/**
 * @ClassName: ExamInfoTextpaperService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 考试与试卷
 * @Version: V1.0
 */
public interface ExamInfoTextpaperService {

    /**
     * 根据考试id寻找试卷
     * @param id
     * @return
     */
    List<ExamInfoTextpaper> findByExamId(String id);
}
