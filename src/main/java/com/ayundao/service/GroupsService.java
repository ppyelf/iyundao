package com.ayundao.service;

import com.ayundao.entity.Depart;
import com.ayundao.entity.Groups;

import java.util.List;

/**
 * @ClassName: GroupsService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 15:14
 * @Description: 服务 - 小组
 * @Version: V1.0
 */
public interface GroupsService {

    /**
     * 根据机构ID获取小组集合
     * @param subjectId
     * @return
     */
    List<Groups> findBySubjectId(String subjectId);

    /**
     * 获取小组集合
     * @return
     */
    List<Groups> getList();

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    Groups findById(String id);

    /**
     * 保存实体
     * @param groups
     * @return
     */
    Groups save(Groups groups);

    /**
     * 根据IDS获取集合信息
     * @param groupIds
     * @return
     */
    List<Groups> findbyIds(String[] groupIds);

    List<Groups> saveAll(List<Groups> groups);

    /**
     * 获取未分配的组织列表
     * @return
     */
    List<Groups> findSubjectIsNull();

    /**
     * 获取子集小组
     * @param id
     * @return
     */
    List<Groups> findByFatherId(String id);

    /**
     * 获取没有父级的机构列表
     * @return
     */
    List<Groups> getListByFatherIsNull();

    /**
     * 根据subjectId获取所有父级实体集合
     * @param subjectId
     * @return
     */
    List<Groups> findBySubjectIdAndFatherIsNull(String subjectId);
}
