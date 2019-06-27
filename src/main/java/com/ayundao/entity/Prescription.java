package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Prescription
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:17
 * @Description: 实体 - 处方
 * @Version: V1.0
 */
@Entity
@Table(name = "t_prescription")
public class Prescription extends BaseEntity<String> {

    private final static long serialVersionUID = -12389218318293090L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 总分
     */
    @Column(name = "TOTAL", columnDefinition = "tinyint default 0", length = 4)
    private int total;

    /**
     * 描述
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 年份
     */
    @Column(name = "YEAR", length = 4)
    private String year;

    /**
     * 处方文件
     */
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PrescriptionFile> prescriptionFiles;

    /**
     * 处方指标
     */
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PrescriptionIndex> prescriptionIndices;

    /**
     * 处方范围
     */
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PrescriptionRange> prescriptionRanges;

    /**
     * 处方用户指标
     */
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Set<PrescriptionFile> getPrescriptionFiles() {
        return prescriptionFiles;
    }

    public void setPrescriptionFiles(Set<PrescriptionFile> prescriptionFiles) {
        this.prescriptionFiles = prescriptionFiles;
    }

    public Set<PrescriptionIndex> getPrescriptionIndices() {
        return prescriptionIndices;
    }

    public void setPrescriptionIndices(Set<PrescriptionIndex> prescriptionIndices) {
        this.prescriptionIndices = prescriptionIndices;
    }

    public Set<PrescriptionRange> getPrescriptionRanges() {
        return prescriptionRanges;
    }

    public void setPrescriptionRanges(Set<PrescriptionRange> prescriptionRanges) {
        this.prescriptionRanges = prescriptionRanges;
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
