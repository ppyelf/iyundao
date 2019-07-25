package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.User;
import com.ayundao.entity.UserGroup;
import com.ayundao.service.UserGroupService;
import com.ayundao.service.UserService;
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
 * @ClassName: UserGroupController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 0:14
 * @Description: 控制层 - 用户组
 * @Version: V1.0
 */
@RestController
@RequestMapping("/user_group")
public class UserGroupController extends BaseController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    /**
     * @api {get} /user_group/list 列表
     * @apiGroup UserGroup
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
     *                /user_group/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{'id':'66aaab6db70b434ca60a753ad3e2bbf9','name':'小组2'},{'id':'813f15af1b1c402da17838e8a067ed68','name':'子小组','father':{'id':'66aaab6db70b434ca60a753ad3e2bbf9','name':'小组2'}},{'id':'8b568d0307c744cb9fedc2fe5b7f1238','name':'小组1'}]"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<UserGroup> userGroups = userGroupService.getListByFatherIsNull();
        JSONArray arr = new JSONArray();
        for (UserGroup ug : userGroups) {
            arr.add(JsonUtils.getJson(ug));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /user_group/view 查看单个用户组
     * @apiGroup UserGroup
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看单个用户组
     * @apiParam {String} id
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
        UserGroup userGroup = userGroupService.findById(id);
        if (userGroup == null) {
            return JsonResult.notFound("未查询到此用户组");
        }
        jsonResult.setData(JsonUtils.getJson(userGroup));
        return jsonResult;
    }

    /**
     * @api {post} /user_group/add 新增个人用户组
     * @apiGroup UserGroup
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看单个用户组
     * @apiParam {String} name
     * @apiParam {String} fatherId
     * @apiParam {String} userId
     * @apiParamExample {json} 请求样例：
     *                ?name=测试用户组1
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "createdDate": "20190618105849",
     *         "lastModifiedDate": "20190618105849",
     *         "name": "测试用户组1",
     *         "id": "402881916b68611a016b6885d4fa0003",
     *         "version": "0"
     *     }
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name, String fatherId, String userId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setLastModifiedDate(new Date(System.currentTimeMillis()));
        userGroup.setCreatedDate(new Date(System.currentTimeMillis()));
        if (StringUtils.isNotBlank(fatherId)) {
            UserGroup father = userGroupService.findById(fatherId);
            userGroup.setFather(father == null ? null : father);
        }
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            userGroup.setUser(user == null ? null : user);
        }
        userGroup = userGroupService.save(userGroup);
        jsonResult.setData(JsonUtils.getJson(userGroup));
        return jsonResult;
    }

    /**
     * @api {post} /user_group/modify 修改个人用户组
     * @apiGroup UserGroup
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 修改个人用户组
     * @apiParam {String} id
     * @apiParam {String} name
     * @apiParam {String} fatherId
     * @apiParam {String} userId
     * @apiParamExample {json} 请求样例：
     *                ?id=402881f46afdef14016afe0d13520005&name=修改用户组
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
     *         "createdDate": "20190528184710",
     *         "lastModifiedDate": "20190618105820",
     *         "name": "修改用户组",
     *         "id": "402881f46afdef14016afe0d13520005",
     *         "version": "3"
     *     }
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id, String name, String fatherId, String userId) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        UserGroup ug = userGroupService.findById(id);
        if (ug == null) {
            return JsonResult.notFound("未查询到此用户组");
        } 
        ug.setLastModifiedDate(new Date(System.currentTimeMillis()));
        ug.setName(name);
        if (StringUtils.isNotBlank(fatherId)) {
            UserGroup father = userGroupService.findById(fatherId);
            ug.setFather(father);
        }
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            ug.setUser(user);
        }
        ug = userGroupService.save(ug);
        jsonResult.setData(JsonUtils.getJson(ug));
        return jsonResult;
    }

    /**
     * @api {post} /user_group/child 查看单个用户组
     * @apiGroup UserGroup
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看单个用户组
     * @apiParam {String} id
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
    @PostMapping("/child")
    public JsonResult child(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        List<UserGroup> userGroup = userGroupService.findByFatherId(id);
        JSONArray arr = new JSONArray();
        for (UserGroup ug : userGroup) {
            arr.add(JsonUtils.getJson(ug));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }
}
