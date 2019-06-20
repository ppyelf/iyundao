package com.ayundao.controller;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Pioneer;
import com.ayundao.entity.PioneerType;
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
@RequestMapping("/pioneer")
public class PioneerController {

    @Autowired
    private PioneerService pioneerService;

    /**
     * @api {post} /pioneer/add 新增先锋人物
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 新增先锋人物
     * @apiParam {Pioneer} params
     * @apiParamExample {json} 请求样例：
     *              /pioneer/add?user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
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
    @PostMapping("/add")
    public JsonResult add(Pioneer params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            params.setId(UUID.randomUUID().toString());
            return JsonResult.success(pioneerService.save(params));
        }
    }

    /**
     * @api {post} /pioneer/add1 新增先锋人物类别
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 新增先锋人物类别
     * @apiParam {PioneerType} params
     * @apiParamExample {json} 请求样例：
     *          /pioneer/add1?depart=Depart&group=Groups&subject=Subject&typeName=\"类别名称\"&flag=\"类别状态\"
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
    @PostMapping("/add1")
    public JsonResult add1(PioneerType params) {
        params.setId(UUID.randomUUID().toString());
        return JsonResult.success(pioneerService.save(params));
    }

    /**
     * @api {post} /pioneer/modify 修改先锋人物
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 修改先锋人物
     * @apiParam {Pioneer} params
     * @apiParamExample {json} 请求样例：
     *                  /pioneer/modify?id=\"id\"&user=User&score=\"分数\"&info=\"备用字段\"&...&\"info25\"=\"备用字段\"
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
    public JsonResult modify(Pioneer params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure("传入数据为空");
        } else {
            return JsonResult.success(pioneerService.save(params));
        }
    }

    /**
     * @api {get} /pioneer/getlist 分页查询所有
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *              /pioneer/getlist?page=页数&size=条数
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
        return JsonResult.success(pioneerService.selectAll(pageable));
    }

    /**
     * @api {get} /pioneer/selectbydepart 根据部门查询所有，分页
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *                  /pioneer/selectbydepart?params=id&page=页数&size=条数
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
     * @api {get} /pioneer/selectbygroup 根据组织查询所有，分页
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *              /pioneer/selectbygroup?params=id&page=页数&size=条数
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     *
     *
     */
    @PostMapping("/selectbygroup")
    public JsonResult selectByGroup(String params,  @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(pioneerService.selectByGroup(params, pageable));
    }

    /**
     * @api {get} /pioneer/selectbysubject 根据机构查询所有，分页
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询所有，分页
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *              /pioneer/selectbysubject?params=id&page=页数&size=条数
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
    public JsonResult selectBySubject(String params, @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return JsonResult.success(pioneerService.selectBySubject(params, pageable));
    }

    /**
     * @api {get} /pioneer/selecttypebysubject 根据机构查询类别
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据机构查询类别
     * @apiParam {String} params
     * @apiParamExample {json} 请求样例：
     *              /pioneer/selecttypebysubject?params=id
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
    @PostMapping("/selecttypebysubject")
    public JsonResult selectTypeBySubject(String params) {
        return pioneerService.selectTypeBySubject(params);
    }

    /**
     * @api {get} /pioneer/selecttypebydepart 根据部门查询类别
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据部门查询类别
     * @apiParam {String} params
     * @apiParamExample {json} 请求样例：
     *              /pioneer/selecttypebysubject?params=id
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/selecttypebydepart")
    public JsonResult selectTypeByDepart(String params) {
        return pioneerService.selectTypeByDepart(params);
    }

    /**
     * @api {get} /pioneer/selecttypebygroup 根据组织查询类别
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据组织查询类别
     * @apiParam {String} params
     * @apiParamExample {json} 请求样例：
     *                  /pioneer/selecttypebygroup?params=id
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
    @PostMapping("/selecttypebygroup")
    public JsonResult selectTypeByGroup(String params) {
        return pioneerService.selectTypeByGroup(params);
    }

    /**
     * @api {get} /pioneer/selectbytype 根据类别查询
     * @apiGroup Pioneer
     * @apiVersion 1.0.0
     * @apiDescription 根据类别查询
     * @apiParam {String} params
     * @apiParam {int}  page
     * @apiParam {int}  size
     * @apiParamExample {json} 请求样例：
     *              /pioneer/selectbytype?params=id&page=页数&size=条数
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
    @PostMapping("/selectbytype")
    public JsonResult selectByType(String params,@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pioneerService.findByType(params, pageable);
    }


}
