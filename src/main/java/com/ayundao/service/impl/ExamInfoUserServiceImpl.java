package com.ayundao.service.impl;

import com.ayundao.entity.ExamInfoUser;
import com.ayundao.repository.ExamInfouserRepository;
import com.ayundao.service.ExamInfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ExamInfoUserServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class ExamInfoUserServiceImpl implements ExamInfoUserService{

    @Autowired
    private ExamInfouserRepository examInfouserRepository;

    @Override
    public List<ExamInfoUser> findByExamUserId(String examid, String userid) {

        return examInfouserRepository.findByExamUserId(examid,userid);
    }
}
