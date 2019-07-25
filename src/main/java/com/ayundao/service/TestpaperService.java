package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Testpaper;

import java.util.List;
import java.util.Map;

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




    /**
     * 添加試卷
     * @param testpaper
     * @param examination
     */
    void savetest(Testpaper testpaper, List<Map<String, String>> examination);
}
