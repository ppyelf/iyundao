package com.ayundao.service.impl;

import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.SpiritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SpiritServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:05
 * @Description: 实现 - 党内精神
 * @Version: V1.0
 */
@Service
@Transactional
public class SpiritServiceImpl implements SpiritService {

    @Autowired
    private SpiritRepository spiritRepository;
    @Autowired
    private SpiritContentRepository spiritContentRepository;
    @Autowired
    private SpiritFileRepository spiritFileRepository;
    @Autowired
    private SpiritImageRepository spiritImageRepository;
    @Autowired
    private SpiritSubjectRepository spiritSubjectRepository;


    @Override
    public SpiritFile createFile(SpiritFile spiritFile) {
        return spiritFileRepository.save(spiritFile);
    }

    @Override
    public void deleteFileById(String id) {
        SpiritFile file = spiritFileRepository.find(id);
        spiritFileRepository.delete(file);
    }

    @Override
    public SpiritImage createImage(SpiritImage spiritImage) {
        return spiritImageRepository.save(spiritImage);
    }

    @Override
    public void deleteImageById(String id) {
        SpiritImage image = spiritImageRepository.find(id);
        spiritImageRepository.delete(image);
    }

    @Override
    public List<SpiritFile> findFileByIds(String[] fileIds) {
        return spiritFileRepository.findByIds(fileIds);
    }

    @Override
    public List<SpiritImage> findImageByIds(String[] imageIds) {
        return spiritImageRepository.findByIds(imageIds);
    }

    @Override
    public Spirit save(String name, String time, String content, List<SpiritFile> spiritFiles, List<SpiritImage> spiritImages, User user, Subject subject, Depart depart, Groups groups) {
        Spirit spirit = new Spirit();
        spirit.setCreatedDate(new Date());
        spirit.setLastModifiedDate(new Date());
        spirit.setName(name);
        spirit.setTime(time);
        spirit = spiritRepository.save(spirit);

        //保存正文
        SpiritContent sc = new SpiritContent();
        sc.setCreatedDate(new Date());
        sc.setLastModifiedDate(new Date());
        sc.setContent(content);
        sc.setSpirit(spirit);
        sc = spiritContentRepository.save(sc);
        spirit.setSpiritContent(sc);

        //批量保存附件
        Set<SpiritFile> files = new HashSet<>();
        for (SpiritFile file : spiritFiles) {
            file.setSpirit(spirit);
            files.add(spiritFileRepository.save(file));
        }
        spirit.setSpiritFiles(files);

        //批量保存图片
        Set<SpiritImage> images = new HashSet<>();
        for (SpiritImage spiritImage : spiritImages) {
            spiritImage.setSpirit(spirit);
            images.add(spiritImageRepository.save(spiritImage));
        }
        spirit.setSpiritImages(images);

        //保存发布机构
        SpiritSubject ss = new SpiritSubject();
        ss.setCreatedDate(new Date());
        ss.setLastModifiedDate(new Date());
        ss.setSubjectId(subject.getId());
        ss.setUserId(user.getId());
        ss.setSpirit(spirit);
        if (depart != null) {
            ss.setDepartId(depart.getId());
        } 
        if (groups != null) {
            ss.setGroupId(groups.getId());
        }
        ss = spiritSubjectRepository.save(ss);
        spirit.setSpiritSubject(ss);
        return spiritRepository.save(spirit);
    }

    @Override
    public Spirit find(String id) {
        return spiritRepository.find(id);
    }

    @Override
    public void deleteSpiritById(String id) {
        Spirit spirit = spiritRepository.find(id);
        if (spirit == null) return ;
        List<SpiritFile> spiritFiles = spiritFileRepository.findBySpiritId(spirit.getId());
        List<SpiritImage> spiritImages = spiritImageRepository.findBySpiritId(spirit.getId());
        SpiritSubject spiritSubjects = spiritSubjectRepository.findBySpiritId(spirit.getId());
        SpiritContent content = spiritContentRepository.findBySpiritId(spirit.getId());

        //删除
        spiritContentRepository.delete(content);
        spiritSubjectRepository.delete(spiritSubjects);
        spiritImageRepository.deleteAll(spiritImages);
        spiritFileRepository.deleteAll(spiritFiles);
        spiritRepository.delete(spirit);
    }

    @Override
    public Page<Spirit> findPage(Pageable pageable) {
        return spiritRepository.findPage(pageable);
    }

    @Override
    public String getContentBySpiritId(String id) {
        return spiritContentRepository.getContentBySpiritId(id);
    }
}
