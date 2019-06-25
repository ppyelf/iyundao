package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoGh;
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
 * @ClassName: UserInfoGhController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -工会信息
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfoGh")
public class UserInfoGhController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/add_gh")
    public JsonResult add_gh(String post,String time,
                             String userinfoid) {
        UserInfoGh userInfoGh = new UserInfoGh();
        userInfoGh.setCreatedDate(new Date());
        userInfoGh.setLastModifiedDate(new Date());
        userInfoGh.setPost(post);
        userInfoGh.setTime(time);
        userInfoGh.setUserinfoid(userinfoid);
        userInfoService.saveGh(userInfoGh);
        return jsonResult;
    }

    /**
     * @api {get} /userInfo/del 删除用户详情 -工会信息
     * @apiGroup UserInfoGh
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户详情 -工会信息ID
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
        userInfoService.deleteGh(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoGh/list 用户详情 -工会信息
     * @apiGroup UserInfoGh
     * @apiVersion 1.0.0
     * @apiDescription 工会信息
     * @apiParamExample {json} 请求样例
     *                /userInfoGh/list
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
        List<UserInfoGh> pages1 = userInfoService.findAllByGh();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoGh userInfoGh : pages1) {
                if (userInfo.getId().equals(userInfoGh.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoGh));
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
