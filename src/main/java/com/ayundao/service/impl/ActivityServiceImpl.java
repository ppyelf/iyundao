package com.ayundao.service.impl;

import com.ayundao.entity.*;
import com.ayundao.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName: ActivityServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 13:42
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityFileRepository activityFileRepository;

    @Autowired
    private ActivityImageRepository activityImageRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ReleaseSubjectRepository releaseSubjectRepository;

    @Autowired
    private ReleaseDepartRepository releaseDepartRepository;

    @Autowired
    private ReleaseGroupRepository releaseGroupRepository;

    @Autowired
    private SignRepository signRepository;

    @Override
    @Modifying
    @Transactional
    public Activity save(Activity activity, List<Attendance> attendances, List<ActivityFile> activityFiles, List<ActivityImage> activityImages, String subjectId, String departId, String groupId) {
        activity = activityRepository.save(activity);
        Set<Attendance> set = new HashSet<>();
        Set<ActivityFile> fileSet = new HashSet<>();
        Set<ActivityImage> imageSet = new HashSet<>();
        for (Attendance attendance : attendances) {
            attendance.setActivity(activity);
            attendanceRepository.save(attendance);
            set.add(attendance);
        }
        for (ActivityFile activityFile : fileSet) {
            activityFile.setActivity(activity);
            fileSet.add(activityFile);
        }
        activityFileRepository.saveAll(fileSet);
        for (ActivityImage activityImage : imageSet) {
            activityImage.setActivity(activity);
            imageSet.add(activityImage);
        }
        activityImageRepository.saveAll(imageSet);
        activity.setAttendances(set);
        activity.setActivityFiles(fileSet);
        activity.setActivityImages(imageSet);
        activity = activityRepository.save(activity);
        if (StringUtils.isNotBlank(subjectId)) {
            ReleaseSubject rs = new ReleaseSubject();
            rs.setActivity(activity);
            rs.setCreatedDate(new Date());
            rs.setLastModifiedDate(new Date());
            rs.setSubjectId(subjectId);
            releaseSubjectRepository.save(rs);
        }
        if (StringUtils.isNotBlank(departId)) {
            ReleaseDepart rd = new ReleaseDepart();
            rd.setActivity(activity);
            rd.setCreatedDate(new Date());
            rd.setLastModifiedDate(new Date());
            rd.setDepartId(departId);
            releaseDepartRepository.save(rd);
        }
        if (StringUtils.isNotBlank(groupId)) {
            ReleaseGroups rg = new ReleaseGroups();
            rg.setActivity(activity);
            rg.setCreatedDate(new Date());
            rg.setLastModifiedDate(new Date());
            rg.setGroupsId(groupId);
            releaseGroupRepository.save(rg);
        }
        return activity;
    }

    @Override
    public Activity find(String id) {
        return activityRepository.find(id);
    }

    @Override
    public void delete(Activity activity) {
        List<Attendance> attendances = attendanceRepository.findByActivityId(activity.getId());
        List<ActivityFile> activityFiles = activityFileRepository.findByActivityId(activity.getId());
        List<ActivityImage> activityImages = activityImageRepository.findByActivityId(activity.getId());
        List<Sign> signs = signRepository.findByActivityId(activity.getId());
        attendanceRepository.deleteAll(attendances);
        activityFileRepository.deleteAll(activityFiles);
        activityImageRepository.deleteAll(activityImages);
        signRepository.deleteAll(signs);
        activityRepository.delete(activity);
    }

    @Override
    public ActivityFile saveFile(ActivityFile file) {
        return activityFileRepository.save(file);
    }

    @Override
    public ActivityImage saveImage(ActivityImage image) {
        return activityImageRepository.save(image);
    }

    @Override
    public List<ActivityFile> findActivityFilesByIds(String[] activityFileIds) {
        List<ActivityFile> activityFiles = activityFileRepository.findByIds(activityFileIds);
        return CollectionUtils.isEmpty(activityFiles)
                ? new ArrayList<>()
                : activityFiles;
    }

    @Override
    public List<ActivityImage> findActivityImageByIds(String[] activityImageIds) {
        List<ActivityImage> activityImages = activityImageRepository.findByIds(activityImageIds);
        return CollectionUtils.isEmpty(activityImages)
                ? new ArrayList<>()
                : activityImages;
    }

    @Override
    public void delFileByIds(String[] ids) {
        List<ActivityFile> activityFiles = activityFileRepository.findByIds(ids);
        activityFileRepository.deleteAll(activityFiles);
    }

    @Override
    public void delImage(String[] ids) {
        List<ActivityImage> activityImages = activityImageRepository.findByIds(ids);
        activityImageRepository.deleteAll(activityImages);
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAllForList();
    }

    @Override
    public Page<Activity> findAllForPage(Pageable pageable) {
        Page<Activity> activityPage = activityRepository.findAllForPage(pageable);
        return activityPage;
    }
}
