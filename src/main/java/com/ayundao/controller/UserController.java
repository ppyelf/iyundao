package com.ayundao.controller;


import com.ayundao.base.BaseController;
import com.ayundao.base.annotation.CurrentSubject;
import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmOnDeleteEnum;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import sun.util.resources.cldr.ti.CalendarData_ti_ER;

import java.util.*;

import static com.ayundao.base.BaseController.ROLE_ADMIN;


/**
 * @ClassName: UserController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户
 * @Version: V1.0
 */
@RequiresUser
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleRelationService roleRelationService;

    /**
     * @api {POST} /user/checkCode 检测code
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 检测编号是否存在
     * @apiParam {String} code
     * @apiParamExample {json} 请求样例：
     *                ?code=1234
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "可以使用"
     * }
     */
    @PostMapping("/checkCode")
    public JsonResult existCode(String code) {
        jsonResult.setData(userService.existsCode(code) ? "已存在" : "可以使用");
        return jsonResult;
    }

    /**
     * @api {POST} /user/checkAccount 检测账号
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 检测账号是否存在
     * @apiParam {String} code
     * @apiParamExample {json} 请求样例：
     *                ?code=1234
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "可以使用"
     * }
     */
    @PostMapping("/checkAccount")
    public JsonResult checkAccount(String account) {
        jsonResult.setData(userService.findByAccount(account) != null ? "已存在" : "可以使用");
        return jsonResult;
    }

    /**
     * @api {POST} /user/search 用户搜索
     * @apiName search
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 用户搜索
     * @apiParam {String} key 搜索条件
     * @apiParam {String} value 查询值
     * @apiParam {int} page 页数(默认:1)
     * @apiParam {int} size 长度(默认:10)
     * @apiParamExample {json} 请求样例
     *                ?key=张三&page=1&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              404:不存在此用户</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{'pages':1,'elements':1,'entity':[{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'管理员','status':'normal','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','remark':'未填写','sex':'0','salt':'3a10624a300f4670','userType':'amdin','account':'admin'}]}"
     * }
     */
    @PostMapping("/search")
    public JsonResult search(String key,
                             String value,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        if (StringUtils.isBlank(key)) {
            return JsonResult.paramError();
        }
        Pageable pageable = new Pageable(page, size);
        pageable.setSearchProperty(key);
        pageable.setSearchValue(value);
        Page<User> userPage = userService.findByKey(pageable);
        if (CollectionUtils.isEmpty(userPage.getContent())) {
            return JsonResult.notFound("不存在此用户");
        }
        jsonResult.setData(JsonUtils.getPage(userPage));
        return jsonResult;
    }

    /**
     * @api {GET} /user/role 用户搜索
     * @apiName search
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 用户搜索
     * @apiParamExample {json} 请求样例
     *                ?key=张三&page=1&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              404:不存在此用户</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "admin": [
     *             "add",
     *             "modify",
     *             "view",
     *             "examine",
     *             "release",
     *             "disable",
     *             "lock",
     *             "delete"
     *         ]
     *     }
     * }
     */
    @GetMapping("/role")
    public JsonResult role(@CurrentUser User user) {
        Set<RoleRelation> roleRelations = roleRelationService.findRolesByUserId(user.getId());
        JSONObject json = new JSONObject();
        Map<String, String> map = new LinkedHashMap<>();
        for (RoleRelation rr : roleRelations) {
            if (map.get(rr.getRole().getCode()) != null) {
                map.put(rr.getRole().getCode(), map.get(rr.getRole().getCode()) + "," + rr.getPermission().getCode());
            } else {
                map.put(rr.getRole().getCode(), rr.getPermission().getCode());
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String[] permission = entry.getValue().contains(",") ? entry.getValue().split(",") : new String[]{entry.getValue()};
            Set<String> set = new HashSet<>();
            set.addAll(Arrays.asList(permission));
            JSONArray arr = new JSONArray();
            for (String s : set) {
                arr.add(s);
            }
            json.put(entry.getKey(), arr);
        }
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {POST} /user/add 新建用户
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 新建用户
     * @apiParam {String} account 账号 必填
     * @apiParam {String} name 姓名 必填
     * @apiParam {String} code 编号 必填
     * @apiParam {int} sex 性别
     * @apiParam {int} userType 用户类型
     * @apiParam {String} departId 部门ID 必填
     * @apiParam {String} groupsId 组织ID 必填
     * @apiParam {String} remark 描述
     * @apiParam {String} password 密码 必填
     * @apiParam {String[]} roleIds 角色IDS 必填
     * @apiParam {String[]} permissionIds 权限IDS 必填
     * @apiParamExample {json} 请求样例：
     *                /user/add?key=张三&page=1&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     *                                 601:用户必须有所属的机构/部门/组织</br>
     *                                 602:用户类型设置异常</br>
     *                                 603:部门/组织不存在</br>
     *                                 604:机构不存在</br>
     *                                 605:账号必须分配角色,权限</br>
     *                                 606:必填字段不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"salt": "45a1d914886d4a92b6835a181b2a20d8","lastModifiedDate": "20190621102140","sex": "0","remark": "","version": "0","userRoles": [    {        "createdDate": "20190517111111",        "lastModifiedDate": "20190517111111",        "level": "0",        "name": "user",        "id": "b08a1e16dfe04d6c98e1599007c31490",        "version": "1"    },    {        "createdDate": "20190517111111",        "lastModifiedDate": "20190620141259",        "level": "1",        "name": "admin",        "id": "c7717f9578b64a819cbfcf75848fcc2a",        "version": "4"    }],"password": "B07EEB8D7F62FBD7","createdDate": "20190621102140","userRelations": [    {        "subject": {            "createdDate": "20190517111111",            "lastModifiedDate": "20190517111111",            "name": "总院",            "id": "bd6886bc88e54ef0a36472efd95c744c",            "version": "1",            "subjectType": "head"        },        "groups": {            "createdDate": "20190517111111",            "lastModifiedDate": "20190517111111",            "name": "总-组织",            "info1": "",            "remark": "",            "id": "ec0e291d5bfd4e98a33cd610c9b1d330",            "info5": "",            "version": "1",            "info4": "",            "info3": "",            "info2": ""        }    }],"name": "测试账号","id": "402881916b77bff1016b77d6e37a002e","userType": "admin","account": "a4","status": ""
     *     }
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String account,
                          String name,
                          String code,
                          @RequestParam(defaultValue = "0") int sex,
                          @RequestParam(defaultValue = "0") int userType,
                          @CurrentSubject Subject subject,
                          String departId,
                          String groupsId,
                          String remark,
                          String password,
                          String[] roleIds,
                          String[] permissionIds) {
        if (isBlank(account, name, code, departId, groupsId, password)) {
            return JsonResult.failure(606, "必填字段不能为空");
        }
        if (userService.existsAccount(account)) {
            return JsonResult.failure("账号已存在");
        }
        if (userService.existsCode(code)) {
            return JsonResult.failure("编号已存在");
        }
        User user = new User();
        user.setCreatedDate(new Date());
        user.setLastModifiedDate(new Date());
        user.setAccount(account);
        user.setName(name);
        user.setCode(code);
        user.setSex(sex);
        user.setSalt(getSalt());
        user.setStatus(User.ACCOUNT_TYPE.normal);
        user.setPassword(setPassword(password, user.getSalt()));
        for (User.USER_TYPE type : User.USER_TYPE.values()) {
            if (type.ordinal() == userType) {
                user.setUserType(type);
                break;
            } 
        }
        if (user.getUserType() == null) {
            return JsonResult.failure(602, "用户类型设置异常");
        }
        List<Role> roles = roleService.findByRoleIds(roleIds);
        List<Permission> permissions = permissionService.findByIds(permissionIds);
        if (CollectionUtils.isEmpty(roles) || CollectionUtils.isEmpty(permissions)) {
            return JsonResult.failure(605, "账号必须分配角色,权限");
        } 
        user.setRemark(remark);
        return userService.save(user, subject, departId, groupsId, roles, permissions, jsonResult);
    }

    /**
     * @api {POST} /user/view 查看用户
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id=0a4179fc06cb49e3ac0db7bcc8cf0882
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              404:此用户不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"salt": "45a1d914886d4a92b6835a181b2a20d8","lastModifiedDate": "20190621102140","sex": "0","remark": "","version": "0","userRoles": [    {        "level": 0,        "name": "user",        "id": "b08a1e16dfe04d6c98e1599007c31490"    },    {        "level": 1,        "name": "admin",        "id": "c7717f9578b64a819cbfcf75848fcc2a"    }],"password": "B07EEB8D7F62FBD7","createdDate": "20190621102140","userRelations": [    {        "subject": {            "name": "总院",            "id": "bd6886bc88e54ef0a36472efd95c744c"        },        "groups": {            "name": "总-组织",            "id": "ec0e291d5bfd4e98a33cd610c9b1d330"        }    }],"name": "测试账号","id": "402881916b77bff1016b77d6e37a002e","userType": "admin","account": "a4","status": ""
     *     }
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        User user = userService.findById(id);
        if (user == null) return JsonResult.notFound("此用户不存在");
        jsonResult.setData(userService.getUserInfoJson(user));
        return jsonResult;
    }

    /**
     * @api {post} /user/groupUser 组织用户分页
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 组织用户分页
     * @apiParam {String} groupId 组织ID
     * @apiParam {int} page 页数
     * @apiParam {int} size 长度
     * @apiParamExample {json} 请求样例
     *                ?page
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{'total':3,'content':[{'id':'0a4179fc06cb49e3ac0db7bcc8cf0882','account':'admin','sex':'男','userType':'管理员','status':'正常','createdTime':'20190517111111','relation':['总院-分-部门-无','分院-总-部门-无'],'remark':'未填写'},{'id':'5cf0d3c3b0da4cbaad179e0d6d230d0c','account':'test','sex':'男','userType':'普通用户','status':'正常','createdTime':'20190517111111','relation':['总院-总-部门-无'],'remark':'未填写'},{'id':'cd22e3407ace4d86bac92f92b9e9dd3e','account':'user','sex':'男','userType':'普通用户','status':'正常','createdTime':'20190517111111','relation':[],'remark':'未填写'}]}"
     * }
     */
    @PostMapping("/groupUser")
    public JsonResult groupUser(String groupId,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        //todo 需要整改小组用户的分页查询
        List<User> userPage = userService.findByGroupIdForPage(groupId);
        JSONArray pageArray = new JSONArray();
        for (User user : userPage) {
            pageArray.add(convertUser(user));
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /user/departUser 部门用户分页
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 部门用户分页
     * @apiParam {String} departId 部门ID
     * @apiParam {int} page 页数
     * @apiParam {int} size 长度
     * @apiParamExample {json} 请求样例
     *                ?page
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{'total':3,'content':[{'id':'0a4179fc06cb49e3ac0db7bcc8cf0882','account':'admin','sex':'男','userType':'管理员','status':'正常','createdTime':'20190517111111','relation':['总院-分-部门-无','分院-总-部门-无'],'remark':'未填写'},{'id':'5cf0d3c3b0da4cbaad179e0d6d230d0c','account':'test','sex':'男','userType':'普通用户','status':'正常','createdTime':'20190517111111','relation':['总院-总-部门-无'],'remark':'未填写'},{'id':'cd22e3407ace4d86bac92f92b9e9dd3e','account':'user','sex':'男','userType':'普通用户','status':'正常','createdTime':'20190517111111','relation':[],'remark':'未填写'}]}"
     * }
     */
    @PostMapping("/departUser")
    public JsonResult departUser(String departId,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        //todo 需要整改部门用户的分页查询
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        List<User> userPage = userService.findByDepartIdForPage(departId);
        if (userPage == null) {
            return JsonResult.success();
        }
        JSONObject pageJson = new JSONObject();
        JSONArray pageArray = new JSONArray();
//        pageJson.put("total", ((Page) userPage).getTotal());
//        pageJson.put("totalPage", userPage.getTotalPages());
//        pageJson.put("page", userPage.getNumber());
        pageJson.put("content", pageArray);
        jsonResult.setData(pageJson);
        return jsonResult;
    }

    /**
     * @api {POST} /user/sign 用户签到
     * @apiGroup User
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看
     * @apiParam {String} userId 用户ID
     * @apiParam {int} type 签到类型
     * @apiParam {String} activityId 活动ID
     * @apiParam {String} singTime 签到时间
     * @apiParam {String} axisx 坐标x
     * @apiParam {String} axisy 坐标y
     * @apiParamExample {json} 请求样例
     *                /user/sign?activityId=402881916b2a3187016b2a3247350002&userId=0a4179fc06cb49e3ac0db7bcc8cf0882&singTime=now&axisx=123&axisy=456
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              601:用户不存在</br>
     *                              602:活动不存在</br>
     *                              603:签到类型不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b32f12a016b33962eb90012","createdDate":"20190608041648","lastModifiedDate":"20190608041648","userId":"0a4179fc06cb49e3ac0db7bcc8cf0882","axisx":"123","axisy":"456","info1":"","info3":"","info4":"","info5":"","info2":"","signTime":"now","signType":"normal"}"
     * }
     */
    @PostMapping("/sign")
    public JsonResult sign(String userId,
                           @RequestParam(defaultValue = "0") int type,
                           String activityId,
                           String singTime,
                           String axisx,
                           String axisy) {
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.failure(601, "用户不存在");
        }
        Activity activity = activityService.find(activityId);
        if (activity == null) {
            return JsonResult.failure(602, "活动不存在");
        }
        Sign s = new Sign();
        s.setCreatedDate(new Date());
        s.setLastModifiedDate(new Date());
        s.setSignTime(singTime);
        s.setAxisx(axisx);
        s.setAxisy(axisy);
        s.setUserId(userId);
        s.setActivity(activity);
        for (Sign.SIGN_TYPE st : Sign.SIGN_TYPE.values()) {
            if (st.ordinal() == type) {
                s.setSignType(st);
                break;
            } 
        }
        if (s.getSignType() == null) {
            return JsonResult.failure(603, "签到类型不能为空");
        }
        s = activityService.saveUserSign(s);
        jsonResult.setData(JsonUtils.getJson(s));
        return jsonResult;
    }

    private JSONObject convertUser(User user) {
        JSONObject json = JsonUtils.getJson(user);
        if (user.getUserType() != null) {
            switch (user.getUserType().ordinal()) {
                case  0:
                    json.put("userType", "普通用户");
                    break;
                case  1:
                    json.put("userType", "管理员");
                    break;
                case  2:
                    json.put("userType", "负责人");
                    break;
            }
        }
        if (user.getStatus() != null) {
            switch (user.getStatus().ordinal()) {
                case  0:
                    json.put("status", "禁用");
                    break;
                case  1:
                    json.put("status", "锁定");
                    break;
                case  2:
                    json.put("status", "正常");
                    break;
            }
        }
        if (CollectionUtils.isNotEmpty(user.getUserRelations())) {
            JSONArray arr = new JSONArray();
            for (UserRelation ur : user.getUserRelations()) {
                JSONObject j = new JSONObject();
                if (ur.getSubject() == null) {
                    continue;
                }
                j.put("subjectId", ur.getSubject().getId());
                j.put("subjectName", ur.getSubject().getName());
                if (ur.getDepart() != null) {
                    j.put("departId", ur.getDepart().getId());
                    j.put("departName", ur.getDepart().getName());
                }
                if (ur.getGroups() != null) {

                    j.put("groupsId", ur.getGroups().getId());
                    j.put("groupsName", ur.getGroups().getName());
                }
                arr.add(j);
            }
            json.put("userRelation", arr);
        }
        return json;
    }
}
