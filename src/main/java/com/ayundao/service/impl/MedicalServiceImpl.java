package com.ayundao.service.impl;

import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.MedicalService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @ClassName: MedicalServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:22
 * @Description: 实现 - 医德
 * @Version: V1.0
 */
@Service
@Transactional
public class MedicalServiceImpl implements MedicalService {

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private MedicalFileRepository medicalFileRepository;

    @Autowired
    private MedicalIndexRepository medicalIndexRepository;

    @Autowired
    private MedicalRangeRepository medicalRangeRepository;

    @Autowired
    private MedicalUserIndexRepository medicalUserIndexRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Value("${server.upload}")
    private String uploadPath;

    @Override
    @Transactional
    public JsonResult saveFile(MultipartFile file, JsonResult jsonResult) {
        MedicalFile medicalFile = new MedicalFile();
        medicalFile.setCreatedDate(new Date());
        medicalFile.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, medicalFile, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        medicalFile.setUrl(map.get("url"));
        medicalFile.setSuffix(map.get("suffix"));
        medicalFile.setName(map.get("name"));
        medicalFile = medicalFileRepository.save(medicalFile);
        jsonResult.setData(JsonUtils.getJson(medicalFile));
        return jsonResult;
    }

    @Override
    public JsonResult deleteFile(String id) {
        MedicalFile file = medicalFileRepository.find(id);
        if (file == null) {
            return JsonResult.notFound("文件不存在或ID错误");
        }
        FileUtils.deleteFile(uploadPath + file.getUrl());
        medicalFileRepository.delete(file);
        return JsonResult.success();
    }

    @Override
    public Medical save(String name, int total, String remark, String year, String[] fileIds, Subject subject, Depart depart, Groups groups, User user) {
        Medical medical = new Medical();
        medical.setCreatedDate(new Date());
        medical.setLastModifiedDate(new Date());
        medical.setName(name);
        medical.setTotal(total);
        medical.setRemark(remark);
        medical.setYear(year);
        medical = medicalRepository.save(medical);

        //保存附件
        List<MedicalFile> files = medicalFileRepository.findByIds(fileIds);
        if (CollectionUtils.isNotEmpty(files)) {
            Set<MedicalFile> fileSet = new HashSet<>();
            for (MedicalFile file : files) {
                file.setMedical(medical);
                fileSet.add(file);
            }
            medicalFileRepository.saveAll(fileSet);
            medical.setMedicalFiles(fileSet);
        }

        //保存医德范围
        Set<MedicalRange> medicalRanges = new HashSet<>();
        if (user == null) {
            List<UserRelation> userRelations = userRelationRepository.findBySubjectAndDepartOrGroups(subject.getId(),
                    depart == null ? null : depart.getId(),
                    groups == null ? null : groups.getId());
            for (UserRelation ur : userRelations) {
                MedicalRange medicalRange = new MedicalRange();
                medicalRange.setCreatedDate(new Date());
                medicalRange.setLastModifiedDate(new Date());
                medicalRange.setSubjectId(subject.getId());
                medicalRange.setDepartId(depart == null ? null : depart.getId());
                medicalRange.setGroupId(groups == null ? null : groups.getId());
                medicalRange.setUserId(ur.getUser().getId());
                medicalRange.setMedical(medical);
                medicalRange = medicalRangeRepository.save(medicalRange);
                medicalRanges.add(medicalRange);
            }
            medical.setMedicalRanges(medicalRanges);
        } else {
            MedicalRange medicalRange = new MedicalRange();
            medicalRange.setCreatedDate(new Date());
            medicalRange.setLastModifiedDate(new Date());
            medicalRange.setSubjectId(subject.getId());
            medicalRange.setDepartId(depart == null ? null :depart.getId());
            medicalRange.setGroupId(groups == null ? null : groups.getId());
            medicalRange.setMedical(medical);
            medicalRangeRepository.save(medicalRange);
            medicalRanges.add(medicalRange);
        }
        medical.setMedicalRanges(medicalRanges);
        return medicalRepository.save(medical);
    }

}
