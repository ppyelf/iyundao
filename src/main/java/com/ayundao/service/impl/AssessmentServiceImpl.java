package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.AssessmentService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName: AssessmentServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/10
 * @Description: 实现 - 考核
 * @Version: V1.0
 */
@Service
@Transactional

public class AssessmentServiceImpl  implements AssessmentService{

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private  GroupsRepository groupsRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PioneerIndexRepository pioneerIndexRepository;

    @Autowired
    private AssessmentRangeRepository assessmentRangeRepository;

    @Autowired
    private AssessmentFileRepository assessmentFileRepository;

    @Autowired
    private AssessmentImageRepository assessmentImageRepository;

    @Autowired
    private AssessmentIndexRepository assessmentIndexRepository;

    @Override
    public List<Assessment> findAll() {
        return (List<Assessment>)assessmentRepository.findAll();
    }

    @Override
    public Page<Assessment> findAllForPage(Pageable pageable) {
        return assessmentRepository.findPage(pageable);
    }

    @Override
    public Assessment saveAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    @Transactional
    public Assessment saveAssessmentRange(Assessment assessment,List<AssessmentFile> af,List<AssessmentImage> ai, String[] subjectIds, String[] userGroupIds, String[] departIds, String[] groupIds, String[] userids) {
        Set<AssessmentFile> fileSet = new HashSet<>();
        Set<AssessmentImage> imageSet = new HashSet<>();
            for (AssessmentFile assessmentFile :af){
                assessmentFile.setAssessment(assessment);
                fileSet.add(assessmentFile);
            }
            assessmentFileRepository.saveAll(fileSet);
            for (AssessmentImage assessmentImage : ai){
                assessmentImage.setAssessment(assessment);
                imageSet.add(assessmentImage);
            }
            assessmentImageRepository.saveAll(imageSet);
            for (int i = 0;i<subjectIds.length;i++){
                duixiang(assessment,0,subjectIds[i]);
            }
            for (int j =0;j<userGroupIds.length;j++){
                duixiang(assessment,1,userGroupIds[j]);
            }
            for (int k =0;k<departIds.length;k++){
                duixiang(assessment,2,departIds[k]);
            }
            for (int h =0;h<groupIds.length;h++){
                duixiang(assessment,3,groupIds[h]);
            }
            for (int p=0;p<userids.length;p++){
                duixiang(assessment,4,userids[p]);
            }
        return assessment;
    }

    @Override
    public Assessment findById(String assessmentid) {
        return assessmentRepository.find(assessmentid);
    }

    @Override
    public AssessmentFile saveFile(AssessmentFile file) {
        return assessmentFileRepository.save(file);
    }

    @Override
    public void delFileByIds(String[] ids) {
        List<AssessmentFile> files =assessmentFileRepository.findByIds(ids);
        assessmentFileRepository.deleteAll(files);
    }

    @Override
    public AssessmentImage saveImage(AssessmentImage image) {
        return assessmentImageRepository.save(image);
    }

    @Override
    public void delImage(String[] ids) {
        List<AssessmentImage> images = assessmentImageRepository.findByIds(ids);
        assessmentImageRepository.deleteAll(images);
    }

    @Override
    public void saveAssessmentIndex(Assessment assessment, List<Map<String, String>> mapList) {
        for (Map<String, String> map : mapList) {
            AssessmentIndex ai =new AssessmentIndex();
            ai.setCreatedDate(new Date());
            ai.setLastModifiedDate(new Date());
            ai.setAssessment(assessment);
            ai.setSortedid(map.get("sortedid"));
            ai.setLname(map.get("lname"));
            ai.setSname(map.get("sname"));
            ai.setParcode(map.get("parcode"));
            ai.setNorder(map.get("norder"));
            ai.setIsuse(map.get("isuse"));
            assessmentIndexRepository.save(ai);
        }
    }

    @Override
    public List<AssessmentFile> findByFileIds(String[] assessmentFiles) {
        return assessmentFileRepository.findByIds(assessmentFiles);
    }

    @Override
    public List<AssessmentImage> findByImageIds(String[] assessmentImages) {
        return assessmentImageRepository.findByIds(assessmentImages);
    }

    @Override
    public List<AssessmentIndex> findIndexByAssessmentId(String id) {

        return assessmentIndexRepository.findIndexByAssessmentId(id);
    }

