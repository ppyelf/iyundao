package com.ayundao.service;

import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;


import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserInfoService
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/12 9:00
 * @Description: 服务层 -- 用户详情
 * @Version: V1.0
 */
public interface UserInfoService {

    /**
     * 人事新增
     * @return
     */
    JsonResult saveAll(String imageids,List<String> fileids,UserInfo userInfo,UserInfoPersonnel userInfoPersonnel,JsonResult jsonResult);

    /**
     * 新增用户附件
     * @param file
     * @return
     */
    UserInfoFile saveFile(UserInfoFile file);

    /**
     * 新增用户图片
     * @param image
     * @return
     */
    UserInfoImage saveImage(UserInfoImage image);
    /**
     * 新增党建信息
     * @param userInfoParty
     * @return
     */
    JsonResult saveParty(UserInfoParty userInfoParty,JsonResult jsonResult);

    /**
     * 新增民主党派信息
     * @param userInfoMzdp
     * @return
     */
    JsonResult saveMzdp(UserInfoMzdp userInfoMzdp,JsonResult jsonResult);

    /**
     * 新增高知群体信息
     * @param userInfoGzqt
     * @return
     */
    JsonResult saveGzqt(UserInfoGzqt userInfoGzqt,JsonResult jsonResult);

    /**
     * 新增工会信息
     * @param userInfoGh
     * @return
     */
    JsonResult saveGh(UserInfoGh userInfoGh,JsonResult jsonResult);

    /**
     * 新增团委信息
     * @param userInfoTw
     * @return
     */
    JsonResult saveTw(UserInfoTw userInfoTw, JsonResult jsonResult);

    /**
     * 新增妇代会信息
     * @param userInfoFdh
     * @return
     */
    JsonResult saveFdh(UserInfoFdh userInfoFdh,JsonResult jsonResult);

    /**
     * 新增离退休老干部信息
     * @param userInfoLtxlgb
     * @return
     */
    JsonResult saveLtxlgb(UserInfoLtxlgb userInfoLtxlgb,JsonResult jsonResult);

    /**
     * 新增基础信息表
     * @param userInfoBasic
     * @return
     */
    JsonResult saveBasic(JsonResult jsonResult,UserInfoBasic userInfoBasic);

    /**
     * 新增合同信息表
     * @param userInfoContract
     * @return
     */
    JsonResult saveContract(JsonResult jsonResult,UserInfoContract userInfoContract);

    /**
     * 新增教育工作表
     * @param userInfoEducationWork
     * @return
     */
    JsonResult saveEducationWork(JsonResult jsonResult,UserInfoEducationWork userInfoEducationWork);

    /**
     * 新增医务护理b表
     * @param userInfoMedicalCare
     * @return
     */
    JsonResult saveMedicalCare(JsonResult jsonResult,UserInfoMedicalCare userInfoMedicalCare);

    /**
     * 新增其他基础表
     * @param userInfoOther
     * @return
     */
    JsonResult saveOther(JsonResult jsonResult,UserInfoOther userInfoOther);

    /**
     * 新增人事信息表
     * @param userInfoPersonnel
     * @return
     */
    JsonResult savePersonnel(JsonResult jsonResult,UserInfoPersonnel userInfoPersonnel);

    /**
     * 新增职务职称表
     * @param userInfoTitleaPost
     * @return
     */
    JsonResult saveTitleaPost(JsonResult jsonResult,UserInfoTitleaPost userInfoTitleaPost);

    /**
     * 新增工作经历表
     * @param userInfoWork
     * @return
     */
    JsonResult saveWork(JsonResult jsonResult,UserInfoWork userInfoWork);

    /**
     * 删除用户详情
     * @param id
     */
    void delete(String id);

    /**
     * 删除用户详情 --个人基础信息
     * @param id
     */
    void deleteBsaic(String id);

    /**
     * 删除用户详情 --个人合同信息
     * @param id
     */
    void deleteContract(String id);

    /**
     * 删除用户详情 --个人教育工作
     * @param id
     */
    void deleteEducationWork(String id);

    /**
     * 删除用户详情 --医务护理表
     * @param id
     */
    void deleteMedicalCare(String id);

    /**
     * 删除用户详情 --其他表
     * @param id
     */
    void deleteOther(String id);

    /**
     * 删除用户详情 --人事信息
     * @param id
     */
    void deletePersonnel(String id);

    /**
     * 删除用户详情 -职称职务
     * @param id
     */
    void deleteTitleaPost(String id);

    /**
     * 删除用户详情
     * @param id
     */
    void deleteWork(String id);

    /**
     * 删除用户详情 -妇代会信息
     * @param id
     */
    void deleteFdh(String id);

