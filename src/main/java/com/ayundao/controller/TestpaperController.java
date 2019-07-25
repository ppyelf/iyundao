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
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ayundao.base.BaseController.*;

/**
 * @ClassName: TestpaperController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 试卷
 * @Version: V1.0
 */
@RequiresUser
@RequiresRoles(value = {ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_PUBLISHER}, logical = Logical.OR)
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_VIEW)
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
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求样例:
     *                {Json} {"title":"title","intro":"intro","examination":[{"examcontent" : "111","answer" : "1.1|1.2|1.3","yesorno":"1|1|1","score":"5"},{"examcontent":"222","answer":"2.1|2.2|2.3","yesorno":"1|0|1","score":"10"},{"examcontent":"333","answer":"3.1|3.2|3.3","yesorno":"0|0|1","score":"15"}]}
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
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
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/add")
    public  JsonResult addd(@RequestBody JSONObject params){

        String title = (String)params.get("title");
        if(StringUtils.isBlank(title)){
            return JsonResult.failure(603, "试卷名称不能为空");
        }
        String intro = (String)params.get("intro");
        Testpaper testpaper = new Testpaper();
        testpaper.setCreatedDate(new Date());
        testpaper.setLastModifiedDate(new Date());
        testpaper.setName(title);
        testpaper.setIntro(intro);
        List<Map<String, String>> examination = (List<Map<String, String>>)params.get("examination");
        String[] one;
        String[] two;
        for (Map<String, String> map : examination) {
            one = map.get("answer").split("\\|");
            two = map.get("yesorno").split("\\|");
            if (one.length != two.length) {
                return JsonResult.failure(601, map.get("answer") + "和" + map.get("yesorno") + "长度不匹配");
            }
        }
        testpaperService.savetest(testpaper,examination);
        jsonResult.setData(testpaper);
        return jsonResult;
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
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填 试卷id
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
    @RequiresPermissions(PERMISSION_VIEW)
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_DELETE)
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
