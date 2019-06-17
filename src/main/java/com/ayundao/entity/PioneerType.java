package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @Author: ink-feather
 * @Description: 先锋人物类别
 * @Date: 2019/6/15 14:34
 */
@Entity
@Table(name = "t_pioneertype")
public class PioneerType extends BaseEntity<String> {

    private static final long serialVersionUID = -1172084710574098416L;

    /**
     * 部门id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPART_ID")
    private Depart depart;

    /**
     * 组织id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_ID")
    private Groups group;

    /**
     * 机构id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    /**
     * 类别名称
     */
    @Column(name = "TYPE_NAME")
    private String typeName;

    /**
     * 类别状态
     */
    @Column(name = "FLAG")
    private int flag;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
