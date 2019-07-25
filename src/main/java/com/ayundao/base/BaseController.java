package com.ayundao.base;

import com.ayundao.base.utils.EncryptUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
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
     * 负责人
     */
    public static final String ROLE_MANAGER = "manager";
    /**
     * 发布人
     */
    public static final String ROLE_PUBLISHER = "publisher";
    /**
     * 管理员
     */
    public static final String ROLE_ADMIN = "admin";
    /**
     * 用户
     */
    public static final String ROLE_USER = "user";
    /**
     * 审核人
     */
    public static final String ROLE_AUDITOR = "auditor";

    /**
     * 删除
     */
    public static final String PERMISSION_DELETE = "delete";
    /**
     * 添加
     */
    public static final String PERMISSION_ADD = "add";
    /**
     * 查看
     */
    public static final String PERMISSION_VIEW = "view";
    /**
     * 发布
     */
    public static final String PERMISSION_RELEASE = "release";
    /**
     * 锁定
     */
    public static final String PERMISSION_LOCK = "lock";
    /**
     * 审核
     */
    public static final String PERMISSION_EXAMINE = "examine";
    /**
     * 修改
     */
    public static final String PERMISSION_MODIFY = "modify";
    /**
     * 禁用
     */
    public static final String PERMISSION_DISABLE = "disable";

    /**
     * "验证结果"属性名称
     */
    private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";
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
     * 设置用户密码
     * @param password
     * @return
     */
    public String setPassword(String password, String salt) {
        return EncryptUtils.getSaltMD5(password, salt);
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

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getSalt() {
        return EncryptUtils.getSalt();
    }
}