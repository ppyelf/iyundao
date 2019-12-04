package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import javax.swing.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: User
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/15 9:39
 * @Description: 用户
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity<String> {

    private static final long serialVersionUID = -1172094710974098503L;

    /**
     * 账号
     */
    @Column(name = "ACCOUNT", nullable = false, unique = true, length = 50)
    private String account;

    /**
     * 姓名
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 个人考评
     *
     * @return
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Evaluation> evaluations;

    /**
     * 编号
     */
    @Column(name = "CODE", nullable = false, length = 10, unique = true)
    private String code;

    /**
     * 密码
     */
    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;

    /**
     * 密码盐
     */
    @Column(name = "SALT", nullable = false, length = 50)
    private String salt;

    /**
     * 性别 0-男, 1-女
     */
    @Column(name = "SEX")
    private int sex;

    /**
     * 账号状态
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS")
    private ACCOUNT_TYPE status;

    /**
     * 用户类型
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "USERTYPE")
    private USER_TYPE userType;

    /**
     * 用户简介
     */
    @Column(name = "REMARK", columnDefinition = "varchar(20) default '未填写'", length = 500)
    private String remark;

    /**
     * 机构关系
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRelation> userRelations;

    /**
     * 角色关系
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RoleRelation> roleRelations;

    /**
     * 部门负责人
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Depart> departs;

    /**
     * 小组负责人
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Groups> groups;

    /**
     * 第三方授权
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserApp> userApps;

    /**
     * 任务发布人id
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Task> task;

    /**
     * 任务人员参与表中的用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskInfoUser> taskInfoUser;

    /**
     * 消息发布人id
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Advices> advices;

    /**
     * 消息人员接收表人员id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdvicesInfoUser> advicesInfoUser;

    /**
     * 活动报名人员id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ActivityInfoUser> activityInfoUser;

    /**
     * 考试人员得分表用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoUserScore> examInfoUserScore;

    /**
     * 考试人员详情表用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoUser> examInfoUser;

    /**
     * 任务组织部门表用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskInfoDepart> taskInfoDepart;

    /**
     * 消息发送部门组织表用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdvicesInfoDepar> advicesInfoDepar;

    /**
     * 考试参与部门组织表用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoDepart> examInfoDepart;

    /**
     * 党内精神审核人用户id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Spirit> spirit;

    public static void main(String[] args) {
        for (int i = 0; i < 1313; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public ACCOUNT_TYPE getStatus() {
        return status;
    }

    public void setStatus(ACCOUNT_TYPE status) {
        this.status = status;
    }

    public USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(USER_TYPE userType) {
        this.userType = userType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<UserRelation> getUserRelations() {
        return userRelations;
    }

    public void setUserRelations(Set<UserRelation> userRelations) {
        this.userRelations = userRelations;
    }

    public Set<RoleRelation> getRoleRelations() {
        return roleRelations;
    }

    public void setRoleRelations(Set<RoleRelation> roleRelations) {
        this.roleRelations = roleRelations;
    }

    public Set<Depart> getDeparts() {
        return departs;
    }

    public void setDeparts(Set<Depart> departs) {
        this.departs = departs;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }


    public Set<UserApp> getUserApps() {
        return userApps;
    }

    public void setUserApps(Set<UserApp> userApps) {
        this.userApps = userApps;
    }

    public Set<Task> getTask() {
        return task;
    }

    public void setTask(Set<Task> task) {
        this.task = task;
    }

    public Set<TaskInfoUser> getTaskInfoUser() {
        return taskInfoUser;
    }

    public void setTaskInfoUser(Set<TaskInfoUser> taskInfoUser) {
        this.taskInfoUser = taskInfoUser;
    }

    public Set<Advices> getAdvices() {
        return advices;
    }

    public void setAdvices(Set<Advices> advices) {
        this.advices = advices;
    }

    public Set<AdvicesInfoUser> getAdvicesInfoUser() {
        return advicesInfoUser;
    }

    public void setAdvicesInfoUser(Set<AdvicesInfoUser> advicesInfoUser) {
        this.advicesInfoUser = advicesInfoUser;
    }

    public Set<ActivityInfoUser> getActivityInfoUser() {
        return activityInfoUser;
    }

    public void setActivityInfoUser(Set<ActivityInfoUser> activityInfoUser) {
        this.activityInfoUser = activityInfoUser;
    }

    public Set<ExamInfoUserScore> getExamInfoUserScore() {
        return examInfoUserScore;
    }

    public void setExamInfoUserScore(Set<ExamInfoUserScore> examInfoUserScore) {
        this.examInfoUserScore = examInfoUserScore;
    }

    public Set<ExamInfoUser> getExamInfoUser() {
        return examInfoUser;
    }

    public void setExamInfoUser(Set<ExamInfoUser> examInfoUser) {
        this.examInfoUser = examInfoUser;
    }


    public Set<TaskInfoDepart> getTaskInfoDepart() {
        return taskInfoDepart;
    }

    public void setTaskInfoDepart(Set<TaskInfoDepart> taskInfoDepart) {
        this.taskInfoDepart = taskInfoDepart;
    }

    public Set<AdvicesInfoDepar> getAdvicesInfoDepar() {
        return advicesInfoDepar;
    }

    public void setAdvicesInfoDepar(Set<AdvicesInfoDepar> advicesInfoDepar) {
        this.advicesInfoDepar = advicesInfoDepar;
    }

    public Set<ExamInfoDepart> getExamInfoDepart() {
        return examInfoDepart;
    }

    public void setExamInfoDepart(Set<ExamInfoDepart> examInfoDepart) {
        this.examInfoDepart = examInfoDepart;
    }

    public Set<Spirit> getSpirit() {
        return spirit;
    }

    public void setSpirit(Set<Spirit> spirit) {
        this.spirit = spirit;
    }

    /**
     * 账号类型
     */
    public enum ACCOUNT_TYPE {
        /**
         * 正常
         */
        normal,

        /**
         * 禁用
         */
        disable,

        /**
         * 锁定
         */
        block
    }

    /**
     * 用户类型
     */
    public enum USER_TYPE {
        /**
         * 普通用户
         */
        normal,

        /**
         * 管理员
         */
        admin,

        /**
         * 资料审核
         */
        shareFile
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
