package com.ayundao.service.impl;

import com.ayundao.entity.Activity;
import com.ayundao.entity.Assessment;
import com.ayundao.entity.AssessmentFile;
import com.ayundao.entity.AssessmentIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: AssessmentService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/7 20:14
 * @Description: 服务 - 考核
 * @Version: V1.0
 */
public interface AssessmentService {

    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<Assessment> findAllForPage(Pageable pageable);

    /**
     * 存储考核未见
     * @param assessmentId
     * @param file
     * @return
     */
    AssessmentFile saveFile(String assessmentId, AssessmentFile file);

    /**
     * 根据IDS集合获取文件集合
     * @param ids
     * @return
     */
    List<AssessmentFile> findFilesByIds(String[] ids);

    /**
     * 删除审核文件集合
     * @param files
     */
    void delFiles(List<AssessmentFile> files);

    /**
     * 根据id获取指标实体
     * @param fatherId
     * @return
     */
    AssessmentIndex findIndexById(String fatherId);

    /**
     * 保存指标
     * @param assessment
     * @param index
     * @return
     */
    AssessmentIndex saveIndex(Assessment assessment, AssessmentIndex index);

    /**
     * 根据ids集合查询指标集合
     * @param ids
     * @return
     */
    List<AssessmentIndex> findIndexByIds(String[] ids);

    /**
     * 删除指标集合
     * @param list
     */
    void delIndexs(List<AssessmentIndex> list);

    /**
     * 保存实体
     * @param assessment
     * @param index
     * @param file
     * @return
     */
    Assessment save(Assessment assessment, List<AssessmentIndex> index, List<AssessmentFile> file, String subjectId, String departId, String groupId, String userGroupId);

    /**
     * 删除实体
     * @param id
     */
    void delete(String id);

    /**
     * 根据ID获取实体信息
     * @param id
     * @return
     */
    Assessment find(String id);

    /**
     * 修改考核实体
     * @param assessment
     * @param index
     * @param file
     * @param subjectId
     * @param departId
     * @param groupId
     * @param userGroupId
     * @return
     */
    Assessment modify(Assessment assessment, List<AssessmentIndex> index, List<AssessmentFile> file, String subjectId, String departId, String groupId, String userGroupId);
}
