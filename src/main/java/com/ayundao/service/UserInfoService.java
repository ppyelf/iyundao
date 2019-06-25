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
    JsonResult saveAll(User user,UserInfo userInfo,UserInfoPersonnel userInfoPersonnel,JsonResult jsonResult);

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
     * @param party
     * @return
     */
    UserInfoParty saveParty(UserInfoParty party);

    /**
     * 新增民主党派信息
     * @param userInfoMzdp
     * @return
     */
    UserInfoMzdp saveMzdp(UserInfoMzdp userInfoMzdp);

    /**
     * 新增高知群体信息
     * @param userInfoGzqt
     * @return
     */
    UserInfoGzqt saveGzqt(UserInfoGzqt userInfoGzqt);

    /**
     * 新增工会信息
     * @param userInfoGh
     * @return
     */
    UserInfoGh saveGh(UserInfoGh userInfoGh);

    /**
     * 新增团委信息
     * @param userInfoTw
     * @return
     */
    UserInfoTw saveTw(UserInfoTw userInfoTw);

    /**
     * 新增妇代会信息
     * @param userInfoFdh
     * @return
     */
    UserInfoFdh saveFdh(UserInfoFdh userInfoFdh);

    /**
     * 新增离退休老干部信息
     * @param userInfoLtxlgb
     * @return
     */
    UserInfoLtxlgb saveLtxlgb(UserInfoLtxlgb userInfoLtxlgb);

    /**
     * 新增基础信息表
     * @param userInfoBasic
     * @return
     */
    UserInfoBasic saveBasic(UserInfoBasic userInfoBasic);

    /**
     * 新增合同信息表
     * @param userInfoContract
     * @return
     */
    UserInfoContract saveContract(UserInfoContract userInfoContract);

    /**
     * 新增教育工作表
     * @param userInfoEducationWork
     * @return
     */
    UserInfoEducationWork saveEducationWork(UserInfoEducationWork userInfoEducationWork);

    /**
     * 新增医务护理b表
     * @param userInfoMedicalCare
     * @return
     */
    UserInfoMedicalCare saveMedicalCare(UserInfoMedicalCare userInfoMedicalCare);

    /**
     * 新增其他基础表
     * @param userInfoOther
     * @return
     */
    UserInfoOther saveOther(UserInfoOther userInfoOther);

    /**
     * 新增人事信息表
     * @param userInfoPersonnel
     * @return
     */
    UserInfoPersonnel savePersonnel(UserInfoPersonnel userInfoPersonnel);

    /**
     * 新增职务职称表
     * @param userInfoTitleaPost
     * @return
     */
    UserInfoTitleaPost saveTitleaPost(UserInfoTitleaPost userInfoTitleaPost);

    /**
     * 新增工作经历表
     * @param userInfoWork
     * @return
     */
    UserInfoWork saveWork(UserInfoWork userInfoWork);

    /**
     * 删除用户详情
     * @param id
     */
    void delete(String id);

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
    List<UserInfoParty> findAllByParty();

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

    Map<String,Integer> countBySex();


}
