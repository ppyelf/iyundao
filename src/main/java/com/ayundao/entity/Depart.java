package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Depart
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:36
 * @Description: 部门(行政)实体
 * @Version: V1.0
 */
@Entity
@Table(name = "t_depart")
public class Depart extends BaseEntity<String> {

    private static final long serialVerisonUID = -1294037981273498L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    /**
     * 描述
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECT_ID", nullable = false)
    private Subject subject;

    /**
     * 父级--部门
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FATHERID")
    private Depart father;

    /**
     * 负责人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;


    /**
     * 部门关系
     */
    @OneToMany(mappedBy = "depart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Depart getFather() {
        return father;
    }

    public void setFather(Depart father) {
        this.father = father;
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


}
