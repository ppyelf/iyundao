package com.ayundao.service.impl;

import com.ayundao.entity.PartySpirit;
import com.ayundao.repository.PartySpiritRepository;
import com.ayundao.repository.UserRelationRepository;
import com.ayundao.service.PartySpiritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ink-feather
 * @Description: 党内精神
 * @Date: 2019/6/12 16:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PartySpiritServiceImpl implements PartySpiritService {

    @Autowired
    private PartySpiritRepository partySpiritRepository;

    @Autowired
    private UserRelationRepository relationRepository;

    @Override
    public PartySpirit save(PartySpirit params) {
        return partySpiritRepository.save(params);
    }

    @Override
    public Page<PartySpirit> selectAll(Pageable pageable) {
        return partySpiritRepository.selectAll(pageable);
    }

    @Override
    public Page<PartySpirit> selectByDepart(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByDepart(params);
        return partySpiritRepository.selectByDepart(list,pageable);
    }

    @Override
    public Page<PartySpirit> selectByGroup(String params, Pageable pageable) {
        List<String> list = relationRepository.selectByGroup(params);
        return partySpiritRepository.selectByDepart(list, pageable);
    }
    @Override
    public Page<PartySpirit> selectBySubject(String params, Pageable pageable) {
        List<String> list = relationRepository.selectBySubjectId(params);
        return partySpiritRepository.selectByDepart(list,pageable);
    }

}
