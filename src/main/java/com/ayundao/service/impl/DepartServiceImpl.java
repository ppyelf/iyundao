package com.ayundao.service.impl;

import com.ayundao.entity.Depart;
import com.ayundao.repository.DepartRepository;
import com.ayundao.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: DepartServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:25
 * @Description: 实现 - 部门
 * @Version: V1.0
 */
@Service
@Transactional
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartRepository departRepository;

    @Override
    public List<Depart> findBySubjectId(String subjectId) {
        return departRepository.findBySubjectId(subjectId);
    }

    @Override
    public List<Depart> getList() {
        return departRepository.getList();
    }

    @Override
    public Depart findById(String id) {
        return departRepository.findByDepartId(id);
    }

    @Override
    public Depart save(Depart depart) {
        return departRepository.save(depart);
    }

    @Override
    public List<Depart> findByIds(String[] departIds) {
        return departRepository.findByIds(departIds);
    }

    @Override
    public List<Depart>  saveAll(List<Depart> departs) {
        return departRepository.saveAll(departs);
    }

    @Override
    public List<Depart> findSubjectIsNull() {
        return departRepository.findSubjectIsNull();
    }

    @Override
    public List<Depart> findByFatherId(String id) {
        return departRepository.findByFatherId(id);
    }

    @Override
    public List<Depart> getListByFatherIdIsNull() {
        return departRepository.getListByFatherIdIsNull();
    }

}
