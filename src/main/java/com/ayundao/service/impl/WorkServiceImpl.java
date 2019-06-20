package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.DepartService;
import com.ayundao.service.GroupsService;
import com.ayundao.service.SubjectService;
import com.ayundao.service.WorkService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: WorkServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 17:30
 * @Description: 实现 - 中心工作
 * @Version: V1.0
 */
@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkSubjectRepository workSubjectRepository;

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private IndicatorInfoRepository indicatorInfoRepository;

    @Autowired
    private IndicatorInfoFileRepository indicatorInfoFileRepository;

    @Autowired
    private IndicatorInfoImageRepository indicatorInfoImageRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Override
    public List<Work> findAll() {
        return workRepository.findAll();
    }

    @Override
    @Transactional
    public JsonResult save(String year, String name, int workType, String startTime, String endTime, String remark, String subjectId, String departId, String groupId, JsonResult jsonResult) {
        Work work = new Work();
        work.setCreatedDate(new Date());
        work.setLastModifiedDate(new Date());
        work.setYear(year);
        work.setName(name);
        for (Work.WORK_TYPE type : Work.WORK_TYPE.values()) {
            if (type.ordinal() == workType) {
                work.setWorkType(type);
                break;
            }
        }
        if (work.getWorkType() == null) {
            return JsonResult.failure(603, "工作类型异常");
        }
        work.setStartTime(startTime);
        work.setEndTime(endTime);
        work.setRemark(remark);
        WorkSubject ws = new WorkSubject();
        ws.setCreatedDate(new Date());
        ws.setLastModifiedDate(new Date());
        Subject subject = subjectService.find(subjectId);
        if (subject == null) {
            return JsonResult.failure(604, "机构不存在");
        }
        ws.setSubjectId(subjectId);
        ws.setDepartId(StringUtils.isBlank(departId) ? null : departService.findById(departId).getId());
        ws.setGroupId(StringUtils.isBlank(groupId) ? null : groupsService.findById(groupId).getId());
        work = workRepository.save(work);
        ws.setWork(work);
        ws = workSubjectRepository.save(ws);
        work.setWorkSubject(ws);
        work = workRepository.save(work);
        jsonResult.setData(JsonUtils.getJson(work));
        return jsonResult;
    }

    @Override
    public Work find(String id) {
        return workRepository.find(id);
    }

    @Override
    @Modifying
    @Transactional
    public void delete(String id) {
        Work work = workRepository.find(id);
        if (work == null) {
            return ;
        }
        WorkSubject ws = workSubjectRepository.findByWorkId(work.getId());
        List<Indicator> indicators = indicatorRepository.findByWorkId(work.getId());
        if (CollectionUtils.isNotEmpty(indicators)) {
            List<IndicatorInfo> indicatorInfos = indicatorInfoRepository.findByIndicator(indicators);
            if (CollectionUtils.isNotEmpty(indicatorInfos)) {
                List<IndicatorInfoFile> indicatorInfoFiles = indicatorInfoFileRepository.findByIndicatorInfo(indicatorInfos);
                List<IndicatorInfoImage> indicatorInfoImages = indicatorInfoImageRepository.findByIndicatorInfo(indicatorInfos);
                indicatorInfoFileRepository.deleteAll(indicatorInfoFiles);
                indicatorInfoImageRepository.deleteAll(indicatorInfoImages);
            }
            indicatorInfoRepository.deleteAll(indicatorInfos);
        }
        workSubjectRepository.delete(ws);
        workRepository.delete(work);
    }


}
