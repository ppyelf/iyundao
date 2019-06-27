package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: SpiritSubject
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 4:51
 * @Description: 实体 - 党内精神机构组织
 * @Version: V1.0
 */
@Entity
@Table(name = "t_spirit_subject")
public class SpiritSubject extends BaseEntity<String> {

    private final static long serialVersionUID = -1328418240810934L;

    /**
     * 机构ID
     */
    @Column(name = "SUBJECTID", nullable = false)
    private String subjectId;

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
     * 所属党内精神
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SPIRITID")
    private Spirit spirit;

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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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

    public Spirit getSpirit() {
        return spirit;
    }

    public void setSpirit(Spirit spirit) {
        this.spirit = spirit;
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
