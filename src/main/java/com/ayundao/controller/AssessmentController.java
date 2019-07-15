package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.AssessmentRepository;
import com.ayundao.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssessmentController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/10
 * @Description: 实现 - 考核
 * @Version: V1.0
 */
@RestController
@RequestMapping("/assessment")
public class AssessmentController extends BaseController {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private DepartService departService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired

    /**
     * @api {GET} /assessment/list 列表
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例:
     *                /assessment/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": [{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "branch"},{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "personnel"},{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "branch"},{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "branch"}]
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Assessment> assessments = assessmentService.findAll();
        JSONArray arr = new JSONArray();
        for (Assessment assessment : assessments) {
            JSONObject object = new JSONObject();
            object.put("number", assessment.getNumber());
            object.put("title", assessment.getName());
            object.put("type", assessment.getType());
            object.put("score", assessment.getTotal());
            object.put("starttime", assessment.getTotal());
            object.put("endtime", assessment.getEndTime());
            object.put("remark", assessment.getRemark());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }


    /**
     * @api {GET} /assessment/list 列表
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParam {int} type  默认0
     * @apiParam {int} total  默认10
     * @apiParamExample {json} 请求样例:
     * /assessment/listpage?page=1&size=2
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": [{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "branch"},{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "personnel"},{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "branch"},{"number": "","score": 11,"endtime": "11","remark": "11","starttime": 11,"title": "11","type": "branch"}]
     * }
     */
    @PostMapping("/listpage")
    public JsonResult list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Page<Assessment> assessmentPage = assessmentService.findAllForPage(new Pageable(page, size));
        jsonResult.setData(JsonUtils.getPage(assessmentPage));
        return jsonResult;
    }
