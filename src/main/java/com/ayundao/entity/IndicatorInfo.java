package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: IndicatorInfo
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 16:52
 * @Description: 实体 - 指标详情
 * @Version: V1.0
 */
@Entity
@Table(name = "t_indicator_info")
public class IndicatorInfo extends BaseEntity<String> {

    private final static long serialVersionUID = -1238210380921830128L;

    /**
     * 年份
     */
    @Column(name = "YEAR", nullable = false, length = 50)
    private String year;

    /**
     * 月份
     */
    @Column(name = "MONTH", nullable = false, length = 50)
    private String month;

    /**
     * 完成情况
     */
    @Column(name = "COMPLETION", length = 50)
    private String completion;

    /**
     * 简介
     */
    @Column(name = "INTRO", length = 500)
    private String intro;

    /**
     * 指标
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INDICATORID", nullable = false)
    private Indicator indicator;

    /**
     * 部门ID
     */
    @Column(name = "DEPARTID")
    private String departId;

    /**
     * 组织ID
     */
    @Column(name = "GROUPID")
    private String groupId;

    /**
     * 用户ID
     */
    @Column(name = "USERID")
    private String userId;

    /**
     * 附件
     */
    @OneToMany(mappedBy = "indicatorInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<IndicatorInfoFile> indicatorInfoFiles;

    /**
     * 图片
     */
    @OneToMany(mappedBy = "indicatorInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<IndicatorInfoImage> indicatorInfoImages;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<IndicatorInfoFile> getIndicatorInfoFiles() {
        return indicatorInfoFiles;
    }

    public void setIndicatorInfoFiles(Set<IndicatorInfoFile> indicatorInfoFiles) {
        this.indicatorInfoFiles = indicatorInfoFiles;
    }

    public Set<IndicatorInfoImage> getIndicatorInfoImages() {
        return indicatorInfoImages;
    }

    public void setIndicatorInfoImages(Set<IndicatorInfoImage> indicatorInfoImages) {
        this.indicatorInfoImages = indicatorInfoImages;
    }
}
