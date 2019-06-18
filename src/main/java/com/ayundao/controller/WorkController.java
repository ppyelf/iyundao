package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Depart;
import com.ayundao.entity.Groups;
import com.ayundao.entity.Subject;
import com.ayundao.service.SubjectService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: WorkController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/17 10:13
 * @Description: 控制层 - 中心工作
 * @Version: V1.0
 */
@RestController
@RequestMapping("/work")
public class WorkController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    /**
     * @api {post} /work/getSubjectList 获取机构列表
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 获取部门成员
     * @apiParam {String} id 部门ID(必填)
     * @apiParamExample {json} 请求样例：
     *                /work/getSubjectList
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{"id":"402881916b5a1eba016b5a1f33d60000","name":"测试账号"},{"id":"402881916b5a1eba016b5a1fafc70002","name":"测试账号11"}]"
     * }
     */
    @GetMapping("/getSubjectList")
    public JsonResult getSubjectList() {
        List<Subject> list = subjectService.findAll();
        JSONArray arr = new JSONArray();
        try {
            for (Subject subject : list) {
                JSONObject json = new JSONObject();
                json.put("id", subject.getId());
                json.put("name", subject.getName());
                if (CollectionUtils.isNotEmpty(subject.getDeparts())) {
                    JSONArray departArr = new JSONArray();
                    for (Depart depart : subject.getDeparts()) {
                        JSONObject d = new JSONObject();
                        d.put("id", depart.getId());
                        d.put("name", depart.getName());
                        departArr.add(d);
                    }
                    json.put("departs", departArr);
                }

                if (CollectionUtils.isNotEmpty(subject.getGroups())) {
                    JSONArray groupArr = new JSONArray();
                    for (Groups group : subject.getGroups()) {
                        JSONObject g = new JSONObject();
                        g.put("id", group.getId());
                        g.put("name", group.getName());
                        groupArr.add(g);
                    }
                    json.put("groups", arr);
                }
                arr.add(json);
            }
            jsonResult.setData(arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
