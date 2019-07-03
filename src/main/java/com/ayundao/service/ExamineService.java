package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName: ExamineService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 9:02
 * @Description: 服务 - 审批
 * @Version: V1.0
 */
public interface ExamineService {

    /**
     * 保存文件
     * @param file
     * @return
     */
    JsonResult saveFile(MultipartFile file, JsonResult jsonResult, String uploadPath);

    /**
     * 查询附件实体
     * @param id
     * @return
     */
    ExamineFile findFile(String id);

    /**
     * 删除附件
     * @param file
     */
    void delFile(ExamineFile file);

    /**
     * 上传图片
     * @param file
     * @param jsonResult
     * @param uploadPath
     * @return
     */
    JsonResult saveImage(MultipartFile file, JsonResult jsonResult, String uploadPath);

    /**
     * 删除图片
     * @param image
     */
    void delImage(ExamineImage image);

    /**
     * 查询图片
     * @param id
     * @return
     */
    ExamineImage findImage(String id);

    /**
     * 根据ids查询实体集合
     * @param ids
     * @return
     */
    List<ExamineImage> findImageByIds(String[] ids);

    /**
     * 根据ids查询实体集合
     * @param ids
     * @return
     */
    List<ExamineFile> findFileByIds(String[] ids);

    /**
     * 添加请假批示
     * @param userRelation
     * @param list
     * @param startTime
     * @param endTime
     * @param type
     * @param cause
     * @param images
     * @return
     */
    Examine saveLeave(UserRelation userRelation, List<UserRelation> list, String startTime, String endTime, Examine.REASON type, String cause, List<ExamineImage> images);

    /**
     * 查询审批实体
     * @param id
     * @return
     */
    Examine find(String id);

    /**
     * 删除审批实体
     * @param examine
     */
    void delete(Examine examine);

    /**
     * 查询用户的请假审批
     * @param userId
     * @return
     */
    List<ExamineProcess> findProcessByUserId(String userId);

    /**
     * 添加请示批复
     * @param userRelation
     * @param list
     * @param cList
     * @param reason
     * @param cause
     * @param detail
     * @param images
     * @param files
     * @return
     */
    Examine saveReply(UserRelation userRelation, List<UserRelation> list, List<UserRelation> cList, Examine.REASON reason, String cause, String detail, List<ExamineImage> images, List<ExamineFile> files);
}
