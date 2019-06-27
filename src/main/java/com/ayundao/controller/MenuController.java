package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private PageService pageService;

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
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{"id":"402881916b5a1eba016b5a1f33d60000","name":"测试账号"},{"id":"402881916b5a1eba016b5a1fafc70002","name":"测试账号11"}]"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Menu> menus = menuService.getList();
        JSONArray arr = new JSONArray();
        for (Menu m : menus) {
            arr.add(JsonUtils.getJson(m));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /menu/view 查看
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                ?id=078fc9671e2a4b028bcf084f662c51d3
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190517111111","lastModifiedDate": "20190517111111","level": "0","father": {    "createdDate": "20190517111111",    "lastModifiedDate": "20190517111111",    "level": "0",    "name": "管理员菜单",    "remark": "",    "id": "da3bde893f544ae9af8ed99bbf788192",    "version": "1",    "uri": ""},"name": "用户菜单","remark": "","menuRelation": [    {        "createdDate": "20190517111111",        "role": {            "createdDate": "20190517111111",            "lastModifiedDate": "20190517111111",            "level": "0",            "name": "user",            "id": "b08a1e16dfe04d6c98e1599007c31490",            "version": "1"        },        "lastModifiedDate": "20190517111111",        "userRelation": {            "createdDate": "20190517111111",            "lastModifiedDate": "20190517111111",            "id": "366f02491fee40c68cc396ab7f8b78e3",            "version": "1"        },        "userGroupRelation": null,        "id": "59b7d2d638bf44faaee397faabfd36bc",        "version": "1"    }],"id": "078fc9671e2a4b028bcf084f662c51d3","version": "1","uri": ""
     *     }
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        jsonResult.setData(convertRelation(menuService.findById(id)));
        return jsonResult;
    }

    /**
     * @api {post} /menu/add 新增
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} name (必填)
     * @apiParam {String} remark
     * @apiParam {String} fatherId
     * @apiParam {number} level
     * @apiParam {String} subjectId (必填)
     * @apiParam {String[]} departIds
     * @apiParam {String[]} groupsIds
     * @apiParam {String[]} roleIds
     * @apiParam {String[]} userGroupIds
     * @apiParamExample {json} 请求样例：
     *                /menu/add
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
     *     "data": "{"version":"0","id":"402881916b1ae347016b1c84de06000c","createdDate":"20190603164638","lastModifiedDate":"20190603164638","name":"添加菜单","level":"0","uri":"","remark":""}"
     * }
     */
    @PostMapping(value = "/add")
    public JsonResult add(String name,
                          String remark,
                          String uri,
                          String fatherId,
                          @RequestParam(defaultValue = "0") int level,
                          String subjectId,
                          String[] departIds,
                          String[] groupsIds,
                          String[] roleIds,
                          String[] userGroupIds) {
        List<UserRelation> userRelations = userRelationService.findBySubjectAndDepartIdsOrGroupsIds(subjectId, departIds, groupsIds);
        if (CollectionUtils.isEmpty(userRelations)) {
            return JsonResult.notFound("未查询到相关用户的机构关系,请先添加用户关系");
        }

        List<UserGroupRelation> userGroupRelations = userGroupRelationService.findByUserGroupIds(userGroupIds);
        if (CollectionUtils.isEmpty(userGroupRelations)) {
            return JsonResult.notFound("用户组尚未添加用户");
        }

        List<Role> role = roleService.findByRoleIds(roleIds);
        if (role == null) {
            return JsonResult.notFound("角色不存在");
        }
        Menu menu = new Menu();
        menu.setCreatedDate(new Date(System.currentTimeMillis()));
        menu.setLastModifiedDate(new Date(System.currentTimeMillis()));
        menu.setName(name);
        menu.setRemark(remark);
        menu.setUri(uri);
        if (StringUtils.isNotBlank(fatherId)) {
            Menu father = menuService.findById(fatherId);
            menu.setFather(father);
        }
        menu.setLevel(level);
        menu = menuService.save(menu, userRelations, role, userGroupRelations);
        if (menu == null) {
            return JsonResult.failure(1, "添加菜单失败");
        }
        jsonResult.setData(JsonUtils.getJson(menu));
        return jsonResult;
    }

    /**
     * @api {post} /menu/modify 修改
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 修改
     * @apiParam {String} id (必填)
     * @apiParam {String} name (必填)
     * @apiParam {String} remark
     * @apiParam {String} fatherId
     * @apiParam {number} level
     * @apiParam {String} subjectId (必填)
     * @apiParam {String[]} departIds
     * @apiParam {String[]} groupsIds
     * @apiParam {String[]} roleIds
     * @apiParam {String[]} userGroupIds
     * @apiParamExample {json} 请求样例：
     *                /menu/modify
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     *                                 601:菜单不存在</br>
     *                                 602:用户组尚未添加用户</br>
     *                                 603:父级菜单不存在</br>
     *                                 604:角色不存在</br>
     *                                 605:未查询到相关用户的机构关系,请先添加用户关系</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{"id":"402881916b5a1eba016b5a1f33d60000","name":"测试账号"},{"id":"402881916b5a1eba016b5a1fafc70002","name":"测试账号11"}]"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             String remark,
                             String uri,
                             String fatherId,
                             @RequestParam(defaultValue = "0") int level,
                             String subjectId,
                             String[] departIds,
                             String[] groupsIds,
                             String[] roleIds,
                             String[] userGroupIds) {
        Menu menu = menuService.findById(id);
        if (menu == null) {
            return jsonResult.failure(601, "菜单不存在");
        }
        menu.setName(name);
        menu.setRemark(remark);
        menu.setUri(uri);
        menu.setLevel(level);
        Menu father = StringUtils.isBlank(fatherId) ? menu.getFather() : menuService.findById(fatherId);
        if (StringUtils.isNotBlank(fatherId) && father == null) {
            return JsonResult.failure(603, "父级菜单不存在");
        }
        menu.setFather(father);

        List<UserRelation> userRelations = userRelationService.findBySubjectAndDepartIdsOrGroupsIds(subjectId, departIds, groupsIds);
        if (CollectionUtils.isEmpty(userRelations)) {
            return JsonResult.failure(605, "未查询到相关用户的机构关系,请先添加用户关系");
        }
        List<UserGroupRelation> userGroupRelations = userGroupRelationService.findByUserGroupIds(userGroupIds);
        if (CollectionUtils.isEmpty(userGroupRelations)) {
            return JsonResult.failure(602,"用户组尚未添加用户");
        }

        List<Role> role = roleService.findByRoleIds(roleIds);
        if (role == null) {
            return JsonResult.failure(604, "角色不存在");
        }
        menu = menuService.modify(menu, userRelations, role, userGroupRelations);
        jsonResult.setData((convertRelation(menu)));
        return jsonResult;
    }

    /**
     * @api {post} /menu/del 查看
     * @apiGroup Menu
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                /menu/del
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 -1:此菜单包含页面,请先删除该菜单拥有的所有页面</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        List<Page> pages = pageService.findPageByMenuId(id);
        if (CollectionUtils.isNotEmpty(pages)) {
            return JsonResult.failure("此菜单包含页面,请先删除该菜单拥有的所有页面");
        }
        menuService.delete(id);
        return jsonResult;
    }

    /**
     * 转换关系json格式
     * @param menu
     * @return
     */
    private JSONObject convertRelation(Menu menu) {
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
            arr.add(j);
        }
        json.put("menuRelation", arr);
        return json;
    }
}
