package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExamServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamInfoDepartRepository examInfoDepartRepository;

    @Autowired
    private ExamInfoTextpaperRepository examInfoTextpaperRepository;

    @Autowired
    private TestpaperService testpaperService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExamInfoUserScoreRepository examInfoUserScoreRepository;

    @Autowired
    private PaperTitleService paperTitleService;

    @Autowired
    private PaperAnswerRepository paperAnswerRepository;

    @Autowired
    private ExamInfouserRepository examInfouserRepository;

    @Override
    public List<Exam> findAll() {

        return  (List<Exam>)(examRepository.findAll());
    }

    @Override
    @Transactional
    public Exam save(Exam exam, String[] subjectIds, String[] departIds, String[] groupIds, String[] testpapers,String[] userids) {
        exam = examRepository.save(exam);
        ExamInfoDepart eid;
        Subject subject;
        Depart depart;
        Groups groups;
        User user;
        Testpaper testpaper;
        ExamInfoTextpaper eit;
        //分别添加部门组织机构用户
            for(int i =0;i<subjectIds.length;i++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                subject = subjectService.find(subjectIds[i]);
                eid.setSubject(subject);
                examInfoDepartRepository.save(eid);
            }
            for(int j = 0;j<departIds.length;j++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                depart = departService.findById(departIds[j]);
                eid.setDepart(depart);
                examInfoDepartRepository.save(eid);
            }
            for (int k = 0;k<groupIds.length;k++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                groups = groupsService.findById(groupIds[k]);
                eid.setGroups(groups);
                examInfoDepartRepository.save(eid);
            }
            for (int u = 0;u<userids.length;u++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                user = userService.findById(userids[u]);
                eid.setUser(user);
                examInfoDepartRepository.save(eid);
            }
        //添加试卷
            for (int p = 0;p<testpapers.length;p++){
                eit = new ExamInfoTextpaper();
                eit.setCreatedDate(new Date());
                eit.setLastModifiedDate(new Date());
                eit.setExam(exam);
                testpaper = testpaperService.findById(testpapers[p]);
                eit.setTestpaper(testpaper);
                examInfoTextpaperRepository.save(eit);
            }

        return exam;
    }

    @Override
    public Exam findById(String id) {
            return examRepository.find(id);
    }

    @Override
    public JSONObject show(Exam exam, List<ExamInfoDepart> examInfoDeparts, List<ExamInfoTextpaper> examInfoTextpapers) {
        JSONObject object = new JSONObject();
        JSONArray subjects = new JSONArray();
        JSONArray departs = new JSONArray();
        JSONArray groups = new JSONArray();
        JSONArray  users = new JSONArray();
        JSONArray textpapers = new JSONArray();
        JSONObject json ;
        if(CollectionUtils.isNotEmpty(examInfoDeparts)){
            for (ExamInfoDepart examInfoDepart : examInfoDeparts) {
                if (examInfoDepart.getSubject()!=null){
                    json = new JSONObject(JsonUtils.getJson(examInfoDepart.getSubject()));
                    subjects.add(json);
                }
                if(examInfoDepart.getDepart()!=null){
                    json = new JSONObject(JsonUtils.getJson(examInfoDepart.getDepart()));
                    departs.add(json);
                }
                if(examInfoDepart.getGroups()!=null){
                    json = new JSONObject(JsonUtils.getJson(examInfoDepart.getGroups()));
                    groups.add(json);
                }
                if(examInfoDepart.getUser()!=null){
                    json = new JSONObject((JsonUtils.getJson(examInfoDepart.getUser())));
                    users.add(json);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(examInfoTextpapers)){
            for (ExamInfoTextpaper examInfoTextpaper : examInfoTextpapers) {
                if(examInfoTextpaper.getTestpaper()!=null){
                    json = new JSONObject(JsonUtils.getJson(examInfoTextpaper.getTestpaper()));
                    textpapers.add(json);
                }
            }
        }
        object.put("title",exam.getTitle());
        object.put("starttime",exam.getStarttime());
        object.put("overtime",exam.getOvertime());
        object.put("examlong",exam.getExamlong());
        object.put("score",exam.getScore());
        object.put("showthat",exam.getShowthat());
        object.put("subjects",subjects);
        object.put("departs",departs);
        object.put("groups",groups);
        object.put("users",users);
        object.put("textpapers",textpapers);
        return object;
    }

    @Override
    @Transactional
    public void deleteExam(Exam exam) {
        examRepository.delete(exam);
    }

    @Override
    public List<ExamInfoUserScore> findALLScore(Exam exam) {
            return examInfoUserScoreRepository.findAllByExamId(exam);
    }

    @Override
    public JsonResult saveExamAndUser(String examid, String testpaperid, String userid, List<Map<String, String>> mapList) {
        JSONArray arr = new JSONArray();
        Exam exam = examRepository.find(examid);
        if(exam ==null){
            return JsonResult.success().notFound("考试不存在");
        }
        Testpaper testpaper =  testpaperService.findById(testpaperid);
        if(testpaper ==null){
            return JsonResult.success().notFound("试卷不存在");
        }
        User user = userService.findById(userid);
        if(user ==null ){
            return JsonResult.success().notFound("用户不存在");
        }

        String answer;
        String[] answers;
        int sumscore = 0;
        for (Map<String, String> map : mapList) {
            ExamInfoUser examInfoUser = new ExamInfoUser();
            examInfoUser.setCreatedDate(new Date());
            examInfoUser.setLastModifiedDate(new Date());
            examInfoUser.setExam(exam);
            examInfoUser.setTestpaper(testpaper);
            examInfoUser.setUser(user);
            //通过题目id找到题目
            PaperTitle paperTitle =paperTitleService.findByid(map.get("paperTitleid"));
            //通过题目找到所有的答案选择
                answer = map.get("answerselect");
            examInfoUser.setPaperTitle(paperTitle);
                examInfoUser.setAnswerselect(answer);
                answers =answer.split("\\|");
            int a =0;
            //查找所有答案选择
            List<PaperAnswer> paperAnswers = paperAnswerRepository.findByPaperTitles(paperTitle);
            //遍历
            for (PaperAnswer paperAnswer : paperAnswers) {
                //如果是正确答案
                a=0;
                if(Integer.parseInt(paperAnswer.getAnswer()) == 1){
                    //循环判断传入的数据里有没有
                    for (int i =0;i<answers.length;i++){
                      if (paperAnswer.getAnswerselect().toString().equals(answers[i].toString())){
                          //如果有 a+1;
                                a ++;
                      }
                    }
                }
            }
            //则答案正确获得分数
            if (answers.length==a){
                examInfoUser.setAnswer("1");
                sumscore= sumscore +Integer.parseInt(paperTitle.getScore());
            }else {
                examInfoUser.setAnswer("0");
            }
            examInfouserRepository.save(examInfoUser);
        }
        ExamInfoUserScore examInfoUserScore = new ExamInfoUserScore();
        examInfoUserScore.setCreatedDate(new Date());
        examInfoUserScore.setLastModifiedDate(new Date());
        examInfoUserScore.setExam(exam);
        examInfoUserScore.setUser(user);
        examInfoUserScore.setScore(sumscore+"");
       examInfoUserScore= examInfoUserScoreRepository.save(examInfoUserScore);
       JsonResult jsonResult = JsonResult.success();
        JSONObject object = JsonUtils.getJson(examInfoUserScore);
       jsonResult.setData(object);
       return jsonResult;
    }

    @Override
    public Page<Exam> finALLForPPage(Pageable pageable) {
        return examRepository.findPage(pageable);
    }

    @Override
    public Page<Exam> findNameForPage(Pageable pageable) {
        return examRepository.findPage(pageable);
    }

    @Override
    public List<ExamInfoUserScore> findScoreByUser(User user) {
        return examInfoUserScoreRepository.findbyUser(user);
    }


}
