package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserRelationService;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: SubjectController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/19 18:24
 * @Description: 控制层 - 机构
 * @Version: V1.0
 */
@RestController
@RequestMapping("/subject")
public class SubjectController extends BaseController {

    @Autowired
    private UserRelationService userRelationService;


    @Autowired
    private SubjectService subjectService;

    /**
     * @api {POST} /subject/list 机构列表
     * @apiGroup Subject
     * @apiVersion 1.0.0
     * @apiDescription 机构列表
     * @apiParamExample {json} 请求样例：
     *                /subject/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:请添加机构</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": "['{'version':'1','id':'bd6886bc88e54ef0a36472efd95c744c','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'总院','subjectType':'head'}','{'version':'1','id':'c72a2c6bd1e8428fac6706b217417831','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'分院','subjectType':'head'}']"
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Subject> subjects = subjectService.findAll();
        if (CollectionUtils.isEmpty(subjects)) {
            jsonResult.setCode(404);
            jsonResult.setMessage("请添加机构");
            return jsonResult;
        }
        JSONArray arr = new JSONArray();
        for (Subject s :subjects){
            s.setUserRelations(null);
            arr.put(JsonUtils.getJson(s));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {get} /user_group/list 列表
     * @apiGroup Subject
     * @apiVersion 1.0.0
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
     *     "data": "[{"id":"bd6886bc88e54ef0a36472efd95c744c","name":"总院","type":"总院"},{"id":"c72a2c6bd1e8428fac6706b217417831","name":"分院","type":"分院"}]"
     * }
     */
    @GetMapping("/manager_list")
    public JsonResult managerList() {
        List<Subject> subjects = subjectService.findAll();
        try {
            JSONArray arr = new JSONArray();
            for (Subject subject : subjects) {
                JSONObject json =new JSONObject();
                json.put("id", subject.getId());
                json.put("name", subject.getName());
                switch (subject.getSubjectType().ordinal()) {
                    case  0:
                        json.put("type", "总院");
                         break;
                    case  1:
                        json.put("type", "分院");
                         break;
                    case  2:
                        json.put("type", "其他");
                         break;
                }
                arr.put(json);
            }
            jsonResult.setData(arr.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * @api {post} /subject/view 查看机构
     * @apiGroup Subject
     * @apiVersion 1.0.0
     * @apiDescription 查看机构
     * @apiParamExample {json} 请求样例：
     *                /subject/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "[{'id':'66aaab6db70b434ca60a753ad3e2bbf9','name':'小组2'},{'id':'813f15af1b1c402da17838e8a067ed68','name':'子小组','father':{'id':'66aaab6db70b434ca60a753ad3e2bbf9','name':'小组2'}},{'id':'8b568d0307c744cb9fedc2fe5b7f1238','name':'小组1'}]"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Subject subject = subjectService.findById(id);
        if (subject == null) {
            return JsonResult.notFound("未查询到此机构");
        }
        try {
            JSONObject json = new JSONObject();
            json.put("id", subject.getId());
            json.put("name", subject.getName());
            jsonResult.setData(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * @api {post} /subject/add 新增机构
     * @apiGroup Subject
     * @apiVersion 1.0.0
     * @apiDescription 新增机构
     * @apiParamExample {json} 请求样例：
     *                /subject/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
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
    @PostMapping("/add")
    public JsonResult add(String name, @RequestParam(defaultValue = "0") int type) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setLastModifiedDate(new Date(System.currentTimeMillis()));
        subject.setCreatedDate(new Date(System.currentTimeMillis()));
        switch (type) {
            case 0 :
                 subject.setSubjectType(Subject.SUBJECT_TYPE.head);
                 break;
            case 1 :
                 subject.setSubjectType(Subject.SUBJECT_TYPE.part);
                 break;
            case 2 :
                 subject.setSubjectType(Subject.SUBJECT_TYPE.etc);
                 break;
            default :
                 subject.setSubjectType(Subject.SUBJECT_TYPE.head);
                 break;
        }
        subject = subjectService.save(subject);
        jsonResult.setData(converType(subject));
        return jsonResult;
    }

    /**
     * @api {post} /subject/modify 修改机构
     * @apiGroup Subject
     * @apiVersion 1.0.0
     * @apiDescription 修改机构
     * @apiParamExample {json} 请求样例：
     *                /subject/modify
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"createdDate\":\"20190528191706\",\"lastModifiedDate\":\"20190528193528\",\"name\":\"修改机构\",\"subjectType\":\"其他\"}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id, String name, @RequestParam(defaultValue = "3") int type) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        Subject subject = subjectService.findById(id);
        if (subject == null) {
            return JsonResult.notFound("未查询到此机构");
        }
        subject.setLastModifiedDate(new Date(System.currentTimeMillis()));
        subject.setName(name);
        if (type != 3) {
            for (Subject.SUBJECT_TYPE subjectType : Subject.SUBJECT_TYPE.values()) {
                if (subjectType.ordinal() == type) {
                    subject.setSubjectType(subjectType);
                    break;
                } 
            }
        } 
        subject = subjectService.save(subject);
        jsonResult.setData(converType(subject));
        return jsonResult;
    }

    /**
     * 转换type为相应json数据
     * @param subject
     * @return
     */
    private String converType(Subject subject) {
        try {
            JSONObject json = new JSONObject(JsonUtils.getJson(subject));
            switch (subject.getSubjectType().ordinal()) {
                case 0 :
                    json.put("subjectType", "总院");
                    break;
                case 1 :
                    json.put("subjectType", "分院");
                    break;
                case 2 :
                    json.put("subjectType", "其他");
                    break;
                default :
                    json.put("subjectType", "总院");
                    break;
            }
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonUtils.getJson(subject);
    }

}
