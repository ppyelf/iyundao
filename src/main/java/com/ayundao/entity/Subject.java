package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: Subject
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:32
 * @Description: 机构实体
 * @Version: V1.0
 */
@Entity
@Table(name = "t_subject")
public class Subject extends BaseEntity<String> {

    private static final long serialVersionUID = -129079014789123784L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    /**
     * 编号
     */
    @Column(name = "CODE", nullable = false, length = 10, unique = true)
    private String code;

    /**
     * 机构类型
     */
    @Column(name = "SUBJECT_TYPE")
    private SUBJECT_TYPE subjectType;

    /**
     * 机构关系
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRelation> userRelations;

    /**
     * 行政/部门关系
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Depart> departs;

    /**
     * 组织/小组关系
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Groups> groups;

    /**
     * 考试部门组织详情表机构id
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoDepart> examInfoDepart;

    /**
     * 任务部门组织详情表机构id
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskInfoDepart> taskInfoDepart;

    /**
     * 消息发布部门组织机构id
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdvicesInfoDepar> advicesInfoDepar;

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

    public SUBJECT_TYPE getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SUBJECT_TYPE subjectType) {
        this.subjectType = subjectType;
    }

    public Set<UserRelation> getUserRelations() {
        return userRelations;
    }

    public void setUserRelations(Set<UserRelation> userRelations) {
        this.userRelations = userRelations;
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

    public Set<ExamInfoDepart> getExamInfoDepart() {
        return examInfoDepart;
    }

    public void setExamInfoDepart(Set<ExamInfoDepart> examInfoDepart) {
        this.examInfoDepart = examInfoDepart;
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

    public enum SUBJECT_TYPE{
        /**
         * 总院
         */
        head(0, "总院"),

        /**
         * 分院
         */
        part(1, "分院"),

        /**
         * 其他
         */
        etc(2, "其他");

        private int index;

        private String name;

        SUBJECT_TYPE(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
