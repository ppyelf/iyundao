package com.ayundao.service.impl;

import com.ayundao.base.Pageable;
import com.ayundao.base.Page;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.PioneerIndex;
import com.ayundao.repository.PioneerIndexRepository;
import com.ayundao.service.PMAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
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


}