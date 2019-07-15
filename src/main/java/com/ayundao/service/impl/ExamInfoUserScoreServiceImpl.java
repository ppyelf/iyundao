package com.ayundao.service.impl;

import com.ayundao.entity.ExamInfoUserScore;
import com.ayundao.repository.ExamInfoUserScoreRepository;
import com.ayundao.service.ExamInfoUserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: ExamInfoUserScoreServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class ExamInfoUserScoreServiceImpl implements ExamInfoUserScoreService {

    @Autowired
    private ExamInfoUserScoreRepository examInfoUserScoreRepository;

    @Override
    public ExamInfoUserScore findByExamUserId(String examid, String userid) {

        return examInfoUserScoreRepository.findByExamUserId(examid,userid);
    }
}
