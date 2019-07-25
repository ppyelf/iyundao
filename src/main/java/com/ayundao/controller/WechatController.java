package com.ayundao.controller;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.shiro.JwtToken;
import com.ayundao.base.shiro.SecurityConsts;
import com.ayundao.base.utils.HttpUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.WxUtils;
import com.ayundao.entity.User;
import com.ayundao.entity.UserApp;
import com.ayundao.service.UserAppService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: WechatController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/20 9:27
 * @Description: 控制层 - 微信
 * @Version: V1.0
 */
@RestController
@RequestMapping("/wx")
public class WechatController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private WxUtils wxUtils;

    @PostMapping("/userAuthor")
    public JsonResult userAuthor(String code,
                                 String avatarUrl,
                                 String nickName,
                                 String city,
                                 String province,
                                 @CurrentUser User user) {
        try {
            if (user == null || StringUtils.isBlank(user.getId())) {
                jsonResult.setCode(404);
                jsonResult.setMessage("用户未登录/登录异常");
                return jsonResult;
            }
            // 配置请求参数
            Map<String, String> param = new HashMap<>(4);
            param.put("appid", wxUtils.getId());
            param.put("secret", wxUtils.getSecret());
            param.put("js_code", code);
            param.put("grant_type", WxUtils.WX_LOGIN_GRANT_TYPE);
            // 发送请求
            String wxResult = HttpUtils.doGet(WxUtils.WX_LOGIN_URL, param);
            JSONObject json = JSONObject.parseObject(wxResult);
            if (json.get("errcode") != null) {
                return JsonResult.failure(800, json.get("errmsg") == null ? null : json.get("errmsg").toString());
            }
            // 获取参数返回的
            String session_key = json.get("session_key").toString();
            String open_id = json.get("openid").toString();
            UserApp userApp = userAppService.findByOpenId(open_id);
            if(userApp != null){
                userApp.setLastLoginTime(new Date());
                userAppService.save(userApp);
            }else{
                userApp = userAppService.create(open_id, avatarUrl, nickName, city, province, UserApp.APP_TYPE.WeApp, user);
            }

            // 封装返回小程序
            jsonResult.setData(json);
        } catch (NullPointerException ex) {
            jsonResult.setCode(400);
            jsonResult.setMessage("用户未登录");
            return jsonResult;
        }
        return jsonResult;
    }

    @PostMapping("/login")
    public JsonResult login(String openId, HttpServletResponse resp) {
        UserApp app = userAppService.findByOpenId(openId);
        if (app == null) {
            return JsonResult.failure(800, "请先登录");
        }
        User user = userService.findById(app.getUser().getId());
        JwtToken token = new JwtToken(user.getAccount(), user.getPassword(), true, SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + user.getAccount());
        jsonResult = login(SecurityUtils.getSubject(), token);
        if (jsonResult.getCode() == JsonResult.CODE_SUCCESS) {
            user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user != null && user.getAccount().equals(user.getAccount())) {
                return loginSuccess(user.getAccount(), resp);
            }
        }
        return JsonResult.failure(400,"账号不存在");
    }

}
