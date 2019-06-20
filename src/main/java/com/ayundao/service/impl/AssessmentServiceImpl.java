package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.AssessmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName: AssessmentServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/7 20:15
 * @Description: 实现 - 服务
 * @Version: V1.0
 */
@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentFileRepository assessmentFileRepository;

    @Autowired
    private AssessmentIndexRepository assessmentIndexRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;

    @Autowired
    private UserGroupRelationRepository userGroupRelationRepository;

    @Autowired
    private AssessmentRangeRepository assessmentRangeRepository;

    @Autowired
    private UserIndexRepository userIndexRepository;

    @Override
    public Page<Assessment> findAllForPage(Pageable pageable) {
        return assessmentRepository.findAllForPage(pageable);
    }

    @Override
    public AssessmentFile saveFile(String assessmentId, AssessmentFile file) {
        if (StringUtils.isNotBlank(assessmentId)) {
            Assessment assessment = assessmentRepository.find(assessmentId);
            if (assessment != null) {
                Set<AssessmentFile> set = new HashSet<>();
                file.setAssessment(assessment);
                set.add(file);
                assessment.setAssessmentFiles(set);
                assessmentRepository.save(assessment);
            }
        }
        file = assessmentFileRepository.save(file);
        return file;
    }

    @Override
    public List<AssessmentFile> findFilesByIds(String[] ids) {
        List<AssessmentFile> files = assessmentFileRepository.findByIds(ids);
        return CollectionUtils.isNotEmpty(files)
                ? files
                : new ArrayList<>();
    }

    @Override
    public void delFiles(List<AssessmentFile> files) {
        assessmentFileRepository.deleteAll(files);
    }

    @Override
    public AssessmentIndex findIndexById(String id) {
        AssessmentIndex index = assessmentIndexRepository.find(id);
        return index;
    }

    @Override
    public AssessmentIndex saveIndex(Assessment assessment, AssessmentIndex index) {
        if (assessment != null) {
            Set<AssessmentIndex> set = new HashSet<>();
            index.setAssessment(assessment);
            set.add(index);
            assessment.setAssessmentIndices(set);
            assessmentRepository.save(assessment);
        }
        index.setCode(getIndexLastCode());
        index = assessmentIndexRepository.save(index);
        return index;
    }

    @Override
    public List<AssessmentIndex> findIndexByIds(String[] ids) {
        List<AssessmentIndex> assessmentIndices = assessmentIndexRepository.findByIds(ids);
        return CollectionUtils.isEmpty(assessmentIndices)
                ? new ArrayList<>()
                : assessmentIndices;
    }

    @Override
    public void delIndexs(List<AssessmentIndex> list) {
        assessmentIndexRepository.deleteAll(list);
    }

    @Override
    @Modifying
    @Transactional
    public Assessment save(Assessment assessment, List<AssessmentIndex> index, List<AssessmentFile> file, String subjectId, String departId, String groupId, String userGroupId) {
        assessmentRepository.save(assessment);
        if (CollectionUtils.isNotEmpty(index)) {
            Set<AssessmentIndex> indexSet = new HashSet<>();
            for (AssessmentIndex ii : indexSet) {
                ii.setAssessment(assessment);
                indexSet.add(ii);
            }
            assessmentIndexRepository.saveAll(indexSet);
            assessment.setAssessmentIndices(indexSet);
        }

        if (CollectionUtils.isNotEmpty(file)) {
            Set<AssessmentFile> fileSet = new HashSet<>();
            for (AssessmentFile ff : file) {
                ff.setAssessment(assessment);
                fileSet.add(ff);
            }
            assessmentFileRepository.saveAll(fileSet);
            assessment.setAssessmentFiles(fileSet);
        }
        List<UserRelation> userRelations = userRelationRepository.findBySubjectAndDepartIdsOrGroupsIds(subjectId, new String[]{departId}, new String[]{groupId});
        List<UserGroupRelation> userGroups = userGroupRelationRepository.findByUserGroupId(userGroupId);
        List<AssessmentRange> assessmentRanges = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userRelations)) {
            for (UserRelation ur : userRelations) {
                AssessmentRange range = new AssessmentRange();
                range.setCreatedDate(new Date());
                range.setLastModifiedDate(new Date());
                range.setSubjectId(ur.getSubject() == null ? null : ur.getSubject().getId());
                range.setDepartId(ur.getDepart() == null ? null : ur.getDepart().getId());
                range.setGroupId(ur.getGroups() == null ? null : ur.getGroups().getId());
                range.setUserId(ur.getUser().getId());
                range.setAssessmentId(assessment.getId());
                assessmentRanges.add(range);
            }
        }
        if (CollectionUtils.isNotEmpty(userGroups)) {
            for (UserGroupRelation ug : userGroups) {
                AssessmentRange range = new AssessmentRange();
                range.setCreatedDate(new Date());
                range.setLastModifiedDate(new Date());
                range.setUserGroupId(ug.getUserGroup().getId());
                range.setAssessmentId(assessment.getId());
                assessmentRanges.add(range);
            }
        }
        assessment = assessmentRepository.save(assessment);
        assessmentRangeRepository.saveAll(assessmentRanges);
        return assessment;
    }

    @Override
    public void delete(String id) {
        List<AssessmentRange> ranges = assessmentRangeRepository.findByAssessmentId(id);
        List<AssessmentIndex> assessmentIndices = assessmentIndexRepository.findByAssessmentId(id);
        List<AssessmentFile> files = assessmentFileRepository.findByAssessmentId(id);
        List<UserIndex> userIndices = userIndexRepository.findByAssessmentId(id);
        assessmentRangeRepository.deleteAll(ranges);
        assessmentIndexRepository.deleteAll(assessmentIndices);
        assessmentFileRepository.deleteAll(files);
        userIndexRepository.deleteAll(userIndices);
        assessmentRepository.delete(assessmentRepository.find(id));
    }

    @Override
    public Assessment find(String id) {
        return assessmentRepository.find(id);
    }

    @Override
    @Transactional
    public Assessment modify(Assessment assessment, List<AssessmentIndex> index, List<AssessmentFile> file, String subjectId, String departId, String groupId, String userGroupId) {
        String id = assessment.getId();
        List<AssessmentRange> ranges = assessmentRangeRepository.findByAssessmentId(id);
        List<AssessmentIndex> assessmentIndices = assessmentIndexRepository.findByAssessmentId(id);
        List<AssessmentFile> files = assessmentFileRepository.findByAssessmentId(id);
        assessmentRangeRepository.deleteAll(ranges);
        assessmentIndexRepository.deleteAll(assessmentIndices);
        assessmentFileRepository.deleteAll(files);
        return save(assessment, index, file, subjectId, departId, groupId, userGroupId);
    }

    @Override
    public AssessmentRange findByAssessmentIdAndUserId(String userId, String assessmentId) {
        return assessmentRangeRepository.findByAssessmentIdAndUserId(userId, assessmentId);
    }

    @Override
    public UserIndex saveUserIndex(UserIndex ui) {
        return userIndexRepository.save(ui);
    }

    @Override
    public Assessment addAssessments(Assessment assessment, List<AssessmentIndex> assessmentIndices) {
        Set<AssessmentIndex> set = new HashSet<>();
        for (AssessmentIndex ai : assessmentIndices) {
            ai.setAssessment(assessment);
            set.add(ai);
        }
        assessmentIndexRepository.saveAll(set);
        assessment.setAssessmentIndices(set);
        assessment = assessmentRepository.save(assessment);
        return assessment;
    }

    private int getIndexLastCode() {
        int index = assessmentIndexRepository.findLastCode();
        return index + 1;
    }
}
