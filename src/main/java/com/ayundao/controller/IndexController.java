package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ayundao.base.BaseController;
import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.shiro.JwtToken;
import com.ayundao.base.shiro.SecurityConsts;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.JwtUtils;
import com.ayundao.entity.User;
import com.ayundao.entity.UserRelation;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserRelationService;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: IndexController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:40
 * @Description: 首页
 * @Version: V1.0
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @CurrentUser
    private User user;

    /**
     * @api {POST} /login 用户登录
     * @apiGroup 首页
     * @apiVersion 1.0.0
     * @apiDescription 用于用户登录
     * @apiParam {String} account 用户名
     * @apiParam {String} password 密码
     * @apriParam {boolean} rememberMe 记住我
     * @apiParamExample {json} 请求样例：
     *                ?account=admin&password=admin
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     *                                 400:账号异常</br>
     *                                 404:账号不存在</br>
     *                                 600:参数错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "登录成功",
     * 	"data": "{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'管理员','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','status':'normal','remark':'未填写','sex':'0','salt':'3a10624a300f4670','account':'admin','userType':'amdin'}"
     * }
     */
    @PostMapping("/login")
    public JsonResult login(String account, String password, boolean rememberMe, Model model, HttpServletRequest req, HttpServletResponse resp) {
        Subject subject = SecurityUtils.getSubject();
        JwtToken token = new JwtToken(account, password, rememberMe, SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account);
        if (subject.isAuthenticated()) {
            return loginSuccess(account, resp);
        }
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException ex) {
            return JsonResult.failure(804, ex.getMessage());
        } catch (LockedAccountException ex) {
            return JsonResult.failure(805, ex.getMessage());
        } catch (AccountException ex) {
            return JsonResult.failure(803, ex.getMessage());
        } catch (TokenExpiredException ex) {
            return JsonResult.failure(806, ex.getMessage());
        } catch (IncorrectCredentialsException ex){
            return JsonResult.failure(807, "密码校验错误");
        }
        //根据权限，指定返回数据
        User user = (User) subject.getPrincipal();
        if (user != null && user.getAccount().equals(account)) {
            return loginSuccess(account, resp);
        }

        return JsonResult.failure(400,"账号不存在");
    }

    /**
     * 返回登录成功结果
     * @return
     */
    private JsonResult loginSuccess(String account, HttpServletResponse resp) {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        Session session = SecurityUtils.getSubject().getSession();
        User user = userService.findByAccount(account);
        //生成token
        JSONObject json = JsonUtils.getJson(user);
        String token = JwtUtils.sign(account, currentTimeMillis);
        json.put("token",token );
        List<UserRelation> userRelations = userRelationService.findByUser(user);
        if (CollectionUtils.isNotEmpty(userRelations)) {
            for (UserRelation userRelation : userRelations) {
                com.ayundao.entity.Subject subject = userRelation.getSubject();
                JSONObject j = new JSONObject();
                j.put("id", subject.getId());
                j.put("name", subject.getName());
                session.setAttribute("currentSubject", subject);
                break;
            }
        }
        resp.setHeader(SecurityConsts.IYUNDAO_ASSESS_TOKEN, token);
        resp.setHeader("Access-Control-Expose-Headers", SecurityConsts.IYUNDAO_ASSESS_TOKEN);
        jsonResult.setCode(200);
        jsonResult.setMessage("登录成功");
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {GET} /subjectList 个人机构列表
     * @apiGroup 首页
     * @apiVersion 1.0.0
     * @apiDescription 个人机构列表
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例：
     *                /subjectList
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:机构不存在/ID为空</br>
     *                                 100:未加入任何机构</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "登录成功",
     * 	"data": "{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'管理员','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','status':'normal','remark':'未填写','sex':'0','salt':'3a10624a300f4670','account':'admin','userType':'amdin'}"
     * }
     */
    @GetMapping("/subjectList")
    public JsonResult subjectList(@CurrentUser User user) {
        Set<com.ayundao.entity.Subject> set = new HashSet<>();
        List<UserRelation> list = userRelationService.findByUserId(user.getId());
        for (UserRelation ur : list) {
            set.add(ur.getSubject());
        }
        JSONArray arr = new JSONArray();
        for (com.ayundao.entity.Subject subject : set) {
            JSONObject json = new JSONObject();
            json.put("id", subject.getId());
            json.put("name", subject.getName());
            arr.add(json);
        }
        jsonResult = JsonResult.success();
        if (CollectionUtils.isEmpty(arr)) {
            jsonResult.setCode(100);
            jsonResult.setMessage("未加入任何和机构");
            return jsonResult;
        } 
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /changeSubject 切换医院机构
     * @apiGroup 首页
     * @apiVersion 1.0.0
     * @apiDescription 切换医院机构
     * @apiParam {String} id 机构ID
     * @apiParamExample {json} 请求样例：
     *                ?id=bfc5bd62010f467cbbe98c9e4741733b
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:机构不存在/ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "登录成功",
     * 	"data": "{'version':'0','id':'0a4179fc06cb49e3ac0db7bcc8cf0882','createdDate':'20190517111111','lastModifiedDate':'20190517111111','name':'管理员','password':'b356a1a11a067620275401a5a3de04300bf0c47267071e06','status':'normal','remark':'未填写','sex':'0','salt':'3a10624a300f4670','account':'admin','userType':'amdin'}"
     * }
     */
    @PostMapping("/changeSubject")
    public JsonResult changeSubject(String id) {
        com.ayundao.entity.Subject currentSubject = subjectService.find(id);
        if (currentSubject == null) {
            return JsonResult.notFound("机构不存在/ID为空");
        }
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject json = new JSONObject();
        session.setAttribute("currentSubject", json);
        jsonResult.setData(json);
        return jsonResult;
    }


    @RequestMapping("/unauthorized")
    public JsonResult unauthorized() {
        jsonResult.setCode(401);
        jsonResult.setMessage("无权限/未登录");
        return jsonResult;
    }
    /**
     * 解除admin 用户的限制登录
     * 写死的 方便测试
     * @return
     */
    @RequestMapping("/unlockAccount")
    public String unlockAccount(Model model){
        model.addAttribute("msg","用户解锁成功");
        return "login";
    }

    /**
     * @api {POST} /logout 退出登录
     * @apiGroup Index
     * @apiVersion 1.0.0
     * @apiDescription 退出登录
     * @apiParamExample {json} 请求样例：
     * /out
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "退出登录成功"
     * }
     */
    @PostMapping("/logout")
    public JsonResult out(HttpServletRequest req) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        req.getSession().removeAttribute("currentUser");
        req.getSession().removeAttribute("currentSubject");
        return JsonResult.success("退出登录成功");
    }
}
