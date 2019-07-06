package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 考试参与部门组织表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_exam_info_depart")
public class ExamInfoDepart extends BaseEntity<String>{

    private static final long serialVersionUID = 8628528258972415843L;

    /**
     * 考试id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXAMID", nullable = false)
    private Exam exam;

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
     * 用户id
     * @return
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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
