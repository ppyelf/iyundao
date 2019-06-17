package com.ayundao.service.impl;

import com.ayundao.entity.PioneerIndex;
import com.ayundao.repository.PioneerIndexRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.PioneerIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 先锋指数
 * @Date: 2019/6/12 16:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PioneerIndexServiceImpl implements PioneerIndexService {

    @Autowired
    private PioneerIndexRepository pioneerIndexRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Override
    public PioneerIndex save(PioneerIndex params) {
        return pioneerIndexRepository.save(params);
    }

    @Override
    public Page<PioneerIndex> selectAll(Pageable pageable) {
        return pioneerIndexRepository.selectAll(pageable);
    }

    @Override
    public Page<PioneerIndex> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return pioneerIndexRepository.selectByDepartId(list,pageable);
    }

    @Override
    public Page<PioneerIndex> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return pioneerIndexRepository.selectByDepartId(list, pageable);
    }
    @Override
    public Page<PioneerIndex> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return pioneerIndexRepository.selectByDepartId(list,pageable);
    }

}
