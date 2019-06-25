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

    @GetMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteTw(id);
        return jsonResult;
    }
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
