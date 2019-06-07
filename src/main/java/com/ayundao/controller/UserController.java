package com.ayundao.controller;


import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.UserService;
import com.ayundao.service.impl.ActivityService;
import com.ayundao.service.impl.AssessmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @ClassName: UserController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户
 * @Version: V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AssessmentService assessmentService;

    /**
     * @api {POST} /user/search 用户搜索
     * @apiName search
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 用户搜索
     * @apiParam {String} key 搜索条件
     * @apiParam {int} page 页数(默认:1)
     * @apiParam {int} size 长度(默认:10)
     * @apiParamExample {json} 请求样例
     *                ?key=张三&page=1&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              404:不存在此用户</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{'pages':1,'elements':1,'entity':[{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'管理员','status':'normal','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','remark':'未填写','sex':'0','salt':'3a10624a300f4670','userType':'amdin','account':'admin'}]}"
     * }
     */
    @PostMapping("/search")
    public JsonResult search(String key,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        if (StringUtils.isBlank(key)) {
            return JsonResult.paramError();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.findByKey(key, pageable);
        if (userPage.getTotalElements() == 0) {
            return JsonResult.notFound("不存在此用户");
        }
        jsonResult.setData(JsonUtils.toJsonPage(userPage));
        return jsonResult;
    }

    /**
     * @api {POST} /user/search 新建用户
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 新建用户
     * @apiParam {String} key 搜索条件
     * @apiParam {int} page 页数(默认:1)
     * @apiParam {int} size 长度(默认:10)
     * @apiParamExample {json} 请求样例：
     *                /user/search?key=张三&page=1&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "登录成功",
     * 	"data": "{}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String account,
                          String name,
                          int sex,
                          int userType,
                          String subjectId,
                          String departId,
                          String groupsId,
                          String remark) {
        User user = new User();
        user.setAccount(account);
        user.setName(name);
        user.setSex(sex);
        for (User.USER_TYPE type : User.USER_TYPE.values()) {
            if (type.ordinal() == userType) {
                user.setUserType(type);
                break;
            } 
        }
        user.setRemark(remark);
        userService.save(user, subjectId, departId, groupsId);
        return jsonResult;
    }

    /**
     * @api {get} /user/del 删除用户
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @GetMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userService.delete(id);
        return jsonResult;
    }

    /**
     * @api {get} /user/view 查看用户
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id=0a4179fc06cb49e3ac0db7bcc8cf0882
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','lastModifiedDate':'20190517111111','createdDate':'20190517111111','name':'管理员','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','status':'normal','sex':'0','remark':'未填写','salt':'3a10624a300f4670','account':'admin','userType':'amdin'}"
     * }
     */
    @GetMapping("/view")
    public JsonResult view(String id) {
        User user = userService.findById(id);
        if (user == null) return JsonResult.paramError();
        jsonResult.setData(JsonUtils.getJson(user));
        return jsonResult;
    }

    /**
     * @api {get} /user/list 用户分页
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParamExample {json} 请求样例
     *                ?page
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{'total':3,'content':[{'id':'0a4179fc06cb49e3ac0db7bcc8cf0882','account':'admin','sex':'男','userType':'管理员','status':'正常','createdTime':'20190517111111','relation':['总院-分-部门-无','分院-总-部门-无'],'remark':'未填写'},{'id':'5cf0d3c3b0da4cbaad179e0d6d230d0c','account':'test','sex':'男','userType':'普通用户','status':'正常','createdTime':'20190517111111','relation':['总院-总-部门-无'],'remark':'未填写'},{'id':'cd22e3407ace4d86bac92f92b9e9dd3e','account':'user','sex':'男','userType':'普通用户','status':'正常','createdTime':'20190517111111','relation':[],'remark':'未填写'}]}"
     * }
     */
    @GetMapping("/list")
    public JsonResult list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pages = userService.findAllForPage(pageable);
        JSONObject pageJson = new JSONObject();
        JSONArray pageArray = new JSONArray();
        try {
            pageJson.put("total", pages.getTotalElements());
            for (User user : pages.getContent()) {
                JSONObject json = new JSONObject();
                json.put("id", user.getId());
                json.put("account", user.getAccount());
                json.put("sex", user.getSex() == 0 ? "男": "女");
                switch (user.getUserType().ordinal()) {
                    case  0:
                        json.put("userType", "普通用户");
                        break;
                    case  1:
                        json.put("userType", "管理员");
                        break;
                    case  2:
                        json.put("userType", "负责人");
                        break;
                }
                switch (user.getStatus().ordinal()) {
                    case  0:
                        json.put("status", "禁用");
                         break;
                    case  1:
                        json.put("status", "锁定");
                         break;
                    case  2:
                        json.put("status", "正常");
                         break;
                }
                json.put("createdTime", user.getCreatedDate());
                JSONArray arr = new JSONArray();
                for (UserRelation relation : getUserRelation(user)) {
                    String s = relation.getSubject() == null ? "无" : relation.getSubject().getName();
                    String d = relation.getDepart() == null ? "无" : relation.getDepart().getName();
                    String g = relation.getGroups() == null ? "无" : relation.getGroups().getName();
                    arr.put(s + "-" + d + "-" + g);
                }
                json.put("relation", arr);
                json.put("remark", user.getRemark());
                pageArray.put(json);
            }
            pageJson.put("content", pageArray);
            jsonResult.setData(JsonUtils.delString(pageJson.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * @api {POST} /user/sign 用户签到
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} userId 用户ID
     * @apiParam {int} type 签到类型
     * @apiParam {String} activityId 活动ID
     * @apiParam {String} singTime 签到时间
     * @apiParam {String} axisx 坐标x
     * @apiParam {String} axisy 坐标y
     * @apiParamExample {json} 请求样例
     *                /user/sign?activityId=402881916b2a3187016b2a3247350002&userId=0a4179fc06cb49e3ac0db7bcc8cf0882&singTime=now&axisx=123&axisy=456
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              601:用户不存在</br>
     *                              602:活动不存在</br>
     *                              603:签到类型不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b32f12a016b33962eb90012","createdDate":"20190608041648","lastModifiedDate":"20190608041648","userId":"0a4179fc06cb49e3ac0db7bcc8cf0882","axisx":"123","axisy":"456","info1":"","info3":"","info4":"","info5":"","info2":"","signTime":"now","signType":"normal"}"
     * }
     */
    @PostMapping("/sign")
    public JsonResult sign(String userId,
                           @RequestParam(defaultValue = "0") int type,
                           String activityId,
                           String singTime,
                           String axisx,
                           String axisy) {
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.failure(601, "用户不存在");
        }
        Activity activity = activityService.find(activityId);
        if (activity == null) {
            return JsonResult.failure(602, "活动不存在");
        }
        Sign s = new Sign();
        s.setCreatedDate(new Date());
        s.setLastModifiedDate(new Date());
        s.setSignTime(singTime);
        s.setAxisx(axisx);
        s.setAxisy(axisy);
        s.setUserId(userId);
        s.setActivity(activity);
        for (Sign.SIGN_TYPE st : Sign.SIGN_TYPE.values()) {
            if (st.ordinal() == type) {
                s.setSignType(st);
                break;
            } 
        }
        if (s.getSignType() == null) {
            return JsonResult.failure(603, "签到类型不能为空");
        }
        s = activityService.saveUserSign(s);
        jsonResult.setData(JsonUtils.getJson(s));
        return jsonResult;
    }

    /**
     * @api {POST} /user/add_index 添加用户指标
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiDescription 添加用户指标
     * @apiParam {String} id 用户ID
     * @apiParam {String} assessmentId 考核项目ID
     * @apiParam {String} assessmentIndexId 考核指标ID
     * @apiParam {int} score 分数
     * @apiParamExample {json} 请求样例
     *                /user/add_index?userId=0a4179fc06cb49e3ac0db7bcc8cf0882&assessmentId=2c92eb816b32f12a016b33a555170013&assessmentIndexId=2c92eb816b32f12a016b33b399950017
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              601:用户不存在</br>
     *                              602:考核不存在</br>
     *                              603:考核指标不存在</br>
     *                              604:用户不属于该项目考核范围</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b32f12a016b33b497400018","createdDate":"20190608045001","lastModifiedDate":"20190608045001","userId":"0a4179fc06cb49e3ac0db7bcc8cf0882","info1":"","info3":"","info4":"","info5":"","info2":"","assessmentId":"2c92eb816b32f12a016b33a555170013","score":"0","finishTime":"20190608045001","assessmentIndexId":"2c92eb816b32f12a016b33b399950017"}"
     * }
     */
    @PostMapping("/add_index")
    public JsonResult addIndex(String userId,
                               String assessmentId,
                               String assessmentIndexId,
                               @RequestParam(defaultValue = "0") int score) {
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.failure(601, "用户不存在");
        }
        Assessment assessment = assessmentService.find(assessmentId);
        if (assessment == null) {
            return JsonResult.failure(602, "考核不存在");
        }
        AssessmentIndex index = assessmentService.findIndexById(assessmentIndexId);
        if (index == null) {
            return JsonResult.failure(603, "考核指标不存在");
        } 
        AssessmentRange ar = assessmentService.findByAssessmentIdAndUserId(userId, assessmentId);
        if (ar == null) {
            return JsonResult.failure(604, "用户不属于该项目考核范围");
        }
        UserIndex ui = new UserIndex();
        ui.setCreatedDate(new Date());
        ui.setLastModifiedDate(new Date());
        ui.setAssessmentId(assessmentId);
        ui.setAssessmentIndexId(assessmentIndexId);
        ui.setUserId(userId);
        ui.setScore(score);
        ui.setFinishTime(new Date());
        ui = assessmentService.saveUserIndex(ui);
        jsonResult.setData(JsonUtils.getJson(ui));
        return jsonResult;
    }

}
