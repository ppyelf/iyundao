package com.ayundao.controller;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.alibaba.fastjson.JSONArray;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Indicator;
import com.ayundao.entity.IndicatorInfoFile;
import com.ayundao.entity.IndicatorInfoImage;
import com.ayundao.entity.Work;
import com.ayundao.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: WorkController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/17 10:13
 * @Description: 控制层 - 中心工作
 * @Version: V1.0
 */
@RestController
@RequestMapping("/work")
public class WorkController extends BaseController {

    @Autowired
    private WorkService workService;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private IndicatorInfoService indicatorInfoService;

    @Autowired
    private IndicatorInfoFileService indicatorInfoFileService;

    @Autowired
    private IndicatorInfoImageService indicatorInfoImageService;

    /**
     * @api {GET} /work/list 列表
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求示例:
     *              /list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "lastModifiedDate": "20190618182741",    "year": "2019",    "remark": "",    "version": "0",    "createdDate": "20190618182741",    "name": "添加工作",    "info1": "",    "workType": "two",    "startTime": "20190618111111",    "id": "402881916b6a1ba7016b6a20c77c0006",    "endTime": "20190618111111",    "info5": "",    "info4": "",    "info3": "",    "info2": ""},{    "lastModifiedDate": "20190618182853",    "year": "2019",    "remark": "",    "version": "0",    "createdDate": "20190618182853",    "name": "添加工作",    "info1": "",    "workType": "two",    "startTime": "20190618111111",    "id": "402881916b6a2198016b6a21e0450000",    "endTime": "20190618111111",    "info5": "",    "info4": "",    "info3": "",    "info2": ""}
     *     ]
     * }
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Work> works = workService.findAll();
        JSONArray arr = new JSONArray();
        for (Work work : works) {
            arr.add(JsonUtils.getJson(work));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /work/add 添加工作
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 添加工作
     * @apiParam {String} year 必填,
     * @apiParam {String} name 必填,
     * @apiParam {int} workType 必填(默认:0),
     * @apiParam {String} startTime 必填,
     * @apiParam {String} endTime 必填,
     * @apiParam {String} remark,
     * @apiParam {String} subjectId 必填,
     * @apiParam {String} departId 选填,
     * @apiParam {String} groupId 选填
     * @apiParamExample {json} 请求示例:
     *              ?name=添加工作&year=2019&workType=1&startTime=20190618111111&endTime=20190618111111&remark&subjectId=bd6886bc88e54ef0a36472efd95c744c&departId=9b7678a607ef4199ad7a4018b892c49d
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称/年份/开始时间/结束时间/所属机构不能为空</br>
     *                                 602:必须有所属部门或者组织</br>
     *                                 603:工作类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"lastModifiedDate": "20190618182853","year": "2019","remark": "","version": "0","createdDate": "20190618182853","name": "添加工作","info1": "","workType": "two","startTime": "20190618111111","id": "402881916b6a2198016b6a21e0450000","endTime": "20190618111111","info5": "","info4": "","info3": "","info2": ""
     *     }
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String year,
                          String name,
                          @RequestParam(defaultValue = "0") int workType,
                          String startTime,
                          String endTime,
                          String remark,
                          String subjectId,
                          String departId,
                          String groupId) {
        if (StringUtils.isBlank(year)
                || StringUtils.isBlank(name)
                || StringUtils.isBlank(startTime)
                || StringUtils.isBlank(endTime)
                || StringUtils.isBlank(subjectId)) {
            return JsonResult.failure(601, "名称/年份/开始时间/结束时间/所属机构不能为空");
        }
        if (StringUtils.isBlank(departId) && StringUtils.isBlank(groupId)) {
            return JsonResult.failure(602, "必须有所属部门或者组织");
        }
        return workService.save(year, name, workType, startTime, endTime, remark, subjectId, departId, groupId, jsonResult);
    }

    /**
     * @api {POST} /work/view 查看工作
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 查看工作
     * @apiParam {String} id 必填,
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b6a2198016b6a21e0450000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:此工作不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * .{
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"lastModifiedDate": "20190618182853","year": "2019","remark": "","version": "0","createdDate": "20190618182853","name": "添加工作","workType": "two","info1": "","startTime": "20190618111111","id": "402881916b6a2198016b6a21e0450000","endTime": "20190618111111","info5": "","info4": "","info3": "","info2": ""
     *     }
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        Work work = workService.find(id);
        if (work == null) {
            return JsonResult.notFound("此工作不存在");
        }
        jsonResult.setData(JsonUtils.getJson(work));
        return jsonResult;
    }

    /**
     * @api {POST} /work/del 删除工作
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 删除工作
     * @apiParam {String} id 必填,
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b6a2198016b6a21e0450000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:此工作不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        workService.delete(id);
        return JsonResult.success();
    }

    /**
     * @api {POST} /work/addIndicator 添加工作指标
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 添加工作指标
     * @apiParam {String} name
     * @apiParam {String} situation
     * @apiParam {String} fatherId
     * @apiParam {String} workId
     * @apiParamExample {json} 请求示例:
     *              ?name=添加指标&situation=测试&workId=402881916b6a1ba7016b6a20c77c0006
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:此工作不存在</br>
     *                                 601:父级工作指标不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "createdDate": "20190619134030",
     *         "lastModifiedDate": "20190619134030",
     *         "name": "添加指标",
     *         "id": "402881916b6d5385016b6e4034ed0003",
     *         "version": "0",
     *         "situation": "测试"
     *     }
     * }
     */
    @PostMapping("/addIndicator")
    public JsonResult addIndicator(String name,
                                   String situation,
                                   String fatherId,
                                   String workId) {
        Work work = workService.find(workId);
        if (work == null) {
            return jsonResult.notFound("此工作不存在");
        } 
        Indicator father = indicatorService.find(fatherId);
        if (father == null && StringUtils.isNotBlank(fatherId)) {
            return JsonResult.failure(601, "父级工作指标不存在");
        }
        return indicatorService.create(name, situation, work, father, jsonResult);
    }

