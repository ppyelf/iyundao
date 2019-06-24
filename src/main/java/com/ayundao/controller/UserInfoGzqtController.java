package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoGzqt;
import com.ayundao.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * @api {get} /userInfoGzqt/del 删除用户详情 -高知群体
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
    @GetMapping("/del")
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
}
