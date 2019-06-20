package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.EthicsRecord;
import com.ayundao.service.EthicsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: ink-feather
 * @Description: 医德档案
 * @Date: 2019/6/12 15:37
 */
@RestController
@RequestMapping("/ethics")
public class EthicsRecordController extends BaseController {

    @Autowired
    private EthicsRecordService ethicsRecordService;

    /**
     * @api {post} /ethics/add 新增医德档案
     * @apiGroup EthicsRecord
     * @apiVersion 1.0.0
     * @apiDescription 新增医德档案
     * @apiParam {EthicsRecord} params
     * @apiParamExample {json} 请求样例：
     *                /ethics/add?user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     */
    @PostMapping("/add")
    public JsonResult add(EthicsRecord params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            params.setId(UUID.randomUUID().toString());
            return JsonResult.success(ethicsRecordService.save(params));
        }
    }

    /**
     * @api {post} /ethics/modify 修改医德档案
     * @apiGroup EthicsRecord
     * @apiVersion 1.0.0
     * @apiDescription 修改医德档案
     * @apiParam {EthicsRecord} params
     * @apiParamExample {json} 请求样例：
     *                 /ethics/modify?id=\"id\"&user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     */
    @PostMapping("/modify")
    public JsonResult modify(EthicsRecord params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            return JsonResult.success(ethicsRecordService.save(params));
        }
    }

    /**
     * @api {get} /ethics/getlist 分页查询所有
     * @apiGroup EthicsRecord
     * @apiVersion 1.0.0
     * @apiDescription
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *                /ethics/getlist?page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     */
    @PostMapping("/getlist")
    public JsonResult getList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(ethicsRecordService.selectAll(pageable));
    }

    /**
     * @api {get} /ethics/selectbydepart 根据部门查询所有，分页
     * @apiGroup EthicsRecord
     * @apiVersion 1.0.0
     * @apiDescription 根据部门查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *                /ethics/selectbydepart?params=\"id\"&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     */
    @PostMapping("/selectbydepart")
    public JsonResult selectByDepart(String params,@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(ethicsRecordService.selectByDepart(params,pageable));
    }

    /**
     * @api {get} /ethics/selectbygroup 根据组织查询所有，分页
     * @apiGroup EthicsRecord
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *                /ethics/selectbygroup?params=\"id\"&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     */
    @PostMapping("/selectbygroup")
    public JsonResult selectByGroup(String params,@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(ethicsRecordService.selectByGroup(params,pageable));
    }

    /**
     * @api {get} /ethics/selectbysubject 根据机构查询所有，分页
     * @apiGroup EthicsRecord
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *                /ethics/selectbysubject?params=\"id\"&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     */
    @PostMapping("/selectbysubject")
    public JsonResult selectBySubject(String params,@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(ethicsRecordService.selectBySubject(params,pageable));
    }
}
