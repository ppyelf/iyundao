package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Groups
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:44
 * @Description: 小组(组织)实体
 * @Version: V1.0
 */
@Entity
@Table(name = "t_groups")
public class Groups extends BaseEntity<String> {

    private static final long serialVerisonUID = -10927349812794379L;

    /**
     * 名称
     */
    @Column(name = "NAME", length = 50)
    private String name;

    /**
     * 描述
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 父级--部门
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FATHERID")
    private Groups father;


    /**
     * 所属机构
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECTID")
    private Subject subject;

    /**
     * 负责人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 组织/小组关系
     */
    @OneToMany(mappedBy = "groups", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRelation> userRelations;

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

    public Groups getFather() {
        return father;
    }

    public void setFather(Groups father) {
        this.father = father;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<UserRelation> getUserRelations() {
        return userRelations;
    }

    public void setUserRelations(Set<UserRelation> userRelations) {
        this.userRelations = userRelations;
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
