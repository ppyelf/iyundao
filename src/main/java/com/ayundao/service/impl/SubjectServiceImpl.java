package com.ayundao.service.impl;

import com.ayundao.entity.Subject;
import com.ayundao.repository.SubjectRepository;
import com.ayundao.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: SubjectServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:29
 * @Description: 服务层--机构
 * @Version: V1.0
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return (List<Subject>) subjectRepository.findAll();
    }
}
