package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Medical
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 10:18
 * @Description: 实体 - 医德
 * @Version: V1.0
 */
@Entity
@Table(name ="t_medical")
public class Medical extends BaseEntity<String> {

    private final static long serialVersionUID = -12381092830218039L;

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
     * 医德文件
     */
    @OneToMany(mappedBy = "medical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalFile> medicalFiles;

    /**
     * 医德指标
     */
    @OneToMany(mappedBy = "medical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalIndex> medicalIndices;

    /**
     * 医德范围
     */
    @OneToMany(mappedBy = "medical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalRange> medicalRanges;

    /**
     * 医德用户指标
     */
    @OneToMany(mappedBy = "medical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalUserIndex> medicalUserIndices;

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

    public Set<MedicalFile> getMedicalFiles() {
        return medicalFiles;
    }

    public void setMedicalFiles(Set<MedicalFile> medicalFiles) {
        this.medicalFiles = medicalFiles;
    }

    public Set<MedicalIndex> getMedicalIndices() {
        return medicalIndices;
    }

    public void setMedicalIndices(Set<MedicalIndex> medicalIndices) {
        this.medicalIndices = medicalIndices;
    }

    public Set<MedicalRange> getMedicalRanges() {
        return medicalRanges;
    }

    public void setMedicalRanges(Set<MedicalRange> medicalRanges) {
        this.medicalRanges = medicalRanges;
    }

    public Set<MedicalUserIndex> getMedicalUserIndices() {
        return medicalUserIndices;
    }

    public void setMedicalUserIndices(Set<MedicalUserIndex> medicalUserIndices) {
        this.medicalUserIndices = medicalUserIndices;
    }
}
