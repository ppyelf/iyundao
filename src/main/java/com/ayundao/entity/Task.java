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
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

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

//    /**
//     * 评论设置
//     */
//    @Column(name = "COMMENTSET", length = 50)
//    private String commentset;

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


//    /**
//     * 备用字段1
//     */
//    @Column(name = "INFO1", length = 50)
//    private String info1;
//    /**
//     * 备用字段2
//     */
//    @Column(name = "INFO2", length = 50)
//    private String info2;
//    /**
//     * 备用字段3
//     */
//    @Column(name = "INFO3", length = 50)
//    private String info3;
//    /**
//     * 备用字段4
//     */
//    @Column(name = "INFO4", length = 50)
//    private String info4;
//    /**
//     * 备用字段5
//     */
//    @Column(name = "INFO5", length = 50)
//    private String info5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public String getCommentset() {
//        return commentset;
//    }
//
//    public void setCommentset(String commentset) {
//        this.commentset = commentset;
//    }

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
