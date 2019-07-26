package com.ayundao.service.impl;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.ExamineService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @ClassName: ExamineServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 9:02
 * @Description: 实现 - 审批
 * @Version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExamineServiceImpl implements ExamineService {

    @Autowired
    private ExamineRepository examineRepository;

    @Autowired
    private ExamineProcessRepository examineProcessRepository;

    @Autowired
    private ExamineFileRepository examineFileRepository;

    @Autowired
    private ExamineImageRepository examineImageRepository;

    @Autowired
    private ExamineTextRepository examineTextRepository;

    @Value("${server.upload}")
    private String uploadPath;

    @Override
    public JsonResult saveFile(MultipartFile file, JsonResult jsonResult, String uploadPath) {
        ExamineFile examineFile = new ExamineFile();
        examineFile.setCreatedDate(new Date());
        examineFile.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, examineFile, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        examineFile.setUrl(map.get("url"));
        examineFile.setSuffix(map.get("suffix"));
        examineFile.setName(map.get("name"));
        examineFile = examineFileRepository.save(examineFile);
        jsonResult.setData(JsonUtils.getJson(examineFile));
        return jsonResult;
    }

    @Override
    public ExamineFile findFile(String id) {
        return examineFileRepository.find(id);
    }

    @Override
    public void delFile(ExamineFile file) {
        examineFileRepository.delete(file);
    }

    @Override
    public JsonResult saveImage(MultipartFile file, JsonResult jsonResult, String uploadPath) {
        ExamineImage examineImage = new ExamineImage();
        examineImage.setCreatedDate(new Date());
        examineImage.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, examineImage, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        examineImage.setUrl(map.get("url"));
        examineImage.setSuffix(map.get("suffix"));
        examineImage.setName(map.get("name"));
        examineImage = examineImageRepository.save(examineImage);
        jsonResult.setData(JsonUtils.getJson(examineImage));
        return jsonResult;
    }

    @Override
    public void delImage(ExamineImage image) {
        examineImageRepository.delete(image);
    }

    @Override
    public ExamineImage findImage(String id) {
        return examineImageRepository.find(id);
    }

    @Override
    public List<ExamineImage> findImageByIds(String[] ids) {
        return examineImageRepository.findByIds(ids);
    }

    @Override
    public List<ExamineFile> findFileByIds(String[] ids) {
        return examineFileRepository.findByIds(ids);
    }

    @Override
    @Transactional
    public Examine saveLeave(UserRelation userRelation, List<UserRelation> list, String startTime, String endTime, Examine.REASON type, String cause, List<ExamineImage> images) {
        Examine examine = new Examine();
        examine.setCreatedDate(new Date());
        examine.setLastModifiedDate(new Date());
        examine.setType(Examine.EXAMINE_TYPE.leave);
        examine.setReasonType(type);
        examine.setStartTime(startTime);
        examine.setEndTime(endTime);
        examine = examineRepository.save(examine);

        //事由
        ExamineText et = new ExamineText();
        et.setCreatedDate(new Date());
        et.setLastModifiedDate(new Date());
        et.setCause(cause);
        et.setExamine(examine);
        et = examineTextRepository.save(et);
        examine.setExamineText(et);
        
        //图片
        if (CollectionUtils.isNotEmpty(images)) {
            Set<ExamineImage> imageSet = new HashSet<>();
            for (ExamineImage image : images) {
                image.setExamine(examine);
                image = examineImageRepository.save(image);
                imageSet.add(image);
            }
            examine.setExamineImages(imageSet);
        } 

        Set<ExamineProcess> epSet = new HashSet<>();
        //发起人
        ExamineProcess sep = new ExamineProcess();
        sep.setCreatedDate(new Date());
        sep.setLastModifiedDate(new Date());
        sep.setSubject(new BaseComponent(userRelation.getSubject().getId(), userRelation.getSubject().getName()));
        sep.setDepart(userRelation.getDepart() == null ? null : new BaseComponent(userRelation.getDepart().getId(), userRelation.getDepart().getName()));
        sep.setGroup(userRelation.getGroups() == null ? null : new BaseComponent(userRelation.getGroups().getId(), userRelation.getGroups().getName()));
        sep.setUser(new BaseComponent(userRelation.getUser().getId(), userRelation.getUser().getName()));
        sep.setExamine(examine);
        sep.setType(ExamineProcess.PERSON_TYPE.Initiator);
        sep.setStatus(ExamineProcess.PROCESS_STATUS.Audit_in_progress);
        sep.setComment(null);
        sep = examineProcessRepository.save(sep);
        epSet.add(sep);

        //审核人
        int level = 1;
        saveExamineProcess(list, level++, examine, epSet, ExamineProcess.PERSON_TYPE.Examiner);

        examine.setExamineProcesses(epSet);
        return examineRepository.save(examine);
    }

    @Override
    public Examine saveReply(UserRelation userRelation, List<UserRelation> list, List<UserRelation> cList, Examine.REASON reason, String cause, String detail, List<ExamineImage> images, List<ExamineFile> files) {
        Examine examine = new Examine();
        examine.setCreatedDate(new Date());
        examine.setLastModifiedDate(new Date());
        examine.setType(Examine.EXAMINE_TYPE.reply);
        examine.setReasonType(reason);
        examine.setStartTime(null);
        examine.setEndTime(null);
        examine = examineRepository.save(examine);

        //事由和详情
        ExamineText et = new ExamineText();
        et.setCreatedDate(new Date());
        et.setLastModifiedDate(new Date());
        et.setCause(cause);
        et.setExamine(examine);
        et.setDetail(detail);
        et = examineTextRepository.save(et);
        examine.setExamineText(et);


        //图片
        Set<ExamineImage> imageSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(images)) {
            for (ExamineImage image : images) {
                image.setExamine(examine);
                image = examineImageRepository.save(image);
                imageSet.add(image);
            }
            examine.setExamineImages(imageSet);
        }

        //附件
        Set<ExamineFile> fileSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(files)) {
            for (ExamineFile file : files) {
                file.setExamine(examine);
                file = examineFileRepository.save(file);
                fileSet.add(file);
            }
            examine.setExamineFiles(fileSet);
        }

        Set<ExamineProcess> epSet = new HashSet<>();
        //发起人
        ExamineProcess sep = new ExamineProcess();
        sep.setCreatedDate(new Date());
        sep.setLastModifiedDate(new Date());
        sep.setSubject(new BaseComponent(userRelation.getSubject().getId(), userRelation.getSubject().getName()));
        sep.setDepart(userRelation.getDepart() == null ? null : new BaseComponent(userRelation.getDepart().getId(), userRelation.getDepart().getName()));
        sep.setGroup(userRelation.getGroups() == null ? null : new BaseComponent(userRelation.getGroups().getId(), userRelation.getGroups().getName()));
        sep.setUser(new BaseComponent(userRelation.getUser().getId(), userRelation.getUser().getName()));
        sep.setExamine(examine);
        sep.setType(ExamineProcess.PERSON_TYPE.Initiator);
        sep.setStatus(ExamineProcess.PROCESS_STATUS.Audit_in_progress);
        sep.setComment(null);
        sep = examineProcessRepository.save(sep);
        epSet.add(sep);

        //审核人
        int level = 1;
        saveExamineProcess(list, level, examine, epSet, ExamineProcess.PERSON_TYPE.Examiner);

        //抄送人
        if (CollectionUtils.isNotEmpty(cList)) {
            saveExamineProcess(cList, level, examine, epSet, ExamineProcess.PERSON_TYPE.Copier);
        }

        examine.setExamineProcesses(epSet);
        return examineRepository.save(examine);
    }

    @Override
    public ExamineProcess findProcessByExamineIdAndUserId(String id, String userId) {
        return examineProcessRepository.findProcessByExamineIdAndUserId(id, userId);
    }

    @Override
    public boolean checkPreviousStatus(String id, int level, ExamineProcess.PROCESS_STATUS status) {
        ExamineProcess ep = examineProcessRepository.findByExamineIdAndLevel(id, level, status);
        return ep == null ? false : true;
    }

    @Override
    public ExamineProcess apply(ExamineProcess ep, ExamineProcess.PROCESS_STATUS status, String comment) {
        ep.setStatus(status);
        ep.setComment(comment);
        Examine examine = ep.getExamine();
        List<ExamineProcess> list = examineProcessRepository.findByExamineIdExcludeCurrent(examine.getId(), ep.getId());
        switch (status.getIndex()) {
            case  1://同意
                if (ep.getLevel() == examineProcessRepository.maxLevel(examine.getId())) {
                    for (ExamineProcess examineProcess : list) {
                        if (examineProcess.getLevel() == 0 || ExamineProcess.PERSON_TYPE.Copier == examineProcess.getType()) {
                            examineProcess.setStatus(ExamineProcess.PROCESS_STATUS.pass);
                        }
                    }
                    examineProcessRepository.saveAll(list);
                }
                break;
            case  2://拒绝
                for (ExamineProcess examineProcess : list) {
                    if (examineProcess.getLevel() == 0 || ep.getLevel() < examineProcess.getLevel()) {
                        examineProcess.setStatus(ExamineProcess.PROCESS_STATUS.refuse);
                    }
                }
                examineProcessRepository.saveAll(list);
                break;
        }
        return examineProcessRepository.save(ep);
    }

    /**
     * 保存审核流程
     * @param list
     * @param level
     * @param examine
     * @param set
     */
    private void saveExamineProcess(List<UserRelation> list, int level, Examine examine, Set<ExamineProcess> set, ExamineProcess.PERSON_TYPE type) {
        for (UserRelation r : list) {
            ExamineProcess eep = new ExamineProcess();
            eep.setCreatedDate(new Date());
            eep.setCreatedDate(new Date());
            eep.setLastModifiedDate(new Date());
            eep.setSubject(new BaseComponent(r.getSubject().getId(), r.getSubject().getName()));
            eep.setDepart(r.getDepart() == null ? null : new BaseComponent(r.getDepart().getId(), r.getDepart().getName()));
            eep.setGroup(r.getGroups() == null ? null : new BaseComponent(r.getGroups().getId(), r.getGroups().getName()));
            eep.setUser(new BaseComponent(r.getUser().getId(), r.getUser().getName()));
            eep.setExamine(examine);
            eep.setType(type);
            eep.setStatus(type.equals(ExamineProcess.PERSON_TYPE.Copier) ? ExamineProcess.PROCESS_STATUS.view : ExamineProcess.PROCESS_STATUS.Audit_in_progress);
            eep.setComment(null);
            eep.setLevel(level++);
            eep = examineProcessRepository.save(eep);
            set.add(eep);
        }
    }

    @Override
    public Examine find(String id) {
        return examineRepository.find(id);
    }

    @Override
    public void delete(Examine examine) {
        List<ExamineImage> images = examineImageRepository.findByExamineId(examine.getId());
        List<ExamineFile> files = examineFileRepository.findByExamineId(examine.getId());
        for (ExamineFile file : files) {
            FileUtils.deleteFile(uploadPath + file.getUrl());
        }
        for (ExamineImage image : images) {
            FileUtils.deleteFile(uploadPath + image.getUrl());
        }
        examineRepository.delete(examine);
    }

    @Override
    public List<ExamineProcess> findProcessByUserIdAndType(String userId, int type) {
        return examineProcessRepository.findProcessByUserIdAndType(userId, type);
    }



}
