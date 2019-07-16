package com.ayundao.base;

import com.alibaba.fastjson.JSONArray;
import com.ayundao.base.utils.EncryptUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.shiro.RedisManager;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * @ClassName: BaseController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: Controller - 基类
 * @Version: V1.0
 */
@Component
public abstract class BaseController {

    /**
     * 错误消息
     */
    protected static final String ERROR_MESSAGE = "common.message.error";

    /**
     * "验证结果"属性名称
     */
    private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";
    private static final String USER_INFO = ":userInfo";
    /**
     * 默认返回结果
     */
    public JsonResult jsonResult = JsonResult.success();
    @Autowired
    private Validator validator;
    @Value("${server.salt}")
    private String salt;
    @Value("${server.upload}")
    public String uploadPath;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRelationService userRelationService;
    private String account;
    private User user;

    /**
     * 数据验证
     *
     * @param target 验证对象
     * @param groups 验证组
     * @return 验证结果
     */
    protected boolean isValid(Object target, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
        if (constraintViolations.isEmpty()) {
            return true;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
        return false;
    }

    /**
     * 数据验证
     *
     * @param targets 验证对象
     * @param groups  验证组
     * @return 验证结果
     */
    protected boolean isValid(Collection<Object> targets, Class<?>... groups) {
        for (Object target : targets) {
            if (!isValid(target, groups)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数据验证
     *
     * @param type     类型
     * @param property 属性
     * @param value    值
     * @param groups   验证组
     * @return 验证结果
     */
    protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
        Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
        if (constraintViolations.isEmpty()) {
            return true;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
        return false;
    }

    /**
     * 存储当前用户
     *
     * @param req
     * @param user
     * @return
     */
    public User setCurrentUser(HttpServletRequest req, User user) {
        //用户信息
        account = user.getAccount();
        account = EncryptUtils.DESencode(user.getPassword(), salt);

        setUser(user);
        redisManager.set(account+USER_INFO, JsonUtils.getJson(user).toString());
        req.getSession().setAttribute("i-YunDao-account", account);
        return this.user;
    }

    /**
     * 设置用户密码
     * @param password
     * @return
     */
    public String setPassword(String password, String salt) {
        return EncryptUtils.getSaltMD5(password, salt);
    }

    /**
     * 存储用户关系
     * @param list
     */
    private void saveRelations(List<? extends BaseEntity> list, String name) {
        if (!list.isEmpty()) {
            String[] ids =  new String[list.size()];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = (String) list.get(i).getId();
            }
            redisManager.set(account + name, JsonUtils.toJson(ids));
        }
    }


    public User getUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (request.getSession().getAttribute("i-YunDao-account") == null) {
            return null;
        }
        account = request.getSession().getAttribute("i-YunDao-account").toString();
        user = user == null ? new User() : user;
        user = redisManager.get(account+USER_INFO) != null
                ? toUser(redisManager.get(account+USER_INFO).toString())
                : null;
        if (user == null) {
            return null;
        } 
        if (StringUtils.isBlank(user.getId())) {
            user = userService.findByAccount(EncryptUtils.DESdecode(account, salt));
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User toUser(String str) {
        try {
            if (StringUtils.isEmpty(str)) {
                return null;
            }
            JSONObject json = new JSONObject(str);
            user.setId(json.get("id").toString());
            user.setAccount(json.get("account").toString());
            user.setName(json.get("name").toString());
            user.setRemark(json.get("remark").toString());
            user.setSalt(json.get("salt").toString());
            user.setPassword(json.get("password").toString());
            user.setSex(Integer.parseInt(json.get("sex").toString()));
            for (User.USER_TYPE type : User.USER_TYPE.values()) {
                if (type.toString().equals(json.get("userType").toString())) {
                    user.setUserType(type);
                    break;
                }
            }
            for (User.ACCOUNT_TYPE type : User.ACCOUNT_TYPE.values()) {
                if (type.toString().equals(json.get("status").toString())) {
                    user.setStatus(type);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 退出登录注销数据
     */
    public void loginOut(String account) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.getSession().removeAttribute("i-YunDao-account");
        redisManager.del(EncryptUtils.DESencode(account, salt)+USER_INFO);
        setUser(null);
        this.user = null;
    }

    /**
     * 获取用户关系
     */
    public List<UserRelation> getUserRelation(User user) {
        return user != null
                ? userRelationService.findByUserId(user.getId())
                : null;
    }

    public Validator getValidator() {
        return validator;
    }

    /**
     * 获取上传文件的json
     * @param entity
     * @return
     */
    public com.alibaba.fastjson.JSONObject getUploadJson(Object entity) {
        com.alibaba.fastjson.JSONObject json = JsonUtils.getJson(entity);
        json.put("url", uploadPath + json.get("url").toString());
        return json;
    }

    /**
     * 获取上传文件array
     * @param entities
     * @return
     */
    public JSONArray getUploadArr(Iterable<? extends BaseEntity> entities) {
        JSONArray arr = new JSONArray();
        for (BaseEntity entity : entities) {
            com.alibaba.fastjson.JSONObject json = JsonUtils.getJson(entities);
            json.put("url", uploadPath + json.get("url").toString());
            arr.add(json);
        }
        return arr;
    }
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getSalt() {
        return EncryptUtils.getSalt();
    }
}