package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.ButtonService;
import com.ayundao.service.FieldService;
import com.ayundao.service.RoleService;
import com.ayundao.service.UserGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ButtonController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 23:21
 * @Description: 控制层 -- 按钮
 * @Version: V1.0
 */
@RestController
@RequestMapping("/button")
public class ButtonController extends BaseController {

    @Autowired
    private ButtonService buttonService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /button/list 列表
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
     *                /button/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "["{"button":"{"version":"1","id":"186c1f7b93d148dd81a119cda63dcb0c","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"字段2按钮","sort":"0","uri":""}","field":"{"name":"","level":"","sort":""}","buttonRoles":[]}","{"button":"{"version":"1","id":"2d970b277d0a4a43a0a9eefc5af6acf5","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"字段3按钮","sort":"0","uri":""}","field":"{"name":"","level":"","sort":""}","buttonRoles":[]}","{"button":"{"version":"1","id":"e7260b4718fa48618bb91859ff750099","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"字段4按钮","sort":"0","uri":""}","field":"{"name":"","level":"","sort":""}","buttonRoles":[]}","{"button":"{"version":"1","id":"fe78513de3c64f859ccf349c8aeea7d7","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"字段1按钮","sort":"0","uri":""}","field":"{"name":"","level":"","sort":""}","buttonRoles":[]}"]"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Button> buttons = buttonService.findAllList();
        JSONArray arr = new JSONArray();
        for (Button button : buttons) {
            arr.put(convert(button));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {post} /button/view 查看
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                /button/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:按钮不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"button":"{"version":"1","id":"186c1f7b93d148dd81a119cda63dcb0c","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"字段2按钮","sort":"0","uri":""}","field":"{"name":"","level":"","sort":""}","buttonRoles":[]}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        Button button = buttonService.find(id);
        if (button == null) {
            return JsonResult.notFound("按钮不存在");
        }
        jsonResult.setData(convert(button));
        return jsonResult;
    }

    /**
     * @api {post} /button/add 新增
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParamExample {json} 请求样例：
     *                /button/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:字段不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"button":"{"version":"0","id":"402881916b210d35016b2174dd750011","createdDate":"20190604154715","lastModifiedDate":"20190604154715","name":"添加按钮","level":"0","sort":"0","uri":""}","field":"{"version":"0","id":"402881916b210d35016b2125086b0004","createdDate":"20190604142003","lastModifiedDate":"20190604142003","name":"添加字段1","level":"0","sort":"0"}","buttonRoles":["{"version":"0","id":"402881916b210d35016b2174dd7c0013","createdDate":"20190604154715","lastModifiedDate":"20190604154715"}","{"version":"0","id":"402881916b210d35016b2174dd7b0012","createdDate":"20190604154715","lastModifiedDate":"20190604154715"}"]}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          String fieldId,
                          String uri,
                          String[] userGroupIds,
                          String[] roleIds,
                          @RequestParam(defaultValue = "0") int sort,
                          @RequestParam(defaultValue = "0") int level) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(fieldId)) return JsonResult.notFound("按钮名或所属字段不能为空");
        Field field = fieldService.findById(fieldId);
        if (field == null) {
            return JsonResult.failure(601, "字段不存在");
        }
        List<UserGroup> userGroups = userGroupService.findByIds(userGroupIds);
        List<Role> roles = roleService.findByRoleIds(roleIds);
        Button button = new Button();
        button.setCreatedDate(new Date());
        button.setLastModifiedDate(new Date());
        button.setName(name);
        button.setField(field);
        button.setUri(uri);
        button.setSort(sort);
        button.setLevel(level);
        button = buttonService.save(button, userGroups, roles);
        jsonResult.setData(convert(button));
        return jsonResult;
    }

    /**
     * @api {post} /button/modify 修改
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 修改
     * @apiParamExample {json} 请求样例：
     *                  button/modify?id=402881916b210d35016b2174dd750011&name=修改按钮11111&fieldId=39b22ac71cb9490584f3fa4c5cf5b940&userGroupIds=66aaab6db70b434ca60a753ad3e2bbf9&roleIds=402881f46afe47db016afe562def0000
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
     *     "data": "{"button":"{"version":"1","id":"402881916b210d35016b2174dd750011","createdDate":"20190604154715","lastModifiedDate":"20190604154715","name":"修改按钮11111","level":"0","sort":"0","uri":""}","field":"{"version":"1","id":"39b22ac71cb9490584f3fa4c5cf5b940","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组2字段","level":"0","sort":"0"}","buttonRoles":["{"version":"0","id":"402881916b210d35016b2185db910014","createdDate":"20190604160549","lastModifiedDate":"20190604160549"}"]}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             String fieldId,
                             String[] userGroupIds,
                             String[] roleIds,
                             @RequestParam(defaultValue = "0") int sort,
                             @RequestParam(defaultValue = "0") int level) {
        Button button = buttonService.find(id);
        Field field = fieldService.findById(fieldId);
        if (button == null || field == null) {
            return JsonResult.notFound("按钮不存在或者所属字段不存在");
        }
        List<UserGroup> userGroups = userGroupService.findByIds(userGroupIds);
        List<Role> roles = roleService.findByRoleIds(roleIds);
        button.setName(StringUtils.isBlank(name) ? button.getName() : name);
        button.setField(field);
        button.setSort(sort);
        button.setLevel(level);
        button = buttonService.update(button, userGroups, roles);
        jsonResult.setData(convert(button));
        return jsonResult;
    }

    /**
     * @api {post} /button/del 删除
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                  /button/del?id=e7260b4718fa48618bb91859ff750099
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:按钮不存在</br>
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
        Button button = buttonService.find(id);
        if (button == null) {
            return JsonResult.notFound("按钮不存在");
        }
        buttonService.delete(button);
        return JsonResult.success();
    }

    /**
     * 按钮关系转换
     * @param button
     * @return
     */
    private String convert(Button button) {
        try {
            JSONObject json = new JSONObject();
            json.put("button", JsonUtils.getJson(button));
            json.put("field", JsonUtils.getJson(button.getField()));
            JSONArray arr = new JSONArray();
            for (ButtonRole br : button.getButtonRoles()) {
                arr.put(JsonUtils.getJson(br));
            }
            json.put("buttonRoles", arr);
            return JsonUtils.delString(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
