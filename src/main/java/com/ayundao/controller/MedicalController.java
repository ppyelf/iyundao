package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

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
     * @api {POST} /medical/delFile 删除医德附件
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 删除医德附件
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
     * @api {POST} /medical/add 新建医德档案
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 新建医德档案
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
     * @api {POST} /medical/list 医德档案分页
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 医德档案分页
     * @apiParam {String} key 搜索字段名
     * @apiParam {String} value 查询值
     * @apiParam {int} page 页数(默认:0)
     * @apiParam {int} size 长度(默认:10)
     * @apiParamExample {json} 请求示例:
     *              ?key=name&value=11
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:医德档案名称不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"total": 11,"totalPage": 1,"page": 0,"content": [    {        "total": "0",        "createdDate": "20190625095050",        "lastModifiedDate": "20190625095050",        "year": "2019",        "name": "1",        "remark": "医德描述",        "id": "402881916b8c5340016b8c5418e50001",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112519",        "lastModifiedDate": "20190625112519",        "year": "2019",        "name": "2",        "remark": "医德描述",        "id": "402881916b8c5340016b8caa9a280003",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112520",        "lastModifiedDate": "20190625112520",        "year": "2019",        "name": "3",        "remark": "医德描述",        "id": "402881916b8c5340016b8caa9db40005",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112521",        "lastModifiedDate": "20190625112521",        "year": "2019",        "name": "4",        "remark": "医德描述",        "id": "402881916b8c5340016b8caaa13b0007",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112556",        "lastModifiedDate": "20190625112556",        "year": "2019",        "name": "5",        "remark": "医德描述",        "id": "402881916b8c5340016b8cab2a5b0009",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112558",        "lastModifiedDate": "20190625112558",        "year": "2019",        "name": "6",        "remark": "医德描述",        "id": "402881916b8c5340016b8cab3466000b",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112602",        "lastModifiedDate": "20190625112602",        "year": "2019",        "name": "7",        "remark": "医德描述",        "id": "402881916b8c5340016b8cab43a9000d",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112605",        "lastModifiedDate": "20190625112605",        "year": "2019",        "name": "8",        "remark": "医德描述",        "id": "402881916b8c5340016b8cab4ff6000f",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112608",        "lastModifiedDate": "20190625112608",        "year": "2019",        "name": "9",        "remark": "医德描述",        "id": "402881916b8c5340016b8cab5ad70011",        "version": "0"    },    {        "total": "0",        "createdDate": "20190625112611",        "lastModifiedDate": "20190625112611",        "year": "2019",        "name": "0",        "remark": "医德描述",        "id": "402881916b8c5340016b8cab65f00013",        "version": "0"    }]
     *     }
     * }
     */
    @PostMapping("/list")
    public JsonResult list(String key, String value,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = new Pageable(page, size);
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
            pageable.setSearchProperty(key);
            pageable.setSearchValue(value);
        }
        jsonResult.setData(JsonUtils.getPage(medicalService.findPage(pageable)));
        return jsonResult;
    }

    /**
     * @api {POST} /medical/view 查看
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 查看文档的字段
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b8c5340016b8c5418e50001
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:医德档案不存在或者ID为空</br>
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
    @PostMapping("/view")
    public JsonResult view(String id) {
        Medical medical = medicalService.find(id);
        if (medical == null) {
            return JsonResult.notFound("医德档案不存在或者ID为空");
        }
        JSONObject json = JsonUtils.getJson(medical);
        json.put("medicalRanges", convertMedicalRagen(medical.getMedicalRanges()));
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {POST} /medical/del 删除医德档案
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 删除医德档案
     * @apiParam {String} id 医德档案ID(必填)
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b8c5340016b8cab74340015
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
    public JsonResult del(String id) {
        Medical medical = medicalService.find(id);
        medicalService.delete(medical);
        return JsonResult.success();
    }

    /**
     * @api {POST} /medical/addIndex 添加医德指标
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 添加医德指标
     * @apiParam {String} name 名称(必填)
     * @apiParam {String} remark 描述
     * @apiParam {String} fatherId 父级指标ID
     * @apiParam {String} medicalId 医德档案ID(必填)
     * @apiParamExample {json} 请求示例:
     *              ?name=添加子指标&remark=子指标描述&fatherId=402881916b8d4f49016b8d5359c50005&medicalId=402881916b8d4f49016b8d501b1d0001
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:医德档案不存在或者医德档案ID为空</br>
     *                                 601:指标名称不能为空</br>
     *                                 602:父级医德指标不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190625143204","code": "1","lastModifiedDate": "20190625143204","name": "添加子指标","info1": "","remark": "子指标描述","id": "402881916b8d4f49016b8d5594a00007","info5": "","version": "0","info4": "","info3": "","info2": ""
     *     }
     * }
     */
    @PostMapping("/addIndex")
    public JsonResult addIndex(String name,
                               String remark,
                               String fatherId,
                               String medicalId) {
        if (StringUtils.isBlank(name)) {
            return JsonResult.failure(601, "指标名称不能为空");
        }
        Medical medical = medicalService.find(medicalId);
        if (medical == null) {
            return JsonResult.notFound("医德档案不存在或者医德档案ID为空");
        }
        MedicalIndex father = medicalService.findMedicalIndexById(fatherId);
        if (StringUtils.isNotBlank(fatherId) && father == null) {
            return JsonResult.failure(602, "父级医德指标不存在");
        }
        MedicalIndex index = medicalService.saveMedicalIndex(name, remark, father, medical);
        jsonResult.setData(JsonUtils.getJson(index));
        return jsonResult;
    }

    /**
     * @api {POST} /medical/addUserIndex 评价指标
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 评价指标
     * @apiParam {String} medicalIndexId 名称(必填)
     * @apiParam {String} userId 描述
     * @apiParam {String} medicalId 父级指标ID
     * @apiParam {int} score 医德档案ID(必填)
     * @apiParamExample {json} 请求示例:
     *              ?name=添加子指标&remark=子指标描述&fatherId=402881916b8d4f49016b8d5359c50005&medicalId=402881916b8d4f49016b8d501b1d0001
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:指标不存在或者指标ID为空</br>
     *                                 602:用户不存在或者用户ID为空</br>
     *                                 603:医德档案不存在或者医德ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"createdDate": "20190625143204","code": "1","lastModifiedDate": "20190625143204","name": "添加子指标","info1": "","remark": "子指标描述","id": "402881916b8d4f49016b8d5594a00007","info5": "","version": "0","info4": "","info3": "","info2": ""
     *     }
     * }
     */
    @PostMapping("/addUserIndex")
    public JsonResult addIndex(String medicalIndexId,
                               String userId,
                               String medicalId,
                               @RequestParam(defaultValue = "0") int score) {
        MedicalIndex index = medicalService.findMedicalIndexById(medicalIndexId);
        if (index == null) {
            return JsonResult.failure(601, "指标不存在或者指标ID为空");
        }
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.failure(602, "用户不存在或者用户ID为空");
        }
        Medical medical = medicalService.find(medicalId);
        if (medical == null) {
            return JsonResult.failure(603, "医德档案不存在或者医德ID为空");
        }
        MedicalUserIndex userIndex = medicalService.saveMedicalUserIndex(index, user, medical, score);
        jsonResult.setData(JsonUtils.getJson(userIndex));
        return jsonResult;
    }
    /**
     * @api {GET} /medical/indexList 指标列表
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 指标列表
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b8c5340016b8c5418e50001
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "createdDate": "20190625142938",    "code": "0",    "lastModifiedDate": "20190625142938",    "name": "添加子指标",    "info1": "",    "remark": "子指标描述",    "id": "402881916b8d4f49016b8d5359c50005",    "info5": "",    "version": "0",    "info4": "",    "info3": "",    "info2": ""}
     *     ]
     * }
     */
    @GetMapping("/indexList")
    public JsonResult indexList() {
        List<MedicalIndex> medicalIndices = medicalService.findMedicalIndexByFatherIsNullForList();
        JSONArray arr = new JSONArray();
        for (MedicalIndex medicalIndex : medicalIndices) {
            JSONObject json = JsonUtils.getJson(medicalIndex);
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /medical/child 获取指标子集
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 获取指标子集
     * @apiParam {String} id 指标ID(必填)
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b8d4f49016b8d5359c50005
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "createdDate": "20190625142938",    "code": "0",    "lastModifiedDate": "20190625142938",    "name": "添加子指标",    "info1": "",    "remark": "子指标描述",    "id": "402881916b8d4f49016b8d5359c50005",    "info5": "",    "version": "0",    "info4": "",    "info3": "",    "info2": ""}
     *     ]
     * }
     */
    @PostMapping("/child")
    public JsonResult child(String id) {
        List<MedicalIndex> medicalIndices = medicalService.findMedicalIndexChild(id);
        JSONArray arr = new JSONArray();
        for (MedicalIndex mi : medicalIndices) {
            JSONObject json = JsonUtils.getJson(mi);
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /medical/downloadExcel 下载模板
     * @apiGroup Medical
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParamExample {json} 请求示例:
     *                      /medical/downloadExcel
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
    @PostMapping("/downloadExcel")
    public JsonResult downloadExcel(HttpServletResponse resp) {
        return null;
    }

    /**
     * 转化医德档案
     * @param medical
     * @return
     */
    private JSONObject convertMedical(Medical medical) {
        return JsonUtils.getJson(medical);
    }

    /**
     * 转化医德范围
     * @param set
     * @return
     */
    private JSONArray convertMedicalRagen(Set<MedicalRange> set) {
        JSONArray arr = new JSONArray();
        if (CollectionUtils.isNotEmpty(set)) {
            for (MedicalRange mr : set) {
                JSONObject json = new JSONObject();
                json.put("id", mr.getId());
                json.put("subjectId", mr.getSubjectId());
                json.put("departId", mr.getDepartId());
                json.put("groupId", mr.getGroupId());
                json.put("userId", mr.getUserId());
                json.put("evaluate", mr.getEvaluate( ));
                arr.add(json);
            }
        }
        return arr;
    }
}
