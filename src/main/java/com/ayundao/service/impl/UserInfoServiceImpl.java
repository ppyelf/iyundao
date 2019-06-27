package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName: UserInfoServiceImpl
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 服务实现 - 用户详情
 * @Version: V1.0
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoPartyRepository userInfoPartyRepository;

    @Autowired
    private UserInfoFdhRepository userInfoFdhRepository;

    @Autowired
    private UserInfoFileRepository userInfoFileRepository;

    @Autowired
    private UserInfoGhRepository userInfoGhRepository;

    @Autowired
    private UserInfoGzqtRepository userInfoGzqtRepository;

    @Autowired
    private UserInfoImageRepository userInfoImageRepository;

    @Autowired
    private UserInfoLtxlgbRepository userInfoLtxlgbRepository;

    @Autowired
    private UserInfoMzdpRepository userInfoMzdpRepository;

    @Autowired
    private UserInfoTwRepository userInfoTwRepository;

    @Autowired
    private UserInfoBasicRepository userInfoBasicRepository;

    @Autowired
    private UserInfoContractRepository userInfoContractRepository;

    @Autowired
    private UserInfoEducationWorkRepository userInfoEducationWorkRepository;

    @Autowired
    private UserInfoMedicalCareRepository userInfoMedicalCareRepository;

    @Autowired
    private UserInfoOtherRepository userInfoOtherRepository;

    @Autowired
    private UserInfoPersonnelRepository userInfoPersonnelRepository;

    @Autowired
    private UserInfoTitleaPostRepository userInfoTitleaPostRepository;

    @Autowired
    private UserInfoWorkRepository userInfoWorkRepository;


    @Override
    @Transactional
    public JsonResult saveAll(User user,UserInfo userInfo, UserInfoPersonnel userInfoPersonnel, JsonResult jsonResult) {
        user = userRepository.save(user);
        userInfo.setUserid(user.getId());
        userInfo = userInfoRepository.save(userInfo);
        userInfoPersonnel.setUserinfoid(userInfo.getId());
        userInfoPersonnel = userInfoPersonnelRepository.save(userInfoPersonnel);
        JSONObject json = new JSONObject();
        json.put("user",user);
        json.put("userinfo", userInfo);
        json.put("userInfoPersonnel",userInfoPersonnel);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveFile(UserInfoFile file,JsonResult jsonResult) {
        file = userInfoFileRepository.save(file);
        JSONObject json = new JSONObject();
        json.put("userInfoFile",file);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveImage(UserInfoImage image,JsonResult jsonResult) {
        image = userInfoImageRepository.save(image);
        JSONObject json = new JSONObject();
        json.put("userInfoImage",image);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    @Transactional
    public JsonResult saveParty(UserInfoParty userInfoParty,JsonResult jsonResult) {
        userInfoParty = userInfoPartyRepository.save(userInfoParty);
        JSONObject json = new JSONObject();
        json.put("userInfoParty",userInfoParty);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveMzdp(UserInfoMzdp userInfoMzdp,JsonResult jsonResult) {
        userInfoMzdp = userInfoMzdpRepository.save(userInfoMzdp);
        JSONObject json =new JSONObject();
        json.put("userInfoMzdp",userInfoMzdp);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveGzqt(UserInfoGzqt userInfoGzqt,JsonResult jsonResult) {
        userInfoGzqt = userInfoGzqtRepository.save(userInfoGzqt);
        JSONObject json =new JSONObject();
        json.put("userInfoGzqt",userInfoGzqt);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveGh(UserInfoGh userInfoGh,JsonResult jsonResult) {
        userInfoGh = userInfoGhRepository.save(userInfoGh);
        JSONObject json =new JSONObject();
        json.put("userInfoGh",userInfoGh);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveTw(UserInfoTw userInfoTw,JsonResult jsonResult) {
        userInfoTw = userInfoTwRepository.save(userInfoTw);
        JSONObject json =new JSONObject();
        json.put("userInfoTw",userInfoTw);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    @Transactional
    public JsonResult saveFdh(UserInfoFdh userInfoFdh,JsonResult jsonResult) {
        userInfoFdh = userInfoFdhRepository.save(userInfoFdh);
        JSONObject json =new JSONObject();
        json.put("userInfoFdh",userInfoFdh);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveLtxlgb(UserInfoLtxlgb userInfoLtxlgb,JsonResult jsonResult) {
        userInfoLtxlgb = userInfoLtxlgbRepository.save(userInfoLtxlgb);
        JSONObject json =new JSONObject();
        json.put("userInfoLtxlgb",userInfoLtxlgb);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public UserInfoBasic saveBasic(UserInfoBasic userInfoBasic) {
        return userInfoBasicRepository.save(userInfoBasic);
    }

    @Override
    public UserInfoContract saveContract(UserInfoContract userInfoContract) {
        return userInfoContractRepository.save(userInfoContract);
    }

    @Override
    public UserInfoEducationWork saveEducationWork(UserInfoEducationWork userInfoEducationWork) {
        return userInfoEducationWorkRepository.save(userInfoEducationWork);
    }

    @Override
    public UserInfoMedicalCare saveMedicalCare(UserInfoMedicalCare userInfoMedicalCare) {
        return userInfoMedicalCareRepository.save(userInfoMedicalCare);
    }

    @Override
    public UserInfoOther saveOther(UserInfoOther userInfoOther) {
        return userInfoOtherRepository.save(userInfoOther);
    }

    @Override
    public UserInfoPersonnel savePersonnel(UserInfoPersonnel userInfoPersonnel) {
        return null;
    }

    @Override
    public UserInfoTitleaPost saveTitleaPost(UserInfoTitleaPost userInfoTitleaPost) {
        return userInfoTitleaPostRepository.save(userInfoTitleaPost);
    }

    @Override
    public UserInfoWork saveWork(UserInfoWork userInfoWork) {
        return userInfoWorkRepository.save(userInfoWork);
    }

    /**
     * 删除用户详情
     * @param id
     */
    @Override
    public void delete(String id) {
        UserInfo userInfo = userInfoRepository.findByUserInfoId(id);
        userInfoRepository.delete(userInfo);
        UserInfoParty userInfoParty = userInfoPartyRepository.findByUserInfoPartyUserid(id);
        if(userInfoParty != null){
            userInfoPartyRepository.delete(userInfoParty);
        }
        UserInfoMzdp userInfoMzdp = userInfoMzdpRepository.findByUserInfoMzdpUserid(id);
        if(userInfoMzdp != null){
            userInfoMzdpRepository.delete(userInfoMzdp);
        }
        UserInfoGzqt userInfoGzqt = userInfoGzqtRepository.findByUserInfoGzqtUserid(id);
        if(userInfoGzqt !=null){
            userInfoGzqtRepository.delete(userInfoGzqt);
        }
        UserInfoGh userInfoGh = userInfoGhRepository.findByUserInfoGhUserid(id);
        if (userInfoGh != null){
            userInfoGhRepository.delete(userInfoGh);
        }
        UserInfoFile userInfoFile = userInfoFileRepository.findByUserInfoFileUserid(id);
        if(userInfoFile != null){
            userInfoFileRepository.delete(userInfoFile);
        }
        UserInfoImage userInfoImage = userInfoImageRepository.findByUserInfoImageUserid(id);
        if(userInfoImage != null){
            userInfoImageRepository.delete(userInfoImage);
        }
        UserInfoTw userInfoTw = userInfoTwRepository.findByUserInfoTwUserid(id);
        if(userInfoTw != null){
            userInfoTwRepository.delete(userInfoTw);
        }
        UserInfoFdh userInfoFdh = userInfoFdhRepository.findByUserInfoFdhUserid(id);
        if(userInfoFdh != null){
            userInfoFdhRepository.delete(userInfoFdh);
        }
        UserInfoLtxlgb userInfoLtxlgb = userInfoLtxlgbRepository.findByUserInfoLtxlgbUserid(id);
        if(userInfoLtxlgb != null){
            userInfoLtxlgbRepository.delete(userInfoLtxlgb);
        }
        UserInfoBasic userInfoBasic = userInfoBasicRepository.findByUserInfoBasicUserId(id);
        if(userInfoBasic != null){
            userInfoBasicRepository.delete(userInfoBasic);
        }
        UserInfoWork userInfoWork = userInfoWorkRepository.findByUserInfoWorkUserid(id);
        if(userInfoWork != null){
            userInfoWorkRepository.delete(userInfoWork);
        }
        UserInfoOther userInfoOther = userInfoOtherRepository.findByUserInfoOtherUserid(id);
        if(userInfoOther != null){
            userInfoOtherRepository.delete(userInfoOther);
        }
        UserInfoPersonnel userInfoPersonnel = userInfoPersonnelRepository.findByUserInfoPersonnelUserid(id);
        if(userInfoPersonnel != null){
            userInfoPersonnelRepository.delete(userInfoPersonnel);
        }
        UserInfoTitleaPost userInfoTitleaPost = userInfoTitleaPostRepository.findByUserInfoTitleaPostUserid(id);
        if(userInfoTitleaPost != null){
            userInfoTitleaPostRepository.delete(userInfoTitleaPost);
        }
        UserInfoMedicalCare userInfoMedicalCare = userInfoMedicalCareRepository.findByUserInfoMedicalCareUserid(id);
        if(userInfoMedicalCare != null){
            userInfoMedicalCareRepository.delete(userInfoMedicalCare);
        }
        UserInfoEducationWork userInfoEducationWork = userInfoEducationWorkRepository.findByUserInfoEducationWorkUserid(id);
        if(userInfoEducationWork != null){
            userInfoEducationWorkRepository.delete(userInfoEducationWork);
        }
        UserInfoContract userInfoContract = userInfoContractRepository.findByUserInfoContractUserid(id);
        if(userInfoContract != null){
            userInfoContractRepository.delete(userInfoContract);
        }

    }

    /**
     * 删除用户详情 -妇代会信息
     * @param id
     */
    @Override
    public void deleteFdh(String id) {
        UserInfoFdh userInfoFdh = userInfoFdhRepository.find(id);
        if(userInfoFdh != null){
            userInfoFdhRepository.delete(userInfoFdh);
        }
    }

    /**
     * 删除用户详情 -工会信息
     * @param id
     */
    @Override
    public void deleteGh(String id) {
        UserInfoGh userInfoGh = userInfoGhRepository.find(id);
        if(userInfoGh != null){
            userInfoGhRepository.delete(userInfoGh);
        }
    }

    /**
     * 删除用户详情 -团委信息
     * @param id
     */
    @Override
    public void deleteTw(String id) {
        UserInfoTw userInfoTw = userInfoTwRepository.find(id);
        if(userInfoTw != null){
            userInfoTwRepository.delete(userInfoTw);
        }
    }

    /**
     * 删除用户详情 -党建信息
     * @param id
     */
    @Override
    public void deleteParty(String id) {
        UserInfoParty userInfoParty = userInfoPartyRepository.find(id);
        if(userInfoParty != null){
            userInfoPartyRepository.delete(userInfoParty);
        }
    }

    /**
     * 删除用户详情 -民主党派信息
     * @param id
     */
    @Override
    public void deleteMzdp(String id) {
        UserInfoMzdp userInfoMzdp = userInfoMzdpRepository.find(id);
        if(userInfoMzdp != null){
            userInfoMzdpRepository.delete(userInfoMzdp);
        }
    }

    /**
     * 删除用户详情 -高知群体信息
     * @param id
     */
    @Override
    public void deleteGzqt(String id) {
        UserInfoGzqt userInfoGzqt = userInfoGzqtRepository.find(id);
        if(userInfoGzqt != null){
            userInfoGzqtRepository.delete(userInfoGzqt);
        }
    }

    /**
     * 删除用户详情 -离退休老干部信息
     * @param id
     */
    @Override
    public void deleteLtxlgb(String id) {
        UserInfoLtxlgb userInfoLtxlgb = userInfoLtxlgbRepository.find(id);
        if(userInfoLtxlgb != null){
            userInfoLtxlgbRepository.delete(userInfoLtxlgb);
        }
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public List<UserInfoFdh> findAllByFdh() {
        return userInfoFdhRepository.findAll();
    }

    @Override
    public List<UserInfoGh> findAllByGh() {
        return userInfoGhRepository.findAll();
    }

    @Override
    public List<UserInfoParty> findAllByParty() {
        return userInfoPartyRepository.findAll();
    }

    @Override
    public List<UserInfoMzdp> findAllByMzdp() {
        return userInfoMzdpRepository.findAll();
    }

    @Override
    public List<UserInfoGzqt> findAllByGzqt() {
        return userInfoGzqtRepository.findAll();
    }

    @Override
    public List<UserInfoTw> findAllByTw() {
        return userInfoTwRepository.findAll();
    }

    @Override
    public List<UserInfoLtxlgb> findAllByLtxlgb() {
        return userInfoLtxlgbRepository.findAll();
    }

    @Override
    public Map<String,Object> countBySex() {
        return userInfoRepository.countBySex();
    }

    @Override
    public Map<String, Object> countByEducation() {
        return userInfoRepository.countByEducation();
    }

    @Override
    public Map<String, Object> countByIdcard() {
        return userInfoRepository.countByIdcard();
    }

    @Override
    public Map<String,Object> countByDepartment(){
        return userInfoRepository.countByDepartment();
    }

}
