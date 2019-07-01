package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.User;
import com.ayundao.entity.UserInfo;
import com.ayundao.entity.UserInfoParty;
import com.ayundao.service.UserInfoService;
import com.ayundao.service.UserService;
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

    @Autowired
    private UserService userService;
    /**
     * @api {post} /userInfoParty/add_party 新增用户党建基础信息
     * @apiGroup UserInfoParty
     * @apiVersion 1.0.0
     * @apiDescription 新增用户党建信息
     * @apiParam {int} type 党员状态
     * @apiParam {int} state 党籍是否在籍
     * @apiParam {String} partyPost 党内职务
     * @apiParam {String} partyBranch 所属支部
     * @apiParam {int} applyDate 入党申请时间
     * @apiParam {int} potDate 建档对象时间
     * @apiParam {String} activistDate 积极分子时间
     * @apiParam {String} readyDate 预备党员时间
     * @apiParam {String} partyDate 入党时间
     * @apiParam {String} userinfoid
     * @apiParamExample {json} 请求样例
     *       ?type= &state= &partyPost= &partyBranch= &applyDate= &potDate= &activistDate= &readyDate=&partyDate= &userinfoid=
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
                                String userinfoid) {
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
        userInfoParty.setUserinfoid(userinfoid);
        return userInfoService.saveParty(userInfoParty,jsonResult);
    }


    /**
     * @api {post} /userInfoParty/del 删除用户详情 -党建基础信息
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
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteParty(id);
        return jsonResult;
    }

    /**
     * @api {post} /userInfoParty/list 用户详情 -党建基础信息
     * @apiGroup UserInfoParty
     * @apiVersion 1.0.0
     * @apiParam {String} type 党员
     * @apiDescription 党建基础信息
     * @apiParamExample {json} 请求样例
     *                /userInfoParty/list?type=党员
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {{
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "birthday": "1984-11-11",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "education": "本科",
     *             "nation": "汉族",
     *             "potDate": "2003-4-10",
     *             "readyDate": "2004-1-1",
     *             "title": "主治医师",
     *             "type": "PARTY",
     *             "userid": "297e47e36b8cbecd016b8cbf239b0000",
     *             "number": "002",
     *             "idEntity": "干部",
     *             "workDate": "2004-11-11",
     *             "post": "院长",
     *             "id": "297e47e36b8d41fb016b8d4643360000",
     *             "place": "浙江杭州",
     *             "state": "NORMAL",
     *             "department": "内科",
     *             "partyBranch": "第一支部",
     *             "lastModifiedDate": "20190625141520",
     *             "sex": "男",
     *             "branchName": "第一党支部",
     *             "correctionDate": "2007-11-11",
     *             "activistDate": "2003-11-11",
     *             "partyPost": "部长",
     *             "version": "0",
     *             "partyDate": "2004-11-11",
     *             "createdDate": "20190625141520",
     *             "phone": "19822222222",
     *             "idcard": "315247198811111811",
     *             "name": "测试1",
     *             "info1": "",
     *             "info5": "",
     *             "applyDate": "2002-11-11",
     *             "info4": "",
     *             "info3": "",
     *             "info2": ""
     *         }
     *     ]
     * }
     */
    @PostMapping("/list")
    public JsonResult list(String type){
        List<UserInfo> pages = userInfoService.findAll();
        List<UserInfoParty> pages1 = userInfoService.findAllByParty(type);
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

    /**
     * @api {post} /userInfoParty/listDepart 用户分页 -根据组织查询
     * @apiGroup UserInfoParty
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParam {String} departId 组织id
     * @apiParamExample {json} 请求样例
     *                /userInfoParty/listDepart
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/listDepart")
    public JsonResult listDepart(String departId,String type){
        List<User> pages = userService.findByDepartIdForPage(departId);
        List<UserInfo> pages1 = userInfoService.findAll();
        List<UserInfoParty> pages2 = userInfoService.findAllByParty(type);
        JSONArray pageArray = new JSONArray();
        for (User user : pages) {

            for (UserInfo userInfo : pages1) {
                for (UserInfoParty userInfoParty : pages2) {
                    if (user.getId().equals(userInfo.getUserid())) {
                        if (userInfo.getId().equals(userInfoParty.getUserinfoid())) {
                            JSONObject json1 = new JSONObject(JsonUtils.getJson(user));
                            JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfo));
                            JSONObject json3 = new JSONObject(JsonUtils.getJson(userInfoParty));
                            JSONObject json = new JSONObject();
                            json.putAll(json1);
                            json.putAll(json2);
                            json.putAll(json3);
                            pageArray.add(json);
                        }
                    }
                }
            }
        }

        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfoParty/listGroupId 用户分页 -根据部门查询
     * @apiGroup UserInfoParty
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParam {String} groupId 部门id
     * @apiParamExample {json} 请求样例
     *                /userInfoParty/listDepart
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/listGroupId")
    public JsonResult listGroupId(String groupId,String type){
        List<User> pages = userService.findByGroupIdForPage(groupId);
        List<UserInfo> pages1 = userInfoService.findAll();
        List<UserInfoParty> pages2 = userInfoService.findAllByParty(type);
        JSONArray pageArray = new JSONArray();
        for (User user : pages) {

            for (UserInfo userInfo : pages1) {
                for (UserInfoParty userInfoParty : pages2) {
                    if (user.getId().equals(userInfo.getUserid())) {
                        if (userInfo.getId().equals(userInfoParty.getUserinfoid())) {
                            JSONObject json1 = new JSONObject(JsonUtils.getJson(user));
                            JSONObject json2 = new JSONObject(JsonUtils.getJson(userInfo));
                            JSONObject json3 = new JSONObject(JsonUtils.getJson(userInfoParty));
                            JSONObject json = new JSONObject();
                            json.putAll(json1);
                            json.putAll(json2);
                            json.putAll(json3);
                            pageArray.add(json);
                        }
                    }
                }
            }
        }


        jsonResult.setData(pageArray);
        return jsonResult;
    }
    /**
     * @api {post} /userInfoParty/findByLike 用户条件查询
     * @apiGroup UserInfoParty
     * @apiVersion 1.0.0
     * @apiDescription 用户条件查询
     * @apiParam {String} name 姓名
     * @apiParam {String} number 编号
     * @apiParam {String} department 科室
     * @apiParamExample {json} 请求样例
     *                /userInfoParty/findByLike?type=党员&name= (or number or department)
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
    public JsonResult findByLike(String name,String number,String department,String type){
        String s = "%" + name + "%";
        String s1 = "%" + number + "%";
        String s2 = "%" + department + "%";
        List<UserInfo> pages  =userInfoService.findByNameOrNumberOrDepartmentLike(s,s1,s2);
        List<UserInfoParty> pages1 = userInfoService.findAllByParty(type);
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