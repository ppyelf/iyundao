package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.User;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoMzdp;
import com.ayundao.service.UserInfoPowerService;
import com.ayundao.service.UserInfoService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ayundao.base.BaseController.ROLE_ADMIN;
import static com.ayundao.base.BaseController.ROLE_MANAGER;
import static com.ayundao.base.BaseController.ROLE_USER;

/**
 * @ClassName: UserInfoMzdpController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -民主党派信息
 * @Version: V1.0
 */
@RequiresUser
@RequiresRoles(value = {ROLE_ADMIN, ROLE_USER, ROLE_MANAGER}, logical = Logical.OR)
@RestController
@RequestMapping("/userInfoMzdp")
public class UserInfoMzdpController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoPowerService userInfoPowerService;

    /**
     * @api {post} /userInfoMzdp/add_mzdp 新增用户民主党派基础信息
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 新增用户民主党派基础信息
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} democraticparties 必选 民主党派
     * @apiParam {String} time 时间
     * @apiParam {String} partyPost 党内职务
     * @apiParam {String} userinfoid 用户详情Id
     * @apiParamExample {json} 请求样例
     *        ?democraticparties= &time= &partyPost=&userinfoid=
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userInfoMzdp": {
     *             "id": "297e47e36b8d653c016b8d65e6b30000",
     *             "democraticparties": "MINZHUJIANGUOHUI",
     *             "time": "2010-11-11",
     *             "partyPost": "主席",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/add_mzdp")
    public JsonResult add_mzdp(int democraticparties,String time,
                               String partyPost,String userinfoid) {
        UserInfoMzdp userInfoMzdp = new UserInfoMzdp();
        userInfoMzdp.setCreatedDate(new Date());
        userInfoMzdp.setLastModifiedDate(new Date());
        for (UserInfoMzdp.DEMOCRATICPARTIES democraticparties1 : UserInfoMzdp.DEMOCRATICPARTIES.values()) {
            if(democraticparties1.ordinal() == democraticparties){
                userInfoMzdp.setDemocraticparties(democraticparties1);
                break;
            }
        }
        userInfoMzdp.setTime(time);
        userInfoMzdp.setPartyPost(partyPost);
        userInfoMzdp.setUserinfoid(userinfoid);
        return userInfoService.saveMzdp(userInfoMzdp,jsonResult);
    }

    /**
     * @api {post} /userInfoMzdp/del 删除用户详情 -民主党派信息
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 用户详情 -民主党派信息ID
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
    @RequiresPermissions(PERMISSION_DELETE)
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteMzdp(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoMzdp/list 用户详情 -民主党派信息
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 民主党派信息
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求样例
     *                /userInfoMzdp/list
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data":{...}
     *  }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/list")
    public JsonResult list(){
        List<UserInfo> pages = userInfoService.findAll();
        List<UserInfoMzdp> pages1 = userInfoService.findAllByMzdp();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoMzdp userInfoMzdp : pages1) {
                if (userInfo.getId().equals(userInfoMzdp.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoMzdp));
                    JSONObject json = new JSONObject();
                    json.putAll(json1);
                    json.putAll(json2);
                    pageArray.add(json);
                }
            }
        }

        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfoMzdp/listDepart 用户分页 -根据组织查询
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} departId 组织id
     * @apiParamExample {json} 请求样例
     *                /userInfoMzdp/listDepart
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/listDepart")
    public JsonResult listDepart(String departId){
        List<User> pages = userService.findByDepartIdForPage(departId);
        List<UserInfo> pages1 = userInfoService.findAll();
        List<UserInfoMzdp> pages2 = userInfoService.findAllByMzdp();
        JSONArray pageArray = new JSONArray();
        for (User user : pages) {

            for (UserInfo userInfo : pages1) {
                for (UserInfoMzdp userInfoMzdp : pages2) {
                    if (user.getId().equals(userInfo.getUserid())) {
                        if (userInfo.getId().equals(userInfoMzdp.getUserinfoid())) {
                            JSONObject json1 = new JSONObject(JsonUtils.getJson(user));
                            JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfo));
                            JSONObject json3 = new JSONObject(JsonUtils.getJson(userInfoMzdp));
                            JSONObject json = new JSONObject();
                            json.putAll(json1);
                            json.putAll(json2);
                            json.putAll(json3);
                            pageArray.add(json);
                        }
                    }
                }
            }
        }

        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfoMzdp/listGroupId 用户分页 -根据部门查询
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} groupId 部门id
     * @apiParamExample {json} 请求样例
     *                /userInfoMzdp/listDepart
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/listGroupId")
    public JsonResult listGroupId(String groupId){
        List<User> pages = userService.findByGroupIdForPage(groupId);
        List<UserInfo> pages1 = userInfoService.findAll();
        List<UserInfoMzdp> pages2 = userInfoService.findAllByMzdp();
        JSONArray pageArray = new JSONArray();
        for (User user : pages) {

            for (UserInfo userInfo : pages1) {
                for (UserInfoMzdp userInfoMzdp : pages2) {
                    if (user.getId().equals(userInfo.getUserid())) {
                        if (userInfo.getId().equals(userInfoMzdp.getUserinfoid())) {
                            JSONObject json1 = new JSONObject(JsonUtils.getJson(user));
                            JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfo));
                            JSONObject json3 = new JSONObject(JsonUtils.getJson(userInfoMzdp));
                            JSONObject json = new JSONObject();
                            json.putAll(json1);
                            json.putAll(json2);
                            json.putAll(json3);
                            pageArray.add(json);
                        }
                    }
                }
            }
        }


        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfoMzdp/findByLike 用户条件查询
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 用户条件查询
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} name 姓名
     * @apiParam {String} number 编号
     * @apiParam {String} department 科室
     * @apiParamExample {json} 请求样例
     *                /userInfoMzdp/findByLike?name= (or number or department)
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "birthday": "1984-11-11",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "education": "本科",
     *             "nation": "汉族",
     *             "title": "主治医师",
     *             "userid": "297e47e36b8cbecd016b8cbf239b0000",
     *             "number": "002",
     *             "idEntity": "干部",
     *             "workDate": "2004-11-11",
     *             "post": "主席",
     *             "id": "297e47e36b8d58dd016b8d5eac8d0001",
     *             "place": "浙江杭州",
     *             "department": "内科",
     *             "lastModifiedDate": "20190625144200",
     *             "sex": "男",
     *             "branchName": "第一党支部",
     *             "correctionDate": "2007-11-11",
     *             "version": "0",
     *             "partyDate": "2004-11-11",
     *             "createdDate": "20190625144200",
     *             "phone": "19822222222",
     *             "idcard": "315247198811111811",
     *             "name": "测试1",
     *             "info1": "",
     *             "time": "2010-11-11",
     *             "info5": "",
     *             "info4": "",
     *             "info3": "",
     *             "info2": ""
     *         }
     *     ]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/findByLike")
    public JsonResult findByLike(String name,String number,String department){
        String s = "%" + name + "%";
        String s1 = "%" + number + "%";
        String s2 = "%" + department + "%";
        List<UserInfo> pages1  =userInfoService.findByNameOrNumberOrDepartmentLike(s,s1,s2);
        List<UserInfoMzdp> pages2 = userInfoService.findAllByMzdp();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages1) {
            for (UserInfoMzdp userInfoMzdp : pages2) {
                if (userInfo.getId().equals(userInfoMzdp.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoMzdp));
                    JSONObject json = new JSONObject();
                    json.putAll(json1);
                    json.putAll(json2);
                    pageArray.add(json);
                }
            }
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoMzdp/listPower 民主党派图形比例
     * @apiGroup UserInfoMzdp
     * @apiVersion 1.0.0
     * @apiDescription 民主党派图形比例
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求样例
     *                /userInfoMzdp/listPower
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "education": {
     *                 "doctor": 0,
     *                 "specialty": 0,
     *                 "postgraduate": 0,
     *                 "highSchool": 0,
     *                 "undergraduate": 2
     *             },
     *             "identity": {
     *                 "cadre": 2,
     *                 "masses": 0
     *             },
     *             "sex": {
     *                 "woman": 0,
     *                 "man": 2
     *             },
     *             "partyAge": {
     *                 "中国民主同盟": 0,
     *                 "台湾民主自治同盟": 0,
     *                 "中国民主建国会": 1,
     *                 "中国致公党": 0,
     *                 "中国国民党革命委员会": 0,
     *                 "中国民主促进会": 0,
     *                 "九三学社": 0,
     *                 "中国农工民主党": 1
     *             },
     *             "place": {
     *                 "NO": 0,
     *                 "yes": 2
     *             },
     *             "department": {
     *                 "nternal": 2,
     *                 "eye": 0,
     *                 "chinese": 0,
     *                 "emergency": 0,
     *                 "surgery": 0
     *             },
     *             "title": {
     *                 "attendingDoctor": 2,
     *                 "doctor": 0,
     *                 "deputyChiefPhysician": 0,
     *                 "chiefPhysician": 0,
     *                 "residents": 0
     *             },
     *             "age": {
     *                 "25周岁以下": 0,
     *                 "25-35周岁": 2,
     *                 "45周岁以上": 0,
     *                 "35-45以下": 0
     *             }
     *         }
     *     ]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/listPower")
    public JsonResult listSex(){
        Map<String,Object> pages = userInfoPowerService.countBySexMzdp();
        Map<String,Object> pages1 = userInfoPowerService.countByEducationMzdp();
        Map<String,Object> pages2 = userInfoPowerService.countByIdcardMzdp();
        Map<String,Object> pages3 = userInfoPowerService.countByDepartmentMzdp();
        Map<String,Object> pages4 = userInfoPowerService.countByPartyAgeMzdp();
        Map<String,Object> pages5 = userInfoPowerService.countByPlaceMzdp();
        Map<String,Object> pages6 = userInfoPowerService.countByTitleMzdp();
        Map<String,Object> pages7 = userInfoPowerService.countByIdentityMzdp();
        JSONArray pageArray = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("sex",pages);
        json.put("education",pages1);
        json.put("age",pages2);
        json.put("department",pages3);
        json.put("partyAge",pages4);
        json.put("place",pages5);
        json.put("title",pages6);
        json.put("identity",pages7);
        pageArray.add(json);
        jsonResult.setData(pageArray);
        return jsonResult;
    }
}
