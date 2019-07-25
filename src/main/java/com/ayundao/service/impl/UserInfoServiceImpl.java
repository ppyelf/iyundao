package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
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

    @Autowired
    private SignRepository signRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    @Transactional
    public JsonResult saveAll(String imageids,List<String> fileids,UserInfo userInfo, UserInfoPersonnel userInfoPersonnel, JsonResult jsonResult) {
        userInfo = userInfoRepository.save(userInfo);
        userInfoPersonnel.setUserinfoid(userInfo.getId());
        userInfoPersonnel = userInfoPersonnelRepository.save(userInfoPersonnel);
        JSONObject json = new JSONObject();
        for (String fileid : fileids) {
            UserInfoFile userInfoFile = userInfoFileRepository.find(fileid);
            if(userInfoFile != null){
                userInfoFile.setUserinfoid(userInfo.getId());
                userInfoFile = userInfoFileRepository.save(userInfoFile);
            }
            json.put("userInfoFile",userInfoFile);
        }
        UserInfoImage userInfoImage = userInfoImageRepository.find(imageids);
        if(userInfoImage != null){
            userInfoImage.setUserinfoid(userInfo.getId());
            userInfoImage = userInfoImageRepository.save(userInfoImage);
            json.put("userInfoImage",userInfoImage);
        }

        json.put("userinfo", userInfo);
        json.put("userInfoPersonnel",userInfoPersonnel);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public    UserInfoFile saveFile(UserInfoFile file) {
        return userInfoFileRepository.save(file);
    }

    @Override
    @Transactional
    public UserInfoImage saveImage(UserInfoImage image) {
        return userInfoImageRepository.save(image);
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
    public JsonResult saveBasic(JsonResult jsonResult,UserInfoBasic userInfoBasic) {
        userInfoBasic = userInfoBasicRepository.save(userInfoBasic);
        JSONObject json =new JSONObject();
        json.put("userInfoBasic",userInfoBasic);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveContract(JsonResult jsonResult,UserInfoContract userInfoContract) {
        userInfoContract = userInfoContractRepository.save(userInfoContract);
        JSONObject json =new JSONObject();
        json.put("userInfoContract",userInfoContract);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveEducationWork(JsonResult jsonResult,UserInfoEducationWork userInfoEducationWork) {
        userInfoEducationWork = userInfoEducationWorkRepository.save(userInfoEducationWork);
        JSONObject json =new JSONObject();
        json.put("userInfoEducationWork",userInfoEducationWork);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveMedicalCare(JsonResult jsonResult,UserInfoMedicalCare userInfoMedicalCare) {
        userInfoMedicalCare = userInfoMedicalCareRepository.save(userInfoMedicalCare);
        JSONObject json =new JSONObject();
        json.put("userInfoMedicalCare",userInfoMedicalCare);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveOther(JsonResult jsonResult,UserInfoOther userInfoOther) {
        userInfoOther = userInfoOtherRepository.save(userInfoOther);
        JSONObject json =new JSONObject();
        json.put("userInfoOther",userInfoOther);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult savePersonnel(JsonResult jsonResult,UserInfoPersonnel userInfoPersonnel) {
        userInfoPersonnel = userInfoPersonnelRepository.save(userInfoPersonnel);
        JSONObject json =new JSONObject();
        json.put("userInfoPersonnel",userInfoPersonnel);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveTitleaPost(JsonResult jsonResult,UserInfoTitleaPost userInfoTitleaPost) {
        userInfoTitleaPost = userInfoTitleaPostRepository.save(userInfoTitleaPost);
        JSONObject json =new JSONObject();
        json.put("userInfoTitleaPost",userInfoTitleaPost);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    public JsonResult saveWork(JsonResult jsonResult,UserInfoWork userInfoWork) {
        userInfoWork = userInfoWorkRepository.save(userInfoWork);
        JSONObject json =new JSONObject();
        json.put("userInfoWork",userInfoWork);
        jsonResult.setData(json);
        return jsonResult;
    }

    /**
     * 删除用户详情
     * @param id
     */
    @Override
    @Transactional
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
        List<UserInfoFile> userInfoFile = userInfoFileRepository.findByUserInfoFileUserid(id);
        if(userInfoFile != null){
            for (UserInfoFile file : userInfoFile) {
                userInfoFileRepository.delete(file);
            }
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
        List<UserInfoWork> userInfoWork = userInfoWorkRepository.findByUserInfoWorkUserid(id);
        if(userInfoWork != null){
            for (UserInfoWork infoWork : userInfoWork) {
                userInfoWorkRepository.delete(infoWork);
            }
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
        List<UserInfoEducationWork> userInfoEducationWork = userInfoEducationWorkRepository.findByUserInfoEducationWorkUserid(id);
        if(userInfoEducationWork != null){
            for (UserInfoEducationWork infoEducationWork : userInfoEducationWork) {
                userInfoEducationWorkRepository.delete(infoEducationWork);
            }

        }
        List<UserInfoContract> userInfoContract = userInfoContractRepository.findByUserInfoContractUserid(id);
        if(userInfoContract != null){
            for (UserInfoContract infoContract : userInfoContract) {
                userInfoContractRepository.delete(infoContract);
            }
        }

    }

    @Override
    public void deleteBsaic(String id) {
        UserInfoBasic userInfoBasic = userInfoBasicRepository.find(id);
        if(userInfoBasic != null){
            userInfoBasicRepository.delete(userInfoBasic);
        }
    }

    @Override
    public void deleteContract(String id) {
        UserInfoContract userInfoContract = userInfoContractRepository.find(id);
        if(userInfoContract != null){
            userInfoContractRepository.delete(userInfoContract);
        }
    }

    @Override
    public void deleteEducationWork(String id) {
        UserInfoEducationWork userInfoEducationWork = userInfoEducationWorkRepository.find(id);
        if(userInfoEducationWork != null){
                userInfoEducationWorkRepository.delete(userInfoEducationWork);
        }
    }

    @Override
    public void deleteMedicalCare(String id) {
        UserInfoMedicalCare userInfoMedicalCare = userInfoMedicalCareRepository.find(id);
        if(userInfoMedicalCare != null){
            userInfoMedicalCareRepository.delete(userInfoMedicalCare);
        }
    }

    @Override
    public void deleteOther(String id) {
        UserInfoOther userInfoOther = userInfoOtherRepository.find(id);
        if(userInfoOther != null){
            userInfoOtherRepository.delete(userInfoOther);
        }
    }

    @Override
    public void deletePersonnel(String id) {
        UserInfoPersonnel userInfoPersonnel = userInfoPersonnelRepository.find(id);
        if(userInfoPersonnel != null){
            userInfoPersonnelRepository.delete(userInfoPersonnel);
        }
    }

    @Override
    public void deleteTitleaPost(String id) {
        UserInfoTitleaPost userInfoTitleaPost = userInfoTitleaPostRepository.find(id);
        if(userInfoTitleaPost != null){
            userInfoTitleaPostRepository.delete(userInfoTitleaPost);
        }
    }

    @Override
    public void deleteWork(String id) {
        UserInfoWork userInfoWork = userInfoWorkRepository.find(id);
        if(userInfoWork != null){
                userInfoWorkRepository.delete(userInfoWork);
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
    public UserInfo findByUserInfoId(String id) {
        return userInfoRepository.findByUserInfoId(id);
    }

    @Override
    public List<UserInfo> findByNameOrNumberOrDepartmentLike(String name,String number,String department){
        return userInfoRepository.findByNameOrNumberOrDepartmentLike(name,number,department);
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
    public List<UserInfoParty> findAllByParty(String type) {
        for (UserInfoParty.TYPE value : UserInfoParty.TYPE.values()) {
            if (value.getName().equals(type)) {
                return userInfoPartyRepository.findByType(value);
            }
        }
        return null;
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
    public UserInfoBasic findByUserInfoBasicUserId(String userinfoid) {
        return userInfoBasicRepository.findByUserInfoBasicUserId(userinfoid);
    }

    @Override
    public List<UserInfoFile> findByUserInfoFileUserid(String userinfoid) {
        return userInfoFileRepository.findByUserInfoFileUserid(userinfoid);
    }

    @Override
    public UserInfoImage findByUserInfoImageUserid(String userinfoid) {
        return userInfoImageRepository.findByUserInfoImageUserid(userinfoid);
    }

    @Override
    public List<UserInfoContract> findByUserInfoContractUserid(String userinfoid) {
        return userInfoContractRepository.findByUserInfoContractUserid(userinfoid);
    }

    @Override
    public List<UserInfoEducationWork> findByUserInfoEducationWorkUserid(String userinfoid) {
        return userInfoEducationWorkRepository.findByUserInfoEducationWorkUserid(userinfoid);
    }

    @Override
    public UserInfoMedicalCare findByUserInfoMedicalCareUserid(String userinfoid) {
        return userInfoMedicalCareRepository.findByUserInfoMedicalCareUserid(userinfoid);
    }

    @Override
    public UserInfoOther findByUserInfoOtherUserid(String userinfoid) {
        return userInfoOtherRepository.findByUserInfoOtherUserid(userinfoid);
    }

    @Override
    public UserInfoPersonnel findByUserInfoPersonnelUserid(String userinfoid) {
        return userInfoPersonnelRepository.findByUserInfoPersonnelUserid(userinfoid);
    }

    @Override
    public UserInfoTitleaPost findByUserInfoTitleaPostUserid(String userinfoid) {
        return userInfoTitleaPostRepository.findByUserInfoTitleaPostUserid(userinfoid);
    }

    @Override
    public List<UserInfoWork> findByUserInfoWorkUserid(String userinfoid) {
        return userInfoWorkRepository.findByUserInfoWorkUserid(userinfoid);
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

    @Override
    public Map<String, Object> countByPartyAge() {
        return userInfoRepository.countByPartyAge();
    }

    @Override
    public Map<String, Object> countByTitle() {
        return userInfoRepository.countByTitle();
    }

    @Override
    public Map<String, Object> countByBranch() {
        return userInfoRepository.countByBranch();
    }

    @Override
    public Map<String, Object> countByPlace() {
        return userInfoRepository.countByPlace();
    }

    @Override
    public Map<String, Object> countByIdentity() {
        return userInfoRepository.countByIdentity();
    }

    @Override
    public UserInfoWork findWorkById(String userid) {
        return userInfoWorkRepository.findbyUserInfoId(userid);
    }

    @Override
    public UserInfoEducationWork findEducationWorkById(String userid) {
        return userInfoEducationWorkRepository.findByUserInfoId(userid);
    }

    @Override
    public UserInfo findbyUserId(String userid) {
        return userInfoRepository.findByUserId(userid);
    }

    @Override
    public List<Sign> findAllByUserId(String userid) {
        for (Sign.SIGN_TYPE type : Sign.SIGN_TYPE.values()){
            if (type.ordinal() == 0){
                return signRepository.findActivityByUserId(type.ordinal(),userid);
            }
        }
        return  null;
    }

    @Override
    public JSONArray findsocreALL(List<Sign> signs,User user) {
        JSONArray array = new JSONArray();
        JSONObject object;
        for (Sign sign : signs) {
            object = new JSONObject();
            object.put("time",sign.getSignTime());
            object.put("activity",JsonUtils.getJson(sign.getActivity()));
            object.put("getscore",Integer.parseInt(sign.getActivity().getTotal())/sign.getActivity().getNumber());
            object.put("user",user);
            array.add(object);
        }
        return array;

    }

}
