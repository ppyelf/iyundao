package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.User;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoGzqt;
import com.ayundao.service.UserInfoPowerService;
import com.ayundao.service.UserInfoService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserInfoGzqtController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -高知群体信息
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfoGzqt")
public class UserInfoGzqtController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoPowerService userInfoPowerService;

    /**
     * @api {post} /userInfoGzqt/add_gzqt 新增用户高知群体基础信息
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 新增用户高知群体基础信息
     * @apiParam {String} education 学历
     * @apiParam {String} title 职称
     * @apiParam {String} userinfoid
     * @apiParamExample {json} 请求样例
     *          ?education=&title=&userinfoid=
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
     *         "userInfoGzqt": {
     *             "id": "297e47e36b8d653c016b8d6a1f6a0001",
     *             "education": "本科",
     *             "title": "博士",
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
    @PostMapping("/add_gzqt")
    public JsonResult add_gzqt(String education,String title,
                               String userinfoid) {
        UserInfoGzqt userInfoGzqt = new UserInfoGzqt();
        userInfoGzqt.setCreatedDate(new Date());
        userInfoGzqt.setLastModifiedDate(new Date());
        userInfoGzqt.setEducation(education);
        userInfoGzqt.setTitle(title);
        userInfoGzqt.setUserinfoid(userinfoid);
        return userInfoService.saveGzqt(userInfoGzqt,jsonResult);
    }

    /**
     * @api {post} /userInfoGzqt/del 删除用户详情 -高知群体
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户详情ID
     * @apiParamExample {json} 请求样例
     *                ?id=
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
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteGzqt(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoGzqt/list 用户详情 -高知群体信息
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 高知群体信息
     * @apiParamExample {json} 请求样例
     *                /userInfoGzqt/list
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
    @GetMapping("/list")
    public JsonResult list(){
        List<UserInfo> pages = userInfoService.findAll();
        List<UserInfoGzqt> pages1 = userInfoService.findAllByGzqt();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoGzqt userInfoGzqt : pages1) {
                if (userInfo.getId().equals(userInfoGzqt.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoGzqt));
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
     * @api {post} /userInfoGzqt/listDepart 用户分页 -根据组织查询
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParam {String} departId 组织id
     * @apiParamExample {json} 请求样例
     *                /userInfoGzqt/listDepart
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
    @PostMapping("/listDepart")
    public JsonResult listDepart(String departId){
        List<User> pages = userService.findByDepartIdForPage(departId);
        List<UserInfo> pages1 = userInfoService.findAll();
        List<UserInfoGzqt> pages2 = userInfoService.findAllByGzqt();
        JSONArray pageArray = new JSONArray();
        for (User user : pages) {

            for (UserInfo userInfo : pages1) {
                for (UserInfoGzqt userInfoGzqt : pages2) {
                    if (user.getId().equals(userInfo.getUserid())) {
                        if (userInfo.getId().equals(userInfoGzqt.getUserinfoid())) {
                            JSONObject json1 = new JSONObject(JsonUtils.getJson(user));
                            JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfo));
                            JSONObject json3 = new JSONObject(JsonUtils.getJson(userInfoGzqt));
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
     * @api {post} /userInfoGzqt/listGroupId 用户分页 -根据部门查询
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParam {String} groupId 部门id
     * @apiParamExample {json} 请求样例
     *                /userInfoGzqt/listDepart
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
    @PostMapping("/listGroupId")
    public JsonResult listGroupId(String groupId){
        List<User> pages = userService.findByGroupIdForPage(groupId);
        List<UserInfo> pages1 = userInfoService.findAll();
        List<UserInfoGzqt> pages2 = userInfoService.findAllByGzqt();
        JSONArray pageArray = new JSONArray();
        for (User user : pages) {

            for (UserInfo userInfo : pages1) {
                for (UserInfoGzqt userInfoGzqt : pages2) {
                    if (user.getId().equals(userInfo.getUserid())) {
                        if (userInfo.getId().equals(userInfoGzqt.getUserinfoid())) {
                            JSONObject json1 = new JSONObject(JsonUtils.getJson(user));
                            JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfo));
                            JSONObject json3 = new JSONObject(JsonUtils.getJson(userInfoGzqt));
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
     * @api {post} /userInfoGzqt/findByLike 用户条件查询
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 用户条件查询
     * @apiParam {String} name 姓名
     * @apiParam {String} number 编号
     * @apiParam {String} department 科室
     * @apiParamExample {json} 请求样例
     *                /userInfoGzqt/findByLike?name= (or number or department)
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
    @PostMapping("/findByLike")
    public JsonResult findByLike(String name,String number,String department){
        String s = "%" + name + "%";
        String s1 = "%" + number + "%";
        String s2 = "%" + department + "%";
        List<UserInfo> pages1  =userInfoService.findByNameOrNumberOrDepartmentLike(s,s1,s2);
        List<UserInfoGzqt> pages2 = userInfoService.findAllByGzqt();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages1) {
            for (UserInfoGzqt userInfoGzqt : pages2) {
                if (userInfo.getId().equals(userInfoGzqt.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoGzqt));
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
     * @api {get} /userInfoGzqt/listSex 高知群体图形比例
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 高知群体图形比例
     * @apiParamExample {json} 请求样例
     *                /userInfoGzqt/listPower
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
     *                 "highSchool": 1,
     *                 "undergraduate": 1
     *             },
     *             "identity": {
     *                 "cadre": 2,
     *                 "masses": 0
     *             },
     *             "sex": {
     *                 "woman": 0,
     *                 "man": 2
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
     *                 "doctor": 0,
     *                 "attendingDoctor": 1,
     *                 "deputyChiefPhysician": 0,
     *                 "chiefPhysician": 1,
     *                 "residents": 0
     *             },
     *             "age": {
     *                 "25-35周岁": 1,
     *                 "25周岁以下": 1,
     *                 "45周岁以上": 0,
     *                 "35-45以下": 0
     *             }
     *         }
     *     ]
     * }
     */
    @GetMapping("/listPower")
    public JsonResult listSex(){
        Map<String,Object> pages = userInfoPowerService.countBySexGzqt();
        Map<String,Object> pages1 = userInfoPowerService.countByEducationGzqt();
        Map<String,Object> pages2 = userInfoPowerService.countByIdcardGzqt();
        Map<String,Object> pages3 = userInfoPowerService.countByDepartmentGzqt();
//        Map<String,Object> pages4 = userInfoPowerService.countByPartyAgeGzqt();
        Map<String,Object> pages5 = userInfoPowerService.countByPlaceGzqt();
        Map<String,Object> pages6 = userInfoPowerService.countByTitleGzqt();
        Map<String,Object> pages7 = userInfoPowerService.countByIdentityGzqt();
        JSONArray pageArray = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("sex",pages);
        json.put("education",pages1);
        json.put("age",pages2);
        json.put("department",pages3);
//        json.put("partyAge",pages4);
        json.put("place",pages5);
        json.put("title",pages6);
        json.put("identity",pages7);
        pageArray.add(json);
        jsonResult.setData(pageArray);
        return jsonResult;
    }
}
