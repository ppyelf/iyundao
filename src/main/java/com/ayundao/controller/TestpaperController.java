package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Task;
import com.ayundao.entity.Testpaper;
import com.ayundao.service.PaperTitleService;
import com.ayundao.service.TestpaperService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: TestpaperController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 试卷
 * @Version: V1.0
 */
@RestController
@RequestMapping("/testpaper")
public class TestpaperController extends BaseController{

    @Autowired
    private TestpaperService testpaperService;

    @Autowired
    private PaperTitleService paperTitleService;

    /**
     * @api {GET} /testpaper/list 列表
     * @apiGroup Testpaper
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例:
     * /testpaper/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     *  "data": [{"intro": null,"name": "第一张","id": "1"},{"intro": null,"name": "第二张","id": "2"}]
     * }
     */
    @GetMapping("/list")
    public JsonResult list(){
        List<Testpaper> testpapers = testpaperService.findAll();
        JSONArray arr = new JSONArray();
        for (Testpaper testpaper : testpapers) {
            JSONObject object = new JSONObject();
            object.put("id",testpaper.getId());
            object.put("name",testpaper.getName());
            object.put("intro",testpaper.getIntro());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }


    /**
     * @api {POST} /testpaper/add 新增
     * @apiGroup Testpaper
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} title 试卷名称  必填
     * @apiParam {String} intro 试卷简介
     * @apiParam {String[]} examcontent 题目
     * @apiParam {String[]} answer 答案  一个题目多个答案|分割
     * @apiParam {String[]} yesorno 正确答案
     * @apiParam {String[]} score
     * @apiParamExample {json} 请求样例:
     *                /testpaper/add?title=1112&intro=222&examcontent=111,222,333&answer=1.1|1.2|1.3,2.1|2.2|2.3,3.1|3.2|3.3&yesorno=1|1|1,2|2|2,3|3|3&score=5,10,15
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     *                                 601:传入的4个数组长度不匹配,无法做到下标1对1的关系</br>
     *                                 602:第"+(i+1)+"道答案与正确答案长度不符合</br>
     *                                 603:试卷名称不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"intro": "222","name": "1112","id": "4028d8816bd07269016bd0cdb85b0034"}
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String title,
                          String intro,
                          String[] examcontent,
                          String[] answer,
                          String[] yesorno,
                          String[] score){
            if(StringUtils.isBlank(title)){
                return JsonResult.failure(603, "试卷名称不能为空");
            }
            Testpaper testpaper = new Testpaper();
            testpaper.setCreatedDate(new Date());
            testpaper.setLastModifiedDate(new Date());
            testpaper.setName(title);
            testpaper.setIntro(intro);
            if(examcontent.length != answer.length || examcontent.length != yesorno.length || examcontent.length != score.length){
                return JsonResult.failure(601,"传入的4个数组长度不匹配,无法做到下标1对1的关系");
            }
        for (int i =0;i<answer.length;i++){
            String[] answers= answer[i].split("\\|");
            String[] yesornos= yesorno[i].split("\\|");
            if(answers.length != yesornos.length ){
                return JsonResult.failure(602,"第"+(i+1)+"道答案与正确答案长度不符合");
            }
        }
        testpaper = testpaperService.save(testpaper,examcontent,answer,yesorno,score);
        jsonResult.setData(converTestpaper(testpaper));
        return  jsonResult;
    }

    /**
     * TestPaper 为json
     */

    private JSONObject converTestpaper(Testpaper testpaper){
        JSONObject json = new JSONObject(JsonUtils.getJson(testpaper));
        return  json;
    }

    /**
     * @api {POST} /testpaper/view 查看
     * @apiGroup Testpaper
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *               /testpaper/view?id=4028d8816bd07269016bd0c82ac10027
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"intro": "222","name": "111","topic": [{"title": "111","selectall": [{"select ": "1.1","istrue": "1"},{"select ": "1.2","istrue": "1"},{"select ": "1.3","istrue": "1"}]},{"title": "222","selectall": [{"select ": "2.1","istrue": "2"},{"select ": "2.2","istrue": "2"},{"select ": "2.3","istrue": "2"}]},{"title": "333","selectall": [{"select ": "3.1","istrue": "3"},{"select ": "3.2","istrue": "3"},{"select ": "3.3","istrue": "3"}]}],"id": "4028d8816bd07269016bd0c82ac10027"}
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id){
        Testpaper testpaper = testpaperService.findById(id);
        if(testpaper ==null){
            return JsonResult.notFound("找不到试卷");
        }
        List<PaperTitle> paperTitles = paperTitleService.find(testpaper);
        jsonResult  = testpaperService.findtitleandanswer(testpaper,paperTitles);
        return jsonResult;
    }

    /**
     * @api {POST} /testpaper/del 删除
     * @apiGroup Testpaper
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /testpaper/del?id=4028d8816bd07269016bd0cdb85b0034
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
        Testpaper testpaper = testpaperService.findById(id);
        if(testpaper ==null){
            return JsonResult.notFound("找不到试卷");
        }
        testpaperService.deleteTestpaper(testpaper);
        return jsonResult;
    }


}
