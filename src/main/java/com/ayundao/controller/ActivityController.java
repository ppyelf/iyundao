package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.ActivityService;
import com.ayundao.service.AttendanceService;
import com.ayundao.service.SignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ActivityController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 13:36
 * @Description: 控制层 - 活动
 * @Version: V1.0
 */
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private SignService signService;

    @PostMapping("/files")
    public JsonResult file() {
        String[] ids = new String[]{"402881916b3a160f016b3a164b940000", "402881916b4535c7016b45362a8b0000"};
//        ActivityFile activityFile = activityService.findByIds("402881916b3a160f016b3a164b940000");
        List<ActivityFile> list = activityService.findAllFile();
        return jsonResult;
    }
    /**
     * @api {POST} /activity/upload_file 上传文件
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 上传文件
     * @apiParam {String} name 必填
     * @apiParam {String} url 必填
     * @apiParam {String} suffix 必填
     * @apiParam {int} type 必填
     * @apiParam {String} content
     * @apiParam {String} fromTo
     * @apiParamExample {json} 请求样例：
     *                /activity/upload_file?name=上传文件1&url=1111111&suffix=jpg&type=1&content=测试内容
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     *                                 602:文件类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"402881916b2a9588016b2abd6f300001","createdDate":"20190606110236","lastModifiedDate":"20190606110236","name":"上传文件","type":"file","content":"测试内容","suffix":"jpg","url":"1111111","info4":"","info3":"","info5":"","info2":"","info1":"","hots":"0","fromTo":""}"
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
            return JsonResult.failure(601, "名称,路径或后缀名不能为空");
        }
        ActivityFile file = new ActivityFile();
        file.setCreatedDate(new Date());
        file.setLastModifiedDate(new Date());
        file.setName(name);
        file.setUrl(url);
        file.setSuffix(suffix);
        for (ActivityFile.ACTIVITY_FILE_TYPE fileType : ActivityFile.ACTIVITY_FILE_TYPE.values()) {
            if (fileType.ordinal() == type) {
                file.setType(fileType);
                break;
            }
        }
        if (file.getType() == null) {
            return jsonResult.failure(602, "文件类型异常");
        }
        file.setContent(content);
        file.setFromTo(fromTo);
        file = activityService.saveFile(file);
        jsonResult.setData(JsonUtils.getJson(file));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/del_file 活动文件删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 活动文件删除
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例：
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
    @PostMapping("/del_file")
    public JsonResult delFile(String[] ids) {
        if (ids == null) {
            return JsonResult.notFound("活动文件不存在");
        }
        activityService.delFileByIds(ids);
        return JsonResult.success();
    }

    /**
     * @api {POST} /activity/upload_file 上传图片
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 上传图片
     * @apiParam {String} name 必填
     * @apiParam {String} url 必填
     * @apiParam {String} suffix 必填
     * @apiParamExample {json} 请求样例：
     *                /activity/upload_file?name=上传图片&url=1111111&suffix=jpg
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{"version":"0","id":"402881916b2a9588016b2adbe569000e","createdDate":"20190606113622","lastModifiedDate":"20190606113622","name":"上传图片","type":"text","content":"","suffix":"jpg","url":"1111111","info4":"","info3":"","info5":"","info2":"","info1":"","hots":"0","fromTo":""}"
     * }
     */
    @PostMapping("/upload_image")
    public JsonResult uploadImage(String name,
                                  String url,
                                  String suffix) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(url) || StringUtils.isBlank(suffix)) {
            return JsonResult.failure(601, "名称,路径或后缀名不能为空");
        }
        ActivityImage image = new ActivityImage();
        image.setCreatedDate(new Date());
        image.setLastModifiedDate(new Date());
        image.setName(name);
        image.setUrl(url);
        image.setSuffix(suffix);
        image = activityService.saveImage(image);
        jsonResult.setData(JsonUtils.getJson(image));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/del_image 活动图片删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 活动图片删除
     * @apiParam {String[]} ids 必填
     * @apiParamExample {json} 请求样例：
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
     * @apiDescription 添加出勤类型
     * @apiParam {String} startTime 必填
     * @apiParam {String} endTime 必填
     * @apiParam {int} day
     * @apiParam {int} type
     * @apiParam {String} axisx
     * @apiParam {String} axisy
     * @apiParam {String} area
     * @apiParamExample {json} 请求样例：
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
     * @apiDescription 新增
     * @apiParam {String} name 必填
     * @apiParam {String} content
     * @apiParam {int} number
     * @apiParam {String} total
     * @apiParam {int} type
     * @apiParam {String[]} attendanceIds
     * @apiParam {String[]} activityFileIds
     * @apiParam {String[]} activityImageIds
     * @apiParam {String} subjectId
     * @apiParam {String} departId
     * @apiParam {String} groupId
     * @apiParamExample {json} 请求样例：
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
    @PostMapping("/add")
    public JsonResult add(String name,
                          String content,
                          @RequestParam(defaultValue = "0") int number,
                          String total,
                          @RequestParam(defaultValue = "0") int type,
                          String[] attendanceIds,
                          String[] activityFileIds,
                          String[] activityImageIds,
                          String subjectId,
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
        activity = activityService.save(activity, attendances, activityFiles, activityImages, subjectId, departId, groupId);
        jsonResult.setData(convertActivity(activity));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/del 删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
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
     * @api {POST} /activity/del 删除
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 必填
     * @apiParam {String[]} attendanceIds
     * @apiParam {String[]} activityFileIds
     * @apiParam {String[]} activityImageIds
     * @apiParam {String} name
     * @apiParam {String} content
     * @apiParam {int} number
     * @apiParam {String} total
     * @apiParam {int} type
     * @apiParam {String} subjectId
     * @apiParam {String} departId
     * @apiParam {String} groupId
     * @apiParamExample {json} 请求样例：
     *                /activity/del?id=2c9ba3816b27cc90016b27cd24800000
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
                             String subjectId,
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
        activity = activityService.save(activity, attendances, activityFiles, activityImages, subjectId, departId, groupId);
        jsonResult.setData(convertActivity(activity));
        return jsonResult;
    }

    /**
     * @api {GET} /activity/list 列表
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
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
    @GetMapping("/list")
    public JsonResult list() {
        List<Activity> activities = activityService.findAll();
        JSONArray arr = new JSONArray();
        for (Activity activity : activities) {
            arr.put(convertActivity(activity));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {POST} /activity/view 查看
     * @apiGroup Activity
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例：
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
     * @apiDescription 分页
     * @apiParamExample {json} 请求样例：
     *                /activity/page?id=
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @PostMapping("/page")
    public JsonResult page(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size,
                           String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activityPage = activityService.findAllForPage(pageable);
        jsonResult.setData(JsonUtils.getPage(activityPage));
        return jsonResult;
    }

    /**
     * 转换Activity为json
     * @param activity
     * @return
     */
    private String convertActivity(Activity activity) {
        try {
            JSONObject json = new JSONObject(JsonUtils.getJson(activity));
            JSONArray arr = new JSONArray();
            if (CollectionUtils.isNotEmpty(activity.getAttendances())) {
                for (Attendance attendance : activity.getAttendances()) {
                    arr.put(JsonUtils.getJson(attendance));
                }
                json.put("attendances", arr);
            } 
            if (CollectionUtils.isNotEmpty(activity.getActivityFiles())) {
                arr = new JSONArray();
                for (ActivityFile file : activity.getActivityFiles()) {
                    arr.put(JsonUtils.getJson(file));
                }
                json.put("activityFiles", arr);
            } 
            if (CollectionUtils.isNotEmpty(activity.getActivityImages())) {
                arr = new JSONArray();
                for (ActivityImage image : activity.getActivityImages()) {
                    arr.put(image);
                }
                json.put("activityImages", arr);
            }
            return JsonUtils.delString(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }
}
