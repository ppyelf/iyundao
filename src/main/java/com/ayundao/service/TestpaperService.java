package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Testpaper;

import java.util.List;

/**
 * @ClassName: TestpaperService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/7
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface TestpaperService {

    /**
     * 通过id查找实体
     * @param testpaper
     * @return
     */
    Testpaper findById(String testpaper);

    /**
     * 查询列表
     * @return
     */
    List<Testpaper> findAll();

    /**
     * 添加试卷
     * @param testpaper
     * @param examcontent
     * @param answer
     * @param yesorno
     * @return
     */
    Testpaper save(Testpaper testpaper, String[] examcontent, String[] answer, String[] yesorno,String[] score);

    /**
     * 找到所有的题目跟答案
     * @param paperTitles
     * @return
     */
    JsonResult findtitleandanswer(Testpaper testpaper,List<PaperTitle> paperTitles);

    /**
     * 删除试卷
     * @param testpaper
     */
    void deleteTestpaper(Testpaper testpaper);

    /**
     * 查找所有试卷
     * @param testpapers
     * @return
     */
    List<Testpaper> findByIds(String[] testpapers);
}
