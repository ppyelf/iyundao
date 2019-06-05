package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: AssessmentRange
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 10:24
 * @Description: 实体 - 考核范围
 * @Version: V1.0
 */
@Entity
@Table(name = "t_assessment_range")
public class AssessmentRange extends BaseEntity<String> {

    private final static long serialVersionUID = -1238401294380912804L;

    /**
     * 考核项目ID
     */
    @Column(name = "ASSESSMENTID", nullable = false, length = 50)
    private String assessmentId;

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

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
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
