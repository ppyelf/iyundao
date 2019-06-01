package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.service.ButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ButtonController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 23:21
 * @Description: 控制层 -- 按钮
 * @Version: V1.0
 */
@RestController
@RequestMapping("/button")
public class ButtonController extends BaseController {

    @Autowired
    private ButtonService buttonService;

    /**
     * @api {get} /button/list 列表
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
     *                /button/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @GetMapping("/list")
    public JsonResult list() {
        return jsonResult;
    }

    /**
     * @api {post} /button/view 查看
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParamExample {json} 请求样例：
     *                /button/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"1\",\"id\":\"79daadcc0cb5402f9f97bf01eaa2da67\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"分-组织\",\"subject\":\"{\"version\":\"1\",\"id\":\"c72a2c6bd1e8428fac6706b217417831\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"分院\",\"subjectType\":\"part\"}\",\"user\":\"{\"version\":\"0\",\"id\":\"5cf0d3c3b0da4cbaad179e0d6d230d0c\",\"lastModifiedDate\":\"20190517111111\",\"createdDate\":\"20190517111111\",\"name\":\"测试用户\",\"status\":\"normal\",\"password\":\"b356a1a11a067620275401a5a3de04300bf0c47267071e06\",\"sex\":\"0\",\"account\":\"test\",\"remark\":\"未填写\",\"salt\":\"3a10624a300f4670\",\"userType\":\"normal\"}\",\"remark\":\"\"}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        return jsonResult;
    }

    /**
     * @api {post} /button/add 新增
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParamExample {json} 请求样例：
     *                /button/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping("/add")
    public JsonResult add() {
        return jsonResult;
    }

    /**
     * @api {post} /button/modify 修改
     * @apiGroup Button
     * @apiVersion 1.0.0
     * @apiDescription 修改
     * @apiParamExample {json} 请求样例：
     *                /button/modify
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     *                                 601:此机构不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping("/modify")
    public JsonResult modify() {
        return jsonResult;
    }

}
