package com.ayundao.service.impl;

import com.ayundao.entity.ExamInfoDepart;
import com.ayundao.repository.ExamInfoDepartRepository;
import com.ayundao.service.ExamInfoDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ExamInfoDepartServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class ExamInfoDepartServiceImpl  implements ExamInfoDepartService{

    @Autowired
    private ExamInfoDepartRepository examInfoDepartRepository;

    @Override
    @Transactional
    public List<ExamInfoDepart> findByExamId(String id) {
        return examInfoDepartRepository.findByExamId(id);

    }
}
