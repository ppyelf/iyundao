package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @Author: ink-feather
 * @Description: 廉政教育附件
 * @Date: 2019/6/15 14:06
 */
@Entity
@Table(name = "t_educationofcleanpolitics_accessory")
public class EducationOfCleanPoliticsAccessory extends BaseEntity<String> {

    private static final long serialVersionUID = -1162284710974098504L;

    /**
     * 廉政教育id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EDUCATIONID")
    private EducationOfCleanPolitics education;

    /**
     * 视频名称
     */
    @Column(name = "VIDEONAME")
    private String videoName;

    /**
     * 视频路径
     */
    @Column(name = "VIDEOURL")
    private String videoUrl;

    /**
     * 操作时间
     */
    @Column(name = "TIME")
    private String time;

    /**
     * 操作人id
     */
    @Column(name = "USERID")
    private String userId;

    /**
     * 操作人姓名
     */
    @Column(name = "USERNAME")
    private String userName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public EducationOfCleanPolitics getEducation() {
        return education;
    }

    public void setEducation(EducationOfCleanPolitics education) {
        this.education = education;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
