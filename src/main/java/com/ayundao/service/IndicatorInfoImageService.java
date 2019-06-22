package com.ayundao.service;

import com.ayundao.entity.IndicatorInfoImage;

import java.util.List;

/**
 * @ClassName: IndicatorInfoImageService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 15:04
 * @Description: 服务 - 指标图片
 * @Version: V1.0
 */
public interface IndicatorInfoImageService {

    /**
     * 保存实体
     * @param image
     * @return
     */
    IndicatorInfoImage create(IndicatorInfoImage image);

    /**
     * 根据IDS查询实体集合
     * @param imageIds
     * @return
     */
    List<IndicatorInfoImage> findByIds(String[] imageIds);
}
