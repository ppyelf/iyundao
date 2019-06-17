package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Pioneer;
import com.ayundao.entity.PioneerType;
import com.ayundao.repository.PioneerRepository;
import com.ayundao.repository.PioneerTypeRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.PioneerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 先锋人物
 * @Date: 2019/6/12 16:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PioneerServiceImpl implements PioneerService {

    @Autowired
    private PioneerRepository pioneerRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Autowired
    private PioneerTypeRepository pioneerTypeRepository;

    @Override
    public Pioneer save(Pioneer params) {
        return pioneerRepository.save(params);
    }

    @Override
    public JsonResult save(PioneerType params) {
        if (params.equals("") || params == null) {
            return JsonResult.failure(801,"传入数据为空");
        } else {
            try {
                PioneerType save = pioneerTypeRepository.save(params);
                return JsonResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return JsonResult.failure(802,e.getMessage());
            }
        }
    }

    @Override
    public Page<Pioneer> selectAll(Pageable pageable) {
        return pioneerRepository.selectAll(pageable);
    }

    @Override
    public Page<Pioneer> selectByDepart(String departId, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(departId);
        return pioneerRepository.selectByDepartId(list,pageable);
    }

    @Override
    public Page<Pioneer> selectByGroup(String departId, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(departId);
        return pioneerRepository.selectByDepartId(list,pageable);
    }

    @Override
    public Page<Pioneer> selectBySubject(String departId, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(departId);
        return pioneerRepository.selectByDepartId(list,pageable);
    }

    @Override
    public JsonResult selectTypeByDepart(String departId) {
        try {
            List<PioneerType> list = pioneerTypeRepository.findByDepartId(departId);
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }

    @Override
    public JsonResult selectTypeByGroup(String departId) {
        try {
            List<PioneerType> list = pioneerTypeRepository.findByGroupId(departId);
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }

    @Override
    public JsonResult selectTypeBySubject(String departId) {
        try {
            List<PioneerType> list = pioneerTypeRepository.findBySubjectId(departId);
            return JsonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }

    @Override
    public JsonResult findByType(String typeId,Pageable pageable) {
        try {
            Page<Pioneer> page = pioneerRepository.selectByTypeId(typeId, pageable);
            return JsonResult.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802, e.getMessage());
        }
    }
}
