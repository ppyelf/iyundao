package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoParty;
import com.ayundao.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    /**
     * @api {post} /userInfo/add_party 新增用户党建基础信息
     * @apiGroup userInfoParty
     * @apiVersion 1.0.0
     * @apiDescription 新增用户党建信息
     * @apiParam {int} type
     * @apiParam {int} state
     * @apiParam {String} partyPost
     * @apiParam {String} partyBranch
     * @apiParam {int} applyDate
     * @apiParam {int} potDate
     * @apiParam {String} activistDate
     * @apiParam {String} readyDate
     * @apiParam {String} id
     * @apiParamExample {json} 请求样例
     *       ?type= &state= &partyPost= &partyBranch= &applyDate= &potDate= &activistDate= &readyDate=&partyDate= &id=
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_party
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
     *         "userInfoParty": {
     *             "id": "297e47e36b8d41fb016b8d4643360000",
     *             "type": "PARTY",
     *             "state": "NORMAL",
     *             "partyPost": "部长",
     *             "partyBranch": "第一支部",
     *             "applyDate": "2002-11-11",
     *             "potDate": "2003-4-10",
     *             "activistDate": "2003-11-11",
     *             "readyDate": "2004-1-1",
     *             "partyDate": "2004-11-11",
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
    @PostMapping("/add_party")
    public JsonResult add_party(int type,int state,String partyPost,
                                String partyBranch,String applyDate,String potDate,
                                String activistDate,String readyDate,String partyDate,
                                String id) {
        UserInfoParty userInfoParty = new UserInfoParty();
        userInfoParty.setCreatedDate(new Date());
        userInfoParty.setLastModifiedDate(new Date());
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
        return userInfoService.saveParty(userInfoParty,jsonResult);
    }


    /**
     * @api {get} /userInfoParty/del 删除用户详情 -党建基础信息
     * @apiGroup UserInfoParty
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
        userInfoService.deleteParty(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfoParty/list 用户详情 -党建基础信息
     * @apiGroup UserInfoParty
     * @apiVersion 1.0.0
     * @apiDescription 党建基础信息
     * @apiParamExample {json} 请求样例
     *                /userInfoParty/list
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