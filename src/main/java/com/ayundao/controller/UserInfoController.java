package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
/**
 * @ClassName: UserInfoController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 用户详情
 * @Version: V1.0
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * @api {post} /userInfo/add 新增用户详情
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 新增用户详情
     * @apiParam {json} fileid:["id",""],
     *          {String}imageid:"id",
     *          userInfo:{
     *               {String} "number":"必填 行政编号",</br>
     *               {String} "name":"必填 姓名",</br>
     *               {String} "branchName":"必填 支部",</br>
     *               {String} "sex":"必填 性别",</br>
     *               {String} "department":"必填 科室",</br>
     *               {String} "birthday":"必填 出生日期",</br>
     *               {String} "education":"必填 学历",</br>
     *               {String} "place":"必填 籍贯",</br>
     *               {String} "nation":"必填 民族",</br>
     *               {String} "post":"必填 职务",</br>
     *               {String} "title":"职称",</br>
     *               {String} "idEntity":"个人身份",</br>
     *               {String} "workDate":"工作时间",</br>
     *               {String} "partyDate":"入党时间",</br>
     *               {String} "correctionDate":"转正时间",</br>
     *               {String} "phone":"联系电话",</br>
     *               {String} "idcard":"身份证号",</br>
     *               {String} "userid":提取过来</br>
     *       		},</br>
     *       		userInfoPersonnel:{
     *       		{String} "workyear":"工龄(年)",</br>
     *       		{String} "workmonth":"工龄(月)",</br>
     *       		{String} "partypost":"党内职务",</br>
     *       		{String} "servingdate":"任命时间",</br>
     *       		{String} "otherpost":"其他职务",</br>
     *       		{String} "jianpingpost":"兼评职称",</br>
     *       		{String} "jianpingdate":"兼评时间",</br>
     *       		{String} "politicalappearance":"政治面貌",</br>
     *       		{String} "partydate":"入党团日期",</br>
     *       		{String} "branchname":"所在支部",</br>
     *       		{String} "typeworker":"工人工种",</br>
     *       		{String} "gradeworker":"工人等级",</br>
     *       		{String} "appointmenttime":"聘任时间",</br>
     *       		{String} "maritalstatus":"婚姻状况",</br>
     *       		{String} "hukounature":"户口性质",</br>
     *       		{String} "hukouwhere":"户口所在",</br>
     *       		{String} "beforecompany":"调入前单位",</br>
     *       		{String} "reserveleavedate":"预定离院日期",</br>
     *       		{String} "firstcontractdate":"首次合同开始时间",</br>
     *       		{String} "familyaddr":"家庭地址",</br>
     *       		{String} "personneltype":"人员类别",</br>
     *       		{String} "fanpinenddate":"返聘到期时间"</br>
     *       		}
     *
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userInfoImage": {
     *             "id": "2c9707816b9c33a4016b9c34191d0000",
     *             "name": "f4b4c806692a400b8cddee34dbbb33dc",
     *             "url": "userinfoimage\\f4b4c806692a400b8cddee34dbbb33dc.jpg",
     *             "suffix": "jpg",
     *             "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         },
     *         "userInfoPersonnel": {
     *             "id": "2c9707816b9c33a4016b9d2de15d0016",
     *             "workyear": "工龄(年)",
     *             "workmonth": "工龄(月)",
     *             "partypost": "党内职务",
     *             "servingdate": "任命时间",
     *             "otherpost": "其他职务",
     *             "jianpingpost": "兼评职称",
     *             "jianpingdate": "兼评时间",
     *             "politicalappearance": "政治面貌",
     *             "partydate": "入党团日期",
     *             "branchname": "所在支部",
     *             "typeworker": "工人工种",
     *             "gradeworker": "工人等级",
     *             "appointmenttime": "聘任时间",
     *             "maritalstatus": "婚姻状况",
     *             "hukounature": "户口性质",
     *             "hukouwhere": "户口所在",
     *             "beforecompany": "调入前单位",
     *             "reserveleavedate": "预定离院日期",
     *             "firstcontractdate": "首次合同开始时间",
     *             "familyaddr": "家庭地址",
     *             "personneltype": "人员类别",
     *             "fanpinenddate": "返聘到期时间",
     *             "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         },
     *         "userInfoFile": {
     *             "id": "2c9707816b9c33a4016b9d28a9e60012",
     *             "name": "e94ef487227c43b2999efcd53b06fc61",
     *             "url": "userinfofile\\e94ef487227c43b2999efcd53b06fc61.jpg",
     *             "suffix": "jpg",
     *             "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         },
     *         "userinfo": {
     *             "id": "2c9707816b9c33a4016b9d2de1560015",
     *             "number": "005",
     *             "name": "测试5",
     *             "branchName": "第一党支部",
     *             "sex": "男",
     *             "department": "内科",
     *             "birthday": "1995-04-10",
     *             "education": "高中",
     *             "place": "杭州",
     *             "nation": "汉族",
     *             "post": "主席",
     *             "title": "主任医师",
     *             "idEntity": "干部",
     *             "workDate": "",
     *             "partyDate": "2011-11-11",
     *             "correctionDate": "",
     *             "phone": "",
     *             "idcard": "61052719950410181X",
     *             "userid": "",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping(value = "/add")
    public JsonResult add(@RequestBody Map<String,Object> map){
        String imageids =(String) map.get("imageid");
        List<String> fileids = (List<String>) map.get("fileid");
        Map<String,Object> object1 = (Map<String,Object>)map.get("userInfo");
        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(object1), UserInfo.class);
        userInfo.setCreatedDate(new Date());
        userInfo.setLastModifiedDate(new Date());
        Map<String,Object> object2 = (Map<String,Object>)map.get("userInfoPersonnel");
        UserInfoPersonnel userInfoPersonnel = JSON.parseObject(JSON.toJSONString(object2), UserInfoPersonnel.class);
        userInfoPersonnel.setCreatedDate(new Date());
        userInfoPersonnel.setLastModifiedDate(new Date());
        return userInfoService.saveAll(imageids,fileids,userInfo,userInfoPersonnel, jsonResult);
    }

    /**
     * @api {POST} /userInfo/upload_file 上传文件
     * @apiGroup UserInfoFile
     * @apiVersion 1.0.0
     * @apiDescription 上传文件
     * @apiParam {String} name 必填 名称
     * @apiParam {String} url 必填路径
     * @apiParam {String} format 必填后缀
     * @apiParam {String} userInfoId 必填
     * @apiParamExample {json} 请求样例:
     *                ?name=&url=&format=&id=
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     *                                 602:文件类型异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userInfoFile": {
     *             "id": "297e47e36b8d653c016b8d6dd5370004",
     *             "name": "上传文件1",
     *             "url": "1111111",
     *             "format": ".jpg",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping("/upload_file")
    public JsonResult uploadFile(MultipartFile file){
        UserInfoFile files = new UserInfoFile();
        files.setCreatedDate(new Date());
        files.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, files, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        files.setName(map.get("name"));
        files.setUrl(map.get("url"));
        files.setSuffix(map.get("suffix"));
        files = userInfoService.saveFile(files);
        jsonResult.setData(getUploadJson(files));
        return jsonResult;
    }

    /**
     * @api {POST} /userInfo/upload_image 上传图片
     * @apiGroup UserInfoImage
     * @apiVersion 1.0.0
     * @apiDescription 上传图片
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求样例:
     *                /userInfo/upload_image
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userinfoid": "",
     *         "lastModifiedDate": "20190628114945",
     *         "suffix": "jpg",
     *         "version": "0",
     *         "url": "d:\\upload\\userinfoimage\\f4b4c806692a400b8cddee34dbbb33dc.jpg",
     *         "createdDate": "20190628114945",
     *         "name": "f4b4c806692a400b8cddee34dbbb33dc",
     *         "info1": "",
     *         "id": "2c9707816b9c33a4016b9c34191d0000",
     *         "info5": "",
     *         "info4": "",
     *         "info3": "",
     *         "info2": ""
     *     }
     * }
     */
    @PostMapping("/upload_image")
    public JsonResult uploadImage(MultipartFile file) {
        UserInfoImage image = new UserInfoImage();
        image.setCreatedDate(new Date());
        image.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, image, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        image.setName(map.get("name"));
        image.setUrl(map.get("url"));
        image.setSuffix(map.get("suffix"));
        image = userInfoService.saveImage(image);
        jsonResult.setData(getUploadJson(image));
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_basic 新增用户基础信息表
     * @apiGroup UserInfoBasic
     * @apiVersion 1.0.0
     * @apiDescription 新增用户基础信息表
     * @apiParam {JSON} userInfoBasic:{
     *         {String} "workerdate":"计算连续工龄时间",</br>
     *         {String} "zhuanzhengdate":"合同制转正时间",</br>
     *         {String} "wagesdate":"工资关系转移时间",</br>
     *         {String} "arrivedate":"到院时间",</br>
     *         {String} "workersnature":"职工性质",</br>
     *         {String} "workerscategory":"职工类别",</br>
     *         {String} "postcategory":"岗位类别",</br>
     *         {String} "checkgroup":"考勤组",</br>
     *         {String} "organization":"编制",</br>
     *         {String} "increasemode":"增加方式",</br>
     *         {String} "othernumber":"其他工号",</br>
     *         {String} "age":"年龄",</br>
     *         {String} "userinfoid":""</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_basic
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_basic")
    public JsonResult add_basic(String workerdate,String zhuanzhengdate,String wagesdate,
                                String arrivedate,String workersnature, String workerscategory,
                                String postcategory, String checkgroup,String organization,
                                String increasemode,String othernumber,String age,
                                String userinfoid) {
        UserInfoBasic userInfoBasic = new UserInfoBasic();
        userInfoBasic.setCreatedDate(new Date());
        userInfoBasic.setLastModifiedDate(new Date());
        userInfoBasic.setWorkerdate(workerdate);
        userInfoBasic.setZhuanzhengdate(zhuanzhengdate);
        userInfoBasic.setWagesdate(wagesdate);
        userInfoBasic.setArrivedate(arrivedate);
        userInfoBasic.setWorkersnature(workersnature);
        userInfoBasic.setWorkerscategory(workerscategory);
        userInfoBasic.setPostcategory(postcategory);
        userInfoBasic.setCheckgroup(checkgroup);
        userInfoBasic.setOrganization(organization);
        userInfoBasic.setIncreasemode(increasemode);
        userInfoBasic.setOthernumber(othernumber);
        userInfoBasic.setAge(age);
        userInfoBasic.setUserinfoid(userinfoid);
        return userInfoService.saveBasic(jsonResult,userInfoBasic);
    }

    /**
     * @api {post} /userInfo/add_contract 新增用户合同信息表
     * @apiGroup UserInfoContract
     * @apiVersion 1.0.0
     * @apiDescription 新增用户合同信息表
     * @apiParam {JSON} userinfoid:xxxx,
     *      UserInfoContract:{
     *         {String} "archivesmanagementunit":"档案管理单位",</br>
     *         {String} "contracttype":"合同类型",</br>
     *         {String} "contractnumber":"合同编号",</br>
     *         {String} "contractstarttime":"合同开始时间",</br>
     *         {String} "contractendtime":"合同结束时间",</br>
     *         {String} "signaturetype":"签订类型",</br>
     *         {String} "contractperiod":"合同期限",</br>
     *         {String} "frequency":"合同签定次数",</br>
     *         {String} "userinfoid":"用户详情ID"</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_contract
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userInfoContract": {
     *             "id": "2c9707816b9c33a4016b9d930573001b",
     *             "archivesmanagementunit": "档案管理单位",
     *             "contracttype": "合同类型",
     *             "contractnumber": "合同编号",
     *             "contractstarttime": "合同开始时间",
     *             "contractendtime": "合同结束时间",
     *             "signaturetype": "签订类型",
     *             "contractperiod": "合同期限",
     *             "frequency": "合同签定次数",
     *             "registrationtime": "注册时间",
     *             "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping("/add_contract")
    public JsonResult add_contract(String archivesmanagementunit,String contracttype,String contractnumber,
                                   String contractstarttime,String contractendtime,String signaturetype,
                                   String contractperiod,String frequency,String userinfoid ){
        UserInfoContract userInfoContract = new UserInfoContract();
        userInfoContract.setCreatedDate(new Date());
        userInfoContract.setLastModifiedDate(new Date());
        userInfoContract.setArchivesmanagementunit(archivesmanagementunit);
        userInfoContract.setContracttype(contracttype);
        userInfoContract.setContractnumber(contractnumber);
        userInfoContract.setContractstarttime(contractstarttime);
        userInfoContract.setContractendtime(contractendtime);
        userInfoContract.setSignaturetype(signaturetype);
        userInfoContract.setContractperiod(contractperiod);
        userInfoContract.setFrequency(frequency);
        userInfoContract.setUserinfoid(userinfoid);
        return userInfoService.saveContract(jsonResult,userInfoContract);
    }

    /**
     * @api {post} /userInfo/add_education_work 新增用户教育工作表
     * @apiGroup UserInfoEducationWork
     * @apiVersion 1.0.0
     * @apiDescription 新增用户教育工作表
     * @apiParam {JSON} userInfoEducationWork:{
     *         {String} "startdate":"开始日期",</br>
     *         {String} "enddate":"毕业日期",</br>
     *         {String} "graduationschool":"毕业学校",</br>
     *         {String} "major":"所学专业",</br>
     *         {String} "educationcategory":"教育类别",</br>
     *         {String} "education":"学历",</br>
     *         {String} "degree":"学位",</br>
     *         {String} "degreedate":"学位时间",</br>
     *         {String} "edusystem":"学制",</br>
     *         {String} "witness":"证明人",</br>
     *         {String} "userinfoid":"用户详情ID"</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_education_work
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userInfoEducationWork": {
     *             "id": "297e47e36b96ab96016b9972f96d0017",
     *             "startdate": "1",
     *             "enddate": "1",
     *             "graduationschool": null,
     *             "major": "1",
     *             "educationcategory": "1",
     *             "education": "1",
     *             "degree": "1",
     *             "degreedate": "1",
     *             "edusystem": "1",
     *             "witness": "1",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping("/add_education_work")
    public JsonResult add_education_work(String startdate,String enddate, String graduationschool,
                                         String major,String educationcategory,String education,
                                         String degree,String degreedate,String edusystem,
                                         String witness,String userinfoid){
        UserInfoEducationWork userInfoEducationWork = new UserInfoEducationWork();
        userInfoEducationWork.setCreatedDate(new Date());
        userInfoEducationWork.setLastModifiedDate(new Date());
        userInfoEducationWork.setStartdate(startdate);
        userInfoEducationWork.setEnddate(enddate);
        userInfoEducationWork.setGraduationschool(graduationschool);
        userInfoEducationWork.setMajor(major);
        userInfoEducationWork.setEducationcategory(educationcategory);
        userInfoEducationWork.setEducation(education);
        userInfoEducationWork.setDegree(degree);
        userInfoEducationWork.setDegreedate(degreedate);
        userInfoEducationWork.setEdusystem(edusystem);
        userInfoEducationWork.setWitness(witness);
        userInfoEducationWork.setUserinfoid(userinfoid);
        return userInfoService.saveEducationWork(jsonResult,userInfoEducationWork);
    }

    /**
     * @api {post} /userInfo/add_medical_care 新增用户医务护理表
     * @apiGroup UserInfoMedicalCare
     * @apiVersion 1.0.0
     * @apiDescription 新增用户医务护理表
     * @apiParam {JSON} UserInfoMedicalCare:{
     *         {String} "parcticelevel":"执业资格等级",</br>
     *         {String} "practicenumber":"执业证号",</br>
     *         {String} "parcticebookobtaindate":"执业证书取得时间",</br>
     *         {String} "technologynumber":"技术资格证号",</br>
     *         {String} "technologybookobtaindate":"技术资格证书取得时间",</br>
     *         {String} "nurseshoesize":"护士鞋号",</br>
     *         {String} "practicerange":"执业范围",</br>
     *         {String} "registrationtime":"注册时间",</br>
     *         {String} "practiceyears":"执业年限",</br>
     *         {String} "practicecategory":"执业类别",</br>
     *         {String} "interruptpractice":"中断执业",</br>
     *         {String} "userinfoid":"用户详情ID"</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_medical_care
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_medical_care")
    public JsonResult add_medical_care(String parcticelevel,String practicenumber,String parcticebookobtaindate,
                                       String technologynumber,String technologybookobtaindate,String nurseshoesize,
                                       String practicerange,String registrationtime,String practiceyears,
                                       String practicecategory,String interruptpractice,String userinfoid){
        UserInfoMedicalCare userInfoMedicalCare = new UserInfoMedicalCare();
        userInfoMedicalCare.setCreatedDate(new Date());
        userInfoMedicalCare.setLastModifiedDate(new Date());
        userInfoMedicalCare.setParcticelevel(parcticelevel);
        userInfoMedicalCare.setPracticenumber(practicenumber);
        userInfoMedicalCare.setParcticebookobtaindate(parcticebookobtaindate);
        userInfoMedicalCare.setTechnologynumber(technologynumber);
        userInfoMedicalCare.setTechnologybookobtaindate(technologybookobtaindate);
        userInfoMedicalCare.setNurseshoesize(nurseshoesize);
        userInfoMedicalCare.setPracticerange(practicerange);
        userInfoMedicalCare.setRegistrationtime(registrationtime);
        userInfoMedicalCare.setPracticeyears(practiceyears);
        userInfoMedicalCare.setPracticecategory(practicecategory);
        userInfoMedicalCare.setInterruptpractice(interruptpractice);
        userInfoMedicalCare.setUserinfoid(userinfoid);
        return userInfoService.saveMedicalCare(jsonResult,userInfoMedicalCare);
    }

    /**
     * @api {post} /userInfo/add_other 新增用户其他信息表
     * @apiGroup UserInfoOther
     * @apiVersion 1.0.0
     * @apiDescription 新增用户其他信息表
     * @apiParam {JSON} UserInfoOther{
     *         {String} "whethersign":"是否允许登录",</br>
     *         {String} "whethercheck":"是否需要考勤",</br>
     *         {String} "state":"在职状态",</br>
     *         {String} "leavedate":"离院时间",</br>
     *         {String} "cancellation":"注销原因",</br>
     *         {String} "userinfoid":"用户详情ID"</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_other
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_other")
    public JsonResult add_other(String whethersign,String whethercheck,String state,
                                String leavedate,String cancellation,String userinfoid){
        UserInfoOther userInfoOther = new UserInfoOther();
        userInfoOther.setCreatedDate(new Date());
        userInfoOther.setLastModifiedDate(new Date());
        userInfoOther.setWhethersign(whethersign);
        userInfoOther.setWhethercheck(whethercheck);
        userInfoOther.setState(state);
        userInfoOther.setLeavedate(leavedate);
        userInfoOther.setCancellation(cancellation);
        userInfoOther.setUserinfoid(userinfoid);
        return userInfoService.saveOther(jsonResult,userInfoOther);
    }

    /**
     * @api {post} /userInfo/add_personnel 新增用户人事信息表
     * @apiGroup UserInfoPersonnel
     * @apiVersion 1.0.0
     * @apiDescription 新增用户人事信息表
     * @apiParam {JSON} userInfoPersonnel:{
     *            		{String} "workyear":"工龄(年)",</br>
     *             		{String} "workmonth":"工龄(月)",</br>
     *             		{String} "partypost":"党内职务",</br>
     *             		{String} "servingdate":"任命时间",</br>
     *             		{String} "otherpost":"其他职务",</br>
     *             		{String} "jianpingpost":"兼评职称",</br>
     *             		{String} "jianpingdate":"兼评时间",</br>
     *             		{String} "politicalappearance":"政治面貌",</br>
     *             		{String} "partydate":"入党团日期",</br>
     *             		{String} "branchname":"所在支部",</br>
     *             		{String} "typeworker":"工人工种",</br>
     *             		{String} "gradeworker":"工人等级",</br>
     *             		{String} "appointmenttime":"聘任时间",</br>
     *             		{String} "maritalstatus":"婚姻状况",</br>
     *             		{String} "hukounature":"户口性质",</br>
     *             		{String} "hukouwhere":"户口所在",</br>
     *             		{String} "beforecompany":"调入前单位",</br>
     *             		{String} "reserveleavedate":"预定离院日期",</br>
     *             		{String} "firstcontractdate":"首次合同开始时间",</br>
     *             		{String} "familyaddr":"家庭地址",</br>
     *             		{String} "personneltype":"人员类别",</br>
     *             		{String} "fanpinenddate":"返聘到期时间",</br>
     *                 {String} "userinfoid":""</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_personnel
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_personnel")
    public JsonResult add_personnel(String workyear,String workmonth,String partypost,
                                    String servingdate,String otherpost,String jianpingpost,
                                    String jianpingdate,String politicalappearance,String partydate,
                                    String branchname,String typeworker,String gradeworker,
                                    String appointmenttime,String maritalstatus,String hukounature,
                                    String hukouwhere,String beforecompany,String reserveleavedate,
                                    String firstcontractdate,String familyaddr,String personneltype,
                                    String fanpinenddate,String userinfoid){
        UserInfoPersonnel userInfoPersonnel = new UserInfoPersonnel();
        userInfoPersonnel.setCreatedDate(new Date());
        userInfoPersonnel.setLastModifiedDate(new Date());
        userInfoPersonnel.setWorkyear(workyear);
        userInfoPersonnel.setWorkmonth(workmonth);
        userInfoPersonnel.setPartypost(partypost);
        userInfoPersonnel.setServingdate(servingdate);
        userInfoPersonnel.setOtherpost(otherpost);
        userInfoPersonnel.setJianpingpost(jianpingpost);
        userInfoPersonnel.setJianpingdate(jianpingdate);
        userInfoPersonnel.setPoliticalappearance(politicalappearance);
        userInfoPersonnel.setPartydate(partydate);
        userInfoPersonnel.setBranchname(branchname);
        userInfoPersonnel.setTypeworker(typeworker);
        userInfoPersonnel.setGradeworker(gradeworker);
        userInfoPersonnel.setAppointmenttime(appointmenttime);
        userInfoPersonnel.setMaritalstatus(maritalstatus);
        userInfoPersonnel.setHukounature(hukounature);
        userInfoPersonnel.setHukouwhere(hukouwhere);
        userInfoPersonnel.setBeforecompany(beforecompany);
        userInfoPersonnel.setFirstcontractdate(firstcontractdate);
        userInfoPersonnel.setFamilyaddr(familyaddr);
        userInfoPersonnel.setReserveleavedate(reserveleavedate);
        userInfoPersonnel.setPersonneltype(personneltype);
        userInfoPersonnel.setFanpinenddate(fanpinenddate);
        userInfoPersonnel.setUserinfoid(userinfoid);
        return userInfoService.savePersonnel(jsonResult,userInfoPersonnel);
    }

    /**
     * @api {post} /userInfo/add_title_apost 新增用户职称职务表
     * @apiGroup UserInfoTitleaPost
     * @apiVersion 1.0.0
     * @apiDescription 新增用户职称职务表
     * @apiParam {JSON} UserInfoTitleaPost:{
     *         {String} "postname":"职务名称",</br>
     *         {String} "administrationpost":"行政职务",</br>
     *         {String} "servingyears":"任职年限",</br>
     *         {String} "administrationlevel":"行政级别",</br>
     *         {String} "servingdate":"任命时间",</br>
     *         {String} "servingstopdate":"任命截止时间",</br>
     *         {String} "beforeadministrationpost":"前行政职务",</br>
     *         {String} "beforeservingdate":"前行政任命时间",</br>
     *         {String} "beforeservingstopdate":"前任命截止时间",</br>
     *         {String} "qualificationtitle":"现资格职称",</br>
     *         {String} "qualificationgetdate":"现资格获取时间",</br>
     *         {String} "engagetitle":"现聘任职称",</br>
     *         {String} "engagetitledate":"现职称聘任时间",</br>
     *         {String} "engagetime":"现聘期",</br>
     *         {String} "beforequalificationtitle":"前资格名称",</br>
     *         {String} "beforequalificationgetdate":"前资格获取时间",</br>
     *         {String} "beforeengagetitle":"前聘任职称",</br>
     *         {String} "beforeengagetitledate":"前职称聘任时间",</br>
     *         {String} "beforeengagetime":"前聘期",</br>
     *         {String} "beforeengagestoptime":"前聘期截止日期",</br>
     *         {String} "technicallevel":"职称技术级别",</br>
     *         {String} "personnelcategory":"职称人员类别",</br>
     *         {String} "weijicategory":"职称卫技类别",</br>
     *         {String} "userinfoid":"用户详情ID"</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_title_apost
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_title_apost")
    public JsonResult add_title_apost(String postname,String administrationpost,String servingyears,
                                      String administrationlevel,String servingdate,String servingstopdate,
                                      String beforeadministrationpost,String beforeservingdate,String beforeservingstopdate,
                                      String qualificationtitle,String qualificationgetdate,String engagetitle,
                                      String engagetitledate,String engagetime,String beforequalificationtitle,
                                      String beforequalificationgetdate,String beforeengagetitle,String beforeengagetitledate,
                                      String beforeengagetime,String beforeengagestoptime,String technicallevel,
                                      String personnelcategory,String weijicategory,String userinfoid){
        UserInfoTitleaPost userInfoTitleaPost = new UserInfoTitleaPost();
        userInfoTitleaPost.setCreatedDate(new Date());
        userInfoTitleaPost.setLastModifiedDate(new Date());
        userInfoTitleaPost.setPostname(postname);
        userInfoTitleaPost.setAdministrationpost(administrationpost);
        userInfoTitleaPost.setServingyears(servingyears);
        userInfoTitleaPost.setAdministrationlevel(administrationlevel);
        userInfoTitleaPost.setServingdate(servingdate);
        userInfoTitleaPost.setServingstopdate(servingstopdate);
        userInfoTitleaPost.setBeforeadministrationpost(beforeadministrationpost);
        userInfoTitleaPost.setBeforeservingdate(beforeservingdate);
        userInfoTitleaPost.setBeforeservingstopdate(beforeservingstopdate);
        userInfoTitleaPost.setQualificationtitle(qualificationtitle);
        userInfoTitleaPost.setQualificationgetdate(qualificationgetdate);
        userInfoTitleaPost.setEngagetitle(engagetitle);
        userInfoTitleaPost.setEngagetitledate(engagetitledate);
        userInfoTitleaPost.setEngagetime(engagetime);
        userInfoTitleaPost.setBeforequalificationtitle(beforequalificationtitle);
        userInfoTitleaPost.setBeforequalificationgetdate(beforequalificationgetdate);
        userInfoTitleaPost.setBeforeengagetitle(beforeengagetitle);
        userInfoTitleaPost.setBeforeengagetitledate(beforeengagetitledate);
        userInfoTitleaPost.setBeforeengagetime(beforeengagetime);
        userInfoTitleaPost.setBeforeengagestoptime(beforeengagestoptime);
        userInfoTitleaPost.setTechnicallevel(technicallevel);
        userInfoTitleaPost.setPersonnelcategory(personnelcategory);
        userInfoTitleaPost.setWeijicategory(weijicategory);
        userInfoTitleaPost.setUserinfoid(userinfoid);
        return userInfoService.saveTitleaPost(jsonResult,userInfoTitleaPost);
    }

    /**
     * @api {post} /userInfo/add_work 新增用户工作经历表
     * @apiGroup UserInfoWork
     * @apiVersion 1.0.0
     * @apiDescription 新增用户工作经历表
     * @apiParam {JSON} UserInfoWork:{
     *         {String} "startdate":"开始时间",</br>
     *         {String} "enddate":"结束时间",</br>
     *         {String} "workunit":"工作单位",</br>
     *         {String} "toservepost":"担任职务",</br>
     *         {String} "posttitle":"岗位职称",</br>
     *         {String} "witness":"证明人",</br>
     *         {String} "userinfoid":"用户详情ID"</br>
     *         }
     * @apiParamExample {json} 请求样例：
     *                /userInfo/add_work
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_work")
//    public JsonResult add_work(@RequestBody UserInfoWork userInfoWork){
//        userInfoWork.setCreatedDate(new Date());
//        userInfoWork.setLastModifiedDate(new Date());
//        return userInfoService.saveWork(jsonResult,userInfoWork);
//    }
    public JsonResult add_work(String startdate,String enddate,String workunit,
                               String toservepost,String posttitle,String witness,
                               String userinfoid){
        UserInfoWork userInfoWork = new UserInfoWork();
        userInfoWork.setCreatedDate(new Date());
        userInfoWork.setLastModifiedDate(new Date());
        userInfoWork.setStartdate(startdate);
        userInfoWork.setEnddate(enddate);
        userInfoWork.setWorkunit(workunit);
        userInfoWork.setToservepost(toservepost);
        userInfoWork.setPosttitle(posttitle);
        userInfoWork.setWitness(witness);
        userInfoWork.setUserinfoid(userinfoid);
        return userInfoService.saveWork(jsonResult,userInfoWork);
    }



    /**
     * @api {Post} /userInfo/del 删除用户详情
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.delete(id);
        return jsonResult;
    }

    /**
     * @api {Post} /userInfo/del_contract 删除用户详情 -合同信息表
     * @apiGroup UserInfoContract
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 合同信息表ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @PostMapping("/del_contract")
    public JsonResult del_contract(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteContract(id);
        return jsonResult;
    }

    /**
     * @api {Post} /userInfo/del_work 删除用户详情 -工作信息表
     * @apiGroup UserInfoWork
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 工作信息表ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @PostMapping("/del_work")
    public JsonResult del_work(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteWork(id);
        return jsonResult;
    }

    /**
     * @api {Post} /userInfo/del_edu 删除用户详情 -工作信息表
     * @apiGroup UserInfoEducationWork
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 工作信息表ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @PostMapping("/del_edu")
    public JsonResult del_edu(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.deleteEducationWork(id);
        return jsonResult;
    }

    /**
     * @api {get} /userInfo/list 用户分页
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParamExample {json} 请求样例
     *                /userInfo/list
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "birthday": "1995-04-10",
     *             "education": "本科",
     *             "nation": "汉族",
     *             "title": "专家",
     *             "userid": "11213546546132151",
     *             "number": "002",
     *             "password": "123456",
     *             "workDate": "40年",
     *             "idEntity": "未知",
     *             "post": "主治医生",
     *             "id": "297e47e36b7821e5016b782294410000",
     *             "place": "陕西省",
     *             "department": "消化科",
     *             "lastModifiedDate": "20190621114420",
     *             "sex": "男",
     *             "branchName": "第一支部",
     *             "correctionDate": "2000-11-11",
     *             "version": "0",
     *             "partyDate": "2011-01-01",
     *             "createdDate": "20190621114420",
     *             "phone": "12124545121",
     *             "idcard": "61052719950410181X",
     *             "name": "测试",
     *             "info1": "",
     *             "info5": "",
     *             "info4": "",
     *             "info3": "",
     *             "username": "管理员1",
     *             "info2": ""
     *         },
     *         {
     *             "birthday": "1995-04-10",
     *             "education": "本科",
     *             "nation": "汉族",
     *             "title": "专家",
     *             "userid": "11213546546132151",
     *             "number": "003",
     *             "password": "123",
     *             "workDate": "40年",
     *             "idEntity": "未知",
     *             "post": "45514",
     *             "id": "297e47e36b7edc64016b7edcaf540000",
     *             "place": "陕西省",
     *             "department": "消化科",
     *             "lastModifiedDate": "20190622190520",
     *             "sex": "男",
     *             "branchName": "第一支部",
     *             "correctionDate": "",
     *             "version": "0",
     *             "partyDate": "2011-01-01",
     *             "createdDate": "20190622190520",
     *             "phone": "12124545121",
     *             "idcard": "61052719950410181X",
     *             "name": "测试",
     *             "info1": "",
     *             "info5": "",
     *             "info4": "",
     *             "info3": "",
     *             "username": "管理员44",
     *             "info2": ""
     *         }
     *     ]
     * }
     */
    @GetMapping("/list")
    public JsonResult list(){
        List<UserInfo> pages = userInfoService.findAll();
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : pages) {
            JSONObject json = new JSONObject(JsonUtils.getJson(userInfo));
            pageArray.add(json);
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }
    /**
     * @api {post} /userInfo/findId 用户个人查询
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 用户分页
     * @apiParamExample {json} 请求样例
     *                /userInfo/findId?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "userInfo": {
     *                 "id": "2c9707816b9c33a4016b9d2de1560015",
     *                 "number": "005",
     *                 "name": "测试5",
     *                 "branchName": "第一党支部",
     *                 "sex": "男",
     *                 "department": "内科",
     *                 "birthday": "1995-04-10",
     *                 "education": "高中",
     *                 "place": "杭州",
     *                 "nation": "汉族",
     *                 "post": "主席",
     *                 "title": "主任医师",
     *                 "idEntity": "干部",
     *                 "workDate": "",
     *                 "partyDate": "2011-11-11",
     *                 "correctionDate": "",
     *                 "phone": "",
     *                 "idcard": "61052719950410181X",
     *                 "userid": "",
     *                 "info1": null,
     *                 "info2": null,
     *                 "info3": null,
     *                 "info4": null,
     *                 "info5": null,
     *                 "new": false
     *             },
     *             "userInfoImage": {
     *                 "id": "2c9707816b9c33a4016b9c34191d0000",
     *                 "name": "f4b4c806692a400b8cddee34dbbb33dc",
     *                 "url": "userinfoimage\\f4b4c806692a400b8cddee34dbbb33dc.jpg",
     *                 "suffix": "jpg",
     *                 "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *                 "info1": null,
     *                 "info2": null,
     *                 "info3": null,
     *                 "info4": null,
     *                 "info5": null,
     *                 "new": false
     *             },
     *             "userInfoPersonnel": {
     *                 "id": "2c9707816b9c33a4016b9d2de15d0016",
     *                 "workyear": "工龄(年)",
     *                 "workmonth": "工龄(月)",
     *                 "partypost": "党内职务",
     *                 "servingdate": "任命时间",
     *                 "otherpost": "其他职务",
     *                 "jianpingpost": "兼评职称",
     *                 "jianpingdate": "兼评时间",
     *                 "politicalappearance": "政治面貌",
     *                 "partydate": "入党团日期",
     *                 "branchname": "所在支部",
     *                 "typeworker": "工人工种",
     *                 "gradeworker": "工人等级",
     *                 "appointmenttime": "聘任时间",
     *                 "maritalstatus": "婚姻状况",
     *                 "hukounature": "户口性质",
     *                 "hukouwhere": "户口所在",
     *                 "beforecompany": "调入前单位",
     *                 "reserveleavedate": "预定离院日期",
     *                 "firstcontractdate": "首次合同开始时间",
     *                 "familyaddr": "家庭地址",
     *                 "personneltype": "人员类别",
     *                 "fanpinenddate": "返聘到期时间",
     *                 "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *                 "info1": null,
     *                 "info2": null,
     *                 "info3": null,
     *                 "info4": null,
     *                 "info5": null,
     *                 "new": false
     *             },
     *             "userInfoFile": {
     *                 "id": "2c9707816b9c33a4016b9d28a9e60012",
     *                 "name": "e94ef487227c43b2999efcd53b06fc61",
     *                 "url": "userinfofile\\e94ef487227c43b2999efcd53b06fc61.jpg",
     *                 "suffix": "jpg",
     *                 "userinfoid": "2c9707816b9c33a4016b9d2de1560015",
     *                 "info1": null,
     *                 "info2": null,
     *                 "info3": null,
     *                 "info4": null,
     *                 "info5": null,
     *                 "new": false
     *             }
     *         }
     *     ]
     * }
     */
    @PostMapping("/findId")
    public JsonResult findId(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        JSONObject json = new JSONObject();
        UserInfo pages = userInfoService.findByUserInfoId(id);
        UserInfoPersonnel pages1 = userInfoService.findByUserInfoPersonnelUserid(id);
        UserInfoImage pages2 = userInfoService.findByUserInfoImageUserid(id);
        List<UserInfoFile> pages3 = userInfoService.findByUserInfoFileUserid(id);
        if(pages3 != null){
            for (UserInfoFile userInfoFile : pages3) {
                json.put("userInfoFile",userInfoFile);
            }
        }
        json.put("userInfo",pages);
        json.put("userInfoPersonnel",pages1);
        json.put("userInfoImage",pages2);
        JSONArray pageArray = new JSONArray();
        pageArray.add(json);
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findBasic 用户个人查询 -基础信息
     * @apiGroup UserInfoBasic
     * @apiVersion 1.0.0
     * @apiDescription 用户基础信息
     * @apiParamExample {json} 请求样例
     *                /userInfo/findBasic?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findBasic")
    public JsonResult findBasic(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        UserInfoBasic pages = userInfoService.findByUserInfoBasicUserId(id);
        JSONArray pageArray = new JSONArray();
        pageArray.add(pages);
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findContract 用户个人查询 -合同信息
     * @apiGroup UserInfoContract
     * @apiVersion 1.0.0
     * @apiDescription 用户合同信息
     * @apiParamExample {json} 请求样例
     *                /userInfo/findContract?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findContract")
    public JsonResult findContract(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        List<UserInfoContract> pages = userInfoService.findByUserInfoContractUserid(id);
        JSONArray pageArray = new JSONArray();
        for (UserInfoContract page : pages) {
            JSONObject json = new JSONObject(JsonUtils.getJson(page));
            pageArray.add(json);
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findEducationWork 用户个人查询 -教育工作
     * @apiGroup UserInfoEducationWork
     * @apiVersion 1.0.0
     * @apiDescription 用户教育工作
     * @apiParamExample {json} 请求样例
     *                /userInfo/findEducationWork?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findEducationWork")
    public JsonResult findEducationWork(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        List<UserInfoEducationWork> pages = userInfoService.findByUserInfoEducationWorkUserid(id);
        JSONArray pageArray = new JSONArray();
        for (UserInfoEducationWork page : pages) {
            JSONObject json = new JSONObject(JsonUtils.getJson(page));
            pageArray.add(json);
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findMedicalCare 用户个人查询 -医务护理
     * @apiGroup UserInfoMedicalCare
     * @apiVersion 1.0.0
     * @apiDescription 用户医务护理
     * @apiParamExample {json} 请求样例
     *                /userInfo/findMedicalCare?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findMedicalCare")
    public JsonResult findMedicalCare(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        UserInfoMedicalCare pages = userInfoService.findByUserInfoMedicalCareUserid(id);
        JSONArray pageArray = new JSONArray();
        pageArray.add(pages);
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findOther 用户个人查询 -其他表
     * @apiGroup UserInfoOther
     * @apiVersion 1.0.0
     * @apiDescription 用户其他表
     * @apiParamExample {json} 请求样例
     *                /userInfo/findOther?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findOther")
    public JsonResult findOther(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        UserInfoOther pages = userInfoService.findByUserInfoOtherUserid(id);
        JSONArray pageArray = new JSONArray();
        pageArray.add(pages);
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findPersonnel 用户个人查询 -人事信息
     * @apiGroup UserInfoPersonnel
     * @apiVersion 1.0.0
     * @apiDescription 用户人事信息
     * @apiParamExample {json} 请求样例
     *                /userInfo/findPersonnel?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findPersonnel")
    public JsonResult findPersonnel(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        UserInfoPersonnel pages = userInfoService.findByUserInfoPersonnelUserid(id);
        JSONArray pageArray = new JSONArray();
        pageArray.add(pages);
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findTitleaPost 用户个人查询 -职务职称
     * @apiGroup UserInfoTitleaPost
     * @apiVersion 1.0.0
     * @apiDescription 用户职务职称
     * @apiParamExample {json} 请求样例
     *                /userInfo/findTitleaPost?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findTitleaPost")
    public JsonResult findTitleaPost(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        UserInfoTitleaPost pages = userInfoService.findByUserInfoTitleaPostUserid(id);
        JSONArray pageArray = new JSONArray();
        pageArray.add(pages);
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findWork 用户个人查询 -工作经历
     * @apiGroup UserInfoWork
     * @apiVersion 1.0.0
     * @apiDescription 用户工作经历
     * @apiParamExample {json} 请求样例
     *                /userInfo/findWork ?id=297e47e36b8cbecd016b8cbf24ec0001
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         null
     *     ]
     * }
     */
    @PostMapping("/findWork")
    public JsonResult findWork(String id){
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        List<UserInfoWork> pages = userInfoService.findByUserInfoWorkUserid(id);
        JSONArray pageArray = new JSONArray();
        for (UserInfoWork page : pages) {
            JSONObject json = new JSONObject(JsonUtils.getJson(page));
            pageArray.add(json);
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/findByLike 用户条件查询
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 用户条件查询
     * @apiParam {String} name 姓名
     * @apiParam {String} number 编号
     * @apiParam {String} department 科室
     * @apiParamExample {json} 请求样例
     *                /userInfo/findByLike?name= (or number or department)
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "birthday": "1984-11-11",
     *             "education": "本科",
     *             "nation": "汉族",
     *             "title": "主治医生",
     *             "userid": "297e47e36b8cbecd016b8cbf239b0000",
     *             "number": "002",
     *             "idEntity": "",
     *             "workDate": "2004-11-11",
     *             "post": "院长",
     *             "id": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "place": "浙江杭州",
     *             "department": "内科",
     *             "lastModifiedDate": "20190625114744",
     *             "sex": "男",
     *             "branchName": "第一党支部",
     *             "correctionDate": "2007-11-11",
     *             "version": "0",
     *             "partyDate": "2004-11-11",
     *             "createdDate": "20190625114744",
     *             "phone": "19822222222",
     *             "idcard": "315247198811111811",
     *             "name": "测试1",
     *             "info1": "",
     *             "info5": "",
     *             "info4": "",
     *             "info3": "",
     *             "info2": ""
     *         }
     *     ]
     * }
     */
    @PostMapping("/findByLike")
    public JsonResult findByLike(String name,String number,String department){
       String s = "%" + name + "%";
       String s1 = "%" + number + "%";
       String s2 = "%" + department + "%";
        List<UserInfo> userInfos  =userInfoService.findByNameOrNumberOrDepartmentLike(s,s1,s2);
        JSONArray pageArray = new JSONArray();
        for (UserInfo userInfo : userInfos) {
            JSONObject json = new JSONObject(JsonUtils.getJson(userInfo));
            pageArray.add(json);
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {get} /userInfo/listSex 党建组织图形比例
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 党建组织图形比例
     * @apiParamExample {json} 请求样例
     *                /userInfo/listSex
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "education": {
     *                 "doctor": 1,
     *                 "specialty": 0,
     *                 "postgraduate": 0,
     *                 "highSchool": 0,
     *                 "undergraduate": 1
     *             },
     *             "identity": {
     *                 "cadre": 1,
     *                 "masses": 0
     *             },
     *             "sex": {
     *                 "woman": 0,
     *                 "man": 2
     *             },
     *             "partyAge": {
     *                 "2年以下": 0,
     *                 "5-10年": 1,
     *                 "2-5年": 0,
     *                 "10年以上": 1
     *             },
     *             "place": {
     *                 "NO": 1,
     *                 "yes": 1
     *             },
     *             "department": {
     *                 "eye": 0,
     *                 "chinese": 0,
     *                 "nternal": 1,
     *                 "emergency": 0,
     *                 "surgery": 1
     *             },
     *             "title": {
     *                 "doctor": 0,
     *                 "attendingDoctor": 1,
     *                 "deputyChiefPhysician": 1,
     *                 "chiefPhysician": 0,
     *                 "residents": 0
     *             },
     *             "branch": {
     *                 "ONE": 1,
     *                 "four": 0,
     *                 "two": 1,
     *                 "three": 0,
     *                 "five": 0
     *             },
     *             "age": {
     *                 "25-35周岁": 1,
     *                 "25周岁以下": 1,
     *                 "45周岁以上": 0,
     *                 "35-45以下": 0
     *             }
     *         }
     *     ]
     * }
     */
    @GetMapping("/listSex")
    public JsonResult listSex(){
        Map<String,Object> pages = userInfoService.countBySex();
        Map<String,Object> pages1 = userInfoService.countByEducation();
        Map<String,Object> pages2 = userInfoService.countByIdcard();
        Map<String,Object> pages3 = userInfoService.countByDepartment();
        Map<String,Object> pages4 = userInfoService.countByPartyAge();
        Map<String,Object> pages5 = userInfoService.countByPlace();
        Map<String,Object> pages6 = userInfoService.countByTitle();
        Map<String,Object> pages7 = userInfoService.countByBranch();
        Map<String,Object> pages8 = userInfoService.countByIdentity();
        JSONArray pageArray = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("sex",pages);
        json.put("education",pages1);
        json.put("age",pages2);
        json.put("department",pages3);
        json.put("partyAge",pages4);
        json.put("place",pages5);
        json.put("title",pages6);
        json.put("branch",pages7);
        json.put("identity",pages8);
        pageArray.add(json);
        jsonResult.setData(pageArray);
        return jsonResult;
    }


}
