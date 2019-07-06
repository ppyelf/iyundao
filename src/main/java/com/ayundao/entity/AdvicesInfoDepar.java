package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: AdvicesInfoDepar
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Entity
@Table(name = "t_advices_info_depar")
public class AdvicesInfoDepar extends BaseEntity<String>{

    private static final long serialVersionUID = -6777003080928731884L;

    /**
     * 消息id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADVICESID", nullable = false)
    private Advices advices;

    /**
     * 机构id
     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECTID")
    private Subject subject;

    /**
     * 部门id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTID")
    private Depart depart;

    /**
     * 组织id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUPSID")
    private Groups groups;

    /**
     * 人员id
     * @return
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    public Advices getAdvices() {
        return advices;
    }

    public void setAdvices(Advices advices) {
        this.advices = advices;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
