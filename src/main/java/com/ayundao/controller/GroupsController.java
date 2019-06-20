package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Depart;
import com.ayundao.entity.Groups;
import com.ayundao.entity.Subject;
import com.ayundao.entity.User;
import com.ayundao.service.GroupsService;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

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
    @PostMapping("/list")
    public JsonResult list(String subjectId) {
        if (StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        } 
        List<Groups> groups = groupsService.findBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(groups)) {
            return JsonResult.notFound("请添加小组");
        }
        JSONArray arr = new JSONArray();
        for (Groups g :groups) {
            JSONObject json = new JSONObject(JsonUtils.getJson(g));
            json.remove("user");
            json.remove("subject");
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {get} /groups/manager_list 部门管理列表
     * @apiGroup Group
     * @apiVersion 1.0.0
     * @apiDescription 部门管理列表
     * @apiParamExample {json} 请求样例：
     *                /groups/manager_list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[\"{\"version\":\"1\",\"id\":\"79daadcc0cb5402f9f97bf01eaa2da67\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"分-组织\",\"subject\":\"{\"version\":\"1\",\"id\":\"c72a2c6bd1e8428fac6706b217417831\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"分院\",\"subjectType\":\"part\"}\",\"user\":\"{\"version\":\"0\",\"id\":\"5cf0d3c3b0da4cbaad179e0d6d230d0c\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"测试用户\",\"status\":\"normal\",\"password\":\"b356a1a11a067620275401a5a3de04300bf0c47267071e06\",\"sex\":\"0\",\"account\":\"test\",\"remark\":\"未填写\",\"salt\":\"3a10624a300f4670\",\"userType\":\"normal\"}\",\"remark\":\"\"}\",\"{\"version\":\"1\",\"id\":\"ec0e291d5bfd4e98a33cd610c9b1d330\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"总-组织\",\"subject\":\"{\"version\":\"1\",\"id\":\"bd6886bc88e54ef0a36472efd95c744c\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"总院\",\"subjectType\":\"head\"}\",\"user\":\"{\"version\":\"0\",\"id\":\"0a4179fc06cb49e3ac0db7bcc8cf0882\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"管理员\",\"status\":\"normal\",\"password\":\"b356a1a11a067620275401a5a3de04300bf0c47267071e06\",\"sex\":\"0\",\"account\":\"admin\",\"remark\":\"未填写\",\"salt\":\"3a10624a300f4670\",\"userType\":\"amdin\"}\",\"remark\":\"\"}\"]"
     * }
     */
    @GetMapping("/manager_list")
    public JsonResult managerList() {
        List<Groups> groups = groupsService.getList();
        JSONArray arr = new JSONArray();
        for (Groups group : groups) {
            arr.add(convertJson(group));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /groups/view 查看小组信息
     * @apiGroup Groups
     * @apiVersion 1.0.0
     * @apiDescription 查看小组信息
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                ?id=813f15af1b1c402da17838e8a067ed68
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "createdDate": "20190517111111",
     *         "lastModifiedDate": "20190517111111",
     *         "name": "子小组",
     *         "id": "813f15af1b1c402da17838e8a067ed68",
     *         "version": "1"
     *     }
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Groups groups = groupsService.findById(id);
        if (groups == null) {
            return JsonResult.notFound("未查询到此用户组");
        }
        try {
            JSONObject json = new JSONObject();
            json.put("id", groups.getId());
            json.put("name", groups.getName());
            json.put("user", groups.getUser());
            json.put("subject", groups.getSubject());
            jsonResult.setData(convertJson(groups));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * @api {post} /groups/add 新增部门
     * @apiGroup Groups
     * @apiVersion 1.0.0
     * @apiDescription 新增部门
     * @apiParam {String} name
     * @apiParam {String} userId
     * @apiParam {String} subjectId
     * @apiParamExample {json} 请求样例：
     *                /groups/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "createdDate": "20190618102549",
     *         "lastModifiedDate": "20190618102549",
     *         "name": "测试用户组1",
     *         "id": "402881916b68611a016b68679ca30000",
     *         "version": "0"
     *     }
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          String userId,
                          String subjectId) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        }
        Groups groups = new Groups();
        groups.setName(name);
        groups.setLastModifiedDate(new Date(System.currentTimeMillis()));
        groups.setCreatedDate(new Date(System.currentTimeMillis()));
        Subject subject = subjectService.find(subjectId);
        if (subject == null) {
            return JsonResult.notFound("机构参数异常");
        }
        groups.setSubject(subject);
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            groups.setUser(user == null ? null : user);
        }
        groups = groupsService.save(groups);
        jsonResult.setData(convertJson(groups));
        return jsonResult;
    }

    /**
     * @api {post} /groups/modify 修改小组信息
     * @apiGroup Groups
     * @apiVersion 1.0.0
     * @apiDescription 修改小组信息
     * @apiParam {String} id 必填
     * @apiParam {String} name
     * @apiParam {String} userId
     * @apiParam {String} subjectId 必填
     * @apiParamExample {json} 请求样例：
     *                ?id=402881f46afdef14016afe0d13520005&name=修改用户组
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     *                                 601:此机构不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190528184710","lastModifiedDate": "20190618102400","name": "修改用户组","id": "402881f46afdef14016afe0d13520005","version": "2"
     *     }
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             String userId,
                             String subjectId) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Groups groups = groupsService.findById(id);
        if (groups == null) {
            return JsonResult.notFound("未查询到此用户组");
        }
        groups.setLastModifiedDate(new Date(System.currentTimeMillis()));
        groups.setName(StringUtils.isBlank(name) ? groups.getName() : name);
        if (StringUtils.isNotBlank(subjectId)) {
            Subject subject = subjectService.find(subjectId);
            if (subject == null)   return JsonResult.notFound("此机构不存在");
            groups.setSubject(subject);
        }
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            groups.setUser(user);
        }
        groups = groupsService.save(groups);
        jsonResult.setData(convertJson(groups));
        return jsonResult;
    }

    /**
     * 转换实体json信息
     * @param groups
     * @return
     */
    private JSONObject convertJson(Groups groups) {
        JSONObject json = new JSONObject(JsonUtils.getJson(groups));
        json.put("id", groups.getId());
        json.put("name", groups.getName());
        json.put("subject", JsonUtils.getJson(groups.getSubject()));
        json.put("user", groups.getUser() == null ? null : JsonUtils.getJson(groups.getUser()));
        return json;
    }

}
