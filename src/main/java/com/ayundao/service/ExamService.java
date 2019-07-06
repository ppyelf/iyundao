package com.ayundao.service;

import com.ayundao.entity.Exam;

import java.util.List;

/**
 * @ClassName: ExamService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface ExamService {

    /**
     * 查找所有考试成绩
     * @return
     */
    List<Exam> findAll();

    /**
     * 添加考试
     * @param exam
     * @param subjectIds
     * @param departIds
     * @param groupIds
     * @param testpapers
     * @return
     */
    Exam save(Exam exam, String[] subjectIds, String[] departIds, String[] groupIds, String[] testpapers);
}
