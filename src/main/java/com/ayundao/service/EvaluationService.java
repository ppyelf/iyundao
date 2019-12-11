package com.ayundao.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Evaluation;
import com.ayundao.entity.EvaluationIndex;
import com.ayundao.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: EvaluationService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 14:26
 * @Description: 服务 - 考评
 * @Version: V1.0
 */
public interface EvaluationService {

    /**
     * 获取指标列表
     * @return
     */
    JSONArray getIndexList();

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    Evaluation find(String id);

    /**
     * 根据ID查询考评指标实体
     * @param id
     * @return
     */
    EvaluationIndex findEvaluationIndex(String id);

    /**
     * 添加个人记录的考评指标
     * @param year
     * @param user
     * @param ei
     * @param score
     * @param remark
     * @param number
     * @param patientName
     * @param operator
     * @return
     */
    Evaluation save(String year, User user, EvaluationIndex ei, double score, String remark, String number, String patientName, User operator);

    /**
     * 获取待审核考评列表
     * @return
     * @param startTime
     * @param endTime
     * @param code
     * @param subjectId
     * @param addSubjectId
     * @param indexId
     * @param status
     * @param currentSubjectId
     * @param num
     * @param size
     * @param departId
     */
    Page<JSONObject> getList(String startTime, String endTime, String code, String subjectId, String addSubjectId, String indexId, int status, String currentSubjectId, int num, int size, String departId);


    /**
     * 根据ID集合删除考评集合
     * @param id
     * @return
     */
    void deleteByIDS(String[] id);

    /**
     * 获取统计分页
     * @param code
     * @param year
     * @param num
     * @param size
     * @param order
     * @return
     */
    Page<JSONObject> getSumList(String code, String year, int num, int size, String order);

    /**
     * 获取年份列表
     * @return
     */
    List<String> getYearList();

    /**
     * 浏览个人统计结果
     * @param user
     * @param year
     * @return
     */
    JsonResult viewSum(User user, String year);

    /**
     * 导入医德医风
     * @param file
     * @param success
     * @return
     */
    JsonResult upload(MultipartFile file, JsonResult success);

    void downloadEvaluation(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    /**
     * 导出个人医德医风
     * @param code
     * @param year
     * @param req
     * @param resp
     * @return
     */
    JsonResult export(String code, String year, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 审核医德医风
     * @param evaluation
     * @param status
     */
    void sure(List<Evaluation> evaluation, Evaluation.STATUS status);

    /**
     * 保存实体
     * @param e
     * @return
     */
    Evaluation save(Evaluation e);

    List<Evaluation> findByIds(String[] ids);
}
