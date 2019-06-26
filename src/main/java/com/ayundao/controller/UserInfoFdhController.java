package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
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
 * @ClassName: UserInfoFdhController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -妇代会
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfoFdh")
public class UserInfoFdhController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * @api {post} /userInfoFdh/add_fdh 新增用户妇代会基础信息
     * @apiGroup userInfoFdh
     * @apiVersion 1.0.0
     * @apiDescription 新增用户妇代会基础信息
     * @apiParam {String} post
     * @apiParam {String} time
     * @apiParam {String} id
     * @apiParamExample {json} 请求样例
     *         ?post=""&time=""&id=""
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
     *         "userInfoFdh": {
     *             "id": "297e47e36b8d58dd016b8d5eac8d0001",
     *             "post": "主席",
     *             "time": "2010-11-11",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "userInfo": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping("/add_fdh")
    public JsonResult add_fdh(String post,String time,
                              String id) {

        UserInfoFdh userInfoFdh = new UserInfoFdh();
        userInfoFdh.setCreatedDate(new Date());
        userInfoFdh.setLastModifiedDate(new Date());
        userInfoFdh.setPost(post);
        userInfoFdh.setTime(time);
        userInfoFdh.setUserinfoid(id);
        return userInfoService.saveFdh(userInfoFdh,jsonResult);
    }

    /**
     * @api {get} /userInfoFdh/del 删除用户详情 -妇代会
     * @apiGroup UserInfoFdh
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户详情ID -妇代会
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
        userInfoService.deleteFdh(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoFdh/list 用户详情 -妇代会信息
     * @apiGroup UserInfoFdh
     * @apiVersion 1.0.0
     * @apiDescription 妇代会信息
     * @apiParamExample {json} 请求样例
     *                /userInfoFdh/list
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "userInfo": {
     *                 "birthday": "1995-04-10",
     *                 "education": "本科",
     *                 "nation": "汉族",
     *                 "title": "专家",
     *                 "userid": "11213546546132151",
     *                 "number": "002",
     *                 "password": "123456",
     *                 "workDate": "40年",
     *                 "idEntity": "未知",
     *                 "post": "主治医生",
     *                 "id": "297e47e36b7821e5016b782294410000",
     *                 "place": "陕西省",
     *                 "department": "消化科",
     *                 "lastModifiedDate": "20190621114420",
     *                 "sex": "男",
     *                 "branchName": "第一支部",
     *                 "correctionDate": "2000-11-11",
     *                 "version": "0",
     *                 "partyDate": "2011-01-01",
     *                 "createdDate": "20190621114420",
     *                 "phone": "12124545121",
     *                 "idcard": "61052719950410181X",
     *                 "name": "测试",
     *                 "info1": "",
     *                 "info5": "",
     *                 "info4": "",
     *                 "info3": "",
     *                 "username": "管理员1",
     *                 "info2": ""
     *             },
     *             "userInfoFdh": {
     *                 "userinfoid": "297e47e36b7821e5016b782294410000",
     *                 "createdDate": "20190621134840",
     *                 "post": "",
     *                 "lastModifiedDate": "20190621134840",
     *                 "info1": "",
     *                 "id": "297e47e36b78937e016b789468be0000",
     *                 "time": "",
     *                 "info5": "",
     *                 "version": "0",
     *                 "info4": "",
     *                 "info3": "",
     *                 "info2": ""
     *             }
     *         }
     *     ]
     * }
     */
    @GetMapping("/list")
    public JsonResult list(){
        List<UserInfo> pages = userInfoService.findAll();
        List<UserInfoFdh> pages1 = userInfoService.findAllByFdh();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoFdh userInfoFdh : pages1) {
                if (userInfo.getId().equals(userInfoFdh.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoFdh));
                    JSONObject json = new JSONObject();
                    json.put("userInfo",json1);
                    json.put("userInfoFdh",json2);
                    pageArray.add(json);
                }
            }
        }

        jsonResult.setData(pageArray);
        return jsonResult;
    }
}
