package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: AssessmentFraction
 * @project: ayundao
 * @author: king
 * @Date: 2019/7/4 10:24
 * @Description: 实体 - 考核分数
 * @Version: V1.0
 */
@Entity
@Table(name = "t_assessment_fraction")
public class AssessmentFraction extends BaseEntity<String> {

    private final static long serialVersionUID = -121245452554L;

    /**
     * 考核项目ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSESSMENTID")
    private Assessment assessment;

    /**
     * 机构ID
     */
    @Column(name = "SUBJECTID", length = 50)
    private String subjectId;

    /**
     * 用户组ID
     */
    @Column(name = "USERGROUPID", length = 50)
    private String userGroupId;

    /**
     * 部门ID
     */
    @Column(name = "DEPARTID", length = 50)
    private String departId;

    /**
     * 小组ID
     */
    @Column(name = "GROUPID", length = 50)
    private String groupId;

    /**
     * 用户ID
     */
    @Column(name = "USERID", length = 50)
    private String userId;

    /**
     * 分数
     */
    @Column(name = "FRACTION",nullable = false)
    private int fraction;

    /**
     * 评价
     */
    @Column(name = "EVALUATE", length = 500)
    private String evaluate;

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

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
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

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
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
