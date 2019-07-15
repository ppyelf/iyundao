package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.ExcelUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: PMAssessController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/13
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@RestController
@RequestMapping("/PMAssess")
public class PMAssessController extends BaseController{

    @Autowired
    private PMAssessService pmAssessService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserService userService;



    /**
     * @api {GET} /PMAssess/downloadPioneerIndex 下载先锋指数
     * @apiGroup PMAssess
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *               /PMAssess/downloadPioneerIndex
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": []
     * }
     */
    @GetMapping("/downloadPioneerIndex")
    public JsonResult downloadPioneerIndex(HttpServletResponse resp){
        try {
            ExcelUtils.downloadWorkBook("先锋指数",PioneerIndex.class,resp);
        }catch (IOException e){
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
     * @api {POST} /PMAssess/importPioneerIndex 导入先锋指数
     * @apiGroup PMAssess
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /PMAssess/importPioneerIndex
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": []
     * }
     */
    @PostMapping("/importPioneerIndex")
    public JsonResult importPioneerIndex(MultipartFile file){
        List<PioneerIndex> list = ExcelUtils.readExcel(file, PioneerIndex.class);
        list = pmAssessService.saveAllPioneerIndex(list);
        jsonResult.setData(converPioneerIndex(list));
        return jsonResult;
    }




    /**
    * @api {POST} /PMAssess/pagePioneerIndex 先锋指数分页列表
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} id 必填
    * @apiParamExample {json} 请求样例:
    *               /PMAssess/pagePioneerIndex?page=2&size=2
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data": {"total": 8,"totalPage": 1,"page": 0,"content": [{"score": "23","groupId": "","name": "","departId": "","id": "4028d8816bf34e2f016bf3514a1b000a","userId": "0a4179fc06cb49e3ac0db7bcc8cf0882","subjectId": ""},{"score": "22","groupId": "","name": "","departId": "","id": "4028d8816bf34e2f016bf3514a1c000b","userId": "402881916ba10b8a016ba113adbc0006","subjectId": ""}]}
    * }
    */
    @PostMapping("pagePioneerIndex")
    public JsonResult pagePioneerIndex(@RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "10")int size){

    Page<PioneerIndex> pioneerIndexPage = pmAssessService.findAllForPage(new Pageable(page,size));
    jsonResult.setData(JsonUtils.getPage(pioneerIndexPage));
        return  jsonResult;
    }


    /**
    * @api {POST} /PMAssess/findSearch 先锋指数搜索
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} id 必填
    * @apiParamExample {json} 请求样例:
    *                /PMAssess/findSearch?property=userId&value=402881916ba10b8a016ba113adbc0006&page=0&size=10
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *       "data": {"total": 4,"totalPage": 1,"page": 0,"content": [{"score": "22","groupId": "","name": "","departId": "","id": "4028d8816bf34e2f016bf3502aef0007","userId": "402881916ba10b8a016ba113adbc0006","subjectId": ""},{"score": "22","groupId": "","name": "","departId": "","id": "4028d8816bf34e2f016bf351471a0009","userId": "402881916ba10b8a016ba113adbc0006","subjectId": ""},{"score": "22","groupId": "","name": "","departId": "","id": "4028d8816bf34e2f016bf3514a1c000b","userId": "402881916ba10b8a016ba113adbc0006","subjectId": ""},{"score": "22","groupId": "","name": "","departId": "","id": "4028d8816bf34e2f016bf3514d12000d","userId": "402881916ba10b8a016ba113adbc0006","subjectId": ""}]}
    * }
    */
    @PostMapping("findSearch")
    public JsonResult findBydeption(String property,
                                    String value,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        if (StringUtils.isBlank(property)){
            return JsonResult.paramError();
        }
        Pageable pageable = new Pageable(page,size);
        pageable.setSearchProperty(property);
        pageable.setSearchValue(value);
        Page<PioneerIndex> pioneerIndexPage = pmAssessService.findByProperty(pageable);

        jsonResult.setData(JsonUtils.getPage(pioneerIndexPage));
        return jsonResult;
    }


    /**
     * @api {POST} /PMAssess/lists 先锋指数查询
     * @apiGroup PMAssess
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParam {int} type (0-默认,本日;1-本周;2-本月;3-本年)
     * @apiParamExample {json} 请求示例:
     *                  ?type=0
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/lists")
    public JsonResult lists(@RequestParam(defaultValue = "0")int type){
        List<PioneerIndex> list = pmAssessService.findOrderByCreatedTime(type);
        jsonResult.setData(converPioneerIndex(list));
        return jsonResult;
    }


    /**
     * 转换先锋指数
     * @param list
     * @return
     */
    private JSONArray converPioneerIndex(List<PioneerIndex> list) {
        JSONArray arr = new JSONArray();
        for (PioneerIndex pi : list){
            JSONObject json = new JSONObject();
            json.put("name",pi.getName());
            json.put("score",pi.getScore());
            Subject subject = subjectService.find(pi.getSubjectId());
            Depart depart = departService.findById(pi.getDepartId());
            Groups groups = groupsService.findById(pi.getGroupId());
            User user = userService.findById(pi.getUserId());
            if (subject !=null){
                json.put("subject", JsonUtils.getJson(subject));
            }
            if (user !=null){
                json.put("user", JsonUtils.getJson(user));
            }
            if (depart != null) {
                json.put("depart", JsonUtils.getJson(depart));
            }
            if (groups != null) {
                json.put("groups", JsonUtils.getJson(groups));
            }
            arr.add(json);
        }
        return arr;
    }
}
