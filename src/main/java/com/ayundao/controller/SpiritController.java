package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SpiritController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:07
 * @Description: 控制层 - 党内精神
 * @Version: V1.0
 */
@RestController
@RequestMapping("/spirit")
public class SpiritController extends BaseController {

    @Autowired
    private SpiritService spiritService;

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
     * @api {GET} /spirit/users 用户列表
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 用户列表
     * @apiParamExample {json} 请求示例:
     *              /users
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "name": "管理员",    "id": "0a4179fc06cb49e3ac0db7bcc8cf0882"},{    "name": "测试账号",    "id": "402881916b5a1eba016b5a1f33d60000"},{    "name": "测试账号11",    "id": "402881916b5a1eba016b5a1fafc70002"},{    "name": "测试账号11",    "id": "402881916b737ac1016b73b07131000b"},{    "name": "姓名",    "id": "402881916b778632016b77a4bfb20002"},{    "name": "测试账号11",    "id": "402881916b77bff1016b77c286d00006"},{    "name": "测试账号11",    "id": "402881916b77bff1016b77c75ca6000a"},{    "name": "测试账号",    "id": "402881916b77bff1016b77cd0a5b000e"},{    "name": "测试账号",    "id": "402881916b77bff1016b77ce076d0012"},{    "name": "测试账号",    "id": "402881916b77bff1016b77d012060016"},{    "name": "测试账号",    "id": "402881916b77bff1016b77d04b58001a"},{    "name": "测试账号",    "id": "402881916b77bff1016b77d11f560022"},{    "name": "测试账号",    "id": "402881916b77bff1016b77d52a4f002a"},{    "name": "测试账号",    "id": "402881916b77bff1016b77d6e37a002e"},{    "name": "测试用户",    "id": "5cf0d3c3b0da4cbaad179e0d6d230d0c"},{    "name": "用户1",    "id": "cd22e3407ace4d86bac92f92b9e9113e"},{    "name": "用户",    "id": "cd22e3407ace4d86bac92f92b9e9dd3e"}
     *     ]
     * }
     */
    @GetMapping("/users")
    public JsonResult list() {
        List<User> users = userService.findAll();
        JSONArray arr = new JSONArray();
        for (User user : users) {
            JSONObject json = new JSONObject();
            json.put("id", user.getId());
            json.put("name", user.getName());
            arr.add(json);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/userSubject 用户所属机构
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 用户所属机构
     * @apiParam {String} id 用户ID (必填)
     * @apiParamExample {json} 请求示例:
     *              ?id=0a4179fc06cb49e3ac0db7bcc8cf0882
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:用户不存在或者ID为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "分-部门,总-部门"
     * }
     */
    @PostMapping("/userSubject")
    public JsonResult userSubject(String id) {
        User user = userService.findById(id);
        if (user == null) {
            return JsonResult.notFound("用户不存在或者ID为空");
        }
        List<UserRelation> userRelations = userRelationService.findByUser(user);
        String result = "";
        for (UserRelation userRelation : userRelations) {
            if (userRelation.getDepart() != null) {
                result += userRelation.getDepart().getName() + ",";
            } 
            if (userRelation.getGroups() != null) {
                result += userRelation.getGroups().getName() + ",";
            } 
        }
        jsonResult.setData(StringUtils.isNotBlank(result) ? result.substring(0, result.length() - 1) : result);
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/addFile 上传附件
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 上传附件
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /work/addFile
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"name": "55b576db12d442e99cf5e03e3a421fc0","info1": "","id": "2c9ba3816b95b1fe016b95c942820000","suffix": "dll","info5": "","info4": "","url": "d:\\upload\\spiritimage\\55b576db12d442e99cf5e03e3a421fc0.dll","info3": "","info2": ""
     *     }
     * }
     */
    @PostMapping("/addFile")
    public JsonResult addFile(MultipartFile file) {
        SpiritFile spiritFile = new SpiritFile();
        spiritFile.setCreatedDate(new Date());
        spiritFile.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, spiritFile, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        spiritFile.setUrl(map.get("url"));
        spiritFile.setSuffix(map.get("suffix"));
        spiritFile.setName(map.get("name"));
        spiritFile = spiritService.createFile(spiritFile);
        jsonResult.setData(getUploadJson(spiritFile));
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/addImage 上传图片
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 上传图片
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求示例:
     *              /work/addImage
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:上传失败</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"name": "55b576db12d442e99cf5e03e3a421fc0","info1": "","id": "2c9ba3816b95b1fe016b95c942820000","suffix": "dll","info5": "","info4": "","url": "d:\\upload\\spiritimage\\55b576db12d442e99cf5e03e3a421fc0.dll","info3": "","info2": ""
     *     }
     * }
     */
    @PostMapping("/addImage")
    public JsonResult addImage(MultipartFile file) {
        SpiritImage spiritImage = new SpiritImage();
        spiritImage.setCreatedDate(new Date());
        spiritImage.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, spiritImage, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        spiritImage.setUrl(map.get("url"));
        spiritImage.setSuffix(map.get("suffix"));
        spiritImage.setName(map.get("name"));
        spiritImage = spiritService.createImage(spiritImage);
        jsonResult.setData(getUploadJson(spiritImage));
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/delFile 删除附件
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 删除附件
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              ?id=2c9ba3816b95b1fe016b95c942820000
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
        spiritService.deleteFileById(id);
        return JsonResult.success();
    }

    /**
     * @api {POST} /spirit/delImage 删除图片
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 删除图片
     * @apiParam {String} id
     * @apiParamExample {json} 请求示例:
     *              ?id=2c9ba3816b95b1fe016b95c942820000
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
    @PostMapping("/delImage")
    public JsonResult delImage(String id) {
        spiritService.deleteImageById(id);
        return JsonResult.success();
    }

    /**
     * @api {POST} /spirit/add 添加党内精神
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 添加党内精神
     * @apiParam {String} name 必填
     * @apiParam {String} time 必填,格式yyyyMMddHHmmss
     * @apiParam {String} content 必填
     * @apiParam {String[]} fileIds
     * @apiParam {String[]} imageIds
     * @apiParam {String} userId 必填
     * @apiParamExample {json} 请求示例:
     *              ?id=2c9ba3816b95b1fe016b95c942820000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          String time,
                          String content,
                          String[] fileIds,
                          String[] imageIds,
                          String userId) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(content) || StringUtils.isBlank(time)) {
            return JsonResult.failure(601, "标题/发布时间/正文不能为空");
        }
        if (!TimeUtils.isyyyyMMddHHmmss(time)) {
            return JsonResult.failure(603, "时间格式不为:yyyyMMddHHmmss");
        }
        List<SpiritFile> spiritFiles = spiritService.findFileByIds(fileIds == null ? null : fileIds);
        List<SpiritImage> spiritImages = spiritService.findImageByIds(imageIds == null ? null : imageIds);
        Subject subject = null;
        Depart depart = null;
        Groups groups = null;
        User user = userService.findById(userId);
        if (user == null) {
            return JsonResult.failure(602, "用户不存在或者用户ID为空");
        }
        List<UserRelation> userRelations = userRelationService.findByUser(user);
        for (UserRelation userRelation : userRelations) {
            subject = userRelation.getSubject();
            depart = userRelation.getDepart();
            groups = userRelation.getGroups();
            break;
        }
        Spirit spirit = spiritService.save(name, time, content, spiritFiles, spiritImages, user, subject, depart, groups);
        jsonResult.setData(convert(spirit));
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/view 查看
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看党内精神
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求示例:
     *              ?id=2c9ba3816b95b1fe016b95fec5dd0005
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        Spirit spirit = spiritService.find(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",spirit.getName());
        jsonObject.put("time",spirit.getTime());
        jsonObject.put("spiritid",spirit.getId());
        jsonObject.put("hots",spirit.getHots());
        jsonObject.put("username",spirit.getUser().getName());
        jsonObject.put("nameid",spirit.getUser().getId());
        jsonObject.put("type",spirit.getType());
        jsonObject.put("remark",spirit.getSpiritContent().getContent());
jsonResult.setData(jsonObject);
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/list 查看
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 查看党内精神
     * @apiParam {String} key 搜索字段
     * @apiParam {String} value 查询值
     * @apiParam {int} page 页数(默认:0)
     * @apiParam {int} size 长度(默认:10)
     * @apiParamExample {json} 请求示例:
     *              /spirit/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/list")
    public JsonResult list(String key, String value,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = new Pageable(page, size);
        if (StringUtils.isNotBlank(key) || StringUtils.isNotBlank(value)) {
            pageable.setSearchKey(new String[]{key});
            pageable.setSearchValue(new String[]{value});
        }
        Page<Spirit> spiritPage = spiritService.findPage(pageable);
        JSONArray arr = new JSONArray();
        JSONObject json = new JSONObject();
        for (Spirit spirit : spiritPage.getContent()) {
            arr.add(convert(spirit));
        }
        json.put("total", spiritPage.getTotal());
        json.put("totalPage", spiritPage.getTotalPages());
        json.put("page", spiritPage.getPageNumber());
        json.put("content", arr);
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * @api {POST} /spirit/del 删除
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 删除党内精神
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求示例:
     *               /spirit/del?id=2c9ba3816b95b1fe016b95fec5dd0005
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (spiritService.find(id)==null){
            return JsonResult.notFound("找不到实体");
        }
        spiritService.deleteSpiritById(id);
        return JsonResult.success();
    }

    /**
     * @api {POST} /spirit/updateState  修改党内精神状态
     * @apiGroup Spirit
     * @apiVersion 1.0.0
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiDescription 删除党内精神
     * @apiParam {String} id 必填
     * @apiParam {int} type 必填  0-未通过 1- 已通过
     * @apiParamExample {json} 请求示例:
     *              ?id=2c9ba3816b95b1fe016b95fec5dd0005
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     *                                 601:标题/发布时间/正文不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": ""
     * }
     */
    @PostMapping("/updateState")
    public JsonResult updateState(String soiritid,
                                  int type){
        System.out.println(soiritid);
        System.out.println(type);
        Spirit spirit = spiritService.find(soiritid);
        System.out.println(spirit);
        if (spirit ==null){
            return JsonResult.notFound("找不到党内精神");
        }
        spiritService.updateState(soiritid,type);
        return JsonResult.success();
    }

    private JSONObject convert(Spirit s) {
        JSONObject json = JsonUtils.getJson(s);
        JSONObject ss = new JSONObject();
        Subject subject = subjectService.find(s.getSpiritSubject().getSubjectId());
        Depart depart = departService.findById(s.getSpiritSubject().getDepartId());
        Groups groups = groupsService.findById(s.getSpiritSubject().getGroupId());
        User user = userService.findById(s.getSpiritSubject().getUserId());
        ss.put("subject", JsonUtils.getSimpleJson(subject, new String[]{"id", "name"}));
        ss.put("user", user == null ? null : JsonUtils.getSimpleJson(user, new String[]{"id", "name"}));
        ss.put("depart", depart == null ? null : JsonUtils.getSimpleJson(subject, new String[]{"id", "name"}));
        ss.put("groups", groups == null ? null : JsonUtils.getSimpleJson(subject, new String[]{"id", "name"}));
        json.put("spiritSubject", ss);
        JSONArray arr = new JSONArray();
        if (CollectionUtils.isNotEmpty(s.getSpiritFiles())) {
            for (SpiritFile file : s.getSpiritFiles()) {
                arr.add(getUploadJson(file));
            }
            json.put("spiritFiles", arr);
        }
        if (CollectionUtils.isNotEmpty(s.getSpiritImages())) {
            arr = new JSONArray();
            for (SpiritImage image : s.getSpiritImages()) {
                arr.add(getUploadJson(image));
            }
            json.put("spiritImages", arr);
        }
        json.put("content", spiritService.getContentBySpiritId(s.getId()));
        return json;
    }

}
