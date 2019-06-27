package com.ayundao.service;

import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName: MedicalService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:20
 * @Description: 服务 - 医德
 * @Version: V1.0
 */
public interface MedicalService {

    /**
     * 保存上传的医德附件
     * @param file
     * @return
     */
    JsonResult saveFile(MultipartFile file, JsonResult jsonResult);

    /**
     * 删除文件
     * @param id
     * @return
     */
    JsonResult deleteFile(String id);

    /**
     * 保存医德档案实体
     * @param name
     * @param total
     * @param remark
     * @param year
     * @param fileIds
     * @param subject
     * @param depart
     * @param groups
     * @param user
     * @return
     */
    Medical save(String name, int total, String remark, String year, String[] fileIds, Subject subject, Depart depart, Groups groups, User user);

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    Medical find(String id);

    /**
     * 删除实体
     * @param medical
     */
    void delete(Medical medical);

    /**
     * 获取医德档案分页集合
     * @param pageable
     * @return
     */
    Page<Medical> findPage(Pageable pageable);

    /**
     * 根据ID查询指标实体
     * @param fatherId
     * @return
     */
    MedicalIndex findMedicalIndexById(String fatherId);

    /**
     * 添加医德指标
     * @param name
     * @param remark
     * @param father
     * @return
     */
    MedicalIndex saveMedicalIndex(String name, String remark, MedicalIndex father, Medical medical);

    /**
     * 获取指标父级为空的集合
     * @return
     */
    List<MedicalIndex> findMedicalIndexByFatherIsNullForList();

    /**
     * 获取指标子集
     * @param id
     * @return
     */
    List<MedicalIndex> findMedicalIndexChild(String id);

    /**
     * 保存医德指标评价
     * @param index
     * @param user
     * @param medical
     * @param score
     * @return
     */
    MedicalUserIndex saveMedicalUserIndex(MedicalIndex index, User user, Medical medical, int score);

}
