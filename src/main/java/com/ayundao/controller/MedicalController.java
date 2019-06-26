package com.ayundao.controller;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName: MedicalController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:15
 * @Description: 控制层 - 医德档案
 * @Version: V1.0
 */
@RestController
@RequestMapping("/medical")
public class MedicalController extends BaseController {

    @Autowired
    private MedicalService medicalService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationService userRelationService;

    /**
     * @api {POST} /medical/addFile 上传医德附件
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 上传医德附件
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /medical/addFile
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
    public JsonResult addFile(MultipartFile file) {
        return medicalService.saveFile(file, jsonResult);
    }

    /**
     * @api {POST} /medical/delFile 上传医德附件
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 上传医德附件
     * @apiParam {String} id 文件ID(必填)
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b889769016b8897885f0000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:文件不存在或ID错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/delFile")
    public JsonResult delFile(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.notFound("文件不存在或ID错误");
        } 
        return medicalService.deleteFile(id);
    }

    /**
     * @api {POST} /medical/add 上传医德附件
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 上传医德附件
     * @apiParam {String} name 名称(必填)
     * @apiParam {int} total 分数
     * @apiParam {String} remark 描述
     * @apiParam {String} year 年份(必填)
     * @apiParam {String[]} fileIds 附件ID集合
     * @apiParam {String} subjectId 机构ID(必填)
     * @apiParam {String} departId 部门ID,选填
     * @apiParam {String} groupId 组织ID,选填
     * @apiParam {String} userId 用户ID,选填
     * @apiParamExample {json} 请求示例:
     *              ?name=添加医德&startTime=20190625000000&endTime=20190625000000&remark=医德描述&year=2019&fileIds=402881916b8c5340016b8c53d5a70000&subjectId=bd6886bc88e54ef0a36472efd95c744c&departId=66d09d0417604cb9a52bff07dee7f408
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:医德档案名称不能为空</br>
     *                                 602:机构不存在或ID为空</br>
     *                                 603:部门ID/组织ID不能都为空</br>
     *                                 604:用户不存在或者ID为空</br>
     *                                 605:部门/组织下无用户,添加失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"total": "0","createdDate": "20190625095050","lastModifiedDate": "20190625095050","year": "2019","name": "添加医德","remark": "医德描述","id": "402881916b8c5340016b8c5418e50001","version": "0"
     *     }
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          @RequestParam(defaultValue = "0") int total,
                          String remark,
                          String year,
                          String[] fileIds,
                          String subjectId,
                          String departId,
                          String groupId,
                          String userId) {
        if (StringUtils.isBlank(name)) {
            return JsonResult.failure(601, "医德档案名称不能为空");
        }
        Subject subject = subjectService.find(subjectId);
        if (subject == null) {
            return JsonResult.failure(602, "机构不存在或ID为空");
        }
        Depart depart = departService.findById(departId);
        Groups groups = groupsService.findById(groupId);
        if (depart == null && groups == null) {
            return JsonResult.failure(603, "部门ID/组织ID都为空");
        }
        User user = userService.findById(userId);
        if (StringUtils.isNotBlank(userId) && user == null) {
            return JsonResult.failure(604, "用户不存在或者ID为空");
        }
        List<UserRelation> userRelations = userRelationService.findBySubjectAndDepartOrGroups(subjectId, departId, groupId);
        if (CollectionUtils.isEmpty(userRelations)) {
            return JsonResult.failure(605, "部门/组织下无用户,添加失败");
        }
        Medical medical = medicalService.save(name, total, remark, year, fileIds, subject, depart, groups, user);
        jsonResult.setData(convertMedical(medical));
        return jsonResult;
    }

    /**
     * 转化医德档案
     * @param medical
     * @return
     */
    private JSONObject convertMedical(Medical medical) {
        JSONObject json = JsonUtils.getJson(medical);
        return json;
    }
}
