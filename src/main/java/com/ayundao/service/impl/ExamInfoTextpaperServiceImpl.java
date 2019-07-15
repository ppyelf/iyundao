package com.ayundao.service.impl;

import com.ayundao.entity.ExamInfoTextpaper;
import com.ayundao.repository.ExamInfoTextpaperRepository;
import com.ayundao.service.ExamInfoTextpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ExamInfoTextpaperServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 考试与试卷关系
 * @Version: V1.0
 */
@Service
@Transactional
public class ExamInfoTextpaperServiceImpl implements ExamInfoTextpaperService{

    @Autowired
    private ExamInfoTextpaperRepository examInfoTextpaperRepository;

    @Override
    public List<ExamInfoTextpaper> findByExamId(String id) {
        return examInfoTextpaperRepository.findByExamId(id);
    }
}
