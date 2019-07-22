package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Role;
import com.ayundao.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: RoleController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 19:36
 * @Description: 控制层 - 角色
 * @Version: V1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    /**
     * @api {get} /role/list 列表
     * @apiGroup Role
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例:
     *                /role/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{\"id\":\"b08a1e16dfe04d6c98e1599007c31490\",\"name\":\"user\",\"level\":0},{\"id\":\"c7717f9578b64a819cbfcf75848fcc2a\",\"name\":\"admin\",\"level\":0},{\"id\":\"db9700dc4a92443eaa96d89b4bee019c\",\"name\":\"manager\",\"level\":0}]"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Role> roles = roleService.getList();
        JSONArray arr = new JSONArray();
        for (Role role : roles) {
            JSONObject json = new JSONObject();
            json.put("id", role.getId());
            json.put("name", role.getName());
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /role/view 查看角色
     * @apiGroup Role
     * @apiVersion 1.0.0
     * @apiDescription 查看角色
     * @apiParam {String} id
     * @apiParamExample {json} 请求样例：
     *                ?id=b08a1e16dfe04d6c98e1599007c31490
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此角色</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"id\":\"b08a1e16dfe04d6c98e1599007c31490\",\"name\":\"user\",\"level\":0}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Role role = roleService.findById(id);
        if (role == null) {
            return JsonResult.notFound("未查询到此角色");
        }
        JSONObject json = new JSONObject();
        json.put("id", role.getId());
        json.put("name", role.getName());
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {post} /role/add 新增角色
     * @apiGroup Role
     * @apiVersion 1.0.0
     * @apiDescription 新增角色
     * @apiParam {String} name
     * @apiParam {int} level
     * @apiParamExample {json} 请求样例：
     *                ?name=添加角色&level=1
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afe47db016afe562def0000\",\"createdDate\":\"20190528200701\",\"lastModifiedDate\":\"20190528200701\",\"name\":\"添加角色\",\"level\":\"1\"}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name, @RequestParam(defaultValue = "0") int level) {
        if (StringUtils.isBlank(name)) {
            return JsonResult.paramError();
        }
        Role role = new Role();
        role.setName(name);
        role.setLastModifiedDate(new Date(System.currentTimeMillis()));
        role.setCreatedDate(new Date(System.currentTimeMillis()));
        role = roleService.save(role);
        jsonResult.setData(JsonUtils.getJson(role));
        return jsonResult;
    }

    /**
     * @api {post} /role/modify 修改角色
     * @apiGroup Role
     * @apiVersion 1.0.0
     * @apiDescription 修改角色
     * @apiParam {String} id
     * @apiParam {String} name
     * @apiParam {String} level
     * @apiParamExample {json} 请求样例：
     *                ?id=402881f46afe47db016afe562def0000&name=修改角色11&level=3
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此角色</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             @RequestParam(defaultValue = "10") int level) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Role role = roleService.findById(id);
        if (role == null) {
            return JsonResult.notFound("未查询到此用户组");
        }
        role.setLastModifiedDate(new Date(System.currentTimeMillis()));
        role.setName(name);
        if (level < 10 && level > 0) {
        }else {
            return JsonResult.paramError();
        }
        role = roleService.save(role);
        jsonResult.setData(JsonUtils.getJson(role));
        return jsonResult;
    }


}
