package com.ayundao.service.impl;

import com.ayundao.entity.IndicatorInfoFile;
import com.ayundao.entity.IndicatorInfoImage;
import com.ayundao.repository.IndicatorInfoImageRepository;
import com.ayundao.service.IndicatorInfoImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: IndicatorInfoImageServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 15:04
 * @Description: 实现 - 指标图片
 * @Version: V1.0
 */
@Service
public class IndicatorInfoImageServiceImpl implements IndicatorInfoImageService {

    @Autowired
    private IndicatorInfoImageRepository indicatorInfoImageRepository;

    @Override
    @Transactional
    public IndicatorInfoImage create(IndicatorInfoImage image) {
        return indicatorInfoImageRepository.save(image);
    }

    @Override
    public List<IndicatorInfoImage> findByIds(String[] ids) {
        return indicatorInfoImageRepository.findByIds(ids);
    }
}
