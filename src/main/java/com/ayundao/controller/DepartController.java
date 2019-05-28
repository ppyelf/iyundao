package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Depart;
import com.ayundao.service.DepartService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: DepartController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:23
 * @Description: 控制层 - 部门
 * @Version: V1.0
 */
@RestController
@RequestMapping("/depart")
public class DepartController extends BaseController {

    @Autowired
    private DepartService departService;

    /**
     * @api {POST} /depart/list 部门列表
     * @apiName list
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 部门列表
     * @apiParam {String} subjectId 机构id
     * @apiExample {json} 请求样例
     *                ?subjectId=bd6886bc88e54ef0a36472efd95c744c
     * @apiSuccess {int} code 200:成功</br>
     *                                 404:请添加部门</br>
     *                                 600:参数异常</br>
     * @apiSuccess {String} message 信息
     * @apiSuccess {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{'version':'1','id':'9b7678a607ef4199ad7a4018b892c49d','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'总-部门','depart':''}]"
     * }
     */
    @PostMapping("/list")
    public JsonResult list(String subjectId) {
        if (StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        }
        List<Depart> departs = departService.findBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(departs)) {
            return jsonResult.notFound("请添加部门");
        }
        try {
            JSONArray arr = new JSONArray();
            for (Depart d :departs) {
                JSONObject json = new JSONObject(JsonUtils.getJson(d));
                json.remove("user");
                json.remove("subject");
                arr.put(json);
            }
            jsonResult = JsonResult.success();
            jsonResult.setData(JsonUtils.delString(arr.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
