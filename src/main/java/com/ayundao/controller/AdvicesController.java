package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.UserRepository;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: AdvicesController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@RestController
@RequestMapping("/advices")
public class AdvicesController extends BaseController{

    @Autowired
    private AdvicesService advicesService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private DepartService departService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdvicesInfoDeparService advicesInfoDeparService;


    /**
     * @api {GET} /advices/list 列表
     * @apiGroup Advices
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例:
     *                /advices/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12959f90000","title": "我撒旦","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc129e1110002","title": "我","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12a88660004","title": "自行车","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12acad10006","title": "9880980","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12b05f60008","title": "测试","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12b1c9b000a","title": "测试111","type": "1","username": "钱正"}]
     * }
     */
    @GetMapping("/list")
    public JsonResult list(){
        List<Advices> advicesAll = advicesService.findAll();
        System.out.println(advicesAll);
        JSONArray arr = new JSONArray();
        JSONObject object ;
        for (Advices advices : advicesAll){
            object = new JSONObject();
            object.put("id",advices.getId());
            object.put("title",advices.getTitle());
            object.put("type",advices.getType());
            object.put("issuertime",advices.getIssuertime());
            object.put("username",advices.getUser().getName());
            object.put("advicestext",advices.getAdvicestext());
            object.put("advicesstatus",advices.getAdvicesstatus());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }



    /**
     * @api {POST} /advices/add 新增
     * @apiGroup Advices
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} title  标题 必填
     * @apiParam {String} type   消息类型
     * @apiParam {String} issuertime 发布时间
     * @apiParam {String} advicestext 任务内容
     * @apiParam {String} userid 必填 发布人员id
     * @apiParam {String[]} subjectIds 机构id
     * @apiParam {String[]} departIds   部门id
     * @apiParam {String[]} groupIds    组织id
     * @apiParam {String[]} userids     用户id
     * @apiParamExample {json} 请求样例:
     *                /advices/add?title=消息标题&type=1&issuertime=2018-12-12 12:12:12&tasktext=消息简介&userid=402881916ba10b8a016ba113adbc0006&subjectIds=402881916b9d3031016b9d626593000c,bfc5bd62010f467cbbe98c9e4741733b&departIds&groupIds=402881916b9d3031016b9d63a172000d,402881916b9d3031016b9d63d7af000e&userids=402881916ba10b8a016ba113adbc0006
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     *                                 601:任务名称不能为空</br>
     *                                 602:发布人id有误</br>
     *                                 605:机构不存在</br>
     *                                 606:部门不存在</br>
     *                                 607:组织不存在</br>
     *                                 608:用户不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": {"advicestext": "","issuertime": "2018-12-12 12:12:12","advicesstatus": "","id": "4028d8816bcc9a32016bccc68bfc0006","type": "1","title": "消息标题1"}
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String title,
                          String type,
                          String issuertime,
                          String advicesstatus,
                          String advicestext,
                          String userid,
                          String[] subjectIds,
                          String[] departIds,
                          String[] groupIds,
                          String[] userids){
        if (StringUtils.isBlank(title)){
            return  JsonResult.failure(601,"消息名称不能为空");
        }
        Advices advices = new Advices();
        advices.setCreatedDate(new Date());
        advices.setLastModifiedDate(new Date());
        advices.setTitle(title);
        advices.setType(type);
        advices.setIssuertime(issuertime);
        advices.setAdvicestext(advicestext);
        advices.setAdvicesstatus(advicesstatus);
        User user1 = userService.findById(userid);
        if (user1 == null) {
            return JsonResult.failure(602, "发布人id有误");
        }
        advices.setUser(user1);
        List<Subject> subjects = subjectService.findbyIds(subjectIds);
            if (subjects.size()!=subjectIds.length){
                return JsonResult.failure(605,"有"+(subjectIds.length-subjects.size())+"个机构不存在");
            }
        List<Depart> departs = departService.findbyIds(departIds);
            if (departs.size()!=departIds.length){
                return JsonResult.failure(606,"有"+(departIds.length-departs.size())+"个部门不存在");
            }
        List<Groups> groups = groupsService.findByIds(groupIds);
            if (groups.size()!=groupIds.length){
                return JsonResult.failure(607,"有"+(groupIds.length-groups.size())+"个组织不存在");
            }
        List<User> users = userService.findbyIds(userids);
            if (users.size()!=userids.length){
                return JsonResult.failure(608,"有"+(userids.length-users.size())+"个用户不存在");
            }
        advices = advicesService.save(advices,subjectIds,departIds,groupIds,userids);
        jsonResult.setData(converAdvices(advices));
        return jsonResult;
    }


    /**
     * @api {POST} /advices/view 查看
     * @apiGroup Advices
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /advices/view?id=4028d8816bcc9a32016bccc68bfc0006
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": {"advicestext": null,"issuertime": "2018-12-12 12:12:12","advicesstatus": null,"subjects": [{"code": "1","name": "分院党组织","id": "402881916b9d3031016b9d626593000c","subjectType": "part"},{"code": "0","name": "富阳人民医院","id": "bfc5bd62010f467cbbe98c9e4741733b","subjectType": "part"}],"name": "钱正","groups": [{"code": "0","name": "行政支部","remark": "","id": "402881916b9d3031016b9d63a172000d"},{"code": "1","name": "后勤支部","remark": "","id": "402881916b9d3031016b9d63d7af000e"}],"id": "4028d8816bcc9a32016bccc68bfc0006","title": "消息标题1","type": "1","users": [{"password": "6A36E430976A64EA","code": "001","salt": "45a1d914886d4a92b6835a181b2a20d8","sex": "0","name": "钱正","remark": "暂无描述","id": "402881916ba10b8a016ba113adbc0006","userType": "normal","account": "user","status": ""}],"departs": []}
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id){
        Advices advices = advicesService.findById(id);
        List<AdvicesInfoDepar> advicesInfoDepars = advicesInfoDeparService.findByAdvicesId(id);
        if (advices == null) {
            return jsonResult.notFound("任务不存在");
        }
        JSONObject object = new JSONObject();
        JSONArray  subjects = new JSONArray();
        JSONArray  departs = new JSONArray();
        JSONArray  groups = new JSONArray();
        JSONArray  users = new JSONArray();
        object.put("id",advices.getId());
        object.put("title",advices.getTitle());
        object.put("type",advices.getType());
        object.put("name",advices.getUser().getName());
        object.put("issuertime",advices.getIssuertime());
        object.put("advicesstatus",advices.getAdvicesstatus());
        object.put("advicestext",advices.getAdvicestext());
        for (AdvicesInfoDepar advicesInfoDepar : advicesInfoDepars){
            if(advicesInfoDepar.getSubject()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(advicesInfoDepar.getSubject()));
                subjects.add(json);
            }
            if(advicesInfoDepar.getDepart()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(advicesInfoDepar.getDepart()));
                departs.add(json);
            }
            if(advicesInfoDepar.getGroups()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(advicesInfoDepar.getGroups()));
                groups.add(json);
            }
            if(advicesInfoDepar.getUser()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(advicesInfoDepar.getUser()));
                users.add(json);
            }
        }

        object.put("subjects", subjects);
        object.put("departs", departs);
        object.put("groups", groups);
        object.put("users", users);
        jsonResult.setData(object);
        return jsonResult;
    }



    /**
     * @api {POST} /advices/del 删除
     * @apiGroup Advices
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /advices/del?id=4028d8816bcc9a32016bccc68bfc0006
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id){
        Advices advices = advicesService.findById(id);
        advicesService.delete(advices);
            return JsonResult.success();
    }

    /**
     * @api {POST} /advices/findBydeption 根据部门组织机构查找消息
     * @apiGroup Advices
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /advices/findBydeption?id=402881916b9d3031016b9d626593000c
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12959f90000","title": "我撒旦","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc129e1110002","title": "我","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12a88660004","title": "自行车","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12acad10006","title": "9880980","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12b05f60008","title": "测试","type": "1","username": "钱正"},{"advicestext": "1","issuertime": "1","advicesstatus": "1","id": "4028d8816bc1252e016bc12b1c9b000a","title": "测试111","type": "1","username": "钱正"}]
     * }
     */
    @PostMapping("/findBydeption")
    public JsonResult findBydeption(String id){
        List<Advices> advices2 = advicesService.findAdvicesByDeptionId(id);
        JSONArray arr = new JSONArray();
        JSONObject object;
        for(Advices advices :advices2){
            object = new JSONObject();
            object.put("id",advices.getId());
            object.put("title",advices.getTitle());
            object.put("type",advices.getType());
            object.put("issuertime",advices.getIssuertime());
            object.put("username",advices.getUser().getName());
            object.put("advicestext",advices.getAdvicestext());
            object.put("advicesstatus",advices.getAdvicesstatus());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }


    /**
     * @api {POST} /advices/sendAdvices 发送任务
     * @apiGroup Advices
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParam {String} id 任务id
     * @apiParamExample {json} 请求样例:
     *                   /advices/sendAdvices?id=4028d8816bc1252e016bc12b1c9b000a
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                  601:已经发送过任务<br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     *  "data": []
     * }
     */
    @PostMapping("/sendAdvices")
    public JsonResult sendAdvices(String id){
        List<AdvicesInfoUser> advicesInfoUsers = advicesService.findsendistrue(id);
            if(CollectionUtils.isNotEmpty(advicesInfoUsers)){
                return  JsonResult.failure(601,"已经发送此消息");
            }
        List<AdvicesInfoDepar> advicesInfoDepars = advicesService.findDeptionByAdvicesId(id);
        if (CollectionUtils.isEmpty(advicesInfoDepars)){
            return JsonResult.failure(602,"没有找到组织关系");
        }
        advicesService.saveAdvices(advicesInfoDepars);
        String state ="已发送";
        advicesService.updatestate(id,state);

        return  jsonResult;
    }

    /**
     * 转换Advices为json
     */

    private JSONObject converAdvices(Advices advices){
        JSONObject json = new JSONObject(JsonUtils.getJson(advices));
        return  json;
    }
}
