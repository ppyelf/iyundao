package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.BaseEntity;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Depart;
import com.ayundao.entity.Subject;
import com.ayundao.entity.User;
import com.ayundao.entity.UserGroup;
import com.ayundao.service.DepartService;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserService;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.util.SetOnce;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    /**
     * @api {POST} /depart/list 机构部门列表
     * @apiName list
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 部门列表
     * @apiParam {String} subjectId 机构id
     * @apiParam {int} type 是否只选择父级部门(默认:0-不选择)
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
    public JsonResult list(String subjectId,
                           @RequestParam(defaultValue = "0") int type) {
        if (StringUtils.isBlank(subjectId)) {
            return JsonResult.paramError();
        }
        List<Depart> departs = type != 0
                ? departService.findBySubjectIdAndFatherIsNull(subjectId)
                : departService.findBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(departs)) {
            return jsonResult.notFound("请添加部门");
        }
        JSONArray arr = new JSONArray();
        for (Depart d :departs) {
            arr.add(convertJson(d));
        }
        jsonResult = JsonResult.success();
        jsonResult.setData(arr);
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
        List<Depart> departs = departService.getListByFatherIdIsNull();
            com.alibaba.fastjson.JSONArray arr = new com.alibaba.fastjson.JSONArray();
            for (Depart depart : departs) {
                arr.add(convertJson(depart));
            }
            jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /depart/view 查看部门信息
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 查看部门信息
     * @apiParam {String} id
     * @apiParamExample {json} 请求样例：
     *                ?id=66d09d0417604cb9a52bff07dee7f408
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190517111111","lastModifiedDate": "20190517111111","subject": {"createdDate": "20190517111111","lastModifiedDate": "20190517111111","name": "分院","id": "c72a2c6bd1e8428fac6706b217417831","version": "1","subjectType": "part"},"father": null,"name": "分-部门","id": "66d09d0417604cb9a52bff07dee7f408","version": "1","user": {"password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06","createdDate": "20190517111111","salt": "3a10624a300f4670","lastModifiedDate": "20190517111111","sex": "0","name": "测试用户","remark": "未填写","id": "5cf0d3c3b0da4cbaad179e0d6d230d0c","userType": "normal","version": "0","account": "test","status": "normal"}}
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
        JSONObject json = new JSONObject();
        json.put("id", depart.getId());
        json.put("name", depart.getName());
        json.put("father", depart.getFather());
        json.put("user", depart.getUser());
        json.put("subject", depart.getSubject());
        jsonResult.setData(convertJson(depart));
        return jsonResult;
    }

    /**
     * @api {post} /depart/add 新增部门
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 新增部门
     * @apiParam {String} name
     * @apiParam {String} fatherId
     * @apiParam {String} userId
     * @apiParam {String} subjectId
     * @apiParamExample {json} 请求样例：
     *                ?name=添加部门11&subjectId=402881f46afdef14016afe28796c000b
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
     *     "data": {"version":"0","id":"402881f46afe9429016afeaf39e30006","lastModifiedDate":"20190528214417","createdDate":"20190528214417","name":"添加部门11","subject":{"version":"1","id":"402881f46afdef14016afe28796c000b","lastModifiedDate":"20190528193528","createdDate":"20190528191706","name":"修改机构","subjectType":"etc"}}
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          String fatherId,
                          String userId,
                          String subjectId) {
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
     * @apiParam {String} id
     * @apiParam {String} name
     * @apiParam {String} fatherId
     * @apiParam {String} userId
     * @apiParam {String} subjectId
     * @apiParamExample {json} 请求样例：
     *                ?id=402881f46afdef14016afdf286170001&name=测试用户组2
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:此部门不存在</br>
     *                                 600:参数异常</br>
     *                                 601:此机构不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190528213713","lastModifiedDate": "20190618095208","subject": {    "createdDate": "20190528191706",    "lastModifiedDate": "20190528193528",    "name": "修改机构",    "id": "402881f46afdef14016afe28796c000b",    "version": "1",    "subjectType": "etc"},"father": {    "name": "总-部门",    "id": "9b7678a607ef4199ad7a4018b892c49d"},"name": "测试修改","id": "402881f46afe9429016afea8c2570001","version": "2","user": {    "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",    "createdDate": "20190517111111",    "salt": "3a10624a300f4670",    "lastModifiedDate": "20190517111111",    "sex": "0",    "name": "管理员",    "remark": "未填写",    "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",    "userType": "admin",    "version": "0",    "account": "admin",    "status": "normal"}}
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             String fatherId,
                             String userId,
                             String subjectId) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Depart depart = departService.findById(id);
        if (depart == null) {
            return JsonResult.notFound("此部门不存在");
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

    /**
     * @api {post} /depart/childs 获取子集部门
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 获取子集部门
     * @apiParam {String} id
     * @apiParamExample {json} 请求样例：
     *                ?id=9b7678a607ef4199ad7a4018b892c49d
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "createdDate": "20190619100709",    "lastModifiedDate": "20190620110819",    "subject": {        "createdDate": "20190620095534",        "lastModifiedDate": "20190620095920",        "name": "测试添加",        "id": "402881916b726258016b7298a5bf0006",        "version": "1",        "subjectType": "etc"    },    "name": "添加部门2",    "id": "402881916b6d5385016b6d7ce3d30000",    "version": "2",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "createdDate": "20190517111111",        "salt": "3a10624a300f4670",        "lastModifiedDate": "20190517111111",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "version": "0",        "account": "admin",        "status": "normal"    }},{    "createdDate": "20190619102419",    "lastModifiedDate": "20190619102419",    "subject": {        "createdDate": "20190528191706",        "lastModifiedDate": "20190528193528",        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "version": "1",        "subjectType": "etc"    },    "name": "添加部门2",    "id": "402881916b6d5385016b6d8c9b3a0001",    "version": "0",    "user": null},{    "createdDate": "20190619102500",    "lastModifiedDate": "20190619102500",    "subject": {        "createdDate": "20190528191706",        "lastModifiedDate": "20190528193528",        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "version": "1",        "subjectType": "etc"    },    "name": "添加部门2",    "id": "402881916b6d5385016b6d8d3c1d0002",    "version": "0",    "user": null},{    "createdDate": "20190528213713",    "lastModifiedDate": "20190618095208",    "subject": {        "createdDate": "20190528191706",        "lastModifiedDate": "20190528193528",        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "version": "1",        "subjectType": "etc"    },    "name": "测试修改",    "id": "402881f46afe9429016afea8c2570001",    "version": "2",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "createdDate": "20190517111111",        "salt": "3a10624a300f4670",        "lastModifiedDate": "20190517111111",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "version": "0",        "account": "admin",        "status": "normal"    },    "childs": [        {            "createdDate": "20190620114515",            "lastModifiedDate": "20190620114515",            "name": "测试部门",            "id": "402881916b726258016b72fd0df00015",            "version": "0"        }    ]}
     *     ]
     * }
     */
    @PostMapping("/childs")
    public JsonResult child(String id) {
        List<Depart> departs = departService.findByFatherId(id);
        JSONArray arr = new JSONArray();
        for (Depart depart : departs) {
            arr.add(convertJson(depart));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {post} /depart/all 获取所有部门
     * @apiGroup Depart
     * @apiVersion 1.0.0
     * @apiDescription 获取所有部门
     * @apiParamExample {json} 请求样例：
     *                /depart/all
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "createdDate": "20190619100709",    "lastModifiedDate": "20190620110819",    "subject": {        "createdDate": "20190620095534",        "lastModifiedDate": "20190620095920",        "name": "测试添加",        "id": "402881916b726258016b7298a5bf0006",        "version": "1",        "subjectType": "etc"    },    "name": "添加部门2",    "id": "402881916b6d5385016b6d7ce3d30000",    "version": "2",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "createdDate": "20190517111111",        "salt": "3a10624a300f4670",        "lastModifiedDate": "20190517111111",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "version": "0",        "account": "admin",        "status": "normal"    }},{    "createdDate": "20190619102419",    "lastModifiedDate": "20190619102419",    "subject": {        "createdDate": "20190528191706",        "lastModifiedDate": "20190528193528",        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "version": "1",        "subjectType": "etc"    },    "name": "添加部门2",    "id": "402881916b6d5385016b6d8c9b3a0001",    "version": "0",    "user": null},{    "createdDate": "20190619102500",    "lastModifiedDate": "20190619102500",    "subject": {        "createdDate": "20190528191706",        "lastModifiedDate": "20190528193528",        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "version": "1",        "subjectType": "etc"    },    "name": "添加部门2",    "id": "402881916b6d5385016b6d8d3c1d0002",    "version": "0",    "user": null},{    "createdDate": "20190528213713",    "lastModifiedDate": "20190618095208",    "subject": {        "createdDate": "20190528191706",        "lastModifiedDate": "20190528193528",        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "version": "1",        "subjectType": "etc"    },    "name": "测试修改",    "id": "402881f46afe9429016afea8c2570001",    "version": "2",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "createdDate": "20190517111111",        "salt": "3a10624a300f4670",        "lastModifiedDate": "20190517111111",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "version": "0",        "account": "admin",        "status": "normal"    },    "childs": [        {            "createdDate": "20190620114515",            "lastModifiedDate": "20190620114515",            "name": "测试部门",            "id": "402881916b726258016b72fd0df00015",            "version": "0"        }    ]}
     *     ]
     * }
     */
    @PostMapping("/all")
    public JsonResult all(String id) {
        List<Depart> departs = departService.getList();
        JSONArray arr = new JSONArray();
        for (Depart depart : departs) {
            arr.add(convertJson(depart));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }
    
    private JSONObject convertJson(Depart depart) {
        JSONObject json = new JSONObject(JsonUtils.getJson(depart));
        json.put("subject", JsonUtils.getJson(depart.getSubject()));
        json.put("user", depart.getUser() == null ? null : JsonUtils.getJson(depart.getUser()));
        return json;
    }

}
