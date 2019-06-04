package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.FieldService;
import com.ayundao.service.PageService;
import com.ayundao.service.RoleService;
import com.ayundao.service.UserGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: FieldController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 23:20
 * @Description: 控制层 -- 字段
 * @Version: V1.0
 */
@RestController
@RequestMapping("/field")
public class FieldController extends BaseController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private PageService pageService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private RoleService roleService;

    /**
     * @api {GET} /field/list 列表
     * @apiGroup Field
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
     *                /field/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "["{"version":"1","id":"04b5fffcdba042e783d44667a5a823e0","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组1字段","sort":"0"}","{"version":"1","id":"0a29958fb7274cf39868e14ade38ac74","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组2字段","sort":"0"}","{"version":"1","id":"39b22ac71cb9490584f3fa4c5cf5b940","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组2字段","sort":"0"}","{"version":"1","id":"bd5b0b97ba00400488c5a8aee29f9777","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组1字段","sort":"0"}"]"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Field> fields = fieldService.findAllForList();
        JSONArray arr = new JSONArray();
        for (Field field : fields) {
            arr.put(JsonUtils.getJson(field));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {post} /field/view 查看
     * @apiGroup Field
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParamExample {json} 请求样例：
     *                /field/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:字段不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"page":"{"name":"","level":"","sort":"","uri":"","title":""}","fieldRoles":["{"version":"1","id":"47cc0f57e76a4e31bd34bb5e5f8fd5b1","createdDate":"20190517111111","lastModifiedDate":"20190517111111"}"],"buttons":["{"version":"1","id":"fe78513de3c64f859ccf349c8aeea7d7","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"字段1按钮","sort":"0","uri":""}"]}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        Field field = fieldService.findById(id);
        if (field == null) {
            return JsonResult.notFound("字段不存在");
        } 
        jsonResult.setData(converField(field));
        return jsonResult;
    }

    /**
     * @api {post} /field/add 新增
     * @apiGroup Field
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} name 必填
     * @apiParam {String} pageId 必填
     * @apiParam {String[]} userGroupIds
     * @apiParam {String[]} roleIds
     * @apiParam {int} sort
     * @apiParam {int} level
     * @apiParamExample {json} 请求样例：
     *                /field/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:字段名或所属页面不能为空
     *                                 601:页面不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"page":"{"version":"1","id":"2c4824f71b6f4de389e0b8b375636d94","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"测试分院页面","level":"0","sort":"0","uri":"","title":"管理员页面"}","fieldRoles":["{"version":"0","id":"402881916b210d35016b2125086c0005","createdDate":"20190604142003","lastModifiedDate":"20190604142003"}","{"version":"0","id":"402881916b210d35016b2125086c0006","createdDate":"20190604142003","lastModifiedDate":"20190604142003"}"]}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          String pageId,
                          String[] userGroupIds,
                          String[] roleIds,
                          @RequestParam(defaultValue = "0") int sort,
                          @RequestParam(defaultValue = "0") int level) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(pageId)) {
            return JsonResult.notFound("字段名或所属页面不能为空");
        } 
        Page page = pageService.find(pageId);
        if (page == null) {
            return JsonResult.failure(601, "页面不存在");
        }
        List<UserGroup> userGroups = userGroupService.findByIds(userGroupIds);
        List<Role> roles = roleService.findByRoleIds(roleIds);
        Field field = new Field();
        field.setCreatedDate(new Date());
        field.setLastModifiedDate(new Date());
        field.setName(name);
        field.setSort(sort);
        field.setLevel(level);
        field.setPage(page);
        field = fieldService.save(field, userGroups, roles);
        jsonResult.setData(converField(field));
        return jsonResult;
    }

    /**
     * @api {post} /field/modify 修改
     * @apiGroup Field
     * @apiVersion 1.0.0
     * @apiDescription 修改
     * @apiParam {String} id 必填
     * @apiParam {String} name 必填
     * @apiParam {String} pageId
     * @apiParam {String[]} userGroupIds
     * @apiParam {String[]} roleIds
     * @apiParam {int} sort
     * @apiParam {int} level
     * @apiParamExample {json} 请求样例：
     *                /field/modify
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:字段名或所属页面不能为空
     *                                 601:页面不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"field":"{"version":"5","id":"402881916b210d35016b21240b440002","createdDate":"20190604141858","lastModifiedDate":"20190604141858","name":"修改字段12","level":"0","sort":"0"}","page":"{"name":"","level":"","sort":"","uri":"","title":""}","fieldRoles":["{"version":"0","id":"402881916b210d35016b213e2979000d","createdDate":"20190604144730","lastModifiedDate":"20190604144730"}"]}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             String pageId,
                             String[] userGroupIds,
                             String[] roleIds,
                             @RequestParam(defaultValue = "0") int sort,
                             @RequestParam(defaultValue = "0") int level) {
        Field field = fieldService.findById(id);
        if (field == null) {
            return JsonResult.notFound("字段名或所属页面不能为空");
        }
        Page page = pageService.find(pageId);
        if (page == null) {
            return JsonResult.failure(601, "页面不存在");
        }
        List<UserGroup> userGroups = userGroupService.findByIds(userGroupIds);
        List<Role> roles = roleService.findByRoleIds(roleIds);
        field.setName(StringUtils.isBlank(name) ? field.getName() : name);
        field.setPage(page);
        field.setSort(sort);
        field.setLevel(level);
        field = fieldService.update(field, userGroups, roles);
        jsonResult.setData(converField(field));
        return jsonResult;
    }

    /**
     * @api {post} /field/del 删除
     * @apiGroup Field
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                /field/del?id=402881916b210d35016b21240b440002
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:字段不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{}"
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        Field field = fieldService.findById(id);
        if (field == null) {
            return JsonResult.notFound("字段不存在");
        }
        fieldService.delete(field);
        return JsonResult.success();
    }

    private String converField(Field field) {
        try {
            JSONObject json = new JSONObject();
            json.put("field", JsonUtils.getJson(field));
            json.put("page", JsonUtils.getJson(field.getPage()));
            JSONArray arr = new JSONArray();
            for (FieldRole fr : field.getFieldRoles()) {
                arr.put(JsonUtils.getJson(fr));
            }
            json.put("fieldRoles", arr);
            arr = new JSONArray();
            if (CollectionUtils.isNotEmpty(field.getButtons())) {
                for (Button button : field.getButtons()) {
                    arr.put(JsonUtils.getJson(button));
                }
                json.put("buttons", arr);
            }
            return JsonUtils.delString(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
