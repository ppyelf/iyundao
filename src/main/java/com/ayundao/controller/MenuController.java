package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: MenuController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 23:20
 * @Description: 控制层 -- 菜单
 * @Version: V1.0
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private UserGroupRelationService userGroupRelationService;

    @Autowired
    private MenuRelationService menuRelationService;

    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /menu/list 列表
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
     *                /menu/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Menu> menus = menuService.getList();
        JSONArray arr = new JSONArray();
        for (Menu m : menus) {
            arr.put(JsonUtils.getJson(m));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {post} /menu/view 查看
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParamExample {json} 请求样例：
     *                /menu/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"1\",\"id\":\"79daadcc0cb5402f9f97bf01eaa2da67\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"分-组织\",\"subject\":\"{\"version\":\"1\",\"id\":\"c72a2c6bd1e8428fac6706b217417831\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"分院\",\"subjectType\":\"part\"}\",\"user\":\"{\"version\":\"0\",\"id\":\"5cf0d3c3b0da4cbaad179e0d6d230d0c\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"测试用户\",\"status\":\"normal\",\"password\":\"b356a1a11a067620275401a5a3de04300bf0c47267071e06\",\"sex\":\"0\",\"account\":\"test\",\"remark\":\"未填写\",\"salt\":\"3a10624a300f4670\",\"userType\":\"normal\"}\",\"remark\":\"\"}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        if (isValid(id)) {
            return JsonResult.paramError();
        }
        jsonResult.setData(convertRelation(menuService.findById(id)));
        return jsonResult;
    }

    /**
     * @api {post} /menu/add 新增
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParamExample {json} 请求样例：
     *                /menu/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping(value = "/add")
    public JsonResult add(String name,
                          String remark,
                          boolean isPublic,
                          String uri,
                          String fatherId,
                          @RequestParam(defaultValue = "0") int level,
                          String subjectId,
                          String departId,
                          String groupsId,
                          String roleId,
                          String userGroupId) {
        if (isValid(name) || isValid(subjectId) || isValid(departId) || isValid(groupsId) || isValid(roleId)) {
            return JsonResult.paramError();
        }
        List<UserRelation> userRelations = userRelationService.findBySubjectAndDepartOrGroups(subjectId, departId, groupsId);
        if (CollectionUtils.isEmpty(userRelations)) {
            return JsonResult.notFound("未查询到相关用户的机构关系,请先添加用户关系");
        }

        List<UserGroupRelation> userGroupRelations = userGroupRelationService.findByUserGroupId(userGroupId);
        if (CollectionUtils.isEmpty(userGroupRelations)) {
            return JsonResult.notFound("用户组尚未添加用户");
        }

        Role role = roleService.findByRoleId(roleId);
        if (role == null) {
            return JsonResult.notFound("角色不存在");
        } 
        Menu menu = menuService.save(name, remark, isPublic, uri, fatherId, level, userRelations, role, userGroupRelations);
        return jsonResult;
    }

    /**
     * @api {post} /menu/modify 修改
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 修改
     * @apiParamExample {json} 请求样例：
     *                /menu/modify
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     *                                 601:此机构不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping("/modify")
    public JsonResult modify() {
        return jsonResult;
    }

    private JSONObject convertRelation(Menu menu) {
        try {
            JSONObject json = new JSONObject(JsonUtils.getJson(menu));

            if (menu.getFather() != null) {
                json.put("father", JsonUtils.getJson(menu.getFather()));
            }
            List<MenuRelation> menuRelations = menuRelationService.findByMenuId(menu.getId());
            JSONArray arr = new JSONArray();
            for (MenuRelation menuRelation : menuRelations) {
                JSONObject j = new JSONObject(JsonUtils.getJson(menuRelation));
                j.put("userRelation", menuRelation.getUserRelation() == null ? null : JsonUtils.getJson(menuRelation.getUserRelation()));
                j.put("userGroupRelation", menuRelation.getUserGroupRelation() == null ? null : JsonUtils.getJson(menuRelation.getUserGroupRelation()));
                j.put("role", menuRelation.getRole() == null ? null : JsonUtils.getJson(menuRelation.getRole()));
                arr.put(j);
            }
            json.put("menuRelation", arr);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
