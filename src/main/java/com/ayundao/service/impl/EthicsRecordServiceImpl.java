package com.ayundao.service.impl;

import com.ayundao.entity.EthicsRecord;
import com.ayundao.repository.EthicsRecordRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.EthicsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 医德档案
 * @Date: 2019/6/12 15:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EthicsRecordServiceImpl implements EthicsRecordService {

    @Autowired
    private EthicsRecordRepository recordRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Override
    public EthicsRecord save(EthicsRecord params) {
        return recordRepository.save(params);
    }

    @Override
    public Page<EthicsRecord> selectAll(Pageable pageable) {
        return recordRepository.selectAll(pageable);
    }

    @Override
    public Page<EthicsRecord> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return recordRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<EthicsRecord> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return recordRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<EthicsRecord> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return recordRepository.selectByDepart(list,pageable);
    }

}