/**
     * @api {POST} /Assessment/view 查看考核详情
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填 考核id
     * @apiParamExample {json} 请求样例:
     *                /assessment/view?id=4028d8816be4b6fd016be521e09f001b
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *       "data": {"number": "12","total": 80,"name": "测试考核","endtime": "2018-12-12 12:12:12","remark": "测试内容","deption": {"subject": ["bfc5bd62010f467cbbe98c9e4741733b","402881916b9d3031016b9d626593000c"],"usergroup": [],"depart": ["123"],"users": ["0a4179fc06cb49e3ac0db7bcc8cf0882","402881916ba10b8a016ba113adbc0006"],"group": ["402881916b9d3031016b9d706b4e0012","402881916b9d3031016b9d708a710013","402881916b9d3031016b9d70b2510014"]},"Index": [{"isuse": "1234567","lname": "12","norder": "123456","sname": "123","sortedcode": "12345","id": "4028d8816be4b6fd016be5224bb60024","parcode": "1234","sortedid": "1"},{"isuse": "9876543","lname": "98","norder": "987654","sname": "987","sortedcode": "98765","id": "4028d8816be4b6fd016be5224bb60025","parcode": "9876","sortedid": "9"}],"id": "4028d8816be4b6fd016be521e09f001b","starttime": "2018-12-12 12:12:12","type": "personnel","Image": [{"name": "123","id": "4028d8816be4b6fd016be52115250017","suffix": "12","url": "1231"},{"name": "123","id": "4028d8816be4b6fd016be521188f0018","suffix": "12","url": "1231"}],"File": [{"name": "12","id": "4028d8816be4b6fd016be52123450019","type": "file","suffix": "12","fromTo": "12","content": "12","url": "12"},{"name": "12","id": "4028d8816be4b6fd016be521264c001a","type": "file","suffix": "12","fromTo": "12","content": "12","url": "12"}]}
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id){
        Assessment assessment = assessmentService.findById(id);
        if (assessment ==null){
            return JsonResult.notFound("找不到此考核");
        }
        List<AssessmentRange> ranges = assessmentService.findRangeById(id);
        List<AssessmentFile> files = assessmentService.findFileById(id);
        List<AssessmentImage> images = assessmentService.findImageById(id);
        List<AssessmentIndex> indices = assessmentService.findIndexById(id);
        JSONObject object =assessmentService.showAssessment(assessment,ranges,files,images,indices);
        jsonResult.setData(object);
        return jsonResult;
    }

    /**
     * @api {POST} /assessment/add 添加考核
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} number 编号
     * @apiParam {String} name  标题(必填)
     * @apiParam {int} type  默认0        0 -个人考核 1 -支部考核 * 2 -行政考核  * 3 -机构考核
     * @apiParam {int} total  默认0 分数
     * @apiParam {String} startTime 开始时间
     * @apiParam {String} endTime 结束时间
     * @apiParam {String} remark  描述
     * @apiParam {String[]} subjectIds
     * @apiParam {String[]} userGroupIds
     * @apiParam {String[]} departIds
     * @apiParam {String[]} groupIds
     * @apiParam {String[]} userids
     * @apiParam {String[]} assessmentFiles,
     * @apiParam {String[]} assessmentImages
     * @apiParamExample {json} 请求样例:
     * /assessment/add?number=12&name=测试考核&type=0&total=80&startTime=2018-12-12 12:12:12&endTime=2018-12-12 12:12:12&remark=测试内容&subjectIds=bfc5bd62010f467cbbe98c9e4741733b,402881916b9d3031016b9d626593000c&userGroupIds=&departIds=123&groupIds=402881916b9d3031016b9d706b4e0012,402881916b9d3031016b9d708a710013,402881916b9d3031016b9d70b2510014&userids=0a4179fc06cb49e3ac0db7bcc8cf0882,402881916ba10b8a016ba113adbc0006&assessmentFiles=4028d8816bdff15a016bdff600950000,4028d8816bdff15a016bdff61e140001&assessmentImages=4028d8816bdff15a016be00f7f10000e
     * @apiSuccess (200) {String} code 200:成功</br>
     * 404:</br>
     * 601:活动名称不能为空</br>
     * 602:type类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": {"number": "12","total": "80","name": "测试考核","startTime": "2018-12-12 12:12:12","remark": "测试内容","id": "4028d8816be3fcaf016be42734820000","endTime": "2018-12-12 12:12:12","type": "personnel"}
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String number,
                          String name,
                          @RequestParam(defaultValue = "0") int type,
                          @RequestParam(defaultValue = "0") int total,
                          String startTime,
                          String endTime,
                          String remark,
                          String[] subjectIds,
                          String[] userGroupIds,
                          String[] departIds,
                          String[] groupIds,
                          String[] userids,
                          String[] assessmentFiles,
                          String[] assessmentImages) {
        Assessment assessment = new Assessment();
        assessment.setCreatedDate(new Date());
        assessment.setLastModifiedDate(new Date());
        assessment.setNumber(number);
        assessment.setName(name);
        assessment.setStartTime(startTime);
        assessment.setEndTime(endTime);
        assessment.setRemark(remark);
        assessment.setTotal(total);
        for (Assessment.ASSESSMENT_TYPE assessmentType : Assessment.ASSESSMENT_TYPE.values()) {
            if (assessmentType.ordinal() == type) {
                assessment.setType(assessmentType);
                break;
            }
        }
        if (assessment.getType() == null) {
            return JsonResult.failure(602, "type类型异常");
        }

        List<Subject> subjects = subjectService.findbyIds(subjectIds);
        if (subjects.size() != subjectIds.length) {
            return JsonResult.failure(605, "有" + (subjectIds.length - subjects.size()) + "个机构不存在");
        }
        List<Depart> departs = departService.findbyIds(departIds);
        if (departs.size() != departIds.length) {
            return JsonResult.failure(606, "有" + (departIds.length - departs.size()) + "个部门不存在");
        }
        List<Groups> groups = groupsService.findByIds(groupIds);
        if (groups.size() != groupIds.length) {
            return JsonResult.failure(607, "有" + (groupIds.length - groups.size()) + "个组织不存在");
        }
        List<User> users = userService.findbyIds(userids);
        if (users.size() != userids.length) {
            return JsonResult.failure(608, "有" + (userids.length - users.size()) + "个用户不存在");
        }
        List<UserGroup> userGroups = userGroupService.findBysomeIds(userGroupIds);
        if (userGroups.size() != userGroupIds.length) {
            return JsonResult.failure(609, "有" + (userGroupIds.length - userGroups.size()) + "个用户组不存在");
        }
        assessment = assessmentService.saveAssessment(assessment);
        List<AssessmentFile> af = assessmentService.findByFileIds(assessmentFiles);
        List<AssessmentImage> ai = assessmentService.findByImageIds(assessmentImages);
        assessment = assessmentService.saveAssessmentRange(assessment, af, ai, subjectIds, userGroupIds, departIds, groupIds, userids);
        jsonResult.setData(converAssessment(assessment));
        return jsonResult;
    }


    /**
     * @api {POST} /assessment/del 删除考核
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填 考核id
     * @apiParamExample {json} 请求样例:
     *          /assessment/del?id=4028d8816be4b6fd016be4c2c93d0002
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     *  "data": []
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        Assessment assessment = assessmentService.findById(id);
        if (assessment == null) {
            return JsonResult.notFound("考核不存在");
        }
        assessmentService.deleteAssessment(assessment);
        return JsonResult.success();
    }


    /**
     * @api {POST} /assessment/upload_file 上传文件
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 上传文件
     * @apiParam {String} name 必填 名称
     * @apiParam {String} url 必填 路径
     * @apiParam {String} suffix 必填
     * @apiParam {int} type 必填 suffix
     * @apiParam {String} content
     * @apiParam {String} fromTo
     * @apiParamExample {json} 请求样例:
     * /assessment/upload_file?name=12&url=12&suffix=12&content=12&fromTo=12
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:名称,路径或后缀名不能为空</br>
     * 602:文件类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": {"name": "12","id": "4028d8816bdfbd59016bdfc7b51a0000","suffix": "12","fromTo": "12","url": "12","content": "12"}
     * }
     */
    @PostMapping("/upload_file")
    public JsonResult uploadFile(String name,
                                 String url,
                                 String suffix,
                                 @RequestParam(defaultValue = "0") int type,
                                 String content,
                                 String fromTo) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(url) || StringUtils.isBlank(suffix)) {
            return JsonResult.failure(601, "名称,路径,后缀名不能为空");
        }
        AssessmentFile file = new AssessmentFile();
        file.setCreatedDate(new Date());
        file.setLastModifiedDate(new Date());
        file.setName(name);
        file.setUrl(url);
        file.setSuffix(suffix);
        file.setContent(content);
        file.setFromTo(fromTo);
        for (AssessmentFile.ASSESSMENT_FILE_TYPE fileType : AssessmentFile.ASSESSMENT_FILE_TYPE.values()) {
            if (fileType.ordinal() == type) {
                file.setType(fileType);
                break;
            }
        }
        if (file.getType() == null) {
            return JsonResult.failure(602, "文件类型异常");
        }
        file = assessmentService.saveFile(file);
        jsonResult.setData(JsonUtils.getJson(file));
        return jsonResult;
    }

    /**
     * @api {POST} /assessment/del_file 文件删除
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 考核文件删除
     * @apiParam {String[]} ids 必填 文件id
     * @apiParamExample {json} 请求样例:
     * /assessment/del_file?ids=4028d8816bdfbd59016bdfc7b51a0000,4028d8816bdfbd59016bdfd5c0610001,4028d8816bdfbd59016bdfd6019d0002
     * @apiSuccess (200) {String} code 200:成功</br>
     * 404:活动文件不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": []
     * }
     */
    @PostMapping("/del_file")
    public JsonResult delFile(String[] ids) {
        if (ids == null) {
            return JsonResult.notFound("考核文件删除");
        }
        assessmentService.delFileByIds(ids);
        return JsonResult.success();
    }

    /**
     * @api {POST} /assessment/upload_image 上传图片
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 上传图片
     * @apiParam {String} name 必填 名称
     * @apiParam {String} url 必填 url
     * @apiParam {String} suffix 必填 后缀名
     * @apiParamExample {json} 请求样例:
     * /assessment/upload_image?name=123&url=1231&suffix=12
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:名称,路径或后缀名不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": {"name": "123","id": "4028d8816bdff15a016be010af5f000f","suffix": "12","url": "1231"}
     * }
     */
    @PostMapping("/upload_image")
    public JsonResult uploadImage(String name,
                                  String url,
                                  String suffix) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(url) || StringUtils.isBlank(suffix)) {
            return JsonResult.failure(601, "名称,路径或后缀名不能为空");
        }
        AssessmentImage image = new AssessmentImage();
        image.setCreatedDate(new Date());
        image.setLastModifiedDate(new Date());
        image.setName(name);
        image.setUrl(url);
        image.setSuffix(suffix);
        image = assessmentService.saveImage(image);
        jsonResult.setData(JsonUtils.getJson(image));
        return jsonResult;
    }

    /**
     * @api {POST} /assessment/del_image 图片删除
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 活动图片删除
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例:
     * /assessment/del_image?ids=4028d8816bdff15a016be016ad770011,4028d8816bdff15a016be016aa500010
     * @apiSuccess (200) {String} code 200:成功</br>
     * 404:活动图片不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": []
     * }
     */
    @PostMapping("/del_image")
    public JsonResult delImage(String[] ids) {
        if (ids == null) {
            return JsonResult.notFound("活动图片不存在");
        }
        assessmentService.delImage(ids);
        return JsonResult.success();
    }





    /**
    * @api {POST} /assessment/view_index 查看考核指标
    * @apiGroup Assessment
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} id 必填 考核id
    * @apiParamExample {json} 请求样例:
    *                /assessment/view_index?id=4028d8816be4b6fd016be521e09f001b
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *       "data": [{"isuse": "1234567","lname": "12","norder": "123456","sname": "123","sortedcode": "12345","id": "4028d8816be4b6fd016be5224bb60024","parcode": "1234","sortedid": "1"},{"isuse": "9876543","lname": "98","norder": "987654","sname": "987","sortedcode": "98765","id": "4028d8816be4b6fd016be5224bb60025","parcode": "9876","sortedid": "9"}]
    * }
    */
    @PostMapping("/view_index")
    public JsonResult viewIndex(String id){
        List<AssessmentIndex> assessmentIndices = assessmentService.findIndexByAssessmentId(id);
        JSONArray array = new JSONArray();
        for (AssessmentIndex assessmentIndex : assessmentIndices) {
            array.add(JsonUtils.getJson(assessmentIndex));
        }
        jsonResult.setData(array);
        return jsonResult;
    }




    /**
     * @api {POST} /assessment/add_index 新增考核指标
     * @apiGroup Assessment
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} assessmentid 考核id必填
     * @apiParam {String[]} indexlist  请看样例中的indexlist
     * @apiParamExample {json} 请求样例:
     * {
    "assessmentid":"4028d8816be4b6fd016be521e09f001b",
    "indexlist":[{"sortedid":"1"   节点id
                ,"lname":"12"         考核全称
                  ,"sname":"123",       考核缩写
                  "parcode":"1234",     父节点id
                     "sortedcode":"12345",      编码
                    "norder":"123456",      排序
                     "isuse":"1234567"},        是否使用
    {"sortedid":"9",
    "lname":"98",
    "sname":"987",
    "parcode":"9876",
    "sortedcode":"98765",
    "norder":"987654",
    "isuse":"9876543"}]}
     * @apiSuccess (200) {String} code 200:成功</br>
     * 404:</br>
     * 601:考核不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": []
     * }
     */
    @PostMapping("/add_index")
    public JsonResult addIndex(@RequestBody JSONObject params) {
        String assessmentid = (String) params.get("assessmentid");
        Assessment assessment = assessmentService.findById(assessmentid);
        if (assessment == null) {
            return JsonResult.failure(601, "考核不存在");
        }
        List<Map<String, String>> mapList = (List<Map<String, String>>) params.get("indexlist");
        assessmentService.saveAssessmentIndex(assessment, mapList);

        return JsonResult.success();
    }


    /**
    * @api {POST} /assessment/del_index 根据考核id删除考核指标
    * @apiGroup Assessment
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} id 必填 考核id
    * @apiParamExample {json} 请求样例:
    *                /assessment/del_index id=4028d8816bcc9a32016bcccd9616000c
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data": []
    * }
    */
    @PostMapping("/del_index")
    public JsonResult delIndex(String id) {
        List<AssessmentIndex> assessmentIndices = assessmentService.findIndexByAssessmentId(id);
        assessmentService.delAll(assessmentIndices);
        return jsonResult;
    }


    /**
    * @api {POST} /assessment/del_byindexid 根据指标id删除该指标
    * @apiGroup Assessment
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} id 必填
    * @apiParamExample {json} 请求样例:
    *                /assessment/del_byindexid?id=4028d8816bf387ac016bf3a207830002
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data": [{"number": "12","score": 80,"endtime": "2018-12-12 12:12:12","remark": "测试内容","starttime": 80,"title": "测试考核","type": "personnel"}]
    * }
    */
    @PostMapping("del_byindexid")
    public JsonResult delbyindexid(String id){
       AssessmentIndex assessmentIndex =  assessmentService.findByIndexId(id);
       if (assessmentIndex == null){
            JsonResult.notFound("没有找到该指标");
       }
        assessmentService.delByIndex(assessmentIndex);
        return JsonResult.success();
    }


    /**
     * 转换Aessessment 为json
     *
     * @param assessment
     * @return
     */
    private JSONObject converAssessment(Assessment assessment) {
        JSONObject jsonObject = new JSONObject(JsonUtils.getJson(assessment));
        return jsonObject;

    }
}
