package com.ayundao.service.impl;

import com.ayundao.entity.FortIndex;
import com.ayundao.entity.PioneerIndex;
import com.ayundao.repository.FortIndexRepository;
import com.ayundao.repository.PioneerIndexRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.FortIndexService;
import com.ayundao.service.PioneerIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 堡垒指数
 * @Date: 2019/6/12 16:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FortIndexServiceImpl implements FortIndexService {

    @Autowired
    private FortIndexRepository pioneerIndexRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Override
    public FortIndex save(FortIndex params) {
        return pioneerIndexRepository.save(params);
    }

    @Override
    public Page<FortIndex> selectAll(Pageable pageable) {
        return pioneerIndexRepository.selectAll(pageable);
    }

    @Override
    public Page<FortIndex> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return pioneerIndexRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<FortIndex> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return pioneerIndexRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<FortIndex> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return pioneerIndexRepository.selectByDepart(list,pageable);
    }

}
