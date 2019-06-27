package com.ayundao.service.impl;

import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.CountryRank;
import com.ayundao.entity.Pioneer;
import com.ayundao.repository.CountryRankRepository;
import com.ayundao.repository.PioneerRepository;
import com.ayundao.service.IdeologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName: IdeologyServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 11:30
 * @Description: 实现 - 意识形态
 * @Version: V1.0
 */
@Service
@Transactional
public class IdeologyServiceImpl implements IdeologyService {

    @Autowired
    private CountryRankRepository countryRankRepository;

    @Autowired
    private PioneerRepository pioneerRepository;

    @Override
    public List<CountryRank> saveAllCountry(List<CountryRank> list) {
        List<CountryRank> result = new ArrayList<>();
        for (CountryRank countryRank : list) {
            countryRank.setCreatedDate(new Date());
            countryRank.setLastModifiedDate(new Date());
            countryRank = countryRankRepository.save(countryRank);
            result.add(countryRank);
        }
        return result;
    }

    @Override
    public List<Pioneer> saveAllPioneer(List<Pioneer> list) {
        List<Pioneer> result = new ArrayList<>();
        for (Pioneer pioneer : list) {
            pioneer.setCreatedDate(new Date());
            pioneer.setLastModifiedDate(new Date());
            pioneer = pioneerRepository.save(pioneer);
            result.add(pioneer);
        }
        return result;
    }

    @Override
    public List<CountryRank> findCountryOrderByCreatedTime(int type) {
        String startTime = convertTime(type, true);
        String endTime = convertTime(type, false);
        List<CountryRank> list = countryRankRepository.findListByCreatedTimeOrderByScore(startTime, endTime);
        return list;
    }

    @Override
    public List<Pioneer> findPioneerOrderByCreatedTime(int type) {
        String startTime = convertTime(type, true);
        String endTime = convertTime(type, false);
        List<Pioneer> list = pioneerRepository.findListByCreatedTimeOrderByScore(startTime, endTime);
        return list;
    }

    private String convertTime(int type, boolean start) {
        switch (type) {
            case  0://今日
                return start
                        ? TimeUtils.convertTime(TimeUtils.getDayBegin(), TimeUtils.yyyyMMddHHmmss)
                        : TimeUtils.convertTime(TimeUtils.getDayEnd(), TimeUtils.yyyyMMddHHmmss);
            case  1://本周
                return start
                        ? TimeUtils.convertTime(TimeUtils.getBeginDayOfWeek(), TimeUtils.yyyyMMddHHmmss)
                        : TimeUtils.convertTime(TimeUtils.getEndDayOfWeek(), TimeUtils.yyyyMMddHHmmss);
            case  2://本月
                return start
                        ? TimeUtils.convertTime(TimeUtils.getBeginDayOfMonth(), TimeUtils.yyyyMMddHHmmss)
                        : TimeUtils.convertTime(TimeUtils.getEndDayOfMonth(), TimeUtils.yyyyMMddHHmmss);
            case  3://本年
                return start
                        ? TimeUtils.convertTime(TimeUtils.getBeginDayOfYear(), TimeUtils.yyyyMMddHHmmss)
                        : TimeUtils.convertTime(TimeUtils.getEndDayOfYear(), TimeUtils.yyyyMMddHHmmss);
        }
        return "";
    }
}