    /**
     * @api {POST} /work/indicatorList 添加工作指标
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 添加工作指标
     * @apiParamExample {json} 请求示例:
     *              /indicatorList
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "createdDate": "20190619135224",
     *             "lastModifiedDate": "20190619135224",
     *             "name": "添加指标1",
     *             "id": "402881916b6e4a53016b6e4b1bf50002",
     *             "version": "0",
     *             "situation": "测试"
     *         }
     *     ]
     * }
     */
    @GetMapping("/indicatorList")
    public JsonResult indicatorList() {
        List<Indicator> list = indicatorService.findAllAndFatherIsNull();
        JSONArray arr =  new JSONArray();
        for (Indicator i : list) {
            JSONObject json = new JSONObject(JsonUtils.getJson(i));
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /work/addIndicatorInfo 添加指标详情
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 添加指标详情
     * @apiParam {String} year 必填
     * @apiParam {String} month 必填
     * @apiParam {String} completion
     * @apiParam {String} intro
     * @apiParam {String} indicatorId 必填
     * @apiParam {String} departId (departId,groupId,userId三选其一)
     * @apiParam {String} groupId
     * @apiParam {String} userId
     * @apiParam {String[]} fileIds
     * @apiParam {String[]} imageIds
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b6e4a53016b6e500b0e0003
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:指标不存在</br>
     *                                 601:年份/月份不能为空</br>
     *                                 602:指标ID不能为空</br>
     *                                 603:部门ID/组织ID/用户ID必须三选一</br>
     *                                 604:用户,部门,组织查询不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"completion": "简报","createdDate": "20190619155709","month": "01","lastModifiedDate": "20190619155709","year": "2019","groupId": "","intro": "简介","departId": "402881916b6d5385016b6d7ce3d30000","id": "402881916b6e4a53016b6ebd52420006","version": "0","userId": ""
     *     }
     * }
     */
    @PostMapping("/addIndicatorInfo")
    public JsonResult addIndicatorInfo(String year,
                                       String month,
                                       String completion,
                                       String intro,
                                       String indicatorId,
                                       String departId,
                                       String groupId,
                                       String userId,
                                       String[] fileIds,
                                       String[] imageIds) {
        if (StringUtils.isBlank(year)
                || StringUtils.isBlank(month)) {
            return JsonResult.failure(601, "年份/月份不能为空");
        } 
        if (StringUtils.isBlank(indicatorId)) {
            return JsonResult.failure(602, "指标ID不能为空");
        }
        Indicator indicator = indicatorService.find(indicatorId);
        if (indicator == null) {
            return JsonResult.notFound("指标不存在");
        } 
        if (StringUtils.isBlank(departId) && StringUtils.isBlank(groupId) && StringUtils.isBlank(userId)) {
            return JsonResult.failure(603, "部门ID/组织ID/用户ID必须三选一");
        }
        List<IndicatorInfoFile> files = indicatorInfoFileService.findByIds(fileIds);
        List<IndicatorInfoImage> images = indicatorInfoImageService.findByIds(imageIds);
        return indicatorInfoService.create(year, month, completion, intro, indicator, departId, groupId, userId, files, images, jsonResult);
    }

    /**
     * @api {POST} /work/addImage 上传指标图片
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 上传指标图片
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *                      /work/addImage
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/addImage")
    public JsonResult addImage(MultipartFile file) {
        IndicatorInfoImage image = new IndicatorInfoImage();
        image.setCreatedDate(new Date());
        image.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, image, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        image.setUrl(map.get("url"));
        image.setSuffix(map.get("suffix"));
        image.setName(map.get("name"));
        image = indicatorInfoImageService.create(image);
        jsonResult.setData(getUploadJson(image));
        return jsonResult;
    }



    /**
     * @api {POST} /work/addFile 上传指标详情附件
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 上传指标详情附件
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /work/addFile
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/addFile")
    public JsonResult addFile(MultipartFile file) {
        IndicatorInfoFile indicatorInfoFile = new IndicatorInfoFile();
        indicatorInfoFile.setCreatedDate(new Date());
        indicatorInfoFile.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, indicatorInfoFile, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        indicatorInfoFile.setUrl(map.get("url"));
        indicatorInfoFile.setSuffix(map.get("suffix"));
        indicatorInfoFile.setName(map.get("name"));
        indicatorInfoFile = indicatorInfoFileService.create(indicatorInfoFile);
        jsonResult.setData(getUploadJson(indicatorInfoFile));
        return jsonResult;
    }

    /**
     * @api {POST} /work/delFile 上传指标详情附件
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 上传指标详情附件
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /work/delFile
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
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
        IndicatorInfoFile file = indicatorInfoFileService.find(id);
        indicatorInfoFileService.delete(file);

        return jsonResult;
    }

    /**
     * @api {POST} /work/delIndicator 删除工作指标
     * @apiGroup Work
     * @apiVersion 1.0.0
     * @apiDescription 删除工作指标
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求示例:
     *              ?id=402881916b6e4a53016b6e500b0e0003
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:此工作不存在</br>
     *                                 601:父级工作指标不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/delIndicator")
    public JsonResult delIndicator(String id) {
        //需要完善
        return indicatorService.delete(id, jsonResult);
    }

}
