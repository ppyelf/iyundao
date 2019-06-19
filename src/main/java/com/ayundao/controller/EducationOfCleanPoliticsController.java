package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.EducationOfCleanPolitics;
import com.ayundao.entity.EducationOfCleanPoliticsAccessory;
import com.ayundao.service.EducationOfCleanPoliticsService;
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
 * @Description: 廉政教育
 * @Date: 2019/6/12 14:35
 */
@RestController
@RequestMapping("/education")
public class EducationOfCleanPoliticsController extends BaseController {

    @Autowired
    private EducationOfCleanPoliticsService cleanPoliticsService;

    /**
     * @api {post} /education/add 新增廉政教育
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 新增廉政教育
     * @apiParam {EducationOfCleanPolitics} params
     * @apiParamExample {json} 请求样例：
     *      /education/add?user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *
     */
    @PostMapping("/add")
    public JsonResult add(EducationOfCleanPolitics params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            params.setId(UUID.randomUUID().toString());
            return JsonResult.success(cleanPoliticsService.save(params));
        }
    }

    /**
     * @api {post} /education/add1 新增廉政教育附件
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 新增廉政教育附件
     * @apiParam {EducationOfCleanPoliticsAccessory} params
     * @apiParamExample {json} 请求样例：
     *          /education/add1?education=EducationOfCleanPolitics&videoName=\"视频名称\"&videoUrl=\"视频路径\"&time=\"上传时间\"&userId=\"操作人id\"&userName=\"操作人姓名\"
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping("/add1")
    public JsonResult add1(EducationOfCleanPoliticsAccessory params) {
        params.setId(UUID.randomUUID().toString());
        return JsonResult.success(cleanPoliticsService.save(params));
    }

    /**
     * @api {post} /education/modify 修改廉政教育
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 修改药材预警
     * @apiParam {EducationOfCleanPolitics} params
     * @apiParamExample {json} 请求样例：
     *              /education/modify?id=\"id\"&user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
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
    @PostMapping("/modify")
    public JsonResult modify(EducationOfCleanPolitics params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            return JsonResult.success(cleanPoliticsService.save(params));
        }
    }

    /**
     * @api {get} /education/getlist 分页查询所有
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 修改药材预警
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *      /education/getlist?page=页数&size=条数
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
    @PostMapping("/getlist")
    public JsonResult getList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(cleanPoliticsService.selectAll(pageable));
    }

    /**
     * @api {get} /education/selectbydepart 根据部门查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *      /education/selectbydepart?params=\"id\"&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     * 404:未查询到此用户</br>
     * 600:参数异常</br>
     * 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/selectbydepart")
    public JsonResult selectByDepart(String params, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(cleanPoliticsService.selectByDepart(params, pageable));
    }

    /**
     * @api {get} /education/selectbygroup 根据组织查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /education/selectbygroup?params=\"id\"&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     * 404:未查询到此用户</br>
     * 600:参数异常</br>
     * 601:机构参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @PostMapping("/selectbygroup")
    public JsonResult selectByGroup(String params, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(cleanPoliticsService.selectByGroup(params, pageable));
    }

    /**
     * @api {get} /education/selectbysubject 根据机构查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *              /education/selectbysubject?params=\"id\"&page=页数&size=条数
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
    public JsonResult selectBySubject(String params,  @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(cleanPoliticsService.selectBySubject(params, pageable));
    }

    /**
     * @api {get} /education/findbyeid 根据廉政教育id查询附件
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据廉政教育id查询附件
     * @apiParam {String} params
     * @apiParamExample {json} 请求样例：
     *      /education/findbyeid?params=id
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
    @PostMapping("/findbyeid")
    public JsonResult findByEid(String params) {
        return cleanPoliticsService.findByEducationId(params);
    }
}
