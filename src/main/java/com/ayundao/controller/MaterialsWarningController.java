package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.MaterialsRational;
import com.ayundao.entity.MaterialsWarning;
import com.ayundao.service.MaterialsWarningService;
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
 * @Description: 耗材预警
 * @Date: 2019/6/12 15:46
 */
@RestController
@RequestMapping("/materials")
public class MaterialsWarningController extends BaseController {

    @Autowired
    private MaterialsWarningService warningService;

    /**
     * @api {post} /materials/add 新增耗材预警
     * @apiGroup MaterialsWarning
     * @apiVersion 1.0.0
     * @apiDescription 新增耗材预警
     * @apiParam {MaterialsWarning} params
     * @apiParamExample {json} 请求样例：
     *              /materials/add?user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
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
    @PostMapping("/add")
    public JsonResult add(MaterialsWarning params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            params.setId(UUID.randomUUID().toString());
            return JsonResult.success(warningService.save(params));
        }
    }

    /**
     * @api {post} /materials/add1 新增耗材点评
     * @apiGroup MaterialsWarning
     * @apiVersion 1.0.0
     * @apiDescription 新增耗材点评
     * @apiParam {MaterialsWarning} params
     * @apiParamExample {json} 请求样例：
     *          /materials/add1?materials=MaterialsWarning&rational=\"是否合理\"&remark=\"点评内容\"&remarkId=User&remarkName=User
     * @apiSuccess (200) {String} code 200:成功</br>
     * 801:参数为空</br>
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
    @PostMapping("/add1")
    public JsonResult add(MaterialsRational params) {
        params.setId(UUID.randomUUID().toString());
        return JsonResult.success(warningService.save(params));

    }

    /**
     * @api {post} /materials/modify 修改耗材预警
     * @apiGroup MaterialsWarning
     * @apiVersion 1.0.0
     * @apiDescription 修改耗材预警
     * @apiParam {MaterialsWarning} params
     * @apiParamExample {json} 请求样例：
     *              /materials/modify?id=\"id\"&user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
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
    @PostMapping("/modify")
    public JsonResult modify(MaterialsWarning params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            return JsonResult.success(warningService.save(params));
        }
    }

    /**
     * @api {get} /materials/getlist 分页查询所有
     * @apiGroup MaterialsWarning
     * @apiVersion 1.0.0
     * @apiDescription
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /materials/getlist?page=页数&size=条数
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
    @PostMapping("/getlist")
    public JsonResult getList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(warningService.selectAll(pageable));
    }

    /**
     * @api {get} /materials/selectbydepart 根据部门查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /materials/selectbydepart?params=id&page=页数&size=条数
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
    public JsonResult selectByDepart(String params,@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(warningService.selectByDepart(params, pageable));
    }

    /**
     * @api {get} /materials/selectbygroup 根据组织查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /materials/selectbygroup?params=id&page=页数&size=条数
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
    public JsonResult selectByGroup(String params,@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(warningService.selectByGroup(params, pageable));
    }

    /**
     * @api {get} /materials/selectbysubject 根据机构查询所有，分页
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *          /materials/selectbysubject?params=id&page=页数&size=条数
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
    @PostMapping("/selectbysubject")
    public JsonResult selectBySubject(String params,@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(warningService.selectBySubject(params, pageable));
    }

    /**
     * @api {get} /materials/findbymaterialsid 根据耗材id查询点评
     * @apiGroup EducationOfCleanPolitics
     * @apiVersion 1.0.0
     * @apiDescription 根据耗材id查询点评
     * @apiParam {String} params
     * @apiParamExample {json} 请求样例：
     *              /materials/findbymaterialsid?params=id
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
    @PostMapping("/findbymaterialsid")
    public JsonResult findByMaterialsId(String params) {
        return warningService.findByMaterialsId(params);
    }
}
