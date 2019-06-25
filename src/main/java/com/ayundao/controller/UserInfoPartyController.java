package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoFdh;
import com.ayundao.entity.UserInfoMzdp;
import com.ayundao.entity.UserInfoParty;
import com.ayundao.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserInfoPartyController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情 -党建基础信息
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfoParty")
public class UserInfoPartyController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/add_party")
    public JsonResult add_party(int type,int state,String partyPost,
                                String partyBranch,String applyDate,String potDate,
                                String activistDate,String readyDate,String partyDate,
                                String id) {
        UserInfoParty userInfoParty = new UserInfoParty();
        for (UserInfoParty.TYPE types : UserInfoParty.TYPE.values()) {
            if(types.ordinal() == type){
                userInfoParty.setType(types);
                break;
            }
        }
        for (UserInfoParty.STATE states : UserInfoParty.STATE.values()) {
            if(states.ordinal() == state){
                userInfoParty.setState(states);
                break;
            }
        }
        userInfoParty.setPartyPost(partyPost);
        userInfoParty.setPartyBranch(partyBranch);
        userInfoParty.setApplyDate(applyDate);
        userInfoParty.setPotDate(potDate);
        userInfoParty.setActivistDate(activistDate);
        userInfoParty.setReadyDate(readyDate);
        userInfoParty.setPartyDate(partyDate);
        userInfoParty.setUserinfoid(id);
        userInfoService.saveParty(userInfoParty);
        return jsonResult;
    }


    @GetMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteParty(id);
        return jsonResult;
    }

    @GetMapping("/list")
    public JsonResult list(){
        List<UserInfo> pages = userInfoService.findAll();
        List<UserInfoParty> pages1 = userInfoService.findAllByParty();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            for (UserInfoParty userInfoParty : pages1) {
                if (userInfo.getId().equals(userInfoParty.getUserinfoid())){
                    JSONObject json1 = new JSONObject(JsonUtils.getJson(userInfo));
                    JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfoParty));
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