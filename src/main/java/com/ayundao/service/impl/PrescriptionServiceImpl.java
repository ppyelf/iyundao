package com.ayundao.service.impl;

import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.PrescriptionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @ClassName: PrescriptionServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:41
 * @Description: 实现 - 处方
 * @Version: V1.0
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionFileRepository prescriptionFileRepository;

    @Autowired
    private PrescriptionIndexRepository prescriptionIndexRepository;

    @Autowired
    private PrescriptionRangeRepository prescriptionRangeRepository;

    @Autowired
    private PrescriptionUserIndexRepository prescriptionUserIndexRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Value("${server.upload}")
    private String uploadPath;

    @Override
    @Transactional
    public JsonResult saveFile(MultipartFile file, JsonResult jsonResult) {
        PrescriptionFile prescriptionFile = new PrescriptionFile();
        prescriptionFile.setCreatedDate(new Date());
        prescriptionFile.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, prescriptionFile, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        prescriptionFile.setUrl(map.get("url"));
        prescriptionFile.setSuffix(map.get("suffix"));
        prescriptionFile.setName(map.get("name"));
        prescriptionFile = prescriptionFileRepository.save(prescriptionFile);
        jsonResult.setData(JsonUtils.getJson(prescriptionFile));
        return jsonResult;
    }

    @Override
    public JsonResult deleteFile(String id) {
        PrescriptionFile file = prescriptionFileRepository.find(id);
        if (file == null) {
            return JsonResult.notFound("文件不存在或ID错误");
        }
        FileUtils.deleteFile(uploadPath + file.getUrl());
        prescriptionFileRepository.delete(file);
        return JsonResult.success();
    }

    @Override
    public Prescription save(String name, int total, String remark, String year, String[] fileIds, Subject subject, Depart depart, Groups groups, User user) {
        Prescription prescription = new Prescription();
        prescription.setCreatedDate(new Date());
        prescription.setLastModifiedDate(new Date());
        prescription.setName(name);
        prescription.setTotal(total);
        prescription.setRemark(remark);
        prescription.setYear(year);
        prescription = prescriptionRepository.save(prescription);

        //保存附件
        List<PrescriptionFile> files = prescriptionFileRepository.findByIds(fileIds);
        if (CollectionUtils.isNotEmpty(files)) {
            Set<PrescriptionFile> fileSet = new HashSet<>();
            for (PrescriptionFile file : files) {
                file.setPrescription(prescription);
                fileSet.add(file);
            }
            prescriptionFileRepository.saveAll(fileSet);
            prescription.setPrescriptionFiles(fileSet);
        }

        //保存医德范围
        Set<PrescriptionRange> prescriptionRanges = new HashSet<>();
        if (user == null) {
            List<UserRelation> userRelations = userRelationRepository.findBySubjectAndDepartOrGroups(subject.getId(),
                    depart == null ? null : depart.getId(),
                    groups == null ? null : groups.getId());
            for (UserRelation ur : userRelations) {
                PrescriptionRange prescriptionRange = new PrescriptionRange();
                prescriptionRange.setCreatedDate(new Date());
                prescriptionRange.setLastModifiedDate(new Date());
                prescriptionRange.setSubjectId(subject.getId());
                prescriptionRange.setDepartId(depart == null ? null : depart.getId());
                prescriptionRange.setGroupId(groups == null ? null : groups.getId());
                prescriptionRange.setUserId(ur.getUser().getId());
                prescriptionRange.setPrescription(prescription);
                prescriptionRange = prescriptionRangeRepository.save(prescriptionRange);
                prescriptionRanges.add(prescriptionRange);
            }
            prescription.setPrescriptionRanges(prescriptionRanges);
        } else {
            PrescriptionRange prescriptionRange = new PrescriptionRange();
            prescriptionRange.setCreatedDate(new Date());
            prescriptionRange.setLastModifiedDate(new Date());
            prescriptionRange.setSubjectId(subject.getId());
            prescriptionRange.setDepartId(depart == null ? null :depart.getId());
            prescriptionRange.setGroupId(groups == null ? null : groups.getId());
            prescriptionRange.setPrescription(prescription);
            prescriptionRangeRepository.save(prescriptionRange);
            prescriptionRanges.add(prescriptionRange);
        }
        prescription.setPrescriptionRanges(prescriptionRanges);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription find(String id) {
        return prescriptionRepository.find(id);
    }

    @Override
    public void delete(Prescription prescription) {
        if (prescription == null) {
            return ;
        }
        List<PrescriptionRange> prescriptionRanges = prescriptionRangeRepository.findByPrescriptionId(prescription.getId());
        List<PrescriptionFile> prescriptionFiles = prescriptionFileRepository.findByPrescriptionId(prescription.getId());
        List<PrescriptionIndex> prescriptionIndices = prescriptionIndexRepository.findByPrescriptionId(prescription.getId());
        List<PrescriptionUserIndex> prescriptionUserIndices = prescriptionUserIndexRepository.findByPrescriptionId(prescription.getId());

        prescriptionRangeRepository.deleteAll(prescriptionRanges);
        prescriptionFileRepository.deleteAll(prescriptionFiles);
        prescriptionIndexRepository.deleteAll(prescriptionIndices);
        prescriptionUserIndexRepository.deleteAll(prescriptionUserIndices);
        prescriptionRepository.delete(prescription);
    }

    @Override
    public Page<Prescription> findPage(Pageable pageable) {
        return prescriptionRepository.findPage(pageable);
    }

    @Override
    public PrescriptionIndex findPrescriptionIndexById(String fatherId) {
        return prescriptionIndexRepository.find(fatherId);
    }

    @Override
    public PrescriptionIndex savePrescriptionIndex(String name, String remark, PrescriptionIndex father, Prescription prescription) {
        PrescriptionIndex index = new PrescriptionIndex();
        index.setCreatedDate(new Date());
        index.setLastModifiedDate(new Date());
        index.setName(name);
        index.setRemark(remark);
        index.setPrescription(prescription);
        if (father != null) {
            index.setFather(father);
        }
        index.setCode(getIndexLastCode());
        index = prescriptionIndexRepository.save(index);


        return index;
    }

    @Override
    public List<PrescriptionIndex> findPrescriptionIndexByFatherIsNullForList() {
        return prescriptionIndexRepository.findPrescriptionIndexByFatherIsNullForList();
    }

    @Override
    public List<PrescriptionIndex> findPrescriptionIndexChild(String id) {
        return prescriptionIndexRepository.findPrescriptionIndexChild(id);
    }

    @Override
    public PrescriptionUserIndex savePrescriptionUserIndex(PrescriptionIndex index, User user, Prescription prescription, int score) {
        PrescriptionUserIndex userIndex = new PrescriptionUserIndex();
        userIndex.setCreatedDate(new Date());
        userIndex.setLastModifiedDate(new Date());
        userIndex.setPrescription(prescription);
        userIndex.setPrescriptionIndex(index);
        userIndex.setUserId(user.getId());
        userIndex.setScore(score);
        userIndex = prescriptionUserIndexRepository.save(userIndex);
        return userIndex;
    }

    /**
     * 获取code的最大值
     * @return
     */
    private int getIndexLastCode() {
        return prescriptionIndexRepository.getLastCode() + 1;
    }
}
