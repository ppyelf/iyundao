package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.DepartService;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserRelationService;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DepartController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:23
 * @Description: 控制层 - 部门
 * @Version: V1.0
 */
@RestController
@RequestMapping("/depart")
public class DepartController extends BaseController {

    @Autowired
    private DepartService departService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserRelationService userRelationService;

    /**
     * @api {POST} /depart/list 机构部门列表
     * @apiName list
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 部门列表
     * @apiParam {String} subjectId 机构id
     * @apiExample {json} 请求样例
     *                ?subjectId=bd6886bc88e54ef0a36472efd95c744c
     * @apiSuccess {int} code 200:成功</br>
     *                                 404:请添加部门</br>
     *                                 600:参数异常</br>
     * @apiSuccess {String} message 信息
     * @apiSuccess {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{'version':'1','id':'9b7678a607ef4199ad7a4018b892c49d','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'总-部门','depart':''}]"
     * }
     */
    @PostMapping("/list")
    public JsonResult list(String subjectId) {
        if (StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        }
        List<Depart> departs = departService.findBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(departs)) {
            return jsonResult.notFound("请添加部门");
        }
        JSONArray arr = new JSONArray();
        for (Depart d :departs) {
            JSONObject json = new JSONObject(JsonUtils.getJson(d));
            json.remove("user");
            json.remove("subject");
            arr.put(json);
        }
        jsonResult = JsonResult.success();
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {get} /depart/manager_list 部门管理列表
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 部门管理列表
     * @apiParamExample {json} 请求样例：
     *                /depart/manager_list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{"id":"66d09d0417604cb9a52bff07dee7f408","name":"分-部门","subject":"{"version":"1","id":"c72a2c6bd1e8428fac6706b217417831","lastModifiedDate":"20190517111111","createdDate":"20190517111111","name":"分院","subjectType":"part"}","user":"{"version":"0","id":"5cf0d3c3b0da4cbaad179e0d6d230d0c","lastModifiedDate":"20190517111111","createdDate":"20190517111111","name":"测试用户","status":"normal","password":"b356a1a11a067620275401a5a3de04300bf0c47267071e06","account":"test","remark":"未填写","salt":"3a10624a300f4670","sex":"0","userType":"normal"}"},{"id":"9b7678a607ef4199ad7a4018b892c49d","name":"总-部门","subject":"{"version":"1","id":"bd6886bc88e54ef0a36472efd95c744c","lastModifiedDate":"20190517111111","createdDate":"20190517111111","name":"总院","subjectType":"head"}","father":"{"version":"1","id":"66d09d0417604cb9a52bff07dee7f408","lastModifiedDate":"20190517111111","createdDate":"20190517111111","name":"分-部门","subject":"java.lang.String@2276b840[version=1,id=c72a2c6bd1e8428fac6706b217417831,lastModifiedDate=20190517111111,createdDate=20190517111111,name=分院,subjectType=part]","user":"java.lang.String@2f66960[version=0,id=5cf0d3c3b0da4cbaad179e0d6d230d0c,lastModifiedDate=20190517111111,createdDate=20190517111111,name=测试用户,status=normal,password=b356a1a11a067620275401a5a3de04300bf0c47267071e06,account=test,remark=未填写,salt=3a10624a300f4670,sex=0,userType=normal]","father":""}","user":"{"version":"0","id":"0a4179fc06cb49e3ac0db7bcc8cf0882","lastModifiedDate":"20190517111111","createdDate":"20190517111111","name":"管理员","status":"normal","password":"b356a1a11a067620275401a5a3de04300bf0c47267071e06","account":"admin","remark":"未填写","salt":"3a10624a300f4670","sex":"0","userType":"amdin"}"}]"
     * }
     */
    @GetMapping("/manager_list")
    public JsonResult managerList() {
        List<Depart> departs = departService.getList();
            com.alibaba.fastjson.JSONArray arr = new com.alibaba.fastjson.JSONArray();
            for (Depart depart : departs) {
                com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
                json.put("id", depart.getId());
                json.put("name", depart.getName());
                arr.add(json);
            }
            jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /depart/view 查看部门信息
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 查看部门信息
     * @apiParamExample {json} 请求样例：
     *                /depart/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"1","id":"66d09d0417604cb9a52bff07dee7f408","lastModifiedDate":"20190517111111","createdDate":"20190517111111","name":"分-部门","subject":"java.lang.String@726f3e8c[version=1,id=c72a2c6bd1e8428fac6706b217417831,lastModifiedDate=20190517111111,createdDate=20190517111111,name=分院,subjectType=part]","user":"java.lang.String@30193032[version=0,id=5cf0d3c3b0da4cbaad179e0d6d230d0c,lastModifiedDate=20190517111111,createdDate=20190517111111,name=测试用户,status=normal,password=b356a1a11a067620275401a5a3de04300bf0c47267071e06,account=test,remark=未填写,salt=3a10624a300f4670,sex=0,userType=normal]","father":""}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Depart depart = departService.findById(id);
        if (depart == null) {
            return JsonResult.notFound("未查询到此用户组");
        }
        try {
            JSONObject json = new JSONObject();
            json.put("id", depart.getId());
            json.put("name", depart.getName());
            json.put("father", depart.getFather());
            json.put("user", depart.getUser());
            json.put("subject", depart.getSubject());
//            jsonFather(userGroup, json);
            jsonResult.setData(convertJson(depart));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * @api {post} /depart/add 新增部门
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 新增部门
     * @apiParamExample {json} 请求样例：
     *                /depart/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name, String fatherId, String userId, String subjectId) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        } 
        Depart depart = new Depart();
        depart.setName(name);
        depart.setLastModifiedDate(new Date(System.currentTimeMillis()));
        depart.setCreatedDate(new Date(System.currentTimeMillis()));
        Subject subject = subjectService.find(subjectId);
        if (subject == null) {
            return JsonResult.notFound("机构参数异常");
        }
        depart.setSubject(subject);
        if (StringUtils.isNotBlank(fatherId)) {
            Depart father = departService.findById(fatherId);
            depart.setFather(father == null ? null : father);
        }
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            depart.setUser(user == null ? null : user);
        }
        depart = departService.save(depart);
        jsonResult.setData(convertJson(depart));
        return jsonResult;
    }

    /**
     * @api {post} /depart/modify 修改部门信息
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 修改部门信息
     * @apiParamExample {json} 请求样例：
     *                /depart/modify
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
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id, String name, String fatherId, String userId, String subjectId) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Depart depart = departService.findById(id);
        if (depart == null) {
            return JsonResult.notFound("未查询到此用户组");
        }
        depart.setLastModifiedDate(new Date(System.currentTimeMillis()));
        depart.setName(name);
        if (StringUtils.isNotBlank(subjectId)) {
            Subject subject = subjectService.find(subjectId);
            if (subject == null) {
                return JsonResult.notFound("此机构不存在");
            }
            depart.setSubject(subject);
        }
        if (StringUtils.isNotBlank(fatherId)) {
            Depart father = departService.findById(fatherId);
            depart.setFather(father);
        }
        if (StringUtils.isNotBlank(userId)) {
            User user = userService.findById(userId);
            depart.setUser(user);
        }
        depart = departService.save(depart);
        jsonResult.setData(convertJson(depart));
        return jsonResult;
    }
    
    private String convertJson(Depart depart) {
        try {
            JSONObject json = new JSONObject(JsonUtils.getJson(depart));
            json.put("id", depart.getId());
            json.put("name", depart.getName());
            json.put("subject", JsonUtils.getJson(depart.getSubject()));
            json.put("father", depart.getFather() == null ? null : setFather(depart.getFather()));
            json.put("user", depart.getUser() == null ? null : JsonUtils.getJson(depart.getUser()));
            return JsonUtils.delString(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @api {post} /depart/add_user 添加成员
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 添加成员
     * @apiParamExample {json} 请求样例：
     *                /depart/add_user
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:部门不存在</br>
     *                                 601:用户ID异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"402881f46afe9429016afea8c2570001","createdDate":"20190528213713","lastModifiedDate":"20190528213713","name":"添加部门"}"
     * }
     */
    @PostMapping("/add_user")
    public JsonResult addUser(String id,
                              String[] userIds) {
        Depart depart = departService.findById(id);
        if (depart == null) {
            return jsonResult.notFound("部门不存在");
        }
        List<User> users = userService.findByIds(userIds);
        if (CollectionUtils.isEmpty(users)) {
            return JsonResult.failure(601, "用户ID异常");
        }
        depart = departService.saveDepartUsers(depart, users);
        jsonResult.setData(JsonUtils.getJson(depart));
        return jsonResult;
    }

    /**
     * 设置父级关系
     * @param depart
     * @return
     */
    private String setFather(Depart depart) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", depart.getId());
            json.put("name", depart.getName());
            return JsonUtils.delString(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
