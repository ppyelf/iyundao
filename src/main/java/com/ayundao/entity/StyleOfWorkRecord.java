package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @Author: ink-feather
 * @Description: 行风效能记录
 * @Date: 2019/6/15 13:42
 */
@Entity
@Table(name = "t_styleofwork_record")
public class StyleOfWorkRecord extends BaseEntity<String> {

    private static final long serialVersionUID = -1161984710974098507L;

    /**
     * 行风id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STYLEOFWORKID")
    private StyleOfWork styleOfWork;

    /**
     * 操作时间
     */
    @Column(name = "OPERATIONTIME")
    private String operationTime;

    /**
     * 原因
     */
    @Column(name = "CAUSE")
    private String cause;

    /**
     * 操作人id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OPERATORID")
    private User operatorId;

    /**
     * 操作人姓名
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OPERATORNAME")
    private User operatorName;

    /**
     * 分数
     */
    @Column(name = "GRADE")
    private String grade;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public StyleOfWork getStyleOfWork() {
        return styleOfWork;
    }

    public void setStyleOfWork(StyleOfWork styleOfWork) {
        this.styleOfWork = styleOfWork;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public User getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(User operatorId) {
        this.operatorId = operatorId;
    }

    public User getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(User operatorName) {
        this.operatorName = operatorName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