    @Override
    public void delAll(List<AssessmentIndex> assessmentIndices) {
        assessmentIndexRepository.deleteAll(assessmentIndices);
    }

    @Override
    public void deleteAssessment(Assessment assessment) {
        assessmentRepository.delete(assessment);
    }

    @Override
    public List<AssessmentRange> findRangeById(String id) {

        return assessmentRangeRepository.findRangeById(id);
    }

    @Override
    public List<AssessmentFile> findFileById(String id) {

        return assessmentFileRepository.findFileById(id);
    }

    @Override
    public List<AssessmentImage> findImageById(String id) {
        return assessmentImageRepository.findImageById(id);
    }

    @Override
    public List<AssessmentIndex> findIndexById(String id) {
        return assessmentIndexRepository.findIndexByAssessmentId(id);
    }

    @Override
    public JSONObject showAssessment(Assessment assessment, List<AssessmentRange> ranges, List<AssessmentFile> files, List<AssessmentImage> images, List<AssessmentIndex> indices) {
        JSONObject obj = new JSONObject();
        JSONArray File = new JSONArray();
        JSONArray Image = new JSONArray();
        JSONArray Indices = new JSONArray();
        JSONObject object;
        obj.put("id",assessment.getId());
        obj.put("number",assessment.getNumber());
        obj.put("name",assessment.getName());
        obj.put("type",assessment.getType());
        obj.put("total",assessment.getTotal());
        obj.put("starttime",assessment.getStartTime());
        obj.put("endtime",assessment.getEndTime());
        obj.put("remark",assessment.getRemark());
        JSONArray subject =new JSONArray();
        JSONArray usergroup = new JSONArray();
        JSONArray depart = new JSONArray();
        JSONArray groups = new JSONArray();
        JSONArray users = new JSONArray();
        //选择部门组织人员遍历拿到数据
        for (AssessmentRange ar : ranges ){
            if (ar.getSubjectId()!=null){
                    subject.add(ar.getSubjectId());
            }
            if (ar.getUserGroupId()!=null){
                usergroup.add(ar.getUserGroupId());
            }
            if (ar.getDepartId()!=null){
                depart.add(ar.getDepartId());
            }
            if (ar.getGroupId()!=null){
                groups.add(ar.getGroupId());
            }
            if (ar.getUserId()!=null){
                users.add(ar.getUserId());
            }
        }
        object = new JSONObject();
        object.put("subject",subject);
        object.put("usergroup",usergroup);
        object.put("depart",depart);
        object.put("group",groups);
        object.put("users",users);
        obj.put("deption",object);
        for (AssessmentFile af : files){
            File.add(JsonUtils.getJson(af));
        }
        obj.put("File",File);
        for (AssessmentImage ai : images){
            Image.add(JsonUtils.getJson(ai));
        }
        obj.put("Image",Image);
        for (AssessmentIndex aid : indices){
            Indices.add(JsonUtils.getJson(aid));
        }
        obj.put("Index",Indices);
        return obj;
    }



    @Override
    public AssessmentIndex findByIndexId(String id) {
      return  assessmentIndexRepository.find(id);
    }

    @Override
    public void delByIndex(AssessmentIndex assessmentIndex) {
        assessmentIndexRepository.delete(assessmentIndex);
    }

    @Override
    public AssessmentIndex findSnameBySortedid(String parcode) {
        return assessmentIndexRepository.findbyparcode(parcode);
    }

    @Override
    public Page<Assessment> findAssessByProperty(Pageable pageable) {

        return assessmentRepository.findPage(pageable);
    }


    //抽取公共部分
    @Transactional
    public void duixiang(Assessment assessment,int i,String str){
        AssessmentRange ar= new AssessmentRange();
        ar.setAssessment(assessment);
        ar.setCreatedDate(new Date());
        ar.setLastModifiedDate(new Date());
        if(i ==0){
            ar.setSubjectId(str);
        }else if (i==1){
            ar.setUserGroupId(str);
        }else if (i==2){
            ar.setDepartId(str);
        }else if(i==3){
            ar.setGroupId(str);
        }else if(i==4){
            ar.setUserId(str);
        }
        assessmentRangeRepository.save(ar);
    }
}
