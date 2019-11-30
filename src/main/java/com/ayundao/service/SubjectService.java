package com.ayundao.service;

import com.ayundao.entity.Subject;
import com.ayundao.entity.UserGroup;

import java.util.List;

/**
 * @ClassName: SubjectService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:28
 * @Description: 服务 -- 机构
 * @Version: V1.0
 */
public interface SubjectService {

    /**
     * 获取所有机构实体
     * @return
     */
    List<Subject> findAll();

    /**
     * 根据id获取实体信息
     * @param id
     * @return
     */
    Subject find(String id);

    /**
     * 保存实体
     * @param subject
     * @return
     */
    Subject save(Subject subject);

    /**
     * 分配组织和
     * @param departIds
     * @param groupIds
     * @return
     */
    Subject saveDepartAndGroup(Subject subject, String[] departIds, String[] groupIds);

    /**
     * 检测对象是否存在
     * @param id
     * @return
     */
    boolean exists(String id);

    /**
     * 检测编号是否存在
     * @param code
     * @return
     */
    boolean existsCode(String code);

    /**
     * 根据CODE查询实体
     * @param code
     * @return
     */
    Subject findByCode(String code);

    /**
     * 查找所有实体
     * @return
     */
    List<Subject> findbyIds(String[] subjectIds);

    /**
     * 获取个人机构列表
     * @param userId
     * @return
     */
    List<Subject> findMySubjectByUserId(String userId);

    /**
     * 根据名字查询实体信息
     * @param val
     * @return
     */
    Subject findByName(String val);
}
