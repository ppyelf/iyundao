package com.ayundao.entity;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: Evaluation
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 9:48
 * @Description: 实体 - 考评
 * @Version: V1.0
 */
@Entity
@Table(name = "t_evaluation")
public class Evaluation extends BaseEntity<String> {

    private static final long serialVersionUID = 179834791827394812L;

    /**
     * 年度
     */
    @Column(name = "YEAR", length = 4, nullable = false)
    private String year;

    /**
     * 所属用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    /**
     * 所属指标
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVALUATIONINDEXID", nullable = false)
    private EvaluationIndex evaluationIndex;

    /**
     * 医德分
     */
    @Column(name = "SCORE", length = 5, nullable = false, columnDefinition = "double(4,1) default '0.0'")
    private double score;

    /**
     * 备注
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 操作人
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "USERCODE")),
            @AttributeOverride(name = "name", column = @Column(name = "USERNAME"))
    })
    private BaseComponent operator;

    /**
     * 病历号
     */
    @Column(name = "NUMBER", length = 10)
    private String number;

    /**
     * 病人姓名
     */
    @Column(name = "PATIENTNAME", length = 20)
    private String patientName;

    /**
     * 党办审核
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS", length = 1, columnDefinition = "tinyint(1) default '0'")
    private STATUS status;

    /**
     * 党办确认时间
     */
    @Column(name = "SURETIME", length = 20)
    private String sureTime;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EvaluationIndex getEvaluationIndex() {
        return evaluationIndex;
    }

    public void setEvaluationIndex(EvaluationIndex evaluationIndex) {
        this.evaluationIndex = evaluationIndex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BaseComponent getOperator() {
        return operator;
    }

    public void setOperator(BaseComponent operator) {
        this.operator = operator;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getSureTime() {
        return sureTime;
    }

    public void setSureTime(String sureTime) {
        this.sureTime = sureTime;
    }

    public enum STATUS{
        /**
         * 等待中
         */
        waiting("等待中"),

        /**
         * 同意
         */
        agree("同意"),

        /**
         * 拒绝
         */
        refuse("拒绝");

        private String name;

        STATUS(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
