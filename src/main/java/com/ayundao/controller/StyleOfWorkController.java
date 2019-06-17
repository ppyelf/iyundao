package com.ayundao.controller;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.StyleOfWork;
import com.ayundao.entity.StyleOfWorkRecord;
import com.ayundao.service.StyleOfWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: ink-feather
 * @Description:
 * @Date: 2019/6/12 15:56
 */
@RestController
@RequestMapping("/style")
public class StyleOfWorkController {

    @Autowired
    private StyleOfWorkService styleOfWorkService;

    /**
     * @api {post} /style/add 新增行风效能
     * @apiGroup StyleOfWork
     * @apiVersion 1.0.0
     * @apiDescription 新增行风效能
     * @apiParamExample {json} 请求样例：
     * /style/add
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
    @PostMapping("/add")
    public JsonResult add(StyleOfWork params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            params.setId(UUID.randomUUID().toString());
            return JsonResult.success(styleOfWorkService.save(params));
        }
    }

    /**
     * @api {post} /style/add1 新增行风效能扣分记录
     * @apiGroup StyleOfWork
     * @apiVersion 1.0.0
     * @apiDescription 新增行风效能扣分记录
     * @apiParamExample {json} 请求样例：
     * /style/add1
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
    @PostMapping("/add1")
    public JsonResult add1(StyleOfWorkRecord params) {
        params.setId(UUID.randomUUID().toString());
        return JsonResult.success(styleOfWorkService.save(params));
    }

    /**
     * @api {post} /style/modify 修改行风效能
     * @apiGroup StyleOfWork
     * @apiVersion 1.0.0
     * @apiDescription 修改行风效能
     * @apiParamExample {json} 请求样例：
     * /style/modify
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
    @PostMapping("/modify")
    public JsonResult modify(StyleOfWork params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            return JsonResult.success(styleOfWorkService.save(params));
        }
    }

    /**
     * @api {get} /style/getlist 分页查询所有
     * @apiGroup StyleOfWork
     * @apiVersion 1.0.0
     * @apiDescription
     * @apiParamExample {json} 请求样例：
     * /style/getlist
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
    @PostMapping("/getlist")
    public JsonResult getList(Pageable pageable) {
        return JsonResult.success(styleOfWorkService.selectAll(pageable));
    }

    /**
     * @api {get} /style/selectbydepart 根据部门查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParamExample {json} 请求样例：
     * /style/selectbydepart
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
    public JsonResult selectByDepart(String params, Pageable pageable) {
        return JsonResult.success(styleOfWorkService.selectByDepart(params, pageable));
    }

    /**
     * @api {get} /style/selectbygroup 根据组织查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParamExample {json} 请求样例：
     * /style/selectbygroup
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
    public JsonResult selectByGroup(String params, Pageable pageable) {
        return JsonResult.success(styleOfWorkService.selectByGroup(params, pageable));
    }

    /**
     * @api {get} /style/selectbysubject 根据机构查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询所有，分页
     * @apiParamExample {json} 请求样例：
     * /style/selectbysubject
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
    @PostMapping("/selectbysubject")
    public JsonResult selectBySubject(String params, Pageable pageable) {
        return JsonResult.success(styleOfWorkService.selectBySubject(params, pageable));
    }

    /**
     * @api {get} /style/findbyworkid 根据行风效能id查询扣分记录
     * @apiGroup StyleOfWork
     * @apiVersion 1.0.0
     * @apiDescription 根据行风效能id查询扣分记录
     * @apiParamExample {json} 请求样例：
     * /style/findbyworkid
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
    @PostMapping("/findbyworkid")
    public JsonResult findByWorkId(String workId) {
        return styleOfWorkService.findByWorkId(workId);
    }

    /**
     * @api {get} /style/statistics 分组织统计科室行风效能平均分 倒序
     * @apiGroup StyleOfWork
     * @apiVersion 1.0.0
     * @apiDescription 分组织统计科室行风效能平均分 倒序
     * @apiParamExample {json} 请求样例：
     * /style/statistics
     * @apiSuccess (200) {String} code 200:成功</br>
     * 802:异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": "{\"version\":\"0\",\"id\":\"402881f46afe9429016afeaf39e30006\",\"lastModifiedDate\":\"20190528214417\",\"createdDate\":\"20190528214417\",\"name\":\"添加部门11\",\"subject\":\"{\"version\":\"1\",\"id\":\"402881f46afdef14016afe28796c000b\",\"lastModifiedDate\":\"20190528193528\",\"createdDate\":\"20190528191706\",\"name\":\"修改机构\",\"subjectType\":\"etc\"}\"}"
     * }
     */
    @GetMapping("/statistics")
    public JsonResult statistics() {
        return styleOfWorkService.statistics();
    }
}
