package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.IndicatorInfoRepository;
import com.ayundao.service.DepartService;
import com.ayundao.service.GroupsService;
import com.ayundao.service.IndicatorInfoService;
import com.ayundao.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: IndicatorInfoServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/19 14:51
 * @Description: 实现 - 指标详情
 * @Version: V1.0
 */
@Service
public class IndicatorInfoServiceImpl implements IndicatorInfoService {

    @Autowired
    private IndicatorInfoRepository indicatorInfoRepository;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public JsonResult create(String year, String month, String completion, String intro, Indicator indicator, String departId, String groupId, String userId, List<IndicatorInfoFile> files, List<IndicatorInfoImage> images, JsonResult jsonResult) {
        IndicatorInfo info = new IndicatorInfo();
        info.setCreatedDate(new Date());
        info.setLastModifiedDate(new Date());
        info.setYear(year);
        info.setMonth(month);
        info.setCompletion(completion);
        info.setIntro(intro);
        info.setIndicator(indicator);
        Depart depart = departService.findById(departId);
        Groups groups = groupsService.findById(groupId);
        User user = userService.findById(userId);
        if (depart != null) {
            info.setDepartId(departId);
        } 
        if (groups != null ) {
            info.setGroupId(groupId);
        } 
        if (user != null) {
            info.setUserId(userId);
        }
        if (depart == null && groups == null && user == null) {
            return JsonResult.failure(604, "用户,部门,组织查询不存在");
        } 
        Set<IndicatorInfoFile> fileSet = new HashSet<>();
        Set<IndicatorInfoImage> imageSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(files)) {
            fileSet.addAll(files);
        } 
        if (CollectionUtils.isNotEmpty(images)) {
            imageSet.addAll(images);
        }
        info = indicatorInfoRepository.save(info);
        jsonResult.setData(JsonUtils.getJson(info));
        return jsonResult;
    }
}
