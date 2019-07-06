package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.User;
import com.ayundao.entity.UserFile;
import com.ayundao.entity.UserRelation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: UserFileService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/5 15:10
 * @Description: 服务 - 用户资源
 * @Version: V1.0
 */
public interface UserFileService {

    /**
     * 保存用户上传的附件
     * @param file
     * @param type
     * @param userId
     * @param isPublic
     * @param userRelations
     * @param jsonResult
     * @return
     */
    JsonResult save(MultipartFile file, UserFile.TYPE type, User userId, boolean isPublic, List<UserRelation> userRelations, JsonResult jsonResult);

    /**
     * 下载用户文件
     * @param id
     * @return
     */
    UserFile find(String id);

    /**
     * 下载文件
     * @param userFile
     * @param req
     * @param resp
     */
    void download(UserFile userFile, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 删除用户资料
     * @param userFile
     */
    void delete(UserFile userFile);

    /**
     * 获取用户已分享和个人资料列表
     * @param id
     * @return
     */
    List<UserFile> getMySelfList(String id);

    /**
     * 查询待审核的资源列表
     * @return
     */
    List<UserFile> findByStatusIsWaiting();

    /**
     * 修改资源文件状态
     *
     * @param userFile
     * @param status
     * @return
     */
    UserFile examineUserFile(UserFile userFile, int status);

    /**
     * 获取资源分享列表
     * @return
     */
    List<UserFile> getShareList();
}
