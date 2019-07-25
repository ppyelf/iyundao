package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * *任务表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_task")
public class Task extends BaseEntity<String>{

    private static final long serialVersionUID = -5198019231045751509L;

    /**
     *  标题
     */
    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    /**
     * 任务类型
     */
    @Column(name = "TYPE" , length = 50)
    private String type;

    /**
     * 发布人员id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 发布时间
     */
    @Column(name = "ISSUERTIME", length = 50)
    private String issuertime;


    /**
     * 任务内容
     */
    @Column(name = "TASKTEXT", length = 9999)
    private  String tasktext;

    /**
     * 发送状态
     */
    @Column(name = "SENDSTATE",length = 50)
    private String sendstate;

    /**
     * 任务人员参与表中的任务id
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskInfoUser>  taskInfoUser;

    /**
     * 任务部门组织参与表中的任务id
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskInfoDepart> taskInfoDepart;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIssuertime() {
        return issuertime;
    }

    public void setIssuertime(String issuertime) {
        this.issuertime = issuertime;
    }

    public String getTasktext() {
        return tasktext;
    }

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
    }

    public Set<TaskInfoUser> getTaskInfoUser() {
        return taskInfoUser;
    }

    public void setTaskInfoUser(Set<TaskInfoUser> taskInfoUser) {
        this.taskInfoUser = taskInfoUser;
    }

    public Set<TaskInfoDepart> getTaskInfoDepart() {
        return taskInfoDepart;
    }

    public void setTaskInfoDepart(Set<TaskInfoDepart> taskInfoDepart) {
        this.taskInfoDepart = taskInfoDepart;
    }

    public String getSendstate() {
        return sendstate;
    }

    public void setSendstate(String sendstate) {
        this.sendstate = sendstate;
    }

//    public String getInfo1() {
//        return info1;
//    }
//
//    public void setInfo1(String info1) {
//        this.info1 = info1;
//    }
//
//    public String getInfo2() {
//        return info2;
//    }
//
//    public void setInfo2(String info2) {
//        this.info2 = info2;
//    }
//
//    public String getInfo3() {
//        return info3;
//    }
//
//    public void setInfo3(String info3) {
//        this.info3 = info3;
//    }
//
//    public String getInfo4() {
//        return info4;
//    }
//
//    public void setInfo4(String info4) {
//        this.info4 = info4;
//    }
//
//    public String getInfo5() {
//        return info5;
//    }
//
//    public void setInfo5(String info5) {
//        this.info5 = info5;
//    }


}
