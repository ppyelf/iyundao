package com.ayundao.base;

import com.ayundao.base.utils.JsonResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: GlobalDefaultExceptionHandler
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/30 16:07
 * @Description: 全局请求异常拦截器
 * @Version: V1.0
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {



    private JsonResult jsonResult = JsonResult.failure(800, "");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult processMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((s1, s2) -> s1.concat(",").concat(s2)).get();
        jsonResult.setCode(800);
        jsonResult.setMessage("参数校验失败:" + errorMessage);
        return jsonResult;
    }

    @ExceptionHandler(BindException.class)
    public JsonResult processBindException(BindException be) {
        Map<String, Object> responseBody = new HashMap<>(3);
        responseBody.put("code", HttpStatus.BAD_REQUEST.value());
        String errorMessage = be.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((s1, s2) -> s1.concat(",").concat(s2)).get();
        jsonResult.setCode(801);
        jsonResult.setMessage("参数校验失败:" + errorMessage);
        return jsonResult;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public JsonResult processIllegalArgumentException(IllegalArgumentException iae) {
        jsonResult.setMessage("参数校验失败:校验参数不能为空");
        jsonResult.setCode(HttpStatus.NOT_FOUND.value());
        return jsonResult;
    }

    @ExceptionHandler(AuthenticationException.class)
    public JsonResult processAuthenticationException(AuthenticationException e) {
        jsonResult.setCode(803);
        jsonResult.setMessage(e.getMessage());
        return jsonResult;
    }

    @ExceptionHandler(AuthorizationException.class)
    public JsonResult processAuthorizationException(AuthorizationException e) {
        jsonResult.setCode(804);
        jsonResult.setMessage(e.getMessage());
        return jsonResult;
    }
}
