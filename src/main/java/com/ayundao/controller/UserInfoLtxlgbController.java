package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoLtxlgb;
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
 * @ClassName: UserInfoLtxlgbController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -离退休老干部信息
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfoLtxlgb")
public class UserInfoLtxlgbController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/add_ltxlgb")
    public JsonResult add_ltxlgb(String post,String time,
                                 String userinfoid) {
        UserInfoLtxlgb userInfoLtxlgb = new UserInfoLtxlgb();
        userInfoLtxlgb.setCreatedDate(new Date());
        userInfoLtxlgb.setLastModifiedDate(new Date());
        userInfoLtxlgb.setPost(post);
        userInfoLtxlgb.setTime(time);
        userInfoLtxlgb.setUserinfoid(userinfoid);
        userInfoService.saveLtxlgb(userInfoLtxlgb);
        return jsonResult;
    }

    @GetMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteLtxlgb(id);
        return jsonResult;
    }

    @GetMapping("/list")
    public JsonResult list(){
        List<UserInfo> pages = userInfoService.findAll();
        List<UserInfoLtxlgb> pages1 = userInfoService.findAllByLtxlgb();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoLtxlgb userInfoLtxlgb : pages1) {
                if (userInfo.getId().equals(userInfoLtxlgb.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoLtxlgb));
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
