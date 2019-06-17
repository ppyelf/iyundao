package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.EducationOfCleanPolitics;
import com.ayundao.entity.EducationOfCleanPoliticsAccessory;
import com.ayundao.repository.EducationOfCleanPoliticsAccessoryRepository;
import com.ayundao.repository.EducationOfCleanPoliticsRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.EducationOfCleanPoliticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 廉政教育
 * @Date: 2019/6/12 14:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EducationOfCleanPoliticsServiceImpl implements EducationOfCleanPoliticsService {

    @Autowired
    private EducationOfCleanPoliticsRepository cleanPoliticsRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Autowired
    private EducationOfCleanPoliticsAccessoryRepository accessoryRepository;

    @Override
    public EducationOfCleanPolitics save(EducationOfCleanPolitics Recipe) {
        return cleanPoliticsRepository.save(Recipe);
    }

    @Override
    public JsonResult save(EducationOfCleanPoliticsAccessory params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure(801,"传入数据为空");
        } else {
            try {
                EducationOfCleanPoliticsAccessory save = accessoryRepository.save(params);
                return JsonResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return JsonResult.failure(802,e.getMessage());
            }
        }
    }

    @Override
    public Page<EducationOfCleanPolitics> selectAll(Pageable pageable) {
        return cleanPoliticsRepository.selectAll(pageable);
    }

    @Override
    public Page<EducationOfCleanPolitics> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return cleanPoliticsRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<EducationOfCleanPolitics> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return cleanPoliticsRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<EducationOfCleanPolitics> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return cleanPoliticsRepository.selectByDepart(list,pageable);
    }

    @Override
    public JsonResult findByEducationId(String id) {
        try {
            List<EducationOfCleanPoliticsAccessory> list = accessoryRepository.findByEducationId(id);
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }
}
