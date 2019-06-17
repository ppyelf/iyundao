package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.StyleOfWork;
import com.ayundao.entity.StyleOfWorkRecord;
import com.ayundao.entity.StyleOfWorkStatistics;
import com.ayundao.repository.StyleOfWorkRecordRepository;
import com.ayundao.repository.StyleOfWorkRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.StyleOfWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ink-feather
 * @Description: 行风效能
 * @Date: 2019/6/12 15:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StyleOfWorkServiceImpl implements StyleOfWorkService {

    @Autowired
    private StyleOfWorkRepository styleOfWorkRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Autowired
    private StyleOfWorkRecordRepository recordRepository;

    @Override
    public StyleOfWork save(StyleOfWork params) {
        return styleOfWorkRepository.save(params);
    }

    @Override
    public JsonResult save(StyleOfWorkRecord params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure(801,"传入数据为空");
        } else {
            try {
                StyleOfWorkRecord save = recordRepository.save(params);
                return JsonResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return JsonResult.failure(802,e.getMessage());
            }
        }
    }

    @Override
    public Page<StyleOfWork> selectAll(Pageable pageable) {
        return styleOfWorkRepository.selectAll(pageable);
    }

    @Override
    public Page<StyleOfWork> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return styleOfWorkRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<StyleOfWork> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return styleOfWorkRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<StyleOfWork> selectBySubject(String params, Pageable pageable) {
            List<String> list = relationRepository.selectBySubjectId(params);
            return styleOfWorkRepository.selectByDepart(list,pageable);
    }

    @Override
    public JsonResult statistics() {
        try {
            List<StyleOfWorkStatistics> list = styleOfWorkRepository.statistics();
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }

    @Override
    public JsonResult findByWorkId(String id) {
        try {
            List<StyleOfWorkRecord> list = recordRepository.findByStyleOfWorkId(id);
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }
}
