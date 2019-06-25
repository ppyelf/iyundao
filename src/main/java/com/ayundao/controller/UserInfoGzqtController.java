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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @PostMapping("/add_gzqt")
    public JsonResult add_gzqt(String education,String title,
                               String userinfoid) {
        UserInfoGzqt userInfoGzqt = new UserInfoGzqt();
        userInfoGzqt.setCreatedDate(new Date());
        userInfoGzqt.setLastModifiedDate(new Date());
        userInfoGzqt.setEducation(education);
        userInfoGzqt.setTitle(title);
        userInfoGzqt.setUserinfoid(userinfoid);
        userInfoService.saveGzqt(userInfoGzqt);
        return jsonResult;
    }

    @GetMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteGzqt(id);
        return jsonResult;
    }

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
