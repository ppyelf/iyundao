package com.ayundao.service;

import com.ayundao.entity.ExamInfoUser;

import java.util.List;

/**
 * @ClassName: ExamInfoUserService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface ExamInfoUserService {
    /**
     * 根据考试和用户id查看详情
     */
    List<ExamInfoUser> findByExamUserId(String examid, String userid);
}
