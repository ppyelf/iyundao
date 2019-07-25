package com.ayundao.service.impl;

import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Testpaper;
import com.ayundao.repository.PaperTitleRepository;
import com.ayundao.service.PaperTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: PaperTitleServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class PaperTitleServiceImpl implements PaperTitleService{

    @Autowired
    private PaperTitleRepository paperTitleRepository;

    @Override
    public List<PaperTitle> find(Testpaper testpaper) {


        return paperTitleRepository.findByTestpaper(testpaper);
    }

    @Override
    public PaperTitle findByid(String paperTitleid) {

        return paperTitleRepository.find(paperTitleid);
    }
}
