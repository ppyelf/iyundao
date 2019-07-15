package com.ayundao.service;

import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Testpaper;

import java.util.List;

/**
 * @ClassName: PaperTitleService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description:
 * @Version: V1.0
 */
public interface PaperTitleService {

    /**
     * 通过试卷实体找到所有的题目
     * @param testpaper
     * @return
     */
    List<PaperTitle> find(Testpaper testpaper);

    /**
     * 通过题目id找到实体
     * @param paperTitleid
     * @return
     */
    PaperTitle findByid(String paperTitleid);
}
