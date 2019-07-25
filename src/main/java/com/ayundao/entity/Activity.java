package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Activity
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/4 16:35
 * @Description: 实体 - 活动
 * @Version: V1.0
 */
@Entity
@Table(name = "t_activity")
public class Activity extends BaseEntity<String> {

    private final static long serialVersionUID = -19841293471923748L;

    /**
     * 标题
     */
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    /**
     * 内容
     */
    @Column(name = "CONTENT", length = 500)
    private String content;

    /**
     * 成员数
     */
    @Column(name = "NUMBER", columnDefinition = "tinyint default 0")
    private int number;

    /**
     * 总分
     */
    @Column(name = "TOTAL", columnDefinition = "varchar(50) default 'blank'", length = 50, nullable = false)
    private String total;

    /**
     * 活动类型
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE")
    private ACTIVITY_TYPE type;

    /**
     * 出勤时间
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attendance> attendances;

    /**
     * 个人签到
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sign> signs;

    /**
     * 活动文件
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ActivityFile> activityFiles;

    /**
     * 活动图片
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ActivityImage> activityImages;

    /**
     * 发布机构
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReleaseSubject> releaseSubjects;

    /**
     * 发布部门
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReleaseDepart> releaseDeparts;

    /**
     * 发布组织
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReleaseGroups> releaseGroups;

    /**
     * 活动报名人员id
     */
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ActivityInfoUser> activityInfoUser;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ACTIVITY_TYPE getType() {
        return type;
    }

    public void setType(ACTIVITY_TYPE type) {
        this.type = type;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Set<Sign> getSigns() {
        return signs;
    }

    public void setSigns(Set<Sign> signs) {
        this.signs = signs;
    }

    public Set<ActivityFile> getActivityFiles() {
        return activityFiles;
    }

    public void setActivityFiles(Set<ActivityFile> activityFiles) {
        this.activityFiles = activityFiles;
    }

    public Set<ReleaseSubject> getReleaseSubjects() {
        return releaseSubjects;
    }

    public void setReleaseSubjects(Set<ReleaseSubject> releaseSubjects) {
        this.releaseSubjects = releaseSubjects;
    }

    public Set<ReleaseDepart> getReleaseDeparts() {
        return releaseDeparts;
    }

    public void setReleaseDeparts(Set<ReleaseDepart> releaseDeparts) {
        this.releaseDeparts = releaseDeparts;
    }

    public Set<ReleaseGroups> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(Set<ReleaseGroups> releaseGroups) {
        this.releaseGroups = releaseGroups;
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

    public Set<ActivityImage> getActivityImages() {
        return activityImages;
    }

    public void setActivityImages(Set<ActivityImage> activityImages) {
        this.activityImages = activityImages;
    }

    public Set<ActivityInfoUser> getActivityInfoUser() {
        return activityInfoUser;
    }

    public void setActivityInfoUser(Set<ActivityInfoUser> activityInfoUser) {
        this.activityInfoUser = activityInfoUser;
    }

    public enum ACTIVITY_TYPE{
        /**
         * 部门考核
         */
        depart,

        /**
         * 老干部考核
         */
        veteranCadres,

        /**
         * etc
         */
        etc
    }
}

