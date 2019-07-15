package com.ayundao.service;

import com.ayundao.entity.ExamInfoUserScore;

/**
 * @ClassName: ExamInfoUserScoreService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface ExamInfoUserScoreService {
    /**
     * 根据考试id，用户id查找分数
     * @param examid
     * @param userid
     * @return
     */
    ExamInfoUserScore findByExamUserId(String examid, String userid);
}
