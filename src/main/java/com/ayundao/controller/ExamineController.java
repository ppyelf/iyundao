package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
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
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.ayundao.base.BaseController.*;


/**
 * @ClassName: ExamineController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 9:01
 * @Description: 控制层 - 审批
 * @Version: V1.0
 */
@RequiresUser
@RequiresRoles(value = {ROLE_MANAGER, ROLE_USER, ROLE_ADMIN, ROLE_AUDITOR}, logical = Logical.OR)
@RestController
@RequestMapping("/examine")
public class ExamineController extends BaseController {

    @Autowired
    private ExamineService examineService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private UserFileService userFileService;

    /**
     * 审核类型阈值
     */
    private int typeIndex = 1;

    /**
     * @api {GET} /examine/getLeaveTypes 获取请假类型
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 获取请假类型
     * @apiHeader {String} IYunDao-AssessToken token验证
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
        JSONArray arr = new JSONArray();
        for (Examine.REASON type : Examine.REASON.values()) {
            if (type.ordinal() <= typeIndex) continue;
            JSONObject json = new JSONObject();
            json.put(type.getIndex() + "", type.getName());
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {GET} /examine/getFileTypes 获取文件类型
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 获取文件类型
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              /examine/getFileTypes
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
    @GetMapping("/getFileTypes")
     public JsonResult getFileTypes(){
         JSONArray arr = new JSONArray();
         for (UserFile.TYPE type : UserFile.TYPE.values()) {
             if (type.ordinal() <= typeIndex) continue;
             JSONObject json = new JSONObject();
             json.put(type.getIndex() + "", type.getName());
             arr.add(json);
         }
         jsonResult.setData(arr);
         return jsonResult;
     }

    /**
     * @api {GET} /examine/getReplyTypes 获取请示类型
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 获取请示批复类型
     * @apiHeader {String} IYunDao-AssessToken token验证
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
        JSONArray arr = new JSONArray();
        for (Examine.REASON type : Examine.REASON.values()) {
            JSONObject json = new JSONObject();
            if (type.ordinal() > typeIndex) continue;
            json.put(type.getIndex() + "", type.getName());
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/addFile 上传审批附件
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 上传审批附件
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/addFile")
    public JsonResult uploadFile(MultipartFile file) {
        return examineService.saveFile(file, jsonResult, uploadPath);
    }

    /**
     * @api {POST} /examine/addImage 上传审批图片
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 上传审批图片
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/addImage")
    public JsonResult addImage(MultipartFile file) {
        return examineService.saveImage(file, jsonResult, uploadPath);
    }

    /**
     * @api {POST} /examine/delFile 删除附件
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 删除审批附件
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_DELETE)
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_DELETE)
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
     *                                 610:自己无法作为审核人</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
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
            if (examinerIds[i].equals(userId)) {
                return JsonResult.failure(610, "自己无法作为审核人");
            }
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
     *                                 100:审核人最多为3级</br>
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
     *                                 610:自己无法作为审核人</br>
     *                                 611:自己无法作为抄送人</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
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
        if (list.size() > 3) {
            return JsonResult.failure(100, "审核人最多为3级");
        }
        List<UserRelation> cList = copierIds == null ? null : checkUserRelation(new ArrayList(){}, copierIds, copierOriginIds);
        if (CollectionUtils.isEmpty(cList) && copierIds != null) {
            return jsonResult.failure(609, "抄送人与部门/组织不符");
        }
        for (UserRelation relation : list) {
            if (relation.getUser().getId().equals(userId)) {
                return JsonResult.failure(610, "自己无法作为审核人");
            } 
        }
        if (CollectionUtils.isNotEmpty(cList)) {
            for (UserRelation relation : cList) {
                if (relation.getUser().getId().equals(userId)) {
                    return JsonResult.failure(611, "自己无法作为抄送人");
                }
            }
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_VIEW)
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
     * @apiHeader {String} IYunDao-AssessToken token验证
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
    @RequiresPermissions(PERMISSION_DELETE)
    @PostMapping("/del")
    public JsonResult delLeave(String id) {
        Examine examine = examineService.find(id);
        if (examine != null) {
            examineService.delete(examine);
        }
        return JsonResult.success();
    }


    /**
     * @api {POST} /examine/list 列表
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} userId 用户ID,必填
     * @apiParam {int} type 列表类型,必填,0-请假列表,1请示列表
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
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/list")
    public JsonResult list(String userId,
                                @RequestParam(defaultValue = "0") int type) {
        List<ExamineProcess> examines = examineService.findProcessByUserIdAndType(userId, type);
        JSONArray arr = new JSONArray();
        for (ExamineProcess ep : examines) {
            JSONObject json = convert(ep.getExamine());
            json.put("examineProcess", convertExamineProcesses(ep.getExamine().getExamineProcesses(), userId));
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/apply 审核
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 审核
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 请假ID,必填
     * @apiParam {String} userId ,必填
     * @apiParam {int} status 审核态度,必填,1-同意(默认),2-拒绝
     * @apiParam {String} comment 评语(拒绝必须填写)
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bb19747016bb197bdd50000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:审核不存在或者审核ID为空</br>
     *                                 601:审核态度类型异常</br>
     *                                 100: 上级审核未通过</br>
     *                                 101:拒绝必须填写拒绝理由</br>
     *                                 102:只有审核人才有审核流程</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresRoles(ROLE_AUDITOR)
    @RequiresPermissions(PERMISSION_EXAMINE)
    @PostMapping("/apply")
    public JsonResult examineEntity(String id,
                                    String userId,
                                    @RequestParam(defaultValue = "1") int status,
                                    String comment) {
        Examine examine = examineService.find(id);
        if (examine == null) {
            return jsonResult.failure(404, "审核不存在或者审核ID为空");
        }
        ExamineProcess.PROCESS_STATUS statusType = null;
        for (ExamineProcess.PROCESS_STATUS type : ExamineProcess.PROCESS_STATUS.values()) {
            if (type.ordinal() == status) {
                statusType = type;
                break;
            } 
        }
        if (statusType == null) {
            return jsonResult.failure(601, "审核态度类型异常");
        } 
        ExamineProcess ep = examineService.findProcessByExamineIdAndUserId(id, userId);
        if (ep.getType() != ExamineProcess.PERSON_TYPE.Examiner) {
            return JsonResult.failure(102, "只有审核人才有审核流程");
        } 
        int level = ep.getLevel() == 1 ? 1 : ep.getLevel() - 1;
        //检测上一级审核是否通过
        if (level != 1 && examineService.checkPreviousStatus(id, level, ExamineProcess.PROCESS_STATUS.Audit_in_progress)) {
            return JsonResult.failure(100, "上级审核未通过");
        }
        if (status != 1 && StringUtils.isBlank(comment)) {
            return jsonResult.failure(101, "拒绝必须填写拒绝理由");
        }

        ep = examineService.apply(ep, statusType, comment);
        JSONObject json = convert(ep.getExamine());
        Set<ExamineProcess> set = new HashSet<>();
        set.add(ep);
        json.put("examineProcess", convertExamineProcesses(set, userId));
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {GET} /examine/userFileType 资料类型列表
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 资料类型列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {MultipartFile} file 文件
     * @apiParam {int} type 文件类型
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bb19747016bb197bdd50000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresRoles(value = {ROLE_PUBLISHER, ROLE_USER, ROLE_MANAGER}, logical = Logical.OR)
    @RequiresPermissions(PERMISSION_RELEASE)
    @GetMapping("/userFileType")
    public JsonResult userFileType() {
        JSONArray arr = new JSONArray();
        for (UserFile.TYPE type : UserFile.TYPE.values()) {
            JSONObject json = new JSONObject();
            json.put(type.getIndex() + "", type.getName());
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /examine/uploadUserFile 上传个人资料
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 上传个人资料
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {MultipartFile} file 文件
     * @apiParam {int} type 文件类型
     * @apiParam {String} userId 用户ID
     * @apiParam {boolean} isPublic 是否公开
     * @apiParam {String[]} shareUserIds 分享用户ID集合
     * @apiParamExample {json} 请求示例:
     *              ?type=0&serId&402881916ba275d7016ba277b20d0000&isPublic=true&shareUserIds=402881916bb1827c016bb19005190001&shareUserIds=402881916bba7802016bbb169ed60000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:用户不存在或者ID为空</br>
     *                                 601:上传失败</br>
     *                                 602:文件类型异常</br>
     *                                 603:用户所属机构关系为空或shareUserIds为空</br>
     *                                 604:共享资料必须有分享人或者机构</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/uploadUserFile")
    public JsonResult uploadUserFile(MultipartFile file,
                                     @RequestParam(defaultValue = "0") int type,
                                     String userId,
                                     @RequestParam(defaultValue = "false") boolean isPublic,
                                     String[] shareUserIds) {
        UserFile.TYPE userType = null;
        for (UserFile.TYPE t : UserFile.TYPE.values()) {
            if (t.ordinal() == type) {
                userType = t;
                break;
            } 
        }
        if (userType == null) {
            return JsonResult.failure(602, "文件类型异常");
        }
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.notFound("用户不存在或者ID为空");
        }
        //检测分享
        List<UserRelation> userRelations = new ArrayList<>();
        if (isPublic) {
            if (shareUserIds == null) {
                return jsonResult.failure(604, "共享资料必须有分享人或者机构");
            } 
            userRelations = userRelationService.findByUserIds(shareUserIds);
            if (CollectionUtils.isEmpty(userRelations)) {
                return JsonResult.failure(603, "用户所属机构关系为空或shareUserIds为空");
            } 
        } 
        return userFileService.save(file, userType, user, isPublic, userRelations, jsonResult);
    }

    /**
     * @api {GET} /examine/downloadUserFile 下载个人资料
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 下载个人资料
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 文件ID
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bc15971016bc17be44c0004
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:文件不存在或者文件id为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/downloadUserFile")
    public JsonResult downloadUserFile(String id, HttpServletRequest req, HttpServletResponse resp) {
        UserFile userFile = userFileService.find(id);
        if (userFile == null) {
            return JsonResult.notFound("文件不存在或者文件id为空");
        }
        userFileService.download(userFile, req, resp);
        return JsonResult.success();
    }

    /**
     * @api {POST} /examine/delUserFile 删除个人资料
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 删除个人资料
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 文件ID
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bc15971016bc17be44c0004
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:文件不存在或者文件id为空</br>
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
    @PostMapping("/delUserFile")
    public JsonResult delUserFile(String id) {
        UserFile userFile = userFileService.find(id);
        if (userFile == null) {
            return JsonResult.notFound("文件不存在或者文件id为空");
        }
        userFileService.delete(userFile);
        return JsonResult.success();
    }

    /**
     * @api {POST} /examine/myselfList 个人资料列表
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 个人资料列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} userId 用户ID
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bc15971016bc17be44c0004
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:用户不存在或者用户ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/myselfList")
    public JsonResult userFileList(String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.notFound("用户不存在或者用户ID为空");
        }
        List<UserFile> userFiles = userFileService.getMySelfList(user.getId());
        JSONArray arr = new JSONArray();
        for (UserFile userFile : userFiles) {

            arr.add(convertUserFile(userFile));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {GET} /examine/shareList 资料分享列表
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 资料分享列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求示例:
     *              /examine/shareList
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 100:只有管理员和资料审核人可以查看该列表
     *                                 404:用户不存在或者用户ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/shareList")
    public JsonResult userFileShareList() {
        List<UserFile> userFiles = userFileService.getShareList();
        JSONArray arr = new JSONArray();
        for (UserFile userFile : userFiles) {
            JSONObject json = convertUserFile(userFile);
            json.put("to", convertFileTo(userFile.getUserFileTo()));
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {GET} /examine/examineList 资料审核列表
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 资料审核列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} userId 用户ID
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bc15971016bc17be44c0004
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 100:只有管理员和资料审核人可以查看该列表
     *                                 404:用户不存在或者用户ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresRoles(ROLE_PUBLISHER)
    @RequiresPermissions(PERMISSION_EXAMINE)
    @GetMapping("/examineList")
    public JsonResult userFileExamineList(String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return jsonResult.notFound("用户不存在或者用户ID为空");
        } 
        if (user.getUserType().ordinal() != 1 && user.getUserType().ordinal() != 2) {
            return JsonResult.failure(100,"只有管理员和资料审核人可以查看该列表");
        }
        List<UserFile> userFiles = userFileService.findByStatusIsWaiting();
        JSONArray arr = new JSONArray();
        for (UserFile userFile : userFiles) {
            JSONObject json = convertUserFile(userFile);
            json.put("to", convertFileTo(userFile.getUserFileTo()));
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {GET} /examine/examineFile 审核文件
     * @apiGroup Examine
     * @apiVersion 1.0.0
     * @apiDescription 审核文件
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 资源文件ID
     * @apiparam {int} status 审核态度 1-分享,3-拒绝分享
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916bc15971016bc17be44c0004
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 100:只有管理员和资料审核人可以查看该列表
     *                                 404:资源文件不存在或者ID为空</br>
     *                                 601:审核态度参数异常
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresRoles(ROLE_PUBLISHER)
    @RequiresPermissions(PERMISSION_EXAMINE)
    @PostMapping("/examineFile")
    public JsonResult examineUserFile(String id, @RequestParam(defaultValue = "0") int status) {
        UserFile userFile = userFileService.find(id);
        if (userFile == null) {
            return jsonResult.notFound("资源文件不存在或者ID为空");
        }
        if (status != 1 && status != 3) {
            return JsonResult.failure(601, "审核态度参数异常");
        }

        userFile = userFileService.examineUserFile(userFile, status);
        JSONObject json = JsonUtils.getJson(userFile);
        json.put("to", userFile.getUserFileTo());
        jsonResult.setData(json);
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

    /**
     * 转化审核流程
     * @param examineProcesses
     * @param userId
     * @return
     */
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
            j.put("comment", ep.getComment());
            arr.add(j);
            if (StringUtils.isNotBlank(userId) && userId.equals(ep.getUser().getId())) {
                arr = new JSONArray();
                arr.add(j);
                return arr;
            }
        }
        return arr;
    }

    /**
     * 转换userFile
     * @param userFile
     * @return
     */
    private JSONObject convertUserFile(UserFile userFile) {
        JSONObject json = JsonUtils.getJson(userFile);
        json.put("type", userFile.getType().getName());
        json.put("status", userFile.getStatus().getName());
        return json;
    }

    private JSONArray convertFileTo(Set<UserFileTo> to) {
        JSONArray arr = new JSONArray();
        for (UserFileTo t : to) {
            JSONObject json = JsonUtils.getJson(t);
            json.put("subject", JsonUtils.getJson(t.getSubject()));
            json.put("depart", t.getDepart() == null ? null : JsonUtils.getJson(t.getDepart()));
            json.put("group", t.getGroup() == null ? null : JsonUtils.getJson(t.getGroup()));
            json.put("user", JsonUtils.getJson(t.getUser()));
            arr.add(json);
        }
        return arr;
    }
}
