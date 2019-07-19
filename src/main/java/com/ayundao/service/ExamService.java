package com.ayundao.service;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Exam;
import com.ayundao.entity.ExamInfoDepart;
import com.ayundao.entity.ExamInfoTextpaper;
import com.ayundao.entity.ExamInfoUserScore;

import java.util.List;
import java.util.Map;

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
    Exam save(Exam exam, String[] subjectIds, String[] departIds, String[] groupIds, String[] testpapers, String[] userids);

    /**
     * 查看考试详情
     * @return
     */
    Exam findById(String id);


    /**
     * 查看考试详情所有所有值
     * @param exam
     * @param examInfoDeparts
     * @param examInfoTextpapers
     * @return
     */
    JSONObject show(Exam exam, List<ExamInfoDepart> examInfoDeparts, List<ExamInfoTextpaper> examInfoTextpapers);



    /**
     * 删除实体
     * @param exam
     */
    void deleteExam(Exam exam);

    /**
     * 查询所有分数
     * @param exam
     * @return
     */
    List<ExamInfoUserScore> findALLScore(Exam exam);


    /**
     * 添加用户详情h和分数
     * @param examid
     * @param testpaperid
     * @param userid
     * @param mapList
     */

    JsonResult saveExamAndUser(String examid, String testpaperid, String userid, List<Map<String, String>> mapList);



    /**
     * 考試列表分頁
     * @return
     */
    Page<Exam> finALLForPPage(Pageable pageable);

    /**
     * 模糊查询分页通过标题
     * @param pageable
     * @return
     */
    Page<Exam> findNameForPage(Pageable pageable);
}
