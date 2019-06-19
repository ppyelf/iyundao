package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.IndicatorInfoFileRepository;
import com.ayundao.repository.IndicatorInfoImageRepository;
import com.ayundao.repository.IndicatorInfoRepository;
import com.ayundao.repository.IndicatorRepository;
import com.ayundao.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: IndicatorServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 11:02
 * @Description: 实现 - 工作指标
 * @Version: V1.0
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private IndicatorInfoRepository indicatorInfoRepository;

    @Autowired
    private IndicatorInfoFileRepository indicatorInfoFileRepository;

    @Autowired
    private IndicatorInfoImageRepository indicatorInfoImageRepository;

    @Override
    public List<Indicator> findByFatherId(String fatherId) {
        return indicatorRepository.findByFatherId(fatherId);
    }

    @Override
    @Transactional
    public JsonResult create(String name, String situation, Work work, Indicator father, JsonResult jsonResult) {
        Indicator indicator = new Indicator();
        indicator.setCreatedDate(new Date());
        indicator.setLastModifiedDate(new Date());
        indicator.setName(name);
        indicator.setSituation(situation);
        indicator.setWork(work);
        if (father != null) {
            indicator.setFather(father);
        }
        indicator = indicatorRepository.save(indicator);
        //TODO 获取父级
        jsonResult.setData(JsonUtils.getJson(indicator));
        return jsonResult;
    }

    @Override
    public Indicator find(String id) {
        return indicatorRepository.find(id);
    }

    @Override
    public List<Indicator> findAll() {
        return indicatorRepository.findAll();
    }

    @Override
    public List<Indicator> findAllAndFatherIsNull() {
        return indicatorRepository.findAllAndFatherIsNull();
    }

    @Override
    public JsonResult delete(String id, JsonResult jsonResult) {
        Indicator indicator = indicatorRepository.find(id);
        List<Indicator> indicators = indicatorRepository.findByFatherId(id);
        return JsonResult.success();
    }
}
