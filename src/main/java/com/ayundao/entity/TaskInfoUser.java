package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 任务人员参与表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_task_info_user")
public class TaskInfoUser extends BaseEntity<String> {

    private static final long serialVersionUID = 463918421039171000L;

    /**
     * 任务id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TASKID", nullable = false)
    private Task task;

    /**
     * 用户id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    /**
     * 手机号码
     */
    @Column(name = "PHONE",length = 50)
    private String phone;

    /**
     * 确认接收状态
     */
    @Column(name = "STATE",length = 50)
    private String state;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
