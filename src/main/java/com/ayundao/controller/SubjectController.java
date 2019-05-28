package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: SubjectController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/19 18:24
 * @Description: 控制层 - 机构
 * @Version: V1.0
 */
@RestController
@RequestMapping("/subject")
public class SubjectController extends BaseController {

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private SubjectService subjectService;

    /**
     * @api {POST} /subject/list 机构列表
     * @apiGroup Subject
     * @apiVersion 1.0.0
     * @apiDescription 机构列表
     * @apiParamExample {json} 请求样例：
     *                /subject/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:请添加机构</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": "['{'version':'1','id':'bd6886bc88e54ef0a36472efd95c744c','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'总院','subjectType':'head'}','{'version':'1','id':'c72a2c6bd1e8428fac6706b217417831','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'分院','subjectType':'head'}']"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Subject> subjects = subjectService.findAll();
        if (CollectionUtils.isEmpty(subjects)) {
            jsonResult.setCode(404);
            jsonResult.setMessage("请添加机构");
            return jsonResult;
        }
        JSONArray arr = new JSONArray();
        for (Subject s :subjects){
            s.setUserRelations(null);
            arr.put(JsonUtils.getJson(s));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

}
