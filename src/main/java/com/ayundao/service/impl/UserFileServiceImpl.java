package com.ayundao.service.impl;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.UserFileRepository;
import com.ayundao.repository.UserFileToRepository;
import com.ayundao.service.UserFileService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @ClassName: UserFileServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/5 15:10
 * @Description: 实现 - 用户资料
 * @Version: V1.0
 */
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileRepository userFileRepository;

    @Autowired
    private UserFileToRepository userFileToRepository;

    @Value("${server.upload}")
    private String uploadPath;

    @Override
    @Transactional
    public JsonResult save(MultipartFile file, UserFile.TYPE type, User user, boolean isPublic, List<UserRelation> userRelations, JsonResult jsonResult) {
        UserFile userFile = new UserFile();
        userFile.setCode(getLastCode());
        userFile.setUser(user);
        userFile.setStatus(UserFile.STATUS.myself);
        userFile.setType(type);
        Map<String, String> map = FileUtils.uploadUserFile(file, userFile, uploadPath,user.getCode());
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        userFile.setUrl(map.get("url"));
        userFile.setSuffix(map.get("suffix"));
        userFile.setName(map.get("name"));
        userFile = userFileRepository.save(userFile);
        if (isPublic) {
            userFile.setPublic(true);
            userFile.setStatus(UserFile.STATUS.waiting);
            Set<UserFileTo> set = new HashSet<>();
            for (UserRelation ur : userRelations) {
                Subject subject = ur.getSubject();
                Depart depart = ur.getDepart();
                Groups groups = ur.getGroups();
                UserFileTo uft = new UserFileTo();
                uft.setUserFile(userFile);
                uft.setSubject(new BaseComponent(subject.getId(), subject.getName()));
                uft.setUser(new BaseComponent(user.getId(), user.getName()));
                
                if (depart != null) {
                    uft.setDepart(new BaseComponent(depart.getId(), depart.getName()));
                }else {
                    uft.setGroup(new BaseComponent(groups.getId(), groups.getName()));
                }

                uft = userFileToRepository.save(uft);
                set.add(uft);
            }
            userFile.setUserFileTo(set);
            userFile = userFileRepository.save(userFile);
        }
        jsonResult.setData(JsonUtils.getJson(userFile));
        return jsonResult;
    }

    @Override
    public UserFile find(String id) {
        return userFileRepository.find(id);
    }

    @Override
    public void download(UserFile userFile, HttpServletRequest req, HttpServletResponse resp) {
        String path = uploadPath + userFile.getUrl();
        FileUtils.download(path, req, resp);
    }

    @Override
    public void delete(UserFile userFile) {
        FileUtils.deleteFile(uploadPath + userFile.getUrl());
        userFileRepository.delete(userFile);
    }

    @Override
    public List<UserFile> getMySelfList(String id) {
        List<UserFile> userFiles = userFileRepository.findByUserIdAndStatusIsNotWaiting(id);
        for (UserFile userFile : userFiles) {
            userFile.setUrl(uploadPath + userFile.getUrl());
        }
        return CollectionUtils.isEmpty(userFiles) ? new ArrayList<>() : userFiles;
    }

    @Override
    public List<UserFile> findByStatusIsWaiting() {
        return userFileRepository.findByStatus(UserFile.STATUS.waiting);
    }

    @Override
    public UserFile examineUserFile(UserFile userFile, int status) {
        UserFile.STATUS s = null;
        for (UserFile.STATUS ss : UserFile.STATUS.values()) {
            if (ss.ordinal() == status) {
                s = ss;
                break;
            }
        }
        userFile.setStatus(s);
        return userFileRepository.save(userFile);
    }

    @Override
    public List<UserFile> getShareList() {
        return userFileRepository.findByStatus(UserFile.STATUS.share);
    }

    private int getLastCode() {
        return userFileRepository.getLastCode() + 1;
    }
}
