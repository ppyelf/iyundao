package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Indicator;
import com.ayundao.entity.IndicatorInfoFile;
import com.ayundao.entity.IndicatorInfoImage;

import java.util.List;

/**
 * @ClassName: IndicatorInfoService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 14:51
 * @Description: 服务 - 指标详情
 * @Version: V1.0
 */
public interface IndicatorInfoService {

    /**
     * 新建实体
     * @param year
     * @param month
     * @param completion
     * @param intro
     * @param indicator
     * @param departId
     * @param groupId
     * @param userId
     * @param files
     * @param images
     * @param jsonResult
     * @return
     */
    JsonResult create(String year, String month, String completion, String intro, Indicator indicator, String departId, String groupId, String userId, List<IndicatorInfoFile> files, List<IndicatorInfoImage> images, JsonResult jsonResult);
}
