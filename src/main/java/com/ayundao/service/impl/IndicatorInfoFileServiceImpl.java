package com.ayundao.service.impl;

import com.ayundao.entity.IndicatorInfoFile;
import com.ayundao.repository.IndicatorInfoFileRepository;
import com.ayundao.service.IndicatorInfoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: IndicatorInfoFileServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 15:02
 * @Description: 实现 - 指标文件
 * @Version: V1.0
 */
@Service
public class IndicatorInfoFileServiceImpl implements IndicatorInfoFileService {

    @Autowired
    private IndicatorInfoFileRepository indicatorInfoFileRepository;

    @Override
    public List<IndicatorInfoFile> findByIds(String[] fileIds) {
        return indicatorInfoFileRepository.findByIds(fileIds);
    }
}
