package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * @apiGroup userInfo
     * @apiVersion 1.0.0
     * @apiDescription 新增用户详情
     * @apiParam {json} user: {
     *             {String} "account": "必填",
     *             {String} "name": "必填",
     *             {String} "password": "必填",
     *             {String} "salt": "必填",
     *             {String} "sex": "",
     *             {String} "status": "",
     *             {String} "userType": "",
     *             {String} "remark": "",
     *             {String} "userRelations": "",
     *             {String} "userGroupRelations": "",
     *             {String} "userRoles": "",
     *             {String} "departs": "",
     *             {String} "groups": ""
     *              },
     *       		userInfo:{
     *               {String} "username":"必填",
     *               {String} "password":"必填",
     *               {String} "number":"必填",
     *               {String} "name":"必填",
     *               {String} "branchName":"必填",
     *               {String} "sex":"必填",
     *               {String} "department":"必填",
     *               {String} "birthday":"必填",
     *               {String} "education":"必填",
     *               {String} "place":"必填",
     *               {String} "nation":"必填",
     *               {String} "post":"必填",
     *               {String} "title":"",
     *               {String} "idEntity":"",
     *               {String} "workDate":"",
     *               {String} "partyDate":"",
     *               {String} "correctionDate":"",
     *               {String} "phone":"",
     *               {String} "idcard":"",
     *               {String} "userid":""
     *       		},
     *       		userInfoPersonnel:{
     *       		{String} "workyear":"12154",
     *       		{String} "workmonth":"",
     *       		{String} "partypost":"",
     *       		{String} "servingdate":"",
     *       		{String} "otherpost":"",
     *       		{String} "jianpingpost":"",
     *       		{String} "jianpingdate":"",
     *       		{String} "politicalappearance":"",
     *       		{String} "partydate":"",
     *       		{String} "branchname":"",
     *       		{String} "typeworker":"",
     *       		{String} "gradeworker":"",
     *       		{String} "appointmenttime":"",
     *       		{String} "maritalstatus":"",
     *       		{String} "hukounature":"",
     *       		{String} "hukouwhere":"",
     *       		{String} "beforecompany":"",
     *       		{String} "reserveleavedate":"",
     *       		{String} "firstcontractdate":"",
     *       		{String} "familyaddr":"",
     *       		{String} "personneltype":"",
     *       		{String} "fanpinenddate":""
     *       		}
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
     *         "userInfoPersonnel": {
     *             "id": "297e47e36b8cbecd016b8cbf25020002",
     *             "workyear": "3",
     *             "workmonth": "36",
     *             "partypost": "主席",
     *             "servingdate": "2008-11-11",
     *             "otherpost": "无",
     *             "jianpingpost": "主治医生",
     *             "jianpingdate": "2011-11-11",
     *             "politicalappearance": "党员",
     *             "partydate": "2004-11-11",
     *             "branchname": "第一支部",
     *             "typeworker": "",
     *             "gradeworker": "",
     *             "appointmenttime": "2005-11-11",
     *             "maritalstatus": "已婚",
     *             "hukounature": "农村户口",
     *             "hukouwhere": "浙江杭州",
     *             "beforecompany": "无",
     *             "reserveleavedate": "无",
     *             "firstcontractdate": "2005-11-11",
     *             "familyaddr": "浙江杭州",
     *             "personneltype": "正式员工",
     *             "fanpinenddate": "无",
     *             "userinfoid": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         },
     *         "user": {
     *             "id": "297e47e36b8cbecd016b8cbf239b0000",
     *             "account": "admin",
     *             "name": "测试1",
     *             "password": "123456",
     *             "salt": "123456",
     *             "sex": 0,
     *             "status": null,
     *             "userType": null,
     *             "remark": null,
     *             "userRelations": null,
     *             "userGroupRelations": null,
     *             "userRoles": null,
     *             "departs": null,
     *             "groups": null,
     *             "new": false
     *         },
     *         "userinfo": {
     *             "id": "297e47e36b8cbecd016b8cbf24ec0001",
     *             "number": "002",
     *             "name": "测试1",
     *             "branchName": "第一支部",
     *             "sex": "男",
     *             "department": "内科",
     *             "birthday": "1984-11-11",
     *             "education": "本科",
     *             "place": "浙江杭州",
     *             "nation": "汉族",
     *             "post": "院长",
     *             "title": "主治医生",
     *             "idEntity": "",
     *             "workDate": "2004-11-11",
     *             "partyDate": "2004-11-11",
     *             "correctionDate": "2007-11-11",
     *             "phone": "19822222222",
     *             "idcard": "315247198411111811",
     *             "userid": "297e47e36b8cbecd016b8cbf239b0000",
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
        Map<String,Object> object = (Map<String,Object>)map.get("user");
        User user = JSON.parseObject(JSON.toJSONString(object), User.class);
        user.setCreatedDate(new Date());
        user.setLastModifiedDate(new Date());
        Map<String,Object> object1 = (Map<String,Object>)map.get("userInfo");
        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(object1), UserInfo.class);
        userInfo.setCreatedDate(new Date());
        userInfo.setLastModifiedDate(new Date());
        Map<String,Object> object2 = (Map<String,Object>)map.get("userInfoPersonnel");
        UserInfoPersonnel userInfoPersonnel = JSON.parseObject(JSON.toJSONString(object2), UserInfoPersonnel.class);
        userInfoPersonnel.setCreatedDate(new Date());
        userInfoPersonnel.setLastModifiedDate(new Date());
        return userInfoService.saveAll(user,userInfo,userInfoPersonnel, jsonResult);
    }

    /**
     * @api {POST} /userInfo/upload_file 上传文件
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 上传文件
     * @apiParam {String} name 必填
     * @apiParam {String} url 必填
     * @apiParam {String} format 必填
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
    public JsonResult uploadFile(String name,String url,
                                 String format,String id){
        if(StringUtils.isBlank(name) || StringUtils.isBlank(url) || StringUtils.isBlank(format)){
            return  JsonResult.failure(601,"名称、路径或后缀名不能为空");
        }
        UserInfoFile file = new UserInfoFile();
        file.setCreatedDate(new Date());
        file.setLastModifiedDate(new Date());
        file.setName(name);
        file.setUrl(url);
        file.setFormat(format);
        file.setUserinfoid(id);
        return userInfoService.saveFile(file,jsonResult);
    }

    /**
     * @api {POST} /userInfo/upload_image 上传图片
     * @apiGroup UserInfoImage
     * @apiVersion 1.0.0
     * @apiDescription 上传图片
     * @apiParam {String} name 必填
     * @apiParam {String} url 必填
     * @apiParam {String} format 必填
     * @apiParam {String} userInfoId 必填
     * @apiParamExample {json} 请求样例:
     *                ?name=&url=&format=&id=
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "userInfoImage": {
     *             "id": "297e47e36b8d653c016b8d6eb24f0005",
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
    @PostMapping("/upload_image")
    public JsonResult uploadImage(String name,String url,
                                  String format,String id) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(url) || StringUtils.isBlank(format)) {
            return JsonResult.failure(601, "名称、路径或后缀名不能为空");
        }
        UserInfoImage image = new UserInfoImage();
        image.setCreatedDate(new Date());
        image.setLastModifiedDate(new Date());
        image.setName(name);
        image.setUrl(url);
        image.setFormat(format);
        image.setUserinfoid(id);
        return userInfoService.saveImage(image,jsonResult);
    }

    /**
     * @api {post} /userInfo/add_basic 新增用户基础信息表
     * @apiGroup userInfoBasic
     * @apiVersion 1.0.0
     * @apiDescription 新增用户基础信息表
     * @apiParam {JSON} userInfoBasic:{
     *         {String} "workerdate":"",
     *         {String} "zhuanzhengdate":"",
     *         {String} "wagesdate":"",
     *         {String} "arrivedate":"",
     *         {String} "workersnature":"",
     *         {String} "workerscategory":"",
     *         {String} "postcategory":"",
     *         {String} "checkgroup":"",
     *         {String} "organization":"",
     *         {String} "increasemode":"",
     *         {String} "othernumber":"",
     *         {String} "age":"",
     *         {String} "userinfoid":""
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
        userInfoService.saveBasic(userInfoBasic);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_contract 新增用户合同信息表
     * @apiGroup UserInfoContract
     * @apiVersion 1.0.0
     * @apiDescription 新增用户合同信息表
     * @apiParam {JSON} UserInfoContract:{
     *         {String} "archivesmanagementunit":"",
     *         {String} "contracttype":"",
     *         {String} "contractstarttime":"",
     *         {String} "contractendtime":"",
     *         {String} "signaturetype":"",
     *         {String} "contractperiod":"",
     *         {String} "frequency":"",
     *         {String} "registrationtime":"",
     *         {String} "userinfoid":""
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
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_contract")
    public JsonResult add_contract(String archivesmanagementunit,String contracttype,String contractstarttime,
                                   String contractendtime,String signaturetype,String contractperiod,
                                   String frequency,String registrationtime,String userinfoid){
        UserInfoContract userInfoContract = new UserInfoContract();
        userInfoContract.setCreatedDate(new Date());
        userInfoContract.setLastModifiedDate(new Date());
        userInfoContract.setArchivesmanagementunit(archivesmanagementunit);
        userInfoContract.setContracttype(contracttype);
        userInfoContract.setContractstarttime(contractstarttime);
        userInfoContract.setContractendtime(contractendtime);
        userInfoContract.setSignaturetype(signaturetype);
        userInfoContract.setContractperiod(contractperiod);
        userInfoContract.setFrequency(frequency);
        userInfoContract.setRegistrationtime(registrationtime);
        userInfoContract.setUserinfoid(userinfoid);
        userInfoService.saveContract(userInfoContract);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_education_work 新增用户教育工作表
     * @apiGroup UserInfoEducationWork
     * @apiVersion 1.0.0
     * @apiDescription 新增用户教育工作表
     * @apiParam {JSON} userInfoEducationWork:{
     *         {String} "startdate":"",
     *         {String} "enddate":"",
     *         {String} "graduationschool":"",
     *         {String} "major":"",
     *         {String} "educationcategory":"",
     *         {String} "education":"",
     *         {String} "degree":"",
     *         {String} "degreedate":"",
     *         {String} "edusystem":"",
     *         {String} "witness":"",
     *         {String} "userinfoid":""
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
     *     "data": "{\"version\":\"0\",\"id\":\"402881f46afdef14016afdf286170001\",\"createdDate\":\"20190528181810\",\"lastModifiedDate\":\"20190528181810\",\"name\":\"测试用户组2\",\"user\":\"\",\"father\":\"\"}"
     * }
     */
    @PostMapping("/add_education_work")
    public JsonResult add_education_work(String startdate,String enddate,String graduationschool,
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
        userInfoService.saveEducationWork(userInfoEducationWork);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_medical_care 新增用户医务护理表
     * @apiGroup UserInfoMedicalCare
     * @apiVersion 1.0.0
     * @apiDescription 新增用户医务护理表
     * @apiParam {JSON} UserInfoMedicalCare:{
     *         {String} "parcticelevel":"",
     *         {String} "practicenumber":"",
     *         {String} "parcticebookobtaindate":"",
     *         {String} "technologynumber":"",
     *         {String} "technologybookobtaindate":"",
     *         {String} "nurseshoesize":"",
     *         {String} "practicerange":"",
     *         {String} "registrationtime":"",
     *         {String} "practiceyears":"",
     *         {String} "practicecategory":"",
     *         {String} "interruptpractice":"",
     *         {String} "userinfoid":""
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
        userInfoService.saveMedicalCare(userInfoMedicalCare);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_other 新增用户其他信息表
     * @apiGroup UserInfoOther
     * @apiVersion 1.0.0
     * @apiDescription 新增用户其他信息表
     * @apiParam {JSON} UserInfoOther{
     *         {String} "whethersign":"",
     *         {String} "whethercheck":"",
     *         {String} "state":"",
     *         {String} "leavedate":"",
     *         {String} "cancellation":"",
     *         {String} "userinfoid":""
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
        userInfoService.saveOther(userInfoOther);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_personnel 新增用户人事信息表
     * @apiGroup UserInfoPersonnel
     * @apiVersion 1.0.0
     * @apiDescription 新增用户人事信息表
     * @apiParam {JSON} UserInfoPersonnel:{
     *         {String} "workyear":"",
     *         {String} "workmonth":"",
     *         {String} "partypost":"",
     *         {String} "servingdate":"",
     *         {String} "otherpost":"",
     *         {String} "jianpingpost":"",
     *         {String} "jianpingdate":"",
     *         {String} "politicalappearance":"",
     *         {String} "partydate":"",
     *         {String} "branchname":"",
     *         {String} "typeworker":"",
     *         {String} "gradeworker":"",
     *         {String} "appointmenttime":"",
     *         {String} "maritalstatus":"",
     *         {String} "hukounature":"",
     *         {String} "hukouwhere":"",
     *         {String} "beforecompany":"",
     *         {String} "reserveleavedate":"",
     *         {String} "firstcontractdate":"",
     *         {String} "familyaddr":"",
     *         {String} "personneltype":"",
     *         {String} "fanpinenddate":"",
     *         {String} "userinfoid":""
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
        userInfoService.savePersonnel(userInfoPersonnel);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_title_apost 新增用户职称职务表
     * @apiGroup UserInfoTitleaPost
     * @apiVersion 1.0.0
     * @apiDescription 新增用户职称职务表
     * @apiParam {JSON} UserInfoTitleaPost:{
     *         {String} "postname":"",
     *         {String} "administrationpost":"",
     *         {String} "servingyears":"",
     *         {String} "administrationlevel":"",
     *         {String} "servingdate":"",
     *         {String} "servingstopdate":"",
     *         {String} "beforeadministrationpost":"",
     *         {String} "beforeservingdate":"",
     *         {String} "beforeservingstopdate":"",
     *         {String} "qualificationtitle":"",
     *         {String} "qualificationgetdate":"",
     *         {String} "engagetitle":"",
     *         {String} "engagetitledate":"",
     *         {String} "engagetime":"",
     *         {String} "beforequalificationtitle":"",
     *         {String} "beforequalificationgetdate":"",
     *         {String} "beforeengagetitle":"",
     *         {String} "beforeengagetitledate":"",
     *         {String} "beforeengagetime":"",
     *         {String} "beforeengagestoptime":"",
     *         {String} "technicallevel":"",
     *         {String} "personnelcategory":"",
     *         {String} "weijicategory":"",
     *         {String} "userinfoid":""
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
        userInfoService.saveTitleaPost(userInfoTitleaPost);
        return jsonResult;
    }

    /**
     * @api {post} /userInfo/add_work 新增用户工作经历表
     * @apiGroup UserInfoWork
     * @apiVersion 1.0.0
     * @apiDescription 新增用户工作经历表
     * @apiParam {JSON} UserInfoWork:{
     *         {String} "startdate":"",
     *         {String} "enddate":"",
     *         {String} "workunit":"",
     *         {String} "toservepost":"",
     *         {String} "posttitle":"",
     *         {String} "witness":"",
     *         {String} "userinfoid":""
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
        userInfoService.saveWork(userInfoWork);
        return jsonResult;
    }



    /**
     * @api {get} /userInfo/del 删除用户详情
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
    @GetMapping("/del")
    public JsonResult del(String id) {
        if (StringUtils.isBlank(id)) {
            return JsonResult.paramError();
        }
        userInfoService.delete(id);
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
     * @api {get} /userInfo/list 用户男女数
     * @apiGroup UserInfo
     * @apiVersion 1.0.0
     * @apiDescription 用户男女数
     * @apiParamExample {json} 请求样例
     *                /userInfo/listSex
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {{
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *         {
     *             "woman": 0,
     *             "man": 1
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
        JSONArray pageArray = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("sex",pages);
        json.put("education",pages1);
        json.put("age",pages2);
        json.put("department",pages3);
        pageArray.add(json);
        jsonResult.setData(pageArray);
        return jsonResult;
    }


}
