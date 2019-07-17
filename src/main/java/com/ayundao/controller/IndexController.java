package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.EncryptUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.User;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: IndexController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:40
 * @Description: 首页
 * @Version: V1.0
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    /**
     * @api {POST} /index/login 用户登录
     * @apiGroup Index
     * @apiVersion 1.0.0
     * @apiDescription 用于用户登录
     * @apiParam {String} account 用户名
     * @apiParam {String} password 密码
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
    public JsonResult login(String account, String password, HttpServletRequest req, HttpServletResponse resp) {
//        out(account);
        User user = getUser();
        JsonResult jsonResult = JsonResult.success();
        if (user != null && user.getAccount().equals(account)) {
            jsonResult.setCode(200);
            jsonResult.setMessage("登录成功");
            jsonResult.setData(JsonUtils.getJson(user));
            return jsonResult;
        }
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)) {
            user = userService.findByAccount(account);
            if (user == null) {
                jsonResult.setCode(404);
                jsonResult.setMessage("用户名/密码不正确");
                return jsonResult;
            }
            if (EncryptUtils.getSaltverifyMD5(password, user.getPassword())) {
                //封装用户
                setCurrentUser(req, user);
                jsonResult.setCode(200);
                jsonResult.setMessage("登录成功");
                jsonResult.setData(JsonUtils.getJson(user));
                return jsonResult;
            }
        } else {
            return JsonResult.failure(600, "参数错误");
        }
        return JsonResult.failure(400, "账号异常");
    }


    /**
     * @api {POST} /index/out 退出登录
     * @apiGroup Index
     * @apiVersion 1.0.0
     * @apiDescription 退出登录
     * @apiParam {String} account 用户名
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
    @PostMapping("/out")
    public JsonResult out(String account) {
        loginOut(account);
        return JsonResult.success("退出登录成功");
    }

}
