package com.ayundao.service;

import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.entity.*;

import java.util.List;

/**
 * @ClassName: SpiritService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:04
 * @Description: 服务 - 党内精神
 * @Version: V1.0
 */
public interface SpiritService {

    /**
     * 上传党内精神附件
     * @param spiritFile
     * @return
     */
    SpiritFile createFile(SpiritFile spiritFile);

    /**
     * 删除文件
     * @param id
     * @return
     */
    void deleteFileById(String id);

    /**
     * 上传党内精神图片
     * @param spiritImage
     * @return
     */
    SpiritImage createImage(SpiritImage spiritImage);

    /**
     * 删除图片
     * @param id
     */
    void deleteImageById(String id);

    /**
     * 根据IDS查询文件实体
     * @param fileIds
     * @return
     */
    List<SpiritFile> findFileByIds(String[] fileIds);

    /**
     * 根据IDS查询图片实体
     * @param imageIds
     * @return
     */
    List<SpiritImage> findImageByIds(String[] imageIds);

    /**
     * 新建党内精神
     * @param name
     * @param time
     * @param content
     * @param spiritFiles
     * @param spiritImages
     * @param user
     * @param subject
     * @param depart
     * @param groups
     * @return
     */
    Spirit save(String name, String time, String content, List<SpiritFile> spiritFiles, List<SpiritImage> spiritImages, User user, Subject subject, Depart depart, Groups groups);

    /**
     * 根据ID查询实体信息
     * @param id
     * @return
     */
    Spirit find(String id);

    /**
     * 删除党内精神实体
     * @param id
     */
    void deleteSpiritById(String id);

    Page<Spirit> findPage(Pageable pageable);

    /**
     * 获取正文信息
     * @param id
     * @return
     */
    String getContentBySpiritId(String id);

    /**
     * 修改审核状态
     * @param soiritid
     * @param type
     */
    void updateState(String soiritid, int type);
}
