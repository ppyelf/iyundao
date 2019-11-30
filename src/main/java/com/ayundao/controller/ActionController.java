package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Action;
import com.ayundao.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: ActionController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/28 11:15
 * @Description: 控制 - 春风行动
 * @Version: V1.0
 */
@RestController
@RequestMapping("/action")
public class ActionController extends BaseController {

    private static final long serialVersionUID = 1983794172943219843L;

    @Autowired
    private ActionService actionService;

    /**
     * @api {POST} /action/list 列表
     * @apiName list
     * @apiGroup Action
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 列表
     * @apiParam {int} num 页数(默认:0)
     * @apiParam {int} size 长度(默认:10)
     * @apiParamExample {json} 请求样例
     *                ?num=0&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping("/list")
    public JsonResult list(@RequestParam(defaultValue = "0") int num,
                           @RequestParam(defaultValue = "10") int size) {
        Page<Action> page = actionService.findPage(new Pageable(num, size));
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Action action : page.getContent()) {
            JSONObject j = JsonUtils.getJson(action);
            JSONObject user = new JSONObject();
            user.put("name", action.getUser().getName());
            user.put("code", action.getUser().getId());
            j.put("user", user);
            user = new JSONObject();
            user.put("name", action.getSubject().getName());
            user.put("code", action.getSubject().getId());
            j.put("subject", user);
            user = new JSONObject();
            user.put("code", action.getGroup().getId());
            user.put("name", action.getGroup().getName());
            j.put("group", user);
            arr.add(j);
        }
        json.put("total", page.getTotal());
        json.put("totalPage", page.getTotalPages());
        json.put("page", page.getPageNumber());
        json.put("content", arr);
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {GET} /action/download 下载春风行动模板
     * @apiName download
     * @apiGroup Action
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 下载春风行动模板
     * @apiParamExample {json} 请求样例
     *                ?num=0&size=10
     * @apiSuccess (200) {int} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *
     */
    @GetMapping("/download")
    public void download(HttpServletRequest req, HttpServletResponse resp) {
        actionService.downloadExcel(req, resp);
    }

    /**
     * @api {POST} /action/upload 导入春风行动
     * @apiName upload
     * @apiGroup Action
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 导入春风行动
     * @apiParam {MultipartFile} file 导入的文件
     * @apiParamExample {json} 请求样例
     *                ?file=xxx.xls
     * @apiSuccess (200) {int} code 200:成功</br>
     * 605:文件不能为空</br>
     * 601:第1行胸牌号不能为空或不存在</br>
     * 602:第3行胸牌号不能为空</br>
     * 603:第3行部门编号不能为空</br>
     * 604:第3行输入大于零的正整数</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "money": "300",    "subject": {        "name": "富阳区第一人民医院",        "id": "0"    },    "groups": {        "name": "行政党支部",        "id": "0"    },    "id": "402868816eb23f62016eb240c5510004",    "user": {        "name": "陈锡良",        "id": "a52"    }},{    "money": "100",    "subject": {        "name": "富阳区第一人民医院",        "id": "0"    },    "groups": {        "name": "后勤党支部",        "id": "1"    },    "id": "402868816eb23f62016eb240c7710005",    "user": {        "name": "陆惠英",        "id": "a348"    }},{    "money": "200",    "subject": {        "name": "富阳区第一人民医院",        "id": "0"    },    "groups": {        "name": "内科一支部",        "id": "2"    },    "id": "402868816eb23f62016eb240c7d00006",    "user": {        "name": "陆鼎铨",        "id": "a281"    }},{    "money": "622",    "subject": {        "name": "富阳区第一人民医院",        "id": "0"    },    "groups": {        "name": "内科二支部",        "id": "3"    },    "id": "402868816eb23f62016eb240c8ac0007",    "user": {        "name": "叶萍",        "id": "a166"    }}
     *     ]
     * }
     */
    @PostMapping("/upload")
    public JsonResult upload(MultipartFile file) {
        if (file == null) {
            return JsonResult.failure(605, "文件不能为空");
        }
        return actionService.upload(file);
    }

    /**
     * @api {GET} /action/export 导出春风行动
     * @apiName export
     * @apiGroup Action
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 导出春风行动
     * @apiParam {String} code 机构code,必填
     * @apiParam {String} year 年份,必填
     * @apiParamExample {json} 请求样例
     *                ?file=xxx.xls
     * @apiSuccess (200) {int} code 200:成功</br>
     * 601:必填参数不能为空</br>
     * 602:机构不存在</br>
     * 603:第3行部门编号不能为空</br>
     * 604:第3行输入大于零的正整数</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *
     */
    @GetMapping("/export")
    public JsonResult export(String code, String year, HttpServletRequest req, HttpServletResponse resp) {
        if (isBlank(code, year)) {
            return JsonResult.failure(601, "必填参数不能为空");
        }
        List<Action> list = actionService.findBySubjectIdAndYear(code, year);
        return actionService.export(code, year, list, req, resp);
    }
}
