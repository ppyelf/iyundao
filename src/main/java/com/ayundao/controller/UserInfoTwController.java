package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoTw;
import com.ayundao.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserInfoTwController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -团委信息
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfoTw")
public class UserInfoTwController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * @api {post} /userInfoTw/add_tw 新增用团委基础信息
     * @apiGroup UserInfoTw
     * @apiVersion 1.0.0
     * @apiDescription 新增用团委基础信息
     * @apiParam {String} post 职务
     * @apiParam {String} time 任职时间
     * @apiParam {String} userinfoid
     * @apiParamExample {json} 请求样例
     *          ?post=&title=&userinfoid=
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
     *         "userInfoTw": {
     *             "id": "297e47e36b8d58dd016b8d59108f0000",
     *             "post": "主席",
     *             "time": "2010-11-11",
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
    @PostMapping("/add_tw")
    public JsonResult add_tw(String post,String time,
                             String userinfoid) {
        UserInfoTw userInfoTw = new UserInfoTw();
        userInfoTw.setCreatedDate(new Date());
        userInfoTw.setLastModifiedDate(new Date());
        userInfoTw.setPost(post);
        userInfoTw.setTime(time);
        userInfoTw.setUserinfoid(userinfoid);
        return userInfoService.saveTw(userInfoTw,jsonResult);
    }

    /**
     * @api {post} /userInfoTw/del 删除用户详情 -团委基础信息
     * @apiGroup UserInfoTw
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 团委基础信息ID
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
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteTw(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoTw/list 用户详情 -团委基础信息
     * @apiGroup UserInfoTw
     * @apiVersion 1.0.0
     * @apiDescription 团委基础信息
     * @apiParamExample {json} 请求样例
     *                /userInfoTw/list
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
        List<UserInfoTw> pages1 = userInfoService.findAllByTw();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoTw userInfoTw : pages1) {
                if (userInfo.getId().equals(userInfoTw.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoTw));
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
     * @api {post} /userInfoTw/findByLike 用户条件查询
     * @apiGroup UserInfoTw
     * @apiVersion 1.0.0
     * @apiDescription 用户条件查询
     * @apiParam {String} name 姓名
     * @apiParam {String} number 编号
     * @apiParam {String} department 科室
     * @apiParamExample {json} 请求样例
     *                /userInfoTw/findByLike?name= (or number or department)
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
    public JsonResult findByLike(String name,String number,String department) {
        String s = "%" + name + "%";
        String s1 = "%" + number + "%";
        String s2 = "%" + department + "%";
        List<UserInfo> pages = userInfoService.findByNameOrNumberOrDepartmentLike(s, s1, s2);
        List<UserInfoTw> pages1 = userInfoService.findAllByTw();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoTw userInfoTw : pages1) {
                if (userInfo.getId().equals(userInfoTw.getUserinfoid())) {
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoTw));
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
}
