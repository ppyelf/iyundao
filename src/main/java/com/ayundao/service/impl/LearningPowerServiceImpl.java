package com.ayundao.service.impl;

import com.ayundao.entity.LearningPower;
import com.ayundao.repository.LearningPowerRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.LearningPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ink-feather
 * @Description: 学习强国
 * @Date: 2019/6/12 16:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LearningPowerServiceImpl implements LearningPowerService {

    @Autowired
    private LearningPowerRepository learningPowerRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Override
    public LearningPower save(LearningPower params) {
        return learningPowerRepository.save(params);
    }

    @Override
    public Page<LearningPower> selectAll(Pageable pageable) {
        return learningPowerRepository.selectAll(pageable);
    }

    @Override
    public Page<LearningPower> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return learningPowerRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<LearningPower> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return learningPowerRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<LearningPower> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return learningPowerRepository.selectByDepart(list,pageable);
    }

}
