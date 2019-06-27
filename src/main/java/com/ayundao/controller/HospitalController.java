package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.ExcelUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Consume;
import com.ayundao.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: HospitalController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 9:50
 * @Description: 控制层 - 清廉医院
 * @Version: V1.0
 */
@RestController
@RequestMapping("/hospital")
public class HospitalController extends BaseController {

    @Autowired
    private ConsumeService consumeService;

    /**
     * @api {POST} /hospital/downloadConsume 下载耗材预警模板
     * @apiGroup Hospital
     * @apiVersion 1.0.0
     * @apiDescription 下载耗材预警模板
     * @apiParamExample {json} 请求示例:
     *                  /ideology/downloadConsume
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
    @GetMapping("/downloadConsume")
    public JsonResult downloadConsume(HttpServletResponse resp) {
        try {
            ExcelUtils.downloadWorkBook("耗材预警", Consume.class, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
     * @api {POST} /hospital/importConsume 导入耗材预警
     * @apiGroup Hospital
     * @apiVersion 1.0.0
     * @apiDescription 导入耗材预警
     * @apiParamExample {json} 请求示例:
     *                  /ideology/importConsume
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
    @PostMapping("/importConsume")
    public JsonResult importConsume(MultipartFile file) {
        List<Consume> list = ExcelUtils.readExcel(file, Consume.class);
        list = consumeService.saveAllConsume(list);
        jsonResult.setData(convert(list));
        return jsonResult;
    }

    /**
     * @api {POST} /hospital/list 先锋人物排名
     * @apiGroup Hospital
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
    @PostMapping("/list")
    public JsonResult pioneerList(@RequestParam(defaultValue = "0") int type) {
        List<Consume> list = consumeService.findOrderByCreatedTime(type);
        jsonResult.setData(convert(list));
        return jsonResult;
    }
    
    private JSONArray convert(List<Consume> list) {
        JSONArray arr = new JSONArray();
        for (Consume consume : list) {
            JSONObject json = JsonUtils.getJson(consume);
            arr.add(json);
        }
        return arr;
    }
}
