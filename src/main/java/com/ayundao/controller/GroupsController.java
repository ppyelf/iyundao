package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Groups;
import com.ayundao.service.GroupsService;
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
 * @ClassName: GroupsController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 15:12
 * @Description: 控制层 - 小组
 * @Version: V1.0
 */
@RestController
@RequestMapping("/groups")
public class GroupsController extends BaseController {

    @Autowired
    private GroupsService groupsService;

    /**
     * @api {POST} /groups/list 小组列表
     * @apiGroup Groups
     * @apiVersion 1.0.0
     * @apiDescription 小组列表
     * @apiParam {String} subjectId 机构id
     * @apiParamExample {json} 请求样例：
     *                /groups/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:请添加小组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{'version':'1','id':'ec0e291d5bfd4e98a33cd610c9b1d330','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'总-组织','remark':''}]"
     * }
     */
    @PostMapping("list")
    public JsonResult list(String subjectId) {
        if (StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        } 
        List<Groups> groups = groupsService.findBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(groups)) {
            return JsonResult.notFound("请添加小组");
        }
        JSONArray arr = new JSONArray();
        try {
            for (Groups g :groups) {
                JSONObject json = new JSONObject(JsonUtils.getJson(g));
                json.remove("user");
                json.remove("subject");
                arr.put(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }
}
