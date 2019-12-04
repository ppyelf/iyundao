package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.annotation.CurrentUser;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Evaluation;
import com.ayundao.entity.EvaluationIndex;
import com.ayundao.entity.User;
import com.ayundao.service.EvaluationService;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EvaluationController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 9:58
 * @Description: 控制 - 考评
 * @Version: V1.0
 */
@RestController
@RequestMapping("/evaluation")
public class EvaluationController extends BaseController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private UserService userService;

    /**
     * @api {GET} /evaluation/indexList 指标列表
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 指标列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求示例:
     * /evaluation/indexList
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": [{    "val": 0,    "max": 10.0,    "min": 2.0,    "type": "加分指标",    "id": "02e1e0523fa84786870804d8a980ff35",    "name": "完成指令性任务"}
     * ]
     * }
     */
    @GetMapping("/indexList")
    public JsonResult indexList() {
        jsonResult.setData(evaluationService.getIndexList());
        return jsonResult;
    }

    /**
     * @api {POST} /evaluation/add 添加个人记录
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 添加个人记录
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} year 年份,必填
     * @apiParam {String} userId 用户ID,必填
     * @apiParam {String} evaluationIndexId 考评指标ID,必填
     * @apiParam {double} score 医德分,必填
     * @apiParam {String} remark 描述
     * @apiParam {String} number 病床号
     * @apiParam {String} patientName 病人姓名
     * @apiParamExample {json} 请求示例:
     * /evaluation/add
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:必填参数不能为空</br>
     * 602:用户不存在</br>
     * 603:考评指标不存在</br>
     * 604:分数设置异常,该指标分数必须在x到x之间</br>
     * 605:考评不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": {"number": "零号病床","score": "2.0","patientName": "感冒发烧患者","year": "2019","remark": "描述","id": "402881916e8cef94016e8cf38f820001","sureTime": "","status": "waiting"
     * }
     * }
     */
    @PostMapping("/add")
    public JsonResult add(@RequestParam(defaultValue = "") String year,
                          String userId,
                          String evaluationIndexId,
                          @RequestParam(defaultValue = "0.0") double score,
                          String remark,
                          String number,
                          String patientName,
                          @CurrentUser User operator) {
        if (isBlank(userId, year, evaluationIndexId)) {
            return JsonResult.failure(601, "必填参数不能为空");
        }
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.failure(602, "用户不存在");
        }
        EvaluationIndex ei = evaluationService.findEvaluationIndex(evaluationIndexId);
        if (ei == null) {
            return JsonResult.failure(603, "考评指标不存在");
        }
        if ((score > ei.getMax()
                && score < ei.getMin()
                && !ei.getType().equals(EvaluationIndex.TYPE.one))
                || score == 0.0) {
            return JsonResult.failure(604, "分数设置异常,该指标分数必须在" + ei.getMin() + "到" + ei.getMax() + "之间");
        }
        Evaluation evaluation = evaluationService.save(year, user, ei, score, remark, number, patientName, operator);
        jsonResult.setData(JsonUtils.getJson(evaluation));
        return jsonResult;
    }

    /**
     * @api {POST} /evaluation/del 删除个人考评
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 删除个人考评
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String[]} ids 考评ID集合,必填
     * /evaluation/del
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:考评不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "删除成功",
     * "data": ""
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String[] ids) {
        evaluationService.deleteByIDS(ids);
        jsonResult.setCode(200);
        jsonResult.setMessage("删除成功");
        jsonResult.setData("");
        return jsonResult;
    }

    /**
     * @api {GET} /evaluation/yearList 年度列表
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 年度列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求示例:
     * /evaluation/yearList
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:status类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         "2019"
     *     ]
     * }
     */
    @GetMapping("/yearList")
    public JsonResult yearList() {
        List<String> list = evaluationService.getYearList();
        JSONArray arr = new JSONArray();
        for (String s : list) {
            arr.add(s);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }


    /**
     * @api {GET} /evaluation/statusList 审核态度列表
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 审核态度列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求示例:
     * /evaluation/statusList
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:status类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "name": "全部",    "index": -1},{    "name": "等待中",    "index": 0},{    "name": "同意",    "index": 1},{    "name": "拒绝",    "index": 2}
     *     ]
     * }
     */
    @GetMapping("/statusList")
    public JsonResult statusList() {
        JSONArray arr = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("index", -1);
        json.put("name", "全部");
        arr.add(json);
        for (Evaluation.STATUS value : Evaluation.STATUS.values()) {
            json = new JSONObject();
            json.put("index", value.ordinal());
            json.put("name", value.getName());
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /evaluation/list 分页
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} startTime 开始时间
     * @apiParam {String} endTime 结束时间
     * @apiParam {String} code 胸牌号
     * @apiParam {String} subjectId 所属科室ID
     * @apiParam {String} addSubjectId 录入科室ID
     * @apiParam {String} indexId 指标ID
     * @apiParam {int} status 审核状态,-1-全部(默认),参见/evaluation/statusList
     * @apiParam {String} currentSubjectId 当前机构
     * @apiParam {int} num 页数,默认0
     * @apiParam {int} size 页宽,默认10
     * @apiParamExample {json} 请求示例:
     * /evaluation/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:status类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"content": [    {        "number": "1",        "userName": "舒兴土",        "remark": "描述",        "id": "1",        "status": false,        "type": "减分指标",        "patientName": "感冒发烧患者",        "userCode": "a330",        "indexName": "投诉通报批评",        "score": 2.0,        "operatorName": "陆惠英",        "operatorTime": "20190821171922",        "sureTime": ""    },    {        "number": "2",        "type": "加分指标",        "remark": "描述",        "id": "2",        "userName": "张亮",        "status": false,        "patientName": "感冒发烧患者",        "score": 2.0,        "indexName": "获得荣誉称号",        "userCode": "a225",        "operatorTime": "20191121171922",        "operatorName": "陆惠英",        "sureTime": ""    },    {        "number": "3",        "type": "加分指标",        "indexName": "医疗服务质量",        "remark": "描述",        "id": "3",        "status": false,        "patientName": "感冒发烧患者",        "userCode": "a10",        "score": 2.0,        "operatorTime": "20191121171922",        "operatorName": "陆惠英",        "sureTime": "",        "userName": "盛丹虹"    },    {        "indexName": "违反服务规范",        "number": "4",        "remark": "描述",        "status": false,        "type": "减分指标",        "patientName": "感冒发烧患者",        "userName": "蒋晓宇",        "id": "4",        "score": 2.0,        "userCode": "a204",        "operatorTime": "20191121171922",        "operatorName": "陆惠英",        "sureTime": ""    },    {        "type": "加分指标",        "number": "4",        "remark": "描述",        "status": false,        "patientName": "感冒发烧患者",        "id": "5",        "score": 2.0,        "userCode": "a51",        "indexName": "受到宣传表扬",        "operatorTime": "20191121171922",        "operatorName": "陆惠英",        "sureTime": "",        "userName": "张晓燕"    },    {        "userName": "张秉欣",        "remark": "描述",        "status": false,        "type": "减分指标",        "patientName": "感冒发烧患者",        "id": "6",        "indexName": "违反院纪院规",        "score": 2.0,        "userCode": "a71",        "operatorTime": "20191121171922",        "operatorName": "陆惠英",        "sureTime": "",        "number": "零号病床"    },    {        "type": "一票否",        "userName": "卢金山",        "remark": "描述",        "status": false,        "patientName": "感冒发烧患者",        "id": "7",        "userCode": "a234",        "score": 2.0,        "operatorTime": "20191121171922",        "operatorName": "陆惠英",        "indexName": "发生重大医疗事故或严重",        "sureTime": "",        "number": "零号病床"    },    {        "userName": "李敏",        "id": "8",        "remark": "描述",        "status": false,        "type": "减分指标",        "patientName": "感冒发烧患者",        "operatorName": "孙鑫琦",        "score": 2.0,        "userCode": "a100",        "operatorTime": "20191121171922",        "indexName": "临床医疗处置不当",        "sureTime": "",        "number": "零号病床"    },    {        "type": "一票否",        "id": "9",        "remark": "描述",        "indexName": "发生违法违纪行为",        "status": false,        "patientName": "感冒发烧患者",        "operatorName": "孙鑫琦",        "score": 2.0,        "userCode": "a111",        "userName": "臧洪城",        "operatorTime": "20191121171922",        "sureTime": "",        "number": "零号病床"    },    {        "id": "10",        "type": "加分指标",        "remark": "描述",        "userCode": "a160",        "status": false,        "patientName": "感冒发烧患者",        "indexName": "支持爱心公益",        "operatorName": "孙鑫琦",        "score": 2.0,        "operatorTime": "20191121171922",        "userName": "项红梅",        "sureTime": "",        "number": "零号病床"    }],"total": 21,"pageable": {    "pageNumber": 0,    "pageSize": 10,    "searchProperty": null,    "searchValue": null,    "orderProperty": null,    "orderDirection": null,    "orders": []},"pageNumber": 0,"pageSize": 10,"searchValue": null,"orders": [],"searchProperty": null,"orderDirection": null,"orderProperty": null,"totalPages": 3
     *     }
     * }
     */
    @PostMapping("/list")
    public JsonResult examines(@RequestParam(defaultValue = "") String startTime,
                               @RequestParam(defaultValue = "") String endTime,
                               @RequestParam(defaultValue = "") String code,
                               @RequestParam(defaultValue = "") String subjectId,
                               @RequestParam(defaultValue = "") String addSubjectId,
                               @RequestParam(defaultValue = "") String indexId,
                               @RequestParam(defaultValue = "-1") int status,
                               @RequestParam(defaultValue = "") String currentSubjectId,
                               @RequestParam(defaultValue = "0") int num,
                               @RequestParam(defaultValue = "10") int size) {
        int s = status == -1 ? -1 : -2;
        for (Evaluation.STATUS value : Evaluation.STATUS.values()) {
            if (value.ordinal() == status) {
                s = value.ordinal();
                break;
            }
        }
        if (s == -2 && s != -1) {
            return JsonResult.failure(601, "status类型异常");
        }
        Page<JSONObject> page = evaluationService.getList(startTime, endTime, code, subjectId, addSubjectId, indexId, s, currentSubjectId, num, size);
        jsonResult.setData(page);
        return jsonResult;
    }

    /**
     * @api {POST} /evaluation/sumList 统计分页
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 统计分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} code 胸牌号
     * @apiParam {String} year 年度
     * @apiParam {int} num 页数,默认0
     * @apiParam {int} size 页宽,默认10
     * @apiParamExample {json} 请求示例:
     * /evaluation/sumList
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:status类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"content": [    {        "code": "a330",        "groupId": "402881916b9d3031016b9d7146d10018",        "id": "1",        "title": "",        "postType": "其他",        "sex": "男",        "year": "2019",        "post": "",        "score": 10.0,        "name": "舒兴土",        "departMent": "保卫科",        "birthday": "19510306",        "groupName": "退休二支部"    },    {        "code": "a240",        "id": "21",        "name": "江建华",        "postType": "其他",        "sex": "男",        "score": 2.0,        "birthday": "19730901",        "departMent": "胃镜室",        "year": "2019",        "post": "",        "title": "副主任医师",        "groupName": "医技党支部",        "groupId": "402881916b9d3031016b9d70ff540016"    },    {        "birthday": "1986.10.06",        "groupName": "后勤党支部",        "departMent": "信息中心",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "groupId": "402881916b9d3031016b9d63d7af000e",        "name": "吴君",        "title": "见习期（本科）",        "code": "a223",        "id": "15"    },    {        "departMent": "放射科",        "birthday": "19650123",        "id": "7",        "postType": "其他",        "sex": "男",        "name": "卢金山",        "score": 2.0,        "year": "2019",        "post": "",        "title": "副主任医师",        "code": "a234",        "groupName": "医技党支部",        "groupId": "402881916b9d3031016b9d70ff540016"    },    {        "title": "    中级",        "code": "a10",        "id": "3",        "departMent": "肾内科",        "name": "盛丹虹",        "groupName": "内科一支部",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "birthday": "19841204",        "groupId": "402881916b9d3031016b9d706b4e0012"    },    {        "groupName": "后勤党支部",        "id": "2",        "departMent": "信息中心",        "title": "",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "groupId": "402881916b9d3031016b9d63d7af000e",        "code": "a225",        "name": "张亮",        "birthday": "1990.11.11"    },    {        "groupId": "402881916b9d3031016b9d70de800015",        "title": "",        "groupName": "外科二支部",        "postType": "其他",        "sex": "男",        "score": 2.0,        "name": "孙静波",        "departMent": "神经外科",        "year": "2019",        "post": "",        "birthday": "19890604",        "id": "16",        "code": "a134"    },    {        "departMent": "财务科",        "id": "20",        "birthday": "1995.12.14",        "groupName": "后勤党支部",        "title": "",        "postType": "其他",        "name": "王天音",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "groupId": "402881916b9d3031016b9d63d7af000e",        "code": "a229"    },    {        "id": "8",        "departMent": "麻醉科",        "title": "",        "groupId": "402881916b9d3031016b9d70b2510014",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "name": "李敏",        "birthday": "19891008",        "code": "a100",        "groupName": "外科一支部"    },    {        "name": "夏加英",        "postType": "其他",        "title": "副高",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "科长",        "birthday": "19770213",        "departMent": "公共卫生科",        "groupId": "402881916b9d3031016b9d63a172000d",        "groupName": "行政党支部",        "id": "402881916e8cef94016e8d424d310002",        "code": "a178"    }],"total": 17,"pageable": {    "pageNumber": 0,    "pageSize": 10,    "searchProperty": null,    "searchValue": null,    "orderProperty": null,    "orderDirection": null,    "orders": []},"pageNumber": 0,"pageSize": 10,"searchValue": null,"orders": [],"searchProperty": null,"orderDirection": null,"orderProperty": null,"totalPages": 2
     *     }
     * }
     */
    @PostMapping("/sumList")
    public JsonResult sumList(@RequestParam(defaultValue = "") String code,
                              @RequestParam(defaultValue = "") String year,
                              @RequestParam(defaultValue = "0") int num,
                              @RequestParam(defaultValue = "10") int size) {
        Page<JSONObject> page = evaluationService.getSumList(code, year, num, size);
        jsonResult.setData(page);
        return jsonResult;
    }

    /**
     * @api {POST} /evaluation/sure 统计分页
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 统计分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 医德医风ID,必填
     * @apiParam {int} type 审核态度,1-同意(默认),2-拒绝
     * @apiParamExample {json} 请求示例:
     * /evaluation/sure
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:必填参数不能为空</br>
     * 602:审核态度异常</br>
     * 603:医德医风不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": {"content": [    {        "code": "a330",        "groupId": "402881916b9d3031016b9d7146d10018",        "id": "1",        "title": "",        "postType": "其他",        "sex": "男",        "year": "2019",        "post": "",        "score": 10.0,        "name": "舒兴土",        "departMent": "保卫科",        "birthday": "19510306",        "groupName": "退休二支部"    },    {        "code": "a240",        "id": "21",        "name": "江建华",        "postType": "其他",        "sex": "男",        "score": 2.0,        "birthday": "19730901",        "departMent": "胃镜室",        "year": "2019",        "post": "",        "title": "副主任医师",        "groupName": "医技党支部",        "groupId": "402881916b9d3031016b9d70ff540016"    },    {        "birthday": "1986.10.06",        "groupName": "后勤党支部",        "departMent": "信息中心",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "groupId": "402881916b9d3031016b9d63d7af000e",        "name": "吴君",        "title": "见习期（本科）",        "code": "a223",        "id": "15"    },    {        "departMent": "放射科",        "birthday": "19650123",        "id": "7",        "postType": "其他",        "sex": "男",        "name": "卢金山",        "score": 2.0,        "year": "2019",        "post": "",        "title": "副主任医师",        "code": "a234",        "groupName": "医技党支部",        "groupId": "402881916b9d3031016b9d70ff540016"    },    {        "title": "    中级",        "code": "a10",        "id": "3",        "departMent": "肾内科",        "name": "盛丹虹",        "groupName": "内科一支部",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "birthday": "19841204",        "groupId": "402881916b9d3031016b9d706b4e0012"    },    {        "groupName": "后勤党支部",        "id": "2",        "departMent": "信息中心",        "title": "",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "groupId": "402881916b9d3031016b9d63d7af000e",        "code": "a225",        "name": "张亮",        "birthday": "1990.11.11"    },    {        "groupId": "402881916b9d3031016b9d70de800015",        "title": "",        "groupName": "外科二支部",        "postType": "其他",        "sex": "男",        "score": 2.0,        "name": "孙静波",        "departMent": "神经外科",        "year": "2019",        "post": "",        "birthday": "19890604",        "id": "16",        "code": "a134"    },    {        "departMent": "财务科",        "id": "20",        "birthday": "1995.12.14",        "groupName": "后勤党支部",        "title": "",        "postType": "其他",        "name": "王天音",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "groupId": "402881916b9d3031016b9d63d7af000e",        "code": "a229"    },    {        "id": "8",        "departMent": "麻醉科",        "title": "",        "groupId": "402881916b9d3031016b9d70b2510014",        "postType": "其他",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "",        "name": "李敏",        "birthday": "19891008",        "code": "a100",        "groupName": "外科一支部"    },    {        "name": "夏加英",        "postType": "其他",        "title": "副高",        "sex": "男",        "score": 2.0,        "year": "2019",        "post": "科长",        "birthday": "19770213",        "departMent": "公共卫生科",        "groupId": "402881916b9d3031016b9d63a172000d",        "groupName": "行政党支部",        "id": "402881916e8cef94016e8d424d310002",        "code": "a178"    }],"total": 17,"pageable": {    "pageNumber": 0,    "pageSize": 10,    "searchProperty": null,    "searchValue": null,    "orderProperty": null,    "orderDirection": null,    "orders": []},"pageNumber": 0,"pageSize": 10,"searchValue": null,"orders": [],"searchProperty": null,"orderDirection": null,"orderProperty": null,"totalPages": 2
     * }
     * }
     */
    @PostMapping("/sure")
    public JsonResult sure(String id,
                           @RequestParam(defaultValue = "1") int type) {
        if (isBlank(id)) {
            return JsonResult.failure(601, "必填参数不能为空");
        }
        Evaluation evaluation = evaluationService.find(id);
        if (evaluation == null) {
            return jsonResult.failure(603, "医德医风不存在");
        }
        if (type != 1 && type == 2) {
            return JsonResult.failure(602, "审核态度异常");
        }
        Evaluation.STATUS status = type == 1 ? Evaluation.STATUS.agree : Evaluation.STATUS.refuse;
        evaluationService.sure(evaluation, status);
        return JsonResult.success();
    }
    /**
     * @api {POST} /evaluation/viewSum 查看个人医德分数
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 查看个人医德分数
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} code 胸牌号,必填
     * @apiParam {String} year 年度
     * @apiParamExample {json} 请求示例:
     * /evaluation/viewSum
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:必填参数不为空</br>
     * 602:用户不存在</br>
     * 603:用户尚未添加用户详情</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"birthday": "19510306","workUnit": "保卫科","post": "","postType": "其他","sex": "男","name": "舒兴土","index": [    {        "score": 2.0,        "name": "支持爱心公益",        "year": "2019",        "type": "加分指标",        "value": 0    },    {        "score": 2.0,        "name": "医德人文教育",        "year": "2019",        "type": "加分指标",        "value": 1    },    {        "score": 2.0,        "year": "2019",        "type": "加分指标",        "name": "医疗技术提升",        "value": 0    },    {        "name": "投诉通报批评",        "score": 2.0,        "year": "2019",        "type": "加分指标",        "value": 1    },    {        "score": 2.0,        "year": "2019",        "type": "加分指标",        "value": 1,        "name": "违反服务规范"    }],"title": ""
     *     }
     * }
     */
    @PostMapping("/viewSum")
    public JsonResult viewSum(String code,
                              @RequestParam(defaultValue = "") String year) {
        if (isBlank(code)) {
            return JsonResult.failure(601, "必填参数不为空");
        }
        User user = userService.findByCode(code);
        if (user == null) {
            return JsonResult.failure(602, "用户不存在");
        }
        return evaluationService.viewSum(user, year);
    }

    /**
     * @api {GET} /evaluation/download 下载医德医风模板
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 下载医德医风模板
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求示例:
     * /evaluation/download
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:导入到文件不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *      "code": 200,
     *      "message": "成功",
     *      "data": {""}
     * }
     */
    @GetMapping("/download")
    public JsonResult download(HttpServletRequest req, HttpServletResponse resp) {
        try {
            evaluationService.downloadEvaluation(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
     * @api {POST} /evaluation/upload 导入医德医风
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 导入医德医风
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {MultipartFile} file 上传文件,必填
     * @apiParamExample {json} 请求示例:
     * /evaluation/upload
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:第3行胸牌号为空或查询不存在</br>
     * 602:第3行指标查询不存在</br>
     * 603:第3行未设置医德分或医德分异常</br>
     * 604:录入人胸牌号不存在</br>
     * 605:第3行行医德分必须在1到2之间</br>
     * 606:年份不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *      "code": 200,
     *      "message": "成功",
     *      "data": {""}
     * }
     */
    @PostMapping("/upload")
    public JsonResult upload(MultipartFile file) {
        if (file == null) {
            return JsonResult.failure(601, "导入到文件不能为空");
        }
        return evaluationService.upload(file, JsonResult.success());
    }

    /**
     * @api {GET} /evaluation/export 导出个人医德医风
     * @apiGroup Evaluation
     * @apiVersion 1.0.0
     * @apiDescription 导出个人医德医风
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} code 用户code,必填
     * @apiParam {String} year 年份,必填
     * @apiParamExample {json} 请求示例:
     * /evaluation/export
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:必填参数不能为空</br>
     * 602:用户不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *      "code": 200,
     *      "message": "成功",
     *      "data": {""}
     * }
     */
    @GetMapping("/export")
    public JsonResult export(String code, String year, HttpServletRequest req, HttpServletResponse resp) {
        if (isBlank(code, year)) {
            return jsonResult.failure(601, "必填参数不能为空");
        }
        return evaluationService.export(code, year, req, resp);
    }
}
