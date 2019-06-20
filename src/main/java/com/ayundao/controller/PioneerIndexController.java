package com.ayundao.controller;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Pioneer;
import com.ayundao.entity.PioneerIndex;
import com.ayundao.service.PioneerIndexService;
import com.ayundao.service.PioneerService;
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
 * @Description: 先锋人物
 * @Date: 2019/6/12 16:43
 */
@RestController
@RequestMapping("/pioneerindex")
public class PioneerIndexController {

    @Autowired
    private PioneerIndexService pioneerService;

    /**
     * @api {post} /pioneerindex/add 新增先锋指数
     * @apiGroup PioneerIndex
     * @apiVersion 1.0.0
     * @apiDescription 新增先锋指数
     * @apiParam {PioneerIndex} params
     * @apiParamExample {json} 请求样例：
     *                /pioneerindex/add?user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(PioneerIndex params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            params.setId(UUID.randomUUID().toString());
            return JsonResult.success(pioneerService.save(params));
        }
    }

    /**
     * @api {post} /pioneerindex/modify 修改先锋指数
     * @apiGroup PioneerIndex
     * @apiVersion 1.0.0
     * @apiDescription 修改先锋指数
     * @apiParam {PioneerIndex} params
     * @apiParamExample {json} 请求样例：
     *                 /pioneerindex/modify?id=\"id\"&user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(PioneerIndex params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            return JsonResult.success(pioneerService.save(params));
        }
    }

    /**
     * @api {get} /pioneerindex/getlist 分页查询所有
     * @apiGroup PioneerIndex
     * @apiVersion 1.0.0
     * @apiDescription
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *                /pioneerindex/getlist?page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户</br>
     *                                 600:参数异常</br>
     *                                 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/getlist")
    public JsonResult getList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(pioneerService.selectAll(pageable));
    }

    /**
     * @api {get} /pioneerindex/selectbydepart 根据部门查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /pioneerindex/selectbydepart?params=id&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
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
    public JsonResult selectByDepart(String params, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(pioneerService.selectByDepart(params, pageable));
    }

    /**
     * @api {get} /pioneerindex/selectbygroup 根据组织查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *      /pioneerindex/selectbygroup?params=id&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
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
    public JsonResult selectByGroup(String params, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(pioneerService.selectByGroup(params, pageable));
    }

    /**
     * @api {get} /pioneerindex/selectbysubject 根据机构查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /pioneerindex/selectbysubject?params=id&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/selectbysubject")
    public JsonResult selectBySubject(String params, @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(pioneerService.selectBySubject(params, pageable));
    }


}
