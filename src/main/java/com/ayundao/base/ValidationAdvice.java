package com.ayundao.base;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ValidationAdvice
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/30 16:07
 * @Description: Advice -- 数据校验
 * @Version: V1.0
 */
@ControllerAdvice
public class ValidationAdvice{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity processMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> responseBody = new HashMap<>(3);
        responseBody.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        String errorMessage = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((s1, s2) -> s1.concat(",").concat(s2)).get();
        responseBody.put("message", "参数校验失败:" + errorMessage);
        return new ResponseEntity(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity processBindException(BindException be) {
        Map<String, Object> responseBody = new HashMap<>(3);
        responseBody.put("code", HttpStatus.BAD_REQUEST.value());
        String errorMessage = be.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((s1, s2) -> s1.concat(",").concat(s2)).get();
        responseBody.put("message", "参数校验失败:" + errorMessage);
        return new ResponseEntity(responseBody, null, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity processIllegalArgumentException(IllegalArgumentException iae) {
//        Map<String, Object> responseBody = new HashMap<>(3);
//        responseBody.put("message", "参数校验失败:校验参数不能为空");
//        responseBody.put("code", HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity(responseBody, null, HttpStatus.NOT_FOUND);
//    }
}
