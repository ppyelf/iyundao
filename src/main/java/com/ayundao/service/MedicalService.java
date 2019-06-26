package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;
import org.springframework.web.multipart.MultipartFile;

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
}
