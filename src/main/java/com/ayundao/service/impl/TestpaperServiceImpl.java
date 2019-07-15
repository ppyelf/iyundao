package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.PaperAnswer;
import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Testpaper;
import com.ayundao.repository.PaperAnswerRepository;
import com.ayundao.repository.PaperTitleRepository;
import com.ayundao.repository.TestpaperRepository;
import com.ayundao.service.TestpaperService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: TestpaperServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/7
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class TestpaperServiceImpl implements TestpaperService{

    @Autowired
    TestpaperRepository testpaperRepository;

    @Autowired
    PaperTitleRepository paperTitleRepository;

    @Autowired
    PaperAnswerRepository paperAnswerRepository;

    @Override
    public Testpaper findById(String testpaper) {
        return testpaperRepository.find(testpaper);
    }

    @Override
    @Transactional
    public List<Testpaper> findAll() {

        return testpaperRepository.findAll();
    }

    @Override
    @Transactional
    public Testpaper save(Testpaper testpaper, String[] examcontent, String[] answer, String[] yesorno,String[] score) {
        testpaper = testpaperRepository.save(testpaper);
        PaperTitle paperTitle;
        PaperAnswer paperAnswer;
        String[] answers;
        String[] yesornos;
        for (int i =0;i<examcontent.length;i++){
            //先添加题目
                paperTitle = new PaperTitle();
                //放入试卷
                paperTitle.setTestpaper(testpaper);
                //放入属性
                paperTitle.setExamcontent(examcontent[i]);
                paperTitle.setScore(score[i]);
                //添加题目
                paperTitle = paperTitleRepository.save(paperTitle);
            //分别拆分装入
            answers = answer[i].split("\\|");
            yesornos = yesorno[i].split("\\|");
            for (int k =0;k<answers.length;k++){
                paperAnswer = new PaperAnswer();
                paperAnswer.setPaperTitle(paperTitle);
                paperAnswer.setAnswerselect(answers[k]);
                paperAnswer.setAnswer(yesornos[k]);
                paperAnswerRepository.save(paperAnswer);
            }
        }

        JsonResult jsonResult = JsonResult.success();
        JSONObject object = JsonUtils.getJson(testpaper);
        jsonResult.setData(object);
        return testpaper;
    }

    @Override
    @Transactional
    public JsonResult findtitleandanswer(Testpaper testpaper,List<PaperTitle> paperTitles) {
        JSONObject object = new JSONObject();
        JSONArray arr = new JSONArray();
        object.put("id",testpaper.getId());
        object.put("name",testpaper.getName());
        object.put("intro",testpaper.getIntro());
        JSONObject obj ;
        JSONObject obj2;
        JSONArray one ;
        for (PaperTitle paperTitle : paperTitles) {
            obj2 = new JSONObject();
            obj2.put("title",paperTitle.getExamcontent());
           List<PaperAnswer> paperAnswers = paperAnswerRepository.findByPaperTitles(paperTitle);
            one = new JSONArray();
            for (PaperAnswer paperAnswer : paperAnswers) {
                obj = new JSONObject();
                obj.put("select ",paperAnswer.getAnswerselect());
                obj.put("istrue",paperAnswer.getAnswer());
                one.add(obj);
            }
            obj2.put("selectall",one);
            arr.add(obj2);
        }
        object.put("topic",arr);
        JsonResult jsonResult = JsonResult.success();
        jsonResult.setData(object);
        return jsonResult;
    }


    @Override
    public void deleteTestpaper(Testpaper testpaper) {
        testpaperRepository.delete(testpaper);
    }

    @Override
    public List<Testpaper> findByIds(String[] testpapers) {
        return testpaperRepository.findByIds(testpapers);
    }


}
