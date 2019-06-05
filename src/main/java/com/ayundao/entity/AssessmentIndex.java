package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: AssessmentIndex
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 10:18
 * @Description: 实体 - 考核指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_assessment_index")
public class AssessmentIndex extends BaseEntity<String> {

    private final static long serialVersionUID = -1083240120981024309L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 描述
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 父级指标
     */
    @JoinColumn(name = "FATHERID")
    private AssessmentIndex assessmentIndex;

    /**
     * 编码
     */
    @Column(name = "CODE", length = 10)
    private int code;

    /**
     * 所属考核ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSESSMENTID")
    private Assessment assessment;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AssessmentIndex getAssessmentIndex() {
        return assessmentIndex;
    }

    public void setAssessmentIndex(AssessmentIndex assessmentIndex) {
        this.assessmentIndex = assessmentIndex;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
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
}
