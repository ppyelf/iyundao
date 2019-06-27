package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: PrescriptionIndex
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:24
 * @Description: 实体 - 处方指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_prescription_index")
public class PrescriptionIndex extends BaseEntity<String> {

    private final static long serialVersionUID = -3148912374971247918L;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FATHERID")
    private PrescriptionIndex father;

    /**
     * 编码
     */
    @Column(name = "CODE", length = 10, unique = true)
    private int code;

    /**
     * 所属处方ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRESCRIPTIONID")
    private Prescription prescription;

    @OneToMany(mappedBy = "prescriptionIndex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PrescriptionUserIndex> prescriptionUserIndices;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PrescriptionIndex getFather() {
        return father;
    }

    public void setFather(PrescriptionIndex father) {
        this.father = father;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Set<PrescriptionUserIndex> getPrescriptionUserIndices() {
        return prescriptionUserIndices;
    }

    public void setPrescriptionUserIndices(Set<PrescriptionUserIndex> prescriptionUserIndices) {
        this.prescriptionUserIndices = prescriptionUserIndices;
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
