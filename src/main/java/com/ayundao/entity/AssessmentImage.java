package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: AssessmentImage
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/11
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Entity
@Table(name = "t_assessment_image")
public class AssessmentImage extends BaseEntity<String>{

    private static final long serialVersionUID = -8147488492810340344L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * url
     */
    @Column(name = "URL", nullable = false, length = 100)
    private String url;

    /**
     * 后缀名
     */
    @Column(name = "SUFFIX", nullable = false, length = 4)
    private String suffix;

    /**
     * 所属考核项目
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSESSMENTID")
    private Assessment assessment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}
