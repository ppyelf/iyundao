package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: PMAssessController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/13
 * @Description: 实现 - 党员
 * @Version: V1.0
 */
@CrossOrigin
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
    JSONObject jsonObject = JsonUtils.getPage(pioneerIndexPage);
    jsonResult.setData(bianshen(jsonObject));
        return  jsonResult;
    }


    /**
    * @api {POST} /PMAssess/findSearch 先锋指数搜索
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} property 必填 模糊查询的字段名
     *                      name名字/userCode/subjectCode/departCode/groupCode/score分数/data评测日期
    * @apiParam {String} value 必填 模糊查询的值
    * @apiParam {String} page 必填 跳过的页数 默认0
    * @apiParam {String} size 必填 一页的数量  默认10
    * @apiParamExample {json} 请求样例:
    *                /PMAssess/findSearch?property=userCode&value=1&page=0&size=10
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *        "data": {"total": 1,"totalPage": 1,"page": 0,"content": [{"score": "0","data": "20181212111111","name": "","departCode": "","id": "11","subjectCode": "1","userCode": "11""groupCode": ""}]}
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
        JSONObject jsonObject = JsonUtils.getPage(pioneerIndexPage);
         jsonObject = bianshen(jsonObject);
        jsonResult.setData(jsonObject);
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
     * @api {GET} /PMAssess/DemocraticAppraisal 下载民主评议模板
     * @apiGroup PMAssess
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParamExample {json} 请求样例:
     *               /PMAssess/downloadDemocraticAppraisal
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
    @GetMapping("downloadDemocraticAppraisal")
    public JsonResult downloadDemocraticAppraisal(HttpServletResponse resp){
        try {
            ExcelUtils.downloadWorkBook("民主评议",DemocraticAppraisal.class,resp);
        }catch (IOException e){
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
    * @api {POST} /PMAssess/importDemocraticAppraisal 导入民主评议
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {MultipartFile} file 必填
    * @apiParamExample {json} 请求样例:
    *                /PMAssess/importDemocraticAppraisal
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
    @PostMapping("importDemocraticAppraisal")
    public JsonResult importDemocraticAppraisal(MultipartFile file){
        List<DemocraticAppraisal> list = ExcelUtils.readExcel(file, DemocraticAppraisal.class);
        list = pmAssessService.saveAllDemocraticAppraisal(list);
        jsonResult.setData(converDemocraticAppraisal(list));
        return jsonResult;
    }




    /**
    * @api {POST} /PMAssess/pageDemocraticAppraisal 明主评议分页列表
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParamExample {json} 请求样例:
    *               /PMAssess/pageDemocraticAppraisal?page=2&size=2
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data": {"total": 3,"totalPage": 1,"page": 0,"content": [{"result": "11","score": "11","data": "11","name": "11","departCode": "11","id": "4028d8816bf8a59a016bf8ab95850009","subjectCode": "11","userCode": "11","groupCode": "11"},{"result": "22","score": "22""data": "22","name": "22","departCode": "22","id": "4028d8816bf8a59a016bf8ab9586000a","subjectCode": "22","userCode": "22","groupCode": "22"}]}
    * }
    */
    @PostMapping("pageDemocraticAppraisal")
    public JsonResult pageDemocraticAppraisal(@RequestParam(defaultValue = "0")int page,
                                              @RequestParam(defaultValue = "10")int size){
        Page<DemocraticAppraisal> democraticAppraisalPage = pmAssessService.findDemoForPage(new Pageable(page,size));
        JSONObject object  = JsonUtils.getPage(democraticAppraisalPage);
        object = bianshen(object);
        jsonResult.setData(object);
        return jsonResult;
    }



    /**
    * @api {POST} /PMAssess/SearchDemocraticAppraisal 民主评议搜索
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} property 必填 模糊查询的字段名
     *   name名字/userCode/subjectCode/departCode/groupCode/result评测结果/score分数/data评测日期
    * @apiParam {String} value 必填 模糊查询的值
    * @apiParam {String} page 必填 跳过的页数 默认0
    * @apiParam {String} page 必填 一页的数量  默认10
    * @apiParamExample {json} 请求样例:
    *                /exam/view?id=4028d8816bcc9a32016bcccd9616000c
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data": {"total": 1,"totalPage": 1,"page": 0,"content": [{"score": "0","data": "20181212111111","name": "","departCode": "","id": "11","subjectCode": "1","userCode": "11""groupCode": ""}]}
    * }
    */
    @PostMapping("SearchDemocraticAppraisal")
    public  JsonResult SearchDemocraticAppraisal(String property,
                                                 String value,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        if (StringUtils.isBlank(property)){
            return JsonResult.paramError();
        }
        Pageable pageable = new Pageable(page,size);
        pageable.setSearchProperty(property);
        pageable.setSearchValue(value);
        Page<DemocraticAppraisal> democraticAppraisalPage = pmAssessService.findDemoByProperty(pageable);
        JSONObject object = JsonUtils.getPage(democraticAppraisalPage);
        object = bianshen(object);
        jsonResult.setData(object);
        return jsonResult;
    }


    /**
    * @api {POST} /PMAssess/annuallist 党员年报
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} year 默认0 表示今年   年份
    * @apiParamExample {json} 请求样例:
    *                /PMAssess/annuallist
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data":  {
                    "pioscoresum": 3,   先锋指数统计
                      "demscoresum": 44        明主评议总分
                      "name": "王",          用户名
                      "usercode": "11",      用户编码
                  "demscoreavg": 44,         民主评议平均得分
                  "pioscoreavg": 3          先锋指数的平均指数
                    qualified:不合格           合格情况为民主评议平均得分加先锋指数的平均指数 >120 为合格
    },
    * }
    */
    @PostMapping("/annuallist")
    public  JsonResult annuallist( @RequestParam(defaultValue = "0") String year){
        if ("0".equals(year)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
             year = sdf.format(date);
        }
        List<JSONObject> pio = pmAssessService.findPioForYear(year);
        List<JSONObject> Dem = pmAssessService.findDemForYear(year);
        JSONArray array = new JSONArray();
        for (JSONObject p : pio) {
            JSONObject object = new JSONObject();
            for (JSONObject d : Dem) {
                if (p.get("name").equals(d.get("name"))){
                    object.put("name",p.get("name"));
                    object.put("usercode",p.get("usercode"));
                    object.put("pioscoresum",p.get("scoresum"));
                    object.put("pioscoreavg",p.get("scoreavg"));
                    object.put("demscoresum",d.get("scoresum"));
                    object.put("demscoreavg",d.get("scoreavg"));
                    object.put("qualified",((Double.parseDouble(p.get("scoreavg").toString())+Double.parseDouble(d.get("scoreavg").toString()))>120?"合格":"不合格"));
                }
            }
            array.add(object);
        }
        jsonResult.setData(array);
        return  jsonResult;
    }

    /**
    * @api {POST} /PMAssess/findpeopleALL 查看个人党员年报情况
    * @apiGroup PMAssess
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} usercode 必填   用户编码
    * @apiParam {String} year   默认0 表示今年    年份
    * @apiParamExample {json} 请求样例:
    *                /PMAssess/findpeopleALL?usercode=001
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *       "data": {
                  "pioscoresum": 4, 先锋指数统计
                  "demscoresum": 44, 明主评议总分
                 "demscoreavg": 44,  明主评议平均指数
                 "pioscoreavg": 2   先锋指数的平均指数
                code": "001",         编码
                "sex": 0,    性别
                "name": "钱正",  姓名
                "remark": "暂无描述",    用户简介
                "id": "402881916ba10b8a016ba113adbc0006",  用户id
                "userType": "normal",    用户类型
                "status": null,   账号状态

                    }
    * }
    */
    @PostMapping("/findpeopleALL")
    public  JsonResult findpeopleALL(String  usercode,
                                     @RequestParam(defaultValue = "0") String year){
        User user = pmAssessService.findUserByusercode(usercode);
        if(user == null){
           return JsonResult.notFound("根据编号找不到用户实体");
        }
        JSONObject object = pmAssessService.findAllForPeople(user,usercode,year);
        jsonResult.setData(object);
        return jsonResult;
    }


    /**
     * 民主评议转换
     */
    private JSONArray converDemocraticAppraisal(List<DemocraticAppraisal> list){
        JSONArray arr = new JSONArray();
        for (DemocraticAppraisal da : list){
            JSONObject json = new JSONObject();
            json.put("name",da.getName());
            json.put("result",da.getResult());
            json.put("score",da.getScore());
            json.put("data",da.getData());
            Subject subject = subjectService.findByCode(da.getSubjectCode());
            Depart depart = departService.findByCode(da.getDepartCode());
            Groups groups = groupsService.findByCode(da.getGroupCode());
            User user = userService.findByCode(da.getUserCode());
            if (subject!= null){
                json.put("subject",JsonUtils.getJson(subject));
            }
            if (depart!=null){
                json.put("depart",JsonUtils.getJson(depart));
            }
            if (groups!=null){
                json.put("groups",JsonUtils.getJson(groups));
            }
            if (user != null){
                json.put("user",JsonUtils.getJson(user));
            }
            arr.add(json);
        }
        return  arr;
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
            Subject subject = subjectService.findByCode(pi.getSubjectCode());
            Depart depart = departService.findByCode(pi.getDepartCode());
            Groups groups = groupsService.findByCode(pi.getGroupCode());
            User user = userService.findByCode(pi.getUserCode());
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



    /**
     * Page转换JSONobject
     */
    private JSONObject bianshen(JSONObject jsonObject){
        JSONArray arr = jsonObject.getJSONArray("content");
        JSONArray jsonArray= new JSONArray();
        for (int i = 0; i<arr.size();i++){
            JSONObject obj = arr.getJSONObject(i);
             if (subjectService.find((String)obj.get("subjectCode"))!=null){
                 obj.put("subject",JsonUtils.getJson(subjectService.find((String)obj.get("subjectCode"))));
             }
             if (departService.findById((String)obj.get("departCode"))!=null){
                 obj.put("depart",JsonUtils.getJson(departService.findById((String)obj.get("departCode"))));
             }
             if (groupsService.findById((String)obj.get("groupCode"))!=null){
                 obj.put("group",JsonUtils.getJson(groupsService.findById((String)obj.get("groupCode"))));
             }
             if (userService.findById((String)obj.get("userCode"))!=null){
                 obj.put("user",JsonUtils.getJson(userService.findById((String)obj.get("userCode"))));
             }
            jsonArray.add(obj);
        }
        jsonObject.put("content",jsonArray);
        return jsonObject;
    }


}
