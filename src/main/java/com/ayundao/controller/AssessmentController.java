package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.Assessment;
import com.ayundao.entity.AssessmentFile;
import com.ayundao.entity.AssessmentIndex;
import com.ayundao.service.impl.AssessmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.hql.spi.id.inline.IdsClauseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: AssessmentController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/7 20:03
 * @Description: 控制层 - 考核
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ass")
public class AssessmentController extends BaseController {

    @Autowired
    private AssessmentService assessmentService;

    /**
     * @api {POST} /ass/page 分页
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiParamExample {json} 请求样例：
     *                /ass/page
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    public JsonResult page(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Assessment> aPage = assessmentService.findAllForPage(pageable);
        jsonResult.setData(JsonUtils.getPage(aPage));
        return jsonResult;
    }

    /**
     * @api {POST} /ass/upload_file 上传文件
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiParam {String} name 必填
     * @apiParam {String} url 必填
     * @apiParam {String} suffix 必填
     * @apiParamExample {json} 请求样例：
     *                /ass/upload_file?name=测试上传&suffix=jpg&url=1111111
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:文件名/后缀名/文件路径不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b31ce71016b3222bf0f0001","lastModifiedDate":"20190607213102","createdDate":"20190607213102","name":"测试上传","suffix":"jpg","url":"1111111","info1":"","info3":"","info4":"","info5":"","info2":""}"
     * }
     */
    @PostMapping("/upload_file")
    public JsonResult uploadFile(String name,
                                 String url,
                                 String suffix) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(suffix) || StringUtils.isBlank(url)) {
            return JsonResult.failure(601, "文件名/后缀名/文件路径不能为空");
        }
        AssessmentFile file = new AssessmentFile();
        file.setCreatedDate(new Date());
        file.setLastModifiedDate(new Date());
        file.setName(name);
        file.setSuffix(suffix);
        file.setUrl(url);
        file = assessmentService.saveFile(null, file);
        jsonResult.setData(JsonUtils.getJson(file));
        return jsonResult;
    }

    /**
     * @api {POST} /ass/del_file 删除审核文件
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 删除审核文件
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例：
     *                /ass/del_file?ids=2c92eb816b31ce71016b3222bf0f0001
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/del_file")
    public JsonResult delFile(String[] ids) {
        List<AssessmentFile> files = assessmentService.findFilesByIds(ids);
        assessmentService.delFiles(files);
        return JsonResult.success();
    }

    /**
     * @api {POST} /ass/add_index 添加指标
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 添加指标
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例：
     *                /ass/add_index?name=添加指标&remark=测试添加指标
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     *                                 601:考核不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b32d9fc016b32da253b0000","createdDate":"20190608005125","lastModifiedDate":"20190608005125","name":"添加指标","code":"124","remark":"测试添加指标","info1":"","info2":"","info5":"","info3":"","info4":""}"
     * }
     */
    @PostMapping("/add_index")
    public JsonResult addIndex(String name,
                               String remark,
                               String fatherId,
                               String assessmentId) {
        Assessment assessment = assessmentService.find(assessmentId);
        if (assessment == null) {
            return JsonResult.failure(601, "考核不存在");
        } 
        AssessmentIndex index = new AssessmentIndex();
        index.setCreatedDate(new Date());
        index.setLastModifiedDate(new Date());
        index.setName(name);
        index.setRemark(remark);
        index.setAssessment(assessment);
        AssessmentIndex father = assessmentService.findIndexById(fatherId);
        if (father != null) {
            index.setFather(father);
        }
        index = assessmentService.saveIndex(null, index);
        jsonResult.setData(JsonUtils.getJson(index));
        return jsonResult;
    }

    /**
     * @api {POST} /ass/del_index 删除指标
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 删除指标
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例：
     *                /ass/del_index?ids=123&ids=2c92eb816b32d9fc016b32da253b0000&ids=1234
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:ids不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/del_index")
    public JsonResult delIndex(String[] ids) {
        if (ids == null) {
            return JsonResult.notFound("ids不能为空");
        }
        List<AssessmentIndex> list = assessmentService.findIndexByIds(ids);
        assessmentService.delIndexs(list);
        return JsonResult.success();
    }

    /**
     * @api {POST} /ass/add 添加审核
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 添加审核
     * @apiParam {String} name 必填
     * @apiParam {int} type 必填
     * @apiParam {int} total 必填
     * @apiParam {String} startTime 必填,格式:yyyyMMddHHmmss
     * @apiParam {String} endTime 必填,格式:yyyyMMddHHmmss
     * @apiParam {String} remark
     * @apiParam {String[]} fileIds
     * @apiParam {String[]} indexIds
     * @apiParam {String} subjectId
     * @apiParam {String} departId
     * @apiParam {String} groupId
     * @apiParam {String} userGroupId
     * @apiParamExample {json} 请求样例：
     *                /ass/add?name=添加考核&type=1&total=111&startTime=20190608111111&endTime=20190608111112&remark=1234&fileIds=2c92eb816b31ce71016b322340970002&fileIds=2c92eb816b31ce71016b322349220003&indexIds=2c92eb816b32d9fc016b32e5eeec0001
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:类型不存在</br>
     *                                 601:考核名称不能为空</br>
     *                                 602:起止时间不能为空</br>
     *                                 603:时间格式不为:yyyyMMddHHmmss</br>
     *                                 604:时间转换错误,请检测时间格式</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b32f12a016b3319ff960007","createdDate":"20190608020110","lastModifiedDate":"20190608020110","name":"添加考核","type":"standard","remark":"1234","total":"111","startTime":"20190608111111","endTime":"20190608111112","info1":"","info3":"","info4":"","info5":"","info2":"","assessmentFiles":["{"version":"4","id":"2c92eb816b31ce71016b322349220003","createdDate":"20190607213141","lastModifiedDate":"20190607213141","name":"测试上传12","url":"1111111","suffix":"jpg","info1":"","info3":"","info4":"","info5":"","info2":""}","{"version":"4","id":"2c92eb816b31ce71016b322340970002","createdDate":"20190607213139","lastModifiedDate":"20190607213139","name":"测试上传1","url":"1111111","suffix":"jpg","info1":"","info3":"","info4":"","info5":"","info2":""}"]}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          @RequestParam(defaultValue = "0") int type,
                          @RequestParam(defaultValue = "0") int total,
                          String startTime,
                          String endTime,
                          String remark,
                          String[] fileIds,
                          String[] indexIds,
                          String subjectId,
                          String departId,
                          String groupId,
                          String userGroupId) {
        if (StringUtils.isBlank(name)) {
            return JsonResult.failure(601, "考核名称不能为空");
        }
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return JsonResult.failure(602, "起止时间不能为空");
        } 
        if (!TimeUtils.isyyyyMMddHHmmss(startTime) || !TimeUtils.isyyyyMMddHHmmss(endTime)) {
            return JsonResult.failure(603, "时间格式不为:yyyyMMddHHmmss");
        } 
        Assessment assessment = new Assessment();
        assessment.setCreatedDate(new Date());
        assessment.setLastModifiedDate(new Date());
        assessment.setName(name);
        assessment.setTotal(total);
        assessment.setStartTime(TimeUtils.getDate(startTime));
        assessment.setEndTime(TimeUtils.getDate(endTime));
        assessment.setRemark(remark);
        if (assessment.getStartTime() == null || assessment.getEndTime() == null) {
            return JsonResult.failure(604, "时间转换错误,请检测时间格式");
        } 
        for (Assessment.ASSESSMENT_TYPE atype : Assessment.ASSESSMENT_TYPE.values()) {
            if (atype.ordinal() == type) {
                assessment.setType(atype);
                break;
            }
        }
        if (assessment.getType() == null) {
            return JsonResult.notFound("类型不存在");
        }
        List<AssessmentIndex> index = assessmentService.findIndexByIds(indexIds);
        List<AssessmentFile> file = assessmentService.findFilesByIds(fileIds);
        assessment = assessmentService.save(assessment, index, file, subjectId, departId, groupId, userGroupId);
        jsonResult.setData(conver(assessment));
        return jsonResult;
    }

    /**
     * @api {POST} /ass/modify 修改考核
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 修改考核
     * @apiParam {String} id 必填
     * @apiParam {String} name
     * @apiParam {int} type
     * @apiParam {int} total
     * @apiParam {String} startTime 必填,格式:yyyyMMddHHmmss
     * @apiParam {String} endTime 必填,格式:yyyyMMddHHmmss
     * @apiParam {String} remark
     * @apiParam {String[]} fileIds
     * @apiParam {String[]} indexIds
     * @apiParam {String} subjectId
     * @apiParam {String} departId
     * @apiParam {String} groupId
     * @apiParam {String} userGroupId
     * @apiParamExample {json} 请求样例：
     *                /ass/modify?id=11&name=1234&startTime=20191111111111&endTime=20191111111111
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:类型不存在</br>
     *                                 601:考核名称不能为空</br>
     *                                 602:起止时间不能为空</br>
     *                                 603:时间格式不为:yyyyMMddHHmmss</br>
     *                                 604:时间转换错误,请检测时间格式</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"2","id":"11","createdDate":"11","lastModifiedDate":"20190608034947","name":"1234","type":"branch","remark":"","total":"0","startTime":"20191111111111","endTime":"20191111111111","info1":"","info3":"","info4":"","info5":"","info2":""}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult add(String id,
                          String name,
                          @RequestParam(defaultValue = "0") int type,
                          @RequestParam(defaultValue = "0") int total,
                          String startTime,
                          String endTime,
                          String remark,
                          String[] fileIds,
                          String[] indexIds,
                          String subjectId,
                          String departId,
                          String groupId,
                          String userGroupId) {
        if (StringUtils.isBlank(name)) {
            return JsonResult.failure(601, "考核名称不能为空");
        }
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return JsonResult.failure(602, "起止时间不能为空");
        }
        if (!TimeUtils.isyyyyMMddHHmmss(startTime) || !TimeUtils.isyyyyMMddHHmmss(endTime)) {
            return JsonResult.failure(603, "时间格式不为:yyyyMMddHHmmss");
        }
        Assessment assessment = assessmentService.find(id);
        assessment.setLastModifiedDate(new Date());
        assessment.setName(name);
        assessment.setTotal(total);
        assessment.setStartTime(TimeUtils.getDate(startTime));
        assessment.setEndTime(TimeUtils.getDate(endTime));
        assessment.setRemark(remark);
        if (assessment.getStartTime() == null || assessment.getEndTime() == null) {
            return JsonResult.failure(604, "时间转换错误,请检测时间格式");
        }
        for (Assessment.ASSESSMENT_TYPE atype : Assessment.ASSESSMENT_TYPE.values()) {
            if (atype.ordinal() == type) {
                assessment.setType(atype);
                break;
            }
        }
        if (assessment.getType() == null) {
            return JsonResult.notFound("类型不存在");
        }
        List<AssessmentIndex> index = assessmentService.findIndexByIds(indexIds);
        List<AssessmentFile> file = assessmentService.findFilesByIds(fileIds);
        assessment = assessmentService.modify(assessment, index, file, subjectId, departId, groupId, userGroupId);
        jsonResult.setData(conver(assessment));
        return jsonResult;
    }

    /**
     * @api {POST} /ass/del 删除考核项目
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 删除考核项目
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                /ass/del?id=2c92eb816b32f12a016b336c81d4000a
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:id不能为空</br>
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
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.failure(601, "id不能为空");
        }
        assessmentService.delete(id);
        return JsonResult.success();
    }

    /**
     * @api {POST} /ass/view 查看考核
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 查看考核
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
     *                /ass/view?id=2c92eb816b32f12a016b336c81d4000a
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:考核项目不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"2c92eb816b32f12a016b3319ff960007","createdDate":"20190608020110","lastModifiedDate":"20190608020110","name":"添加考核","type":"standard","remark":"1234","total":"111","startTime":"20190608111111","endTime":"20190608111112","info1":"","info3":"","info4":"","info5":"","info2":""}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        Assessment assessment = assessmentService.find(id);
        if (assessment == null) {
            return JsonResult.notFound("考核项目不存在");
        }
        jsonResult.setData(conver(assessment));
        return jsonResult;
    }

    private String conver(Assessment ass) {
        String reuslt = null;
        try {
            JSONObject json = new JSONObject(JsonUtils.getJson(ass));
            JSONArray arr = new JSONArray();
            if (CollectionUtils.isNotEmpty(ass.getAssessmentIndices())) {
                for (AssessmentIndex index : ass.getAssessmentIndices()) {
                    arr.put(JsonUtils.getJson(index));
                }
                json.put("assessmentIndices", arr);
                arr = new JSONArray();
            } 
            if (CollectionUtils.isNotEmpty(ass.getAssessmentFiles())) {
                for (AssessmentFile file : ass.getAssessmentFiles()) {
                    arr.put(JsonUtils.getJson(file));
                }
                json.put("assessmentFiles", arr);
            }
            reuslt = JsonUtils.delString(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reuslt;
    }

}
