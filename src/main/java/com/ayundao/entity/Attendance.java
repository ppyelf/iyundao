package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.utils.TimeUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: Attendance
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/4 16:49
 * @Description: 实体 - 出勤时间
 * @Version: V1.0
 */
@Entity
@Table(name = "t_attendance")
public class Attendance extends BaseEntity<String> {

    private final static long serialVersionUID = -123782108301832L;

    /**
     * 活动
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVITYID")
    private Activity activity;

    /**
     * 开始时间
     */
    @Column(name = "STARTTIME", nullable = false)
    private String startTime;

    /**
     * 结束时间
     */
    @Column(name = "ENDTIME", nullable = false)
    private String endTime;

    /**
     * 天数
     */
    @Column(name = "DAY")
    private int day;

    /**
     * 类型
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE")
    private ATTENDANCE_TYPE attendanceType;

    /**
     * 位置X
     */
    @Column(name = "AXISX", length = 50)
    private String axisx;

    /**
     * 位置Y
     */
    @Column(name = "AXISY", length = 50)
    private String axisy;

    /**
     * 范围
     */
    @Column(name = "AREA", length = 50)
    private String area;
    /**
     * 备用字段1
     */
    @Column(name = "INFO1")
    private String info1;
    /**
     * 备用字段2
     */
    @Column(name = "INFO2")
    private String info2;
    /**
     * 备用字段3
     */
    @Column(name = "INFO3")
    private String info3;
    /**
     * 备用字段4
     */
    @Column(name = "INFO4")
    private String info4;
    /**
     * 备用字段5
     */
    @Column(name = "INFO5")
    private String info5;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = TimeUtils.convertTime(endTime, "yyyyMMddHHmmss");
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ATTENDANCE_TYPE getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(ATTENDANCE_TYPE attendanceType) {
        this.attendanceType = attendanceType;
    }

    public String getAxisx() {
        return axisx;
    }

    public void setAxisx(String axisx) {
        this.axisx = axisx;
    }

    public String getAxisy() {
        return axisy;
    }

    public void setAxisy(String axisy) {
        this.axisy = axisy;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public enum ATTENDANCE_TYPE{
        /**
         * 日常出勤
         */
        daily(0, "日常出勤"),

        /**
         * 考核出勤
         */
        assessment(1, "考核出勤"),

        /**
         * 学习出勤
         */
        learn(2, "学期出勤"),

        /**
         * 活动出勤
         */
        activity(3, "活动出勤"),

        /**
         * 其他
         */
        etc(4, "其他");

        private int index;

        private String name;

        ATTENDANCE_TYPE(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
