package com.ayundao.service;

import com.ayundao.entity.IndicatorInfoFile;

import java.util.List;

/**
 * @ClassName: IndicatorInfoFileService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 14:52
 * @Description: 服务 - 指标文件
 * @Version: V1.0
 */
public interface IndicatorInfoFileService {

    /**
     * 根据IDS查询实体集合
     * @param fileIds
     * @return
     */
    List<IndicatorInfoFile> findByIds(String[] fileIds);

    /**
     *
     * @param indicatorInfoFile
     */
    IndicatorInfoFile create(IndicatorInfoFile indicatorInfoFile);

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    IndicatorInfoFile find(String id);

    /**
     * 删除实体
     * @param file
     */
    void delete(IndicatorInfoFile file);
}
