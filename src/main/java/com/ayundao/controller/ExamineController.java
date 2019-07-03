package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.BaseEntity;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * @ClassName: ExamineController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 9:01
 * @Description: 控制层 - 审批
 * @Version: V1.0
 */
@RestController
@RequestMapping("/examine")
public class ExamineController extends BaseController {

    @Autowired
    private ExamineService examineService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserRelationService userRelationService;

    /**
     * 审核类型阈值
     */
    private int typeIndex = 1;

    /**
     * @api {GET} /examine/getLeaveTypes 获取请假类型
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 获取请假类型
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              /examine/getLeaveTypes
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"2": "年假","3": "事假","4": "病假","5": "调休","6": "产假","7": "陪产假","8": "婚假","9": "例假","10": "丧假","11": "哺乳假"
     *     }
     * }
     */
    @GetMapping("/getLeaveTypes")
    public JsonResult getLeaveTypes() {
        JSONObject json = new JSONObject();
        for (Examine.REASON type : Examine.REASON.values()) {
            if (type.ordinal() <= typeIndex) continue;
            json.put(type.getIndex() + "", type.getName());
        }
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {GET} /examine/getReplyTypes 获取请示类型
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 获取请示批复类型
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              /examine/getReplyTypes
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "0": "个人原因",
     *         "1": "工作原因"
     *     }
     * }
     */
    @GetMapping("/getReplyTypes")
    public JsonResult getReplyTypes() {
        JSONObject json = new JSONObject();
        for (Examine.REASON type : Examine.REASON.values()) {
            if (type.ordinal() > typeIndex) continue;
            json.put(type.getIndex() + "", type.getName());
        }
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/addFile 上传审批附件
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 上传审批附件
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /examine/addFile
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190624162600","lastModifiedDate": "20190624162600","name": "970c835d577242238acdf7f1ca7e3d11","info1": "","id": "402881916b889769016b8897885f0000","info5": "","suffix": "xlsx","version": "0","info4": "","info3": "","url": "medicalfile\\970c835d577242238acdf7f1ca7e3d11.xlsx","info2": ""
     *     }
     * }
     */
    @PostMapping("/addFile")
    public JsonResult uploadFile(MultipartFile file) {
        return examineService.saveFile(file, jsonResult, uploadPath);
    }

    /**
     * @api {POST} /examine/addImage 上传审批图片
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 上传审批图片
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /examine/addImage
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190624162600","lastModifiedDate": "20190624162600","name": "970c835d577242238acdf7f1ca7e3d11","info1": "","id": "402881916b889769016b8897885f0000","info5": "","suffix": "xlsx","version": "0","info4": "","info3": "","url": "medicalfile\\970c835d577242238acdf7f1ca7e3d11.xlsx","info2": ""
     *     }
     * }
     */
    @PostMapping("/addImage")
    public JsonResult addImage(MultipartFile file) {
        return examineService.saveImage(file, jsonResult, uploadPath);
    }

    /**
     * @api {POST} /examine/delFile 删除附件
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 删除审批附件
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              ?id=xxx
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:文档不存在或者ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/delFile")
    public JsonResult delFile(String id) {
        ExamineFile file = examineService.findFile(id);
        if (file == null) {
            return JsonResult.notFound( "文档不存在或者ID为空");
        }
        examineService.delFile(file);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/delImage 删除图片
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 删除审批图片
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              ?id=xxx
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:文档不存在或者ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/delImage")
    public JsonResult delImage(String id) {
        ExamineImage image = examineService.findImage(id);
        if (image == null) {
            return JsonResult.notFound( "文档不存在或者ID为空");
        }
        examineService.delImage(image);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/addLeave 添加请假
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 添加请假
     * @apiParam {String} startTime
     * @apiParam {String} endTime
     * @apiParam {int} type
     * @apiParam {String} cause
     * @apiParam {String} userId
     * @apiParam {String} originId
     * @apiParam {String[]} examinerIds
     * @apiParam {String[]} examinerOriginIds
     * @apiParam {String[]} imageIds
     * @apiParamExample {json} 请求示例:
     *              ?startTime=20190702000000&endTime=20190702000000&type=2&cause=病假&userId=402881916ba275d7016ba277b20d0000&originId=402881916b9d3031016b9d63a172000d&examinerIds=402881916bb1827c016bb19005190001&examinerOriginIds=402881916b9d3031016b9d63a172000d&imageIds=402881916bb1827c016bb182f1500000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:用户不存在或者用户ID为空</br>
     *                                 601:时间格式不符</br>
     *                                 602:请假事由不能为空</br>
     *                                 603:用户所属部门/组织不存在,或userId与originId为空</br>
     *                                 604:审批人不能为空</br>
     *                                 605:审批人所属部门/组织不存在</br>
     *                                 606:审批人ID与审批人组织/部门ID长度不符</br>
     *                                 607:审批人与所属部门/机构不符</br>
     *                                 608:type类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/addLeave")
    public JsonResult addLeave(String startTime,
                          String endTime,
                          @RequestParam(defaultValue = "2") int type,
                          String cause,
                          String userId,
                          String originId,
                          String[] examinerIds,
                          String[] examinerOriginIds,
                          String[] imageIds) {
        if (!TimeUtils.isyyyyMMddHHmmss(startTime) || !TimeUtils.isyyyyMMddHHmmss(endTime)) {
            return JsonResult.failure(601, "时间格式不符");
        }
        if (StringUtils.isBlank(cause)) {
            return JsonResult.failure(602, "请假事由不能为空");
        }
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.notFound("用户不存在或者用户ID为空");
        }
        UserRelation userRelation = userRelationService.findByUserIdAndDepartIdOrGroupId(user.getId(), originId, originId);
        if (userRelation == null) {
            return JsonResult.failure(603, "用户所属部门/组织不存在,或userId与originId为空");
        }
        if (examinerIds == null || examinerIds.length == 0) {
            return JsonResult.failure(604, "审批人不能为空");
        } 
        if (examinerOriginIds == null || examinerOriginIds.length == 0) {
            return JsonResult.failure(605, "审批人所属部门/组织不存在");
        } 
        if (examinerIds.length != examinerOriginIds.length) {
            return jsonResult.failure(606, "审批人ID与审批人组织/部门ID长度不符");
        }
        if (type <= 1 || type > Examine.REASON.values().length) {
            return JsonResult.failure(608, "type类型异常");
        } 
        List<UserRelation> list = new LinkedList<>();
        for (int i = 0; i < examinerIds.length; i++) {
            UserRelation ur = userRelationService.findByUserIdAndDepartIdOrGroupId(examinerIds[i], examinerOriginIds[i], examinerOriginIds[i]);
            if (ur == null) {
                return JsonResult.failure(607, "审批人与所属部门/机构不符");
            }
            list.add(ur);
        }
        List<ExamineImage> images = examineService.findImageByIds(imageIds);
        Examine.REASON reason = null;
        for (Examine.REASON reason_type : Examine.REASON.values()) {
            if (reason_type.ordinal() == type) {
                reason = reason_type;
                break;
            } 
        }
        Examine examine = examineService.saveLeave(userRelation, list, startTime, endTime, reason, cause, images);
        JSONObject json = convert(examine);
        json.put("examineProcess", convertExamineProcesses(examine.getExamineProcesses(), userId));
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/addReply 添加请假
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 添加请假
     * @apiParam {int} type 请示类型:0-个人原因(默认),1-工作原因
     * @apiParam {String} cause 事由
     * @apiParam {String} detail 详情
     * @apiParam {String} userId 用户ID,必填
     * @apiParam {String} originId 用户所属部门/组织ID,必填
     * @apiParam {String[]} examinerIds 审批人ID
     * @apiParam {String[]} examinerOriginIds 审批人所属部门/组织ID
     * @apiParam {String[]} copierIds 抄送人ID
     * @apiParam {String[]} copierOriginIds 抄送人所属部门/组织ID
     * @apiParam {String[]} imageIds    图片ID
     * @apiParam {String[]} fileIds     附件ID
     * @apiParamExample {json} 请求示例:
     *              ?
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:用户不存在或者用户ID为空</br>
     *                                 601:时间格式不符</br>
     *                                 602:请假事由不能为空</br>
     *                                 603:用户所属部门/组织不存在,或userId与originId为空</br>
     *                                 604:审批人不能为空</br>
     *                                 605:审批人所属部门/组织不存在</br>
     *                                 606:审批人ID与审批人组织/部门ID长度不符</br>
     *                                 607:审批人与所属部门/组织不符</br>
     *                                 608:type类型异常</br>
     *                                 609:抄送人与部门/组织不符</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @PostMapping("/addReply")
    public JsonResult addReply(@RequestParam(defaultValue = "0") int type,
                               String cause,
                               String detail,
                               String userId,
                               String originId,
                               String[] examinerIds,
                               String[] examinerOriginIds,
                               String[] copierIds,
                               String[] copierOriginIds,
                               String[] imageIds,
                               String[] fileIds) {
        if (StringUtils.isBlank(cause)) {
            return JsonResult.failure(602, "请假事由不能为空");
        }
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.notFound("用户不存在或者用户ID为空");
        }
        UserRelation userRelation = userRelationService.findByUserIdAndDepartIdOrGroupId(user.getId(), originId, originId);
        if (userRelation == null) {
            return JsonResult.failure(603, "用户所属部门/组织不存在,或userId与originId为空");
        }
        if (examinerIds == null || examinerIds.length == 0) {
            return JsonResult.failure(604, "审批人不能为空");
        }
        if (examinerOriginIds == null || examinerOriginIds.length == 0) {
            return JsonResult.failure(605, "审批人所属部门/组织不存在");
        }
        if (examinerIds.length != examinerOriginIds.length) {
            return jsonResult.failure(606, "审批人ID与审批人组织/部门ID长度不符");
        }
        if (type < 1 || type > typeIndex) {
            return JsonResult.failure(608, "type类型异常");
        }
        List<UserRelation> list = checkUserRelation(new ArrayList(){}, examinerIds, examinerOriginIds);
        if (CollectionUtils.isEmpty(list)) {
            return JsonResult.failure(607, "审批人与所属部门/组织不符");
        }
        List<UserRelation> cList = copierIds == null ? null : checkUserRelation(new ArrayList(){}, copierIds, copierOriginIds);
        if (CollectionUtils.isEmpty(cList) && copierIds != null) {
            return jsonResult.failure(609, "抄送人与部门/组织不符");
        } 
        List<ExamineImage> images = examineService.findImageByIds(imageIds);
        List<ExamineFile> files = examineService.findFileByIds(fileIds);
        Examine.REASON reason = null;
        for (Examine.REASON reason_type : Examine.REASON.values()) {
            if (reason_type.ordinal() == type) {
                reason = reason_type;
                break;
            }
        }
        Examine examine = examineService.saveReply(userRelation, list, cList, reason, cause, detail, images, files);
        JSONObject json = convert(examine);
        json.put("examineProcess", convertExamineProcesses(examine.getExamineProcesses(), userId));
        jsonResult.setData(json);
        return jsonResult;
    }
    
    private List<BaseEntity> checkUserRelation(List<BaseEntity> list, String[] ids, String[] originIds) {
        for (int i = 0; i < ids.length; i++) {
            UserRelation ur = userRelationService.findByUserIdAndDepartIdOrGroupId(ids[i], originIds[i], originIds[i]);
            if (ur == null) {
                return new ArrayList<>();
            }
            list.add(ur);
        }
        return list;
    }

    /**
     * @api {POST} /examine/view 查看请假审批
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 查看请假审批
     * @apiParam {String} id 请假ID,必填
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bb19747016bb197bdd50000
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
    @PostMapping("/view")
    public JsonResult view(String id) {
        Examine examine = examineService.find(id);
        JSONObject json = convert(examine);
        json.put("examineProcess", convertExamineProcesses(examine.getExamineProcesses(), null));
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/del 删除请假/请示
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 删除请假/请示
     * @apiParam {String} id 请假ID,必填
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bb19747016bb197bdd50000
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
    @PostMapping("/del")
    public JsonResult delLeave(String id) {
        Examine examine = examineService.find(id);
        if (examine != null) {
            examineService.delete(examine);
        }
        return JsonResult.success();
    }


    /**
     * @api {POST} /examine/list 请假列表
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 请假列表
     * @apiParam {String} userId 用户ID,必填
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bb19747016bb197bdd50000
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "type": 0,    "reasonType": 2,    "examineProcess": [        {            "level": 0,            "subject": {                "name": "富阳人民医院",                "id": ""            },            "groups": {                "name": "行政支部",                "id": ""            },            "depart": null,            "type": 0,            "user": {                "name": "普通用户",                "id": ""            },            "status": 0        },        {            "level": 1,            "subject": {                "name": "富阳人民医院",                "id": ""            },            "groups": {                "name": "行政支部",                "id": ""            },            "depart": null,            "type": 0,            "user": {                "name": "部门主管",                "id": ""            },            "status": 0        }    ],    "startTime": "20190702000000",    "id": "402881916bb66e6b016bb66eaa0e0000",    "endTime": "20190702000000",    "examineText": {        "cause": "病假",        "detail": ""    }},{    "type": 0,    "reasonType": 2,    "examineProcess": [        {            "level": 1,            "subject": {                "name": "富阳人民医院",                "id": ""            },            "groups": {                "name": "行政支部",                "id": ""            },            "depart": null,            "type": 0,            "user": {                "name": "部门主管",                "id": ""            },            "status": 0        },        {            "level": 0,            "subject": {                "name": "富阳人民医院",                "id": ""            },            "groups": {                "name": "行政支部",                "id": ""            },            "depart": null,            "type": 0,            "user": {                "name": "普通用户",                "id": ""            },            "status": 0        }    ],    "startTime": "20190702000000",    "id": "402881916bb66e6b016bb66f6eb00004",    "endTime": "20190702000000",    "examineText": {        "cause": "病假",        "detail": ""    }}
     *     ]
     * }
     */
    @PostMapping("/list")
    public JsonResult listLeave(String userId) {
        List<ExamineProcess> examines = examineService.findProcessByUserId(userId);
        JSONArray arr = new JSONArray();
        for (ExamineProcess ep : examines) {
            JSONObject json = convert(ep.getExamine());
            json.put("examineProcess", convertExamineProcesses(ep.getExamine().getExamineProcesses(), null));
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * 转换Examine
     * @param examine
     * @return
     */
    private JSONObject convert(Examine examine) {
        JSONObject json = JsonUtils.getJson(examine);
        json.put("type", examine.getType().ordinal());
        json.put("reasonType", examine.getReasonType().ordinal());
        json.put("examineText", JsonUtils.getSimpleJson(examine.getExamineText(), new String[]{"cause", "detail"}));
        return json;
    }

    private JSONArray convertExamineProcesses(Set<ExamineProcess> examineProcesses, String userId) {
        JSONArray arr = new JSONArray();
        for (ExamineProcess ep : examineProcesses) {
            JSONObject j = new JSONObject();
            j.put("subject", JsonUtils.getJson(ep.getSubject()));
            j.put("user", JsonUtils.getJson(ep.getUser()));
            j.put("depart", ep.getDepart() == null ? null : JsonUtils.getJson(ep.getDepart()));
            j.put("groups", ep.getGroup() == null ? null : JsonUtils.getJson(ep.getGroup()));
            j.put("type", ExamineProcess.PERSON_TYPE.valueOf(ep.getType().name()).getName());
            j.put("status", ExamineProcess.PROCESS_STATUS.valueOf(ep.getStatus().name()).getName());
            j.put("level", ep.getLevel());
            arr.add(j);
            if (StringUtils.isNotBlank(userId) && userId.equals(ep.getUser().getId())) {
                arr = new JSONArray();
                arr.add(j);
                break;
            }
        }

        return arr;
    }
}
