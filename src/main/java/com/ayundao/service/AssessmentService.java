package com.ayundao.service;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssessmentService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/10
 * @Description: 实现 - 考核
 * @Version: V1.0
 */
public interface AssessmentService {

    /**
     * 查询所有考核
     * @return
     */
    List<Assessment> findAll();

    /**
     * 查询所有考核分页
     * @return
     */
    Page<Assessment> findAllForPage(Pageable pageable);


    /**
     * 添加考核
     * @param assessment
     * @return
     */
    Assessment saveAssessment(Assessment assessment);

    /**
     * 增加考核组织关系
     * @param assessment
     * @param subjectIds
     * @param userGroupIds
     * @param departIds
     * @param groupIds
     * @param userids
     */
    Assessment saveAssessmentRange(Assessment assessment, List<AssessmentFile> af,List<AssessmentImage> ai  ,String[] subjectIds, String[] userGroupIds, String[] departIds, String[] groupIds, String[] userids);

    /**
     * 根据id找到实体
     * @param assessmentId
     * @return
     */
    Assessment findById(String assessmentId);

    /**
     * 上传文件
     * @param file
     * @return
     */
    AssessmentFile saveFile(AssessmentFile file);

    /**
     * 删除文件
     * @param ids
     */
    void delFileByIds(String[] ids);

    /**
     * 上传图片
     * @param image
     * @return
     */
    AssessmentImage saveImage(AssessmentImage image);

    /**
     * 删除图片
     * @param ids
     */
    void delImage(String[] ids);

    /**
     * 新增考核
     * @param assessment
     * @param mapList
     */
    void saveAssessmentIndex(Assessment assessment, List<Map<String, String>> mapList);

    /**
     * 查找文件集合
     * @param assessmentFiles
     * @return
     */
    List<AssessmentFile> findByFileIds(String[] assessmentFiles);

    /**
     * 查找图片集合
     * @param assessmentImages
     * @return
     */
    List<AssessmentImage> findByImageIds(String[] assessmentImages);

    /**
     *  查找指标集合通过考核id
     * @param id
     * @return
     */
    List<AssessmentIndex> findIndexByAssessmentId(String id);

    /**
     * 删除考核集合
     * @param assessmentIndices
     */
    void delAll(List<AssessmentIndex> assessmentIndices);

    /**
     * 删除考核
     * @param assessment
     */
    void deleteAssessment(Assessment assessment);

    /**
     * 根据id查找所有考核参与实体
     * @param id
     * @return
     */
    List<AssessmentRange> findRangeById(String id);

    /**
     *根据id查找所有文件
     * @param id
     * @return
     */
    List<AssessmentFile> findFileById(String id);

    /**
     *根据id查找所有图片
     * @param id
     * @return
     */
    List<AssessmentImage> findImageById(String id);

    /**
     *根据id查找所有指标
     * @param id
     * @return
     */
    List<AssessmentIndex> findIndexById(String id);

    /**
     * 查看考核所有
     * @param assessment
     * @param ranges
     * @param files
     * @param images
     * @param indices
     * @return
     */
    JSONObject showAssessment(Assessment assessment, List<AssessmentRange> ranges, List<AssessmentFile> files, List<AssessmentImage> images, List<AssessmentIndex> indices);


    /**
     * 通过指标id查找指标
     */
    AssessmentIndex findByIndexId(String id);


    /**
     * 删除一条考核指标
     * @param  assessmentIndex
     */
    void delByIndex(AssessmentIndex assessmentIndex);

    /**
     * 通过本机id找到 对应的实体
     * @param parCode
     * @return
     */
    AssessmentIndex findSnameBySortedid(String parCode);

    /**
     * 模糊查询
     * @param pageable
     * @return
     */
    Page<Assessment> findAssessByProperty(Pageable pageable);
}
