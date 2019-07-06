package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: TaskInfoDepart
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/3
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Entity
@Table(name = "t_task_info_depart")
public class TaskInfoDepart extends BaseEntity<String> {

    private static final long serialVersionUID = -7254595348862242914L;

    /**
     * 任务id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TASKID", nullable = false)
    private Task task;

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
}
