package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Exam;
import com.ayundao.service.ExamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ExamController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 考试
 * @Version: V1.0
 */
@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController{

    @Autowired
    private ExamService examService;

    /**
     * @api {GET} /exam/list 列表
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例:
     *                /exam/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{"examlong": "60","starttime": "2121","title": "name","score": "100","showthat": "员工测试","overtime": "2121","id": "123123"},{"examlong": "60","starttime": "2121","title": "name","score": "100","showthat": "员工测试","overtime": "2121","id": "55555555"}]
     * }
     */
    @GetMapping("/list")
    public JsonResult list(){
        List<Exam> exams = examService.findAll();
        JSONArray arr = new JSONArray();
         for (Exam exam : exams){
                arr.add(converExam(exam));
         }
        jsonResult.setData(arr);

        return jsonResult;
    }

    /**
     * 添加考试
     */
    @PostMapping("/add")
    public JsonResult add(String title,
                          String starttime,
                          String overtime,
                          String examlong,
                          String score,
                          String showthat,
                          String[] subjectIds,
                          String[] departIds,
                          String[] groupIds,
                          String[] testpapers
                          ){
        Exam exam = new Exam();
        exam.setCreatedDate(new Date());
        exam.setLastModifiedDate(new Date());
        exam.setTitle(title);
        exam.setStarttime(starttime);
        exam.setOvertime(overtime);
        exam.setExamlong(examlong);
        exam.setScore(score);
        exam.setShowthat(showthat);
        exam = examService.save(exam,subjectIds,departIds,groupIds,testpapers);

        return jsonResult;
    }





    /**
     * 转换Exam 为json
     * @param exam
     * @return
     */
    private JSONObject converExam(Exam exam){
            JSONObject jsonObject = new JSONObject(JsonUtils.getJson(exam));
            return jsonObject;

    }


}
