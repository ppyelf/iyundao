package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.MaterialsRational;
import com.ayundao.entity.MaterialsWarning;
import com.ayundao.repository.MaterialsRationalRepository;
import com.ayundao.repository.MaterialsWarningRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.MaterialsWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ink-feather
 * @Description: 耗材预警
 * @Date: 2019/6/12 15:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialsWarningServiceImpl implements MaterialsWarningService {

    @Autowired
    private MaterialsWarningRepository warningRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Autowired
    private MaterialsRationalRepository materialsRationalRepository;

    @Override
    public MaterialsWarning save(MaterialsWarning params) {
        return warningRepository.save(params);
    }

    @Override
    public JsonResult save(MaterialsRational params) {
        if(params.equals("") || params == null) {
            return JsonResult.failure(801,"传入数据为空");
        } else {
            try {
                MaterialsRational save = materialsRationalRepository.save(params);
                return JsonResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return JsonResult.failure(802,e.getMessage());
            }
        }
    }

    @Override
    public Page<MaterialsWarning> selectAll(Pageable pageable) {
        return warningRepository.selectAll(pageable);
    }

    @Override
    public Page<MaterialsWarning> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return warningRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<MaterialsWarning> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return warningRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<MaterialsWarning> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return warningRepository.selectByDepart(list,pageable);
    }

    @Override
    public JsonResult findByMaterialsId(String materialsId) {
        try {
            List<MaterialsRational> byMaterialsId = materialsRationalRepository.findByMaterialsId(materialsId);
            return JsonResult.success(byMaterialsId);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(802,e.getMessage());
        }
    }
}
