package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Advices;
import com.ayundao.entity.AdvicesInfoDepar;
import com.ayundao.entity.User;
import com.ayundao.service.AdvicesInfoDeparService;
import com.ayundao.service.AdvicesService;
import com.ayundao.service.UserService;
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
     * 添加消息
     */
    @PostMapping("/add")
    public JsonResult add(String title,
                          String type,
                          String issuertime,
                          String advicesstatus,
                          String advicestext,
                          String userid,
                          String subjectId,
                          String departId,
                          String groupId ){
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
        User user = userService.findById(userid);
        advices.setUser(user);
        advices =  advicesService.save(advices,subjectId,departId,groupId);
        jsonResult.setData(converAdvices(advices));

        return jsonResult;
    }

    /**
     * 删除消息
     */
    @PostMapping("/del")
    public JsonResult del(String id){
        Advices advices = advicesService.findById(id);
        advicesService.delete(advices);
            return jsonResult;
    }

    /**
     * 通过部门组织机构寻找消息
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
     * 转换Advices为json
     */

    private JSONObject converAdvices(Advices advices){
        JSONObject json = new JSONObject(JsonUtils.getJson(advices));
        return  json;
    }
}
