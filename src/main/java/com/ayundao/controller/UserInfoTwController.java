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
     * @api {post} /userInfo/add_tw 新增用团委基础信息
     * @apiGroup userInfoTw
     * @apiVersion 1.0.0
     * @apiDescription 新增用团委基础信息
     * @apiParam {JSON}
     *         "post":必填，
     *         "time":必填，
     *         "userid":必填，
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_tw
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
    @PostMapping("/add_tw")
    public JsonResult add_tw(String post,String time,
                             String userinfoid) {
        UserInfoTw userInfoTw = new UserInfoTw();
        userInfoTw.setCreatedDate(new Date());
        userInfoTw.setLastModifiedDate(new Date());
        userInfoTw.setPost(post);
        userInfoTw.setTime(time);
        userInfoTw.setUserinfoid(userinfoid);
        userInfoService.saveTw(userInfoTw);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoGzqt/del 删除用户详情 -高知群体
     * @apiGroup UserInfoGzqt
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户详情ID
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
        userInfoService.deleteTw(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoFdh/list 用户详情 -妇代会信息
     * @apiGroup UserInfoFdh
     * @apiVersion 1.0.0
     * @apiDescription 妇代会信息分页
     * @apiParamExample {json} 请求样例
     *                /userInfoFdh/list
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
}