    /**
     * 删除用户详情 -工会信息
     * @param id
     */
    void deleteGh(String id);

    /**
     * 删除用户详情 -团委信息
     * @param id
     */
    void deleteTw(String id);

    /**
     * 删除用户详情 -党建信息信息
     * @param id
     */
    void deleteParty(String id);

    /**
     * 删除用户详情 -民主党派信息
     * @param id
     */
    void deleteMzdp(String id);

    /**
     * 删除用户详情 -高知群体信息
     * @param id
     */
    void deleteGzqt(String id);

    /**
     * 删除用户详情 -离退休老干部信息
     * @param id
     */
    void deleteLtxlgb(String id);

    /**
     * 查询单个用户详情
     * @param id
     * @return
     */
    UserInfo findByUserInfoId(String id);

    /**
     * 条件查询
     * @param name
     * @param number
     * @param department
     * @return
     */
    List<UserInfo> findByNameOrNumberOrDepartmentLike(String name,String number,String department);
    /**
     * 查询用户详情
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 查询用户详情 -妇代会
     * @return
     */
    List<UserInfoFdh> findAllByFdh();

    /**
     * 查询用户详情 -工会
     * @return
     */
    List<UserInfoGh> findAllByGh();

    /**
     * 查询用户详情 -党建基础
     * @return
     */
    List<UserInfoParty> findAllByParty(String type);

    /**
     * 查询用户详情 -民主党派
     * @return
     */
    List<UserInfoMzdp> findAllByMzdp();


    /**
     * 查询用户详情 -高知群体
     * @return
     */
    List<UserInfoGzqt> findAllByGzqt();

    /**
     * 查询用户详情 -团委
     * @return
     */
    List<UserInfoTw> findAllByTw();

    /**
     * 查询用户详情 -离退休老干部
     * @return
     */
    List<UserInfoLtxlgb> findAllByLtxlgb();

    /**
     * 查询用户详情 -基本信息表
     * @param userinfoid
     * @return
     */
    UserInfoBasic findByUserInfoBasicUserId(String userinfoid);

    /**
     * 查询用户详情 -附属文件表
     * @param userinfoid
     * @return
     */
    List<UserInfoFile> findByUserInfoFileUserid(String userinfoid);

    /**
     * 查询用户详情 -图片表
     * @param userinfoid
     * @return
     */
    UserInfoImage findByUserInfoImageUserid(String userinfoid);
    /**
     * 查询用户详情 -合同信息表
     * @param userinfoid
     * @return
     */
    List<UserInfoContract> findByUserInfoContractUserid(String userinfoid);

    /**
     * 查询用户详情 -教育工作表
     * @param userinfoid
     * @return
     */
    List<UserInfoEducationWork> findByUserInfoEducationWorkUserid(String userinfoid);

    /**
     * 查询用户详情 -医务护理表
     * @param userinfoid
     * @return
     */
    UserInfoMedicalCare findByUserInfoMedicalCareUserid(String userinfoid);

    /**
     * 查询用户详情 -其他表
     * @param userinfoid
     * @return
     */
    UserInfoOther findByUserInfoOtherUserid(String userinfoid);

    /**
     * 查询用户详情 -人事信息表
     * @param userinfoid
     * @return
     */
    UserInfoPersonnel findByUserInfoPersonnelUserid(String userinfoid);

    /**
     * 查询用户详情 -职称职务表
     * @param userinfoid
     * @return
     */
    UserInfoTitleaPost findByUserInfoTitleaPostUserid(String userinfoid);

    /**
     * 查询用户详情 -工作经历表
     * @param userinfoid
     * @return
     */
    List<UserInfoWork> findByUserInfoWorkUserid(String userinfoid);

    /**
     * 党建男女比例
     * @return
     */
    Map<String,Object> countBySex();

    /**
     * 党建学历比例
     * @return
     */
    Map<String,Object> countByEducation();

    /**
     * 党建人员年龄比例
     * @return
     */
    Map<String,Object> countByIdcard();

    /**
     * 党建科室比例
     * @return
     */
    Map<String,Object> countByDepartment();

    /**
     * 党建党龄比例
     * @return
     */
    Map<String,Object> countByPartyAge();

    /**
     * 党建职称比例
     * @return
     */
    Map<String,Object> countByTitle();

    /**
     * 党建支部比例
     * @return
     */
    Map<String,Object> countByBranch();

    /**
     * 党建籍贯比例
     * @return
     */
    Map<String,Object> countByPlace();

    /**
     * 党建身份比例
     * @return
     */
    Map<String,Object> countByIdentity();


}
