package com.ayundao.service.impl;

import com.ayundao.entity.Activity;
import com.ayundao.entity.Attendance;

import java.util.List;

/**
 * @ClassName: AttendanceService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 13:51
 * @Description: 服务 - 出勤时间
 * @Version: V1.0
 */
public interface AttendanceService {

    /**
     * 根据活动查询实体集合
     * @param Id
     * @return
     */
    List<Attendance> findByActivityId(String Id);

    /**
     * 根据IDS获取集合信息
     * @param attendanceIds
     * @return
     */
    List<Attendance> findByIds(String[] attendanceIds);

    /**
     * 保存实体
     * @param activity
     * @param startTime
     * @param endTime
     * @param day
     * @param type
     * @param axisx
     * @param axisy
     * @param area
     * @return
     */
    Attendance save(Activity activity, String startTime, String endTime, int day, int type, String axisx, String axisy, String area);
}
