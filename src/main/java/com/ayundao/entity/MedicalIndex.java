package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: MedicalIndex
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 14:24
 * @Description: 实体 - 医德指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_medical_index")
public class MedicalIndex extends BaseEntity<String> {

    private final static long serialVersionUID = -12309238048320948L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 描述
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 父级指标
     */
    @Column(name = "FATHERID",length = 50)
    private MedicalIndex father;

    /**
     * 编码
     */
    @Column(name = "CODE", length = 10, unique = true)
    private int code;

    /**
     * 所属考核ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEDICALID")
    private Medical medical;

    @OneToMany(mappedBy = "medicalIndex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalUserIndex> medicalUserIndices;

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
