package com.ayundao.service.impl;

import com.ayundao.entity.UserInfoGh;
import com.ayundao.entity.UserInfoMzdp;
import com.ayundao.repository.*;
import com.ayundao.service.UserInfoPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
/**
 * @ClassName: UserInfoPowerServiceImpl
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 服务实现 - 用户详情 -力量图谱
 * @Version: V1.0
 */
@Service
@Transactional
public class UserInfoPowerServiceImpl implements UserInfoPowerService {
    @Autowired
    private UserInfoFdhRepository userInfoFdhRepository;

    @Autowired
    private UserInfoTwRepository userInfoTwRepository;

    @Autowired
    private UserInfoGhRepository userInfoGhRepository;

    @Autowired
    private UserInfoMzdpRepository userInfoMzdpRepository;

    @Autowired
    private UserInfoGzqtRepository userInfoGzqtRepository;

    @Autowired
    private UserInfoLtxlgbRepository userInfoLtxlgbRepository;

    @Override
    public Map<String, Object> countBySexFdh() {
        return userInfoFdhRepository.countBySexFdh();
    }

    @Override
    public Map<String, Object> countByEducationFdh() {
        return userInfoFdhRepository.countByEducationFdh();
    }

    @Override
    public Map<String, Object> countByIdcardFdh() {
        return userInfoFdhRepository.countByIdcardFdh();
    }

    @Override
    public Map<String, Object> countByDepartmentFdh() {
        return userInfoFdhRepository.countByDepartmentFdh();
    }

    @Override
    public Map<String, Object> countByPartyAgeFdh() {
        return userInfoFdhRepository.countByPartyAgeFdh();
    }

    @Override
    public Map<String, Object> countByTitleFdh() {
        return userInfoFdhRepository.countByTitleFdh();
    }

    @Override
    public Map<String, Object> countByPlaceFdh() {
        return userInfoFdhRepository.countByPlaceFdh();
    }

    @Override
    public Map<String, Object> countByIdentityFdh() {
        return userInfoFdhRepository.countByIdentityFdh();
    }

    @Override
    public Map<String, Object> countBySexGh() {
        return userInfoGhRepository.countBySexGh();
    }

    @Override
    public Map<String, Object> countByEducationGh() {
        return userInfoGhRepository.countByEducationGh();
    }

    @Override
    public Map<String, Object> countByIdcardGh() {
        return userInfoGhRepository.countByIdcardGh();
    }

    @Override
    public Map<String, Object> countByDepartmentGh() {
        return userInfoGhRepository.countByDepartmentGh();
    }

    @Override
    public Map<String, Object> countByPartyAgeGh() {
        return userInfoGhRepository.countByPartyAgeGh();
    }

    @Override
    public Map<String, Object> countByTitleGh() {
        return userInfoGhRepository.countByTitleGh();
    }

    @Override
    public Map<String, Object> countByPlaceGh() {
        return userInfoGhRepository.countByPlaceGh();
    }

    @Override
    public Map<String, Object> countByIdentityGh() {
        return userInfoGhRepository.countByIdentityGh();
    }

    @Override
    public Map<String, Object> countBySexTw() {
        return userInfoTwRepository.countBySexTw();
    }

    @Override
    public Map<String, Object> countByEducationTw() {
        return userInfoTwRepository.countByEducationTw();
    }

    @Override
    public Map<String, Object> countByIdcardTw() {
        return userInfoTwRepository.countByIdcardTw();
    }

    @Override
    public Map<String, Object> countByDepartmentTw() {
        return userInfoTwRepository.countByDepartmentTw();
    }

    @Override
    public Map<String, Object> countByPartyAgeTw() {
        return userInfoTwRepository.countByPartyAgeTw();
    }

    @Override
    public Map<String, Object> countByTitleTw() {
        return userInfoTwRepository.countByTitleTw();
    }

    @Override
    public Map<String, Object> countByPlaceTw() {
        return userInfoTwRepository.countByPlaceTw();
    }

    @Override
    public Map<String, Object> countByIdentityTw() {
        return userInfoTwRepository.countByIdentityTw();
    }

    @Override
    public Map<String, Object> countBySexLtxlgb() {
        return userInfoLtxlgbRepository.countBySexLtxlgb();
    }

    @Override
    public Map<String, Object> countByEducationLtxlgb() {
        return userInfoLtxlgbRepository.countByEducationLtxlgb();
    }

    @Override
    public Map<String, Object> countByIdcardLtxlgb() {
        return userInfoLtxlgbRepository.countByIdcardLtxlgb();
    }

    @Override
    public Map<String, Object> countByDepartmentLtxlgb() {
        return userInfoLtxlgbRepository.countByDepartmentLtxlgb();
    }

    @Override
    public Map<String, Object> countByPartyAgeLtxlgb() {
        return userInfoLtxlgbRepository.countByPartyAgeLtxlgb();
    }

    @Override
    public Map<String, Object> countByTitleLtxlgb() {
        return userInfoLtxlgbRepository.countByTitleLtxlgb();
    }

    @Override
    public Map<String, Object> countByPlaceLtxlgb() {
        return userInfoLtxlgbRepository.countByPlaceLtxlgb();
    }

    @Override
    public Map<String, Object> countByIdentityLtxlgb() {
        return userInfoLtxlgbRepository.countByIdentityLtxlgb();
    }

    @Override
    public Map<String, Object> countBySexGzqt() {
        return userInfoGzqtRepository.countBySexGzqt();
    }

    @Override
    public Map<String, Object> countByEducationGzqt() {
        return userInfoGzqtRepository.countByEducationGzqt();
    }

    @Override
    public Map<String, Object> countByIdcardGzqt() {
        return userInfoGzqtRepository.countByIdcardGzqt();
    }

    @Override
    public Map<String, Object> countByDepartmentGzqt() {
        return userInfoGzqtRepository.countByDepartmentGzqt();
    }

//    @Override
//    public Map<String, Object> countByPartyAgeGzqt() {
//        return userInfoGzqtRepository.countByPartyAgeGzqt();
//    }

    @Override
    public Map<String, Object> countByTitleGzqt() {
        return userInfoGzqtRepository.countByTitleGzqt();
    }

    @Override
    public Map<String, Object> countByPlaceGzqt() {
        return userInfoGzqtRepository.countByPlaceGzqt();
    }

    @Override
    public Map<String, Object> countByIdentityGzqt() {
        return userInfoGzqtRepository.countByIdentityGzqt();
    }

    @Override
    public Map<String, Object> countBySexMzdp() {
        return userInfoMzdpRepository.countBySexMzdp();
    }

    @Override
    public Map<String, Object> countByEducationMzdp() {
        return userInfoMzdpRepository.countByEducationMzdp();
    }

    @Override
    public Map<String, Object> countByIdcardMzdp() {
        return userInfoMzdpRepository.countByIdcardMzdp();
    }

    @Override
    public Map<String, Object> countByDepartmentMzdp() {
        return userInfoMzdpRepository.countByDepartmentMzdp();
    }

    @Override
    public Map<String, Object> countByPartyAgeMzdp() {
        return userInfoMzdpRepository.countByPartyAgeMzdp();
    }

    @Override
    public Map<String, Object> countByTitleMzdp() {
        return userInfoMzdpRepository.countByTitleMzdp();
    }

    @Override
    public Map<String, Object> countByPlaceMzdp() {
        return userInfoMzdpRepository.countByPlaceMzdp();
    }

    @Override
    public Map<String, Object> countByIdentityMzdp() {
        return userInfoMzdpRepository.countByIdentityMzdp();
    }
}
