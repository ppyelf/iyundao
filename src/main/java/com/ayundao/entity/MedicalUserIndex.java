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
}
