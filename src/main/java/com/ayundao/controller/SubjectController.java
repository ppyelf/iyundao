package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    /**
     * @api {POST} /subject/list 获取结构关系
     * @apiGroup Index
     * @apiVersion 1.0.0
     * @apiDescription 获取结构关系
     * @apiParamExample {json} 请求样例：
     *                /subject/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未登录</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "登录成功",
     * 	"data": "{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'管理员','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','status':'normal','remark':'未填写','sex':'0','salt':'3a10624a300f4670','account':'admin','userType':'amdin'}"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        User user = getUser();
        if (user == null) {
            jsonResult.setCode(404);
            jsonResult.setMessage("未登录");
            return jsonResult;
        }
        List<UserRelation> userRelations = getUserRelation(user);
        JSONArray arr = new JSONArray();
        try {
            for (UserRelation ur : userRelations) {
                JSONObject json = new JSONObject();
                json.put("subject", JsonUtils.getJson(ur.getSubject()));
                json.put("depart", ur.getDepart() != null ? JsonUtils.getJson(ur.getDepart()) : null);
                json.put("groups", ur.getGroups() != null ? JsonUtils.getJson(ur.getGroups()) : null);
                json.put("user", JsonUtils.getJson(ur.getUser()));
                arr.put(json);
            }
            jsonResult.setCode(200);
            jsonResult.setMessage("成功");
            jsonResult.setData(arr.toString());
            return jsonResult;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

}
