package com.ayundao.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.ayundao.base.BaseController;
import com.ayundao.base.annotation.CurrentSubject;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ayundao.base.BaseController.*;

/**
 * @ClassName: ActivityController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 13:36
 * @Description: 控制层 - 活动
 * @Version: V1.0
 */
@RequiresUser
@RequiresRoles(value = {ROLE_ADMIN, ROLE_USER, ROLE_MANAGER}, logical = Logical.OR)
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ActivityInfoUserService activityInfoUserService;

    @Autowired
    private SignService signService;

    @Autowired
    private UserService userService;

    /**
     * @api {POST} /activity/upload 上传文件
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 上传文件
     * @apiParam {MultipartFile} file 必填
     * @apiParam {String} content
     * @apiParam {String} fromTo
     * @apiParam {int} type 0-文档,1-文件
     * @apiParamExample {json} 请求样例:
     *                ?name=上传文件1&url=1111111&suffix=jpg&type=1&content=测试内容
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     *                                 602:文件类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"hots": "0","name": "1a66c7cf216341e1b2fa12973c616348","id": "402881916c8ecadc016c8ede422c0005","type": "file","suffix": "sql","fromTo": "","content": "1234内容部分","url": "activityfile\\1a66c7cf216341e1b2fa12973c616348.sql"
     *     }
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/upload")
    public JsonResult uploadFile(MultipartFile file,
                                 @RequestParam(defaultValue = "0") int type,
                                 String content,
                                 String fromTo) {
        ActivityFile activityFile = new ActivityFile();
        activityFile.setCreatedDate(new Date());
        activityFile.setLastModifiedDate(new Date());
        for (ActivityFile.ACTIVITY_FILE_TYPE fileType : ActivityFile.ACTIVITY_FILE_TYPE.values()) {
            if (fileType.ordinal() == type) {
                activityFile.setType(fileType);
                break;
            }
        }
        if (activityFile.getType() == null) {
            return jsonResult.failure(602, "文件类型异常");
        }
        activityFile.setContent(content);
        activityFile.setFromTo(fromTo);
        Map<String, String> map = FileUtils.uploadFile(file, activityFile, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        activityFile.setUrl(map.get("url"));
        activityFile.setSuffix(map.get("suffix"));
        activityFile.setName(map.get("name"));
        activityFile = activityService.saveFile(activityFile);
        jsonResult.setData(JsonUtils.getJson(activityFile));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/del_file 活动文件删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 活动文件删除
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例:
     *                /activity/del_file?ids=402881916b2a9588016b2abd6f300001
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动文件不存在</br>
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
    @PostMapping("/del_file")
    public JsonResult delFile(String[] ids) {
        if (ids == null) {
            return JsonResult.notFound("活动文件不存在");
        }
        activityService.delFileByIds(ids);
        return JsonResult.success();
    }

    /**
     * @api {POST} /activity/del_image 活动图片删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 活动图片删除
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例:
     *                /activity/del_image?ids=402881916b2b9dd2016b2b9f15010002
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动图片不存在</br>
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
    @PostMapping("/del_image")
    public JsonResult delImage(String[] ids) {
        if (ids == null) {
            return JsonResult.notFound("活动图片不存在");
        }
        activityService.delImage(ids);
        return JsonResult.success();
    }

    /**
     * @api {POST} /activity/add_time 添加出勤类型
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 添加出勤类型
     * @apiParam {String} startTime 必填
     * @apiParam {String} endTime 必填
     * @apiParam {int} day
     * @apiParam {int} type
     * @apiParam {String} axisx
     * @apiParam {String} axisy
     * @apiParam {String} area
     * @apiParamExample {json} 请求样例:
     *                /activity/add_time?name=上传图片&url=1111111&suffix=jpg
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:起止时间不能为空</br>
     *                                 602:出勤类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"402881916b2a9588016b2b5f1c1c0011","createdDate":"20190606135941","lastModifiedDate":"20190606135941","startTime":"某年某月某日","endTime":"结束时间","day":"100","axisx":"33","axisy":"333","area":"500米","attendanceType":"daily","info4":"","info3":"","info5":"","info2":"","info1":""}"
     * }
     */
    @RequiresPermissions(value = {PERMISSION_ADD})
    @PostMapping("/add_time")
    public JsonResult addTime(String startTime,
                              String endTime,
                              @RequestParam(defaultValue = "0") int day,
                              @RequestParam(defaultValue = "0") int type,
                              String axisx,
                              String axisy,
                              String area) {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return JsonResult.failure(601, "起止时间不能为空");
        }
        if (type < 0 && type > Attendance.ATTENDANCE_TYPE.etc.ordinal()) {
            return JsonResult.failure(602, "出勤类型异常");
        }
        Attendance attendance = attendanceService.save(null, startTime, endTime, day, type, axisx, axisy, area);
        jsonResult.setData(JsonUtils.getJson(attendance));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/add 新增
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 新增
     * @apiParam {String} name 必填
     * @apiParam {String} content
     * @apiParam {int} number
     * @apiParam {String} total
     * @apiParam {int} type
     * @apiParam {String[]} attendanceIds
     * @apiParam {String[]} activityFileIds
     * @apiParam {String[]} activityImageIds
     * @apiParam {String} departId
     * @apiParam {String} groupId
     * @apiParamExample {json} 请求样例:
     *                /activity/add?name=添加活动2&content=测试内容个&number=13&total=100&type=2&attendanceIds=402881916b2a9588016b2b5dbc480010&attendanceIds=402881916b2a9588016b2b5f1c1c0011&activityFileIds=402881916b2a9588016b2abd6f300001&activityImageIds=402881916b2b9dd2016b2b9f15010002&subjectId=402881f46afdef14016afe28796c000b
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     *                                 601:活动名称不能为空</br>
     *                                 602:type类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"402881916b2b9dd2016b2ba9227d0005","createdDate":"20190606152033","lastModifiedDate":"20190606152033","name":"添加活动2","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":"","attendances":["{"version":"4","id":"402881916b2a9588016b2b5f1c1c0011","createdDate":"20190606135941","lastModifiedDate":"20190606135941","axisy":"333","axisx":"33","startTime":"某年某月某日","area":"500米","day":"100","endTime":"结束时间","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"4","id":"402881916b2a9588016b2b5dbc480010","createdDate":"20190606135811","lastModifiedDate":"20190606135811","axisy":"","axisx":"","startTime":"某年某月某日","area":"","day":"0","endTime":"结束时间","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}"]}"
     * }
     */
    @RequiresPermissions(value = {PERMISSION_ADD, PERMISSION_MODIFY})
    @PostMapping("/add")
    public JsonResult add(String name,
                          String content,
                          @RequestParam(defaultValue = "0") int number,
                          String total,
                          @RequestParam(defaultValue = "0") int type,
                          String[] attendanceIds,
                          String[] activityFileIds,
                          String[] activityImageIds,
                          @CurrentSubject Subject subject,
                          String departId,
                          String groupId) {
        if (StringUtils.isBlank(name)) {
            return JsonResult.failure(601, "活动名称不能为空");
        }
        //生成活动实体
        Activity activity = new Activity();
        activity.setCreatedDate(new Date());
        activity.setLastModifiedDate(new Date());
        activity.setName(name);
        activity.setContent(content);
        activity.setNumber(number);
        activity.setTotal(total);
        for (Activity.ACTIVITY_TYPE activityType : Activity.ACTIVITY_TYPE.values()) {
            if (activityType.ordinal() == type) {
                activity.setType(activityType);
                break;
            }
        }
        if (activity.getType() == null) {
            return JsonResult.failure(602, "type类型异常");
        }
        List<Attendance> attendances = attendanceService.findByIds(attendanceIds);
        List<ActivityFile> activityFiles = activityService.findActivityFilesByIds(activityFileIds);
        List<ActivityImage> activityImages = activityService.findActivityImageByIds(activityImageIds);
        activity = activityService.save(activity, attendances, activityFiles, activityImages, subject.getId(), departId, groupId);
        jsonResult.setData(convertActivity(activity));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/del 删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /activity/del?id=2c9ba3816b27cc90016b27cd24800000
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
    @RequiresPermissions({PERMISSION_DELETE})
    @PostMapping("/del")
    public JsonResult del(String id) {
        Activity activity = activityService.find(id);
        if (activity == null) {
            return JsonResult.failure(601, "活动不存在");
        }
        activityService.delete(activity);
        return JsonResult.success();
    }

    /**
     * @api {POST} /activity/modify 修改
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 修改
     * @apiParam {String} id 必填
     * @apiParam {String[]} attendanceIds
     * @apiParam {String[]} activityFileIds
     * @apiParam {String[]} activityImageIds
     * @apiParam {String} name
     * @apiParam {String} content
     * @apiParam {int} number
     * @apiParam {String} total
     * @apiParam {int} type
     * @apiParam {String} departId
     * @apiParam {String} groupId
     * @apiParamExample {json} 请求样例:
     *                ?id=402881916b2a3187016b2a3306ca0006&name=添加活动1111&content=测试内容个&number=13&total=100&type=2&attendanceIds=402881916b2a9588016b2b5dbc480010&attendanceIds=402881916b2a9588016b2b5f1c1c0011&activityFileIds=402881916b2a9588016b2abd6f300001&activityImageIds=402881916b2b9dd2016b2b9f15010002&subjectId=402881f46afdef14016afe28796c000b
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     *                                 601:type超出范围</br>
     *                                 602:已有签到信息,无法修改</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"2","id":"402881916b2a3187016b2a3306ca0006","createdDate":"20190606083155","lastModifiedDate":"20190606083155","name":"添加活动1111","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":"","attendances":["{"version":"5","id":"402881916b2a9588016b2b5f1c1c0011","createdDate":"20190606135941","lastModifiedDate":"20190606135941","axisy":"333","axisx":"33","startTime":"某年某月某日","area":"500米","day":"100","endTime":"结束时间","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"5","id":"402881916b2a9588016b2b5dbc480010","createdDate":"20190606135811","lastModifiedDate":"20190606135811","axisy":"","axisx":"","startTime":"某年某月某日","area":"","day":"0","endTime":"结束时间","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}"]}"
     * }
     */
    @RequiresPermissions(PERMISSION_MODIFY)
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String[] attendanceIds,
                             String[] activityFileIds,
                             String[] activityImageIds,
                             String name,
                             String content,
                             @RequestParam(defaultValue = "0") int number,
                             String total,
                             @RequestParam(defaultValue = "0") int type,
                             @CurrentSubject Subject subject,
                             String departId,
                             String groupId) {
        Activity activity = activityService.find(id);
        if (activity == null) {
            return JsonResult.notFound("活动不存在");
        }
        List<Sign> signs = signService.findByActivityId(activity.getId());
        if (CollectionUtils.isNotEmpty(signs)) {
            return JsonResult.failure(602, "已有签到信息,无法修改");
        }
        activity.setName(name);
        activity.setContent(content);
        activity.setNumber(number);
        activity.setTotal(total);
        for (Activity.ACTIVITY_TYPE activityType : Activity.ACTIVITY_TYPE.values()) {
            if (type == activityType.ordinal()) {
                activity.setType(activityType);
                break;
            }
        }
        if (activity.getType() == null) {
            return JsonResult.failure(601, "type超出范围");
        }
        List<ActivityFile> activityFiles = activityService.findActivityFilesByIds(activityFileIds);
        List<Attendance> attendances = attendanceService.findByIds(attendanceIds);
        List<ActivityImage> activityImages = activityService.findActivityImageByIds(activityImageIds);
        activity = activityService.save(activity, attendances, activityFiles, activityImages, subject.getId(), departId, groupId);
        jsonResult.setData(convertActivity(activity));
        return jsonResult;
    }

    /**
     * @api {GET} /activity/list 列表
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例:
     *                /activity/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "["{"version":"0","id":"402881916b2a3187016b2a3247350002","createdDate":"20190606083106","lastModifiedDate":"20190606083106","name":"添加活动1","type":"depart","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":"","attendances":["{"version":"0","id":"402881916b2a3187016b2a3247360003","createdDate":"20190606083106","lastModifiedDate":"20190606083106","axisy":"","axisx":"","startTime":"2019060511","area":"","day":"13","endTime":"2019","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}"]}","{"version":"0","id":"402881916b2a3187016b2a32570c0004","createdDate":"20190606083110","lastModifiedDate":"20190606083110","name":"添加活动2","type":"depart","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":"","attendances":["{"version":"0","id":"402881916b2a3187016b2a32570c0005","createdDate":"20190606083110","lastModifiedDate":"20190606083110","axisy":"","axisx":"","startTime":"2019060511","area":"","day":"13","endTime":"2019","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}"]}","{"version":"2","id":"402881916b2a3187016b2a3306ca0006","createdDate":"20190606083155","lastModifiedDate":"20190606083155","name":"添加活动1111","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":"","attendances":["{"version":"5","id":"402881916b2a9588016b2b5f1c1c0011","createdDate":"20190606135941","lastModifiedDate":"20190606135941","axisy":"333","axisx":"33","startTime":"某年某月某日","area":"500米","day":"100","endTime":"结束时间","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"5","id":"402881916b2a9588016b2b5dbc480010","createdDate":"20190606135811","lastModifiedDate":"20190606135811","axisy":"","axisx":"","startTime":"某年某月某日","area":"","day":"0","endTime":"结束时间","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"0","id":"402881916b2a3187016b2a3306cb0007","createdDate":"20190606083155","lastModifiedDate":"20190606083155","axisy":"","axisx":"","startTime":"2019060511","area":"","day":"13","endTime":"2019","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}"]}","{"version":"0","id":"402881916b2a9588016b2b9a0ef10012","createdDate":"20190606150405","lastModifiedDate":"20190606150405","name":"添加活动2","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"0","id":"402881916b2b9dd2016b2b9e77620000","createdDate":"20190606150853","lastModifiedDate":"20190606150853","name":"添加活动2","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"0","id":"402881916b2b9dd2016b2b9f91f40003","createdDate":"20190606151006","lastModifiedDate":"20190606151006","name":"添加活动2","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":""}","{"version":"0","id":"402881916b2b9dd2016b2ba9227d0005","createdDate":"20190606152033","lastModifiedDate":"20190606152033","name":"添加活动2","type":"etc","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":""}"]"
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/list")
    public JsonResult list() {
        List<Activity> activities = activityService.findAll();
        JSONArray arr = new JSONArray();
        for (Activity activity : activities) {
            arr.add(convertActivity(activity));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /activity/view 查看
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /activity/view?id=402881916b2a3187016b2a3247350002
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"402881916b2a3187016b2a3247350002","createdDate":"20190606083106","lastModifiedDate":"20190606083106","name":"添加活动1","type":"depart","number":"13","content":"测试内容个","total":"100","info5":"","info4":"","info2":"","info3":"","info1":"","attendances":["{"version":"0","id":"402881916b2a3187016b2a3247360003","createdDate":"20190606083106","lastModifiedDate":"20190606083106","axisy":"","axisx":"","startTime":"2019060511","area":"","day":"13","endTime":"2019","attendanceType":"daily","info5":"","info4":"","info2":"","info3":"","info1":""}"]}"
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/view")
    public JsonResult view(String id) {
        Activity activity = activityService.find(id);
        if (activity == null) {
            return JsonResult.notFound("活动不存在");
        }
        jsonResult.setData(convertActivity(activity));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/page 分页
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 分页
     * @apiParam {int} page 页码,默认0
     * @apiParam {int} size 长度,默认10
     * @apiParamExample {json} 请求示例:
     *              /activity/page?id=123
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"total":7,"page":3,"content":"["{"id":"402881916b2a9588016b2b9a0ef10012","version":"0","lastModifiedDate":"20190606150405","createdDate":"20190606150405","name":"添加活动2","type":"etc","total":"100","number":"13","content":"测试内容个","info3":"","info2":"","info5":"","info1":"","info4":""}","{"id":"402881916b2b9dd2016b2b9e77620000","version":"0","lastModifiedDate":"20190606150853","createdDate":"20190606150853","name":"添加活动2","type":"etc","total":"100","number":"13","content":"测试内容个","info3":"","info2":"","info5":"","info1":"","info4":""}","{"id":"402881916b2b9dd2016b2b9f91f40003","version":"0","lastModifiedDate":"20190606151006","createdDate":"20190606151006","name":"添加活动2","type":"etc","total":"100","number":"13","content":"测试内容个","info3":"","info2":"","info5":"","info1":"","info4":""}"]"}"
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/page")
    public JsonResult page(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = new Pageable(page, size);
        Page<Activity> activityPage = activityService.findAllForPage(pageable);
        jsonResult.setData(JsonUtils.getPage(activityPage));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/registration 添加活动报名人员
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} activityid 必填
     * @apiParam {String[]} userid 必填
     * @apiParamExample {json} 请求示例:
     *              /activity/registration?activityid=88888888&userid=402881916ba10b8a016ba113adbc0006
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:"没有此活动"<br>
     *                                  602:"没有此用户"<br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "id": "4028d8816bc50e52016bc52dae240001"
     * }
     */
    @PostMapping("/registration")
    private JsonResult registration(String activityid,
                                    String[] userid
                                    ){
        List<User> user = userService.findbyIds(userid);
        Activity activity = activityService.find(activityid);
        if (activity == null){
            return JsonResult.failure(601,"没有此活动");
        }
        if(CollectionUtils.isEmpty(user)){
            return JsonResult.failure(602,"没有此用户");
        }
        if (userid.length!=user.size()){
            return JsonResult.failure(603,"有"+(userid.length-user.size())+"个用户实体不存在");
        }
       List<ActivityInfoUser> activities = activityService.findActivityInfoUserByUserAndActivity(activity,user);
        if (CollectionUtils.isNotEmpty(activities)){
            return JsonResult.failure(604,"有用户已经在这个活动中了");
        }
        activityInfoUserService.save(activity,user);
        return  jsonResult;
    }


    /**
     * @api {POST} /activity/viewPeopleForActivity 根据活动查找改活动下的人员
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} activityid 必填
     * @apiParamExample {json} 请求示例:
     *             /activity/viewPeopleForActivity?activityid=88888888
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:"没有此活动"<br>
     *                                  602:"没有此用户"<br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *        "data": [{"password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06","code": "000","salt": "3a10624a300f4670","sex": "0","name": "管理员","remark": "未填写","id": "0a4179fc06cb49e3ac0db7bcc8cf0882","userType": "normal","account": "admin","status": "normal"},{"password": "6A36E430976A64EA","code": "001","salt": "45a1d914886d4a92b6835a181b2a20d8","sex": "0","name": "钱正","remark": "暂无描述","id": "402881916ba10b8a016ba113adbc0006","userType": "normal","account": "user","status": ""}]
     * }
     */
    @PostMapping("/viewPeopleForActivity")
    public JsonResult viewPeopleForActivity(String activityid){
        Activity activity = activityService.find(activityid);
        if (activity == null){
            return JsonResult.notFound("找不到活动");
        }
        List<User> users = activityService.findUserFromActivityInfroUserByActivity(activity);
        JSONArray array = new JSONArray();
        for (User user : users) {
            array.add(JsonUtils.getJson(user));
        }
        jsonResult.setData(array);
        return jsonResult;
    }


    /**
     * @api {POST} /activity/delPeople 删除人员
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String[]} userids 必填 用户id
     * @apiParamExample {json} 请求示例:
     *             /activity/delPeople?userids=402881916ba10b8a016ba113adbc0006,0a4179fc06cb49e3ac0db7bcc8cf0882
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *        "data": []
     * }
     */
    @PostMapping("/delPeople")
    public  JsonResult delPeople(String[] userids){
        List<User> users = userService.findbyIds(userids);
        if (CollectionUtils.isEmpty(users)){
            return JsonResult.notFound("没有找到用户实体");
        }
        List<ActivityInfoUser> activityInfoUsers = activityService.findByUsers(users);
        activityService.deleteActivityInfoUsers(activityInfoUsers);
        return JsonResult.success();
    }


    /**
    * @api {POST} /activity/searchName 模糊查询活动名称
    * @apiGroup Activity
    * @apiVersion 1.0.0
    * @apiDescription 查看
    * @apiParam {String} name 模糊查询名称必填
    * @apiParam {int} page  跳过的页数
    * @apiParam {int} size 每页的数量
    * @apiParamExample {json} 请求样例:
    *                /activity/searchName?name=1&page&size
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *       "data": {
    "total": 2,
    "totalPage": 1,
    "page": 0,
    "content": [
    {
                "number": "3",    成员数
                "total": "15",          总分
                "name": "12312",        标题
                "id": "88888888",       活动id
                "type": "depart",   0-部门考核 1老干部考核 2 etc
                "content": "21321"          内容
                },
                {
                "number": "1",
                "total": "4",
                "name": "154",
                "id": "121211231",
                "type": "depart",
                "content": ""
    }]}
    * }
    */
    @PostMapping("/searchName")
    public JsonResult searchtitle(String name,
                                  @RequestParam(defaultValue = "0")int page,
                                  @RequestParam(defaultValue = "10")int size){
        Pageable pageable = new Pageable(page,size);
        pageable.setSearchProperty("name");
        pageable.setSearchValue(name);
        Page<Activity> activityPage = activityService.findAllForPage(pageable);
        JSONObject jsonObject = JsonUtils.getPage(activityPage);
        jsonResult.setData(jsonObject);
        return jsonResult;
    }


    /**
     * 转换Activity为json
     * @param activity
     * @return
     */
    private JSONObject convertActivity(Activity activity) {
        JSONObject json = new JSONObject(JsonUtils.getJson(activity));
        JSONArray arr = new JSONArray();
        if (CollectionUtils.isNotEmpty(activity.getAttendances())) {
            for (Attendance attendance : activity.getAttendances()) {
                arr.add(JsonUtils.getJson(attendance));
            }
            json.put("attendances", arr);
        }
        if (CollectionUtils.isNotEmpty(activity.getActivityFiles())) {
            arr = new JSONArray();
            for (ActivityFile file : activity.getActivityFiles()) {
                arr.add(JsonUtils.getJson(file));
            }
            json.put("activityFiles", arr);
        }
        if (CollectionUtils.isNotEmpty(activity.getActivityImages())) {
            arr = new JSONArray();
            for (ActivityImage image : activity.getActivityImages()) {
                arr.add(image);
            }
            json.put("activityImages", arr);
        }
        return json;
    }

}
