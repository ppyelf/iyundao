package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Pageable;
import com.ayundao.base.Page;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.DemocraticAppraisal;
import com.ayundao.entity.PioneerIndex;
import com.ayundao.entity.User;
import com.ayundao.repository.DemocraticAppraisalRepository;
import com.ayundao.repository.PioneerIndexRepository;
import com.ayundao.repository.UserRepository;
import com.ayundao.service.PMAssessService;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: PMAssessServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/13
 * @Description: 实现 - 党员考核
 * @Version: V1.0
 */
@Service
@Transactional
public class PMAssessServiceImpl implements PMAssessService {

    @Autowired
    private PioneerIndexRepository pioneerIndexRepository;

    @Autowired
    private DemocraticAppraisalRepository democraticAppraisalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PioneerIndex> saveAllPioneerIndex(List<PioneerIndex> list) {
        List<PioneerIndex> result = new ArrayList<>();
        for (PioneerIndex pioneerIndex : list) {
            pioneerIndex.setCreatedDate(new Date());
            pioneerIndex.setLastModifiedDate(new Date());
            pioneerIndex = pioneerIndexRepository.save(pioneerIndex);
            result.add(pioneerIndex);
        }
        return result;
    }

    @Override
    public Page<PioneerIndex> findAllForPage(Pageable pageable) {
        return pioneerIndexRepository.findPage(pageable);
    }

    @Override
    public Page<PioneerIndex> findByProperty(Pageable pageable) {
        return pioneerIndexRepository.findPage(pageable);
    }

    @Override
    public List<PioneerIndex> findOrderByCreatedTime(int type) {
        switch (type) {
            case 0:
                return pioneerIndexRepository.findListByCreatedTimeOrderByWarn(
                        TimeUtils.convertTime(TimeUtils.getDayBegin(), TimeUtils.yyyyMMddHHmmss),
                        TimeUtils.convertTime(TimeUtils.getDayEnd(), TimeUtils.yyyyMMddHHmmss)
                );
            case 1:
                return pioneerIndexRepository.findListByCreatedTimeOrderByWarn(
                        TimeUtils.convertTime(TimeUtils.getBeginDayOfWeek(), TimeUtils.yyyyMMddHHmmss),
                        TimeUtils.convertTime(TimeUtils.getEndDayOfWeek(), TimeUtils.yyyyMMddHHmmss)
                );
            case 2:
                return pioneerIndexRepository.findListByCreatedTimeOrderByWarn(
                        TimeUtils.convertTime(TimeUtils.getBeginDayOfMonth(), TimeUtils.yyyyMMddHHmmss),
                        TimeUtils.convertTime(TimeUtils.getEndDayOfMonth(), TimeUtils.yyyyMMddHHmmss)
                );
            case 3:
                return pioneerIndexRepository.findListByCreatedTimeOrderByWarn(
                        TimeUtils.convertTime(TimeUtils.getBeginDayOfYear(), TimeUtils.yyyyMMddHHmmss),
                        TimeUtils.convertTime(TimeUtils.getEndDayOfYear(), TimeUtils.yyyyMMddHHmmss)
                );
        }
        return null;
    }

    @Override
    public List<DemocraticAppraisal> saveAllDemocraticAppraisal(List<DemocraticAppraisal> list) {
        List<DemocraticAppraisal> da = new ArrayList<>();
        for (DemocraticAppraisal democraticAppraisal : list){
            democraticAppraisal.setCreatedDate(new Date());
            democraticAppraisal.setLastModifiedDate(new Date());
            democraticAppraisal = democraticAppraisalRepository.save(democraticAppraisal);
            da.add(democraticAppraisal);
        }
        return da;
    }

    @Override
    public Page<DemocraticAppraisal> findDemoForPage(Pageable pageable) {
        return democraticAppraisalRepository.findPage(pageable);
    }

    @Override
    public Page<DemocraticAppraisal> findDemoByProperty(Pageable pageable) {
        return democraticAppraisalRepository.findPage(pageable);
    }

    @Override
    public List<JSONObject> findPioForYear(String year) {

        return pioneerIndexRepository.findPioForYear(year);
    }


    @Override
    public List<DemocraticAppraisal> findAllDemo() {
        return democraticAppraisalRepository.findAll();
    }

    @Override
    public List<JSONObject> findDemForYear(String year) {
        return democraticAppraisalRepository.findDemForYear(year);
    }

    @Override
    public User findUserByusercode(String usercode) {
        return userRepository.findByCode(usercode);
    }

    @Override
    public JSONObject findAllForPeople(User user, String usercode,String year) {
        if ("0".equals(year)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            year = sdf.format(date);
        }
        JSONObject object = pioneerIndexRepository.findByUsercode(usercode,year);
        JSONObject object1 = democraticAppraisalRepository.findByUsercode(usercode,year);
        JSONObject obj = new JSONObject();
        obj.put("id",user.getId());
        obj.put("code",user.getCode());
        obj.put("sex",user.getSex());
        obj.put("name",user.getName());
        obj.put("remark",user.getRemark());
        obj.put("userType",user.getUserType());
        obj.put("status",user.getStatus());
        obj.put("pioscoresum",object.get("scoresum"));
        obj.put("pioscoreavg",object.get("scoreavg"));
        obj.put("demscoresum",object1.get("scoresum"));
        obj.put("demscoreavg",object1.get("scoreavg"));
        return obj;
    }


}