package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.ExamInfoDepartRepository;
import com.ayundao.repository.ExamRepository;
import com.ayundao.service.DepartService;
import com.ayundao.service.ExamService;
import com.ayundao.service.GroupsService;
import com.ayundao.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Override
    public List<Exam> findAll() {

        return  (List<Exam>)(examRepository.findAll());
    }

    @Override
    public Exam save(Exam exam, String[] subjectIds, String[] departIds, String[] groupIds, String[] testpapers) {
        exam = examRepository.save(exam);
        ExamInfoDepart eid;
        Subject subject;
        Depart depart;
        Groups groups;
        ExamInfoTextpaper eit;
        //分别添加部门组织机构
        if(subjectIds.length>0){
            for(int i =0;i<subjectIds.length;i++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                subject = subjectService.find(subjectIds[i]);
                eid.setSubject(subject);
                examInfoDepartRepository.save(eid);
            }
        }
        if(departIds.length>0){
            for(int j = 0;j<departIds.length;j++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                depart = departService.findById(departIds[j]);
                eid.setDepart(depart);
                examInfoDepartRepository.save(eid);
            }
        }
        if(groupIds.length>0){
            for (int k = 0;k<groupIds.length;k++){
                eid = new ExamInfoDepart();
                eid.setCreatedDate(new Date());
                eid.setLastModifiedDate(new Date());
                eid.setExam(exam);
                groups = groupsService.findById(groupIds[k]);
                eid.setGroups(groups);
                examInfoDepartRepository.save(eid);
            }
        }
//        //添加试卷
//        if(testpapers.length>0){
//            for (int p = 0;p<testpapers.length;p++){
//                eit = new ExamInfoTextpaper();
//                eit.setCreatedDate(new Date());
//                eit.setLastModifiedDate(new Date());
//                eit.setExam(exam);
//
//            }
//        }

        return null;
    }
}
