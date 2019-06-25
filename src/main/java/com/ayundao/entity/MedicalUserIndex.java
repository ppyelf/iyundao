package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: MedicalUserIndex
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 14:40
 * @Description: 实体 - 医德用户指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_medical_user_index")
public class MedicalUserIndex extends BaseEntity<String> {

    private final static long serialVersionUID = -1209381092831082381L;

    /**
     * 考核项目ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEDICALID", nullable = false)
    private Medical medical;

    /**
     * 考核指标ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEDICALINDEXID", nullable = false)
    private MedicalIndex medicalIndex;

    /**
     * 分数
     */
    @Column(name = "SCORE", columnDefinition = "tinyint default 0", length = 4)
    private int score;

    /**
     * 用户ID
     */
    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;

    /**
     * 完成时间
     */
    @Column(name = "FINISHTIME", nullable = false, length = 50)
    private String finishTime;

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

    public Medical getMedical() {
        return medical;
    }

    public void setMedical(Medical medical) {
        this.medical = medical;
    }

    public MedicalIndex getMedicalIndex() {
        return medicalIndex;
    }

    public void setMedicalIndex(MedicalIndex medicalIndex) {
        this.medicalIndex = medicalIndex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
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
