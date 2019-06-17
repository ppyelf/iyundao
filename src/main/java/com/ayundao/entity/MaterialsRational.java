package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @Author: ink-feather
 * @Description: 耗材点评
 * @Date: 2019/6/15 10:35
 */
@Entity
@Table(name = "t_materialsrational_rational")
public class MaterialsRational extends BaseEntity<String> {

    private static final long serialVersionUID = -1171984710974098503L;

    /**
     *  耗材id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATERIALSID",nullable = false)
    private MaterialsWarning materials;

    /**
     * 是否合理
     */
    @Column(name = "RATIONAL")
    private String rational;

    /**
     * 点评内容
     */
    @Column(name = "REMARK",length = 500)
    private String remark;

    /**
     * 点评人id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REMARK_ID")
    private User remarkId;

    /**
     * 点评人姓名
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REMARK_NAME")
    private User remarkName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MaterialsWarning getMaterials() {
        return materials;
    }

    public void setMaterials(MaterialsWarning materials) {
        this.materials = materials;
    }

    public String getRational() {
        return rational;
    }

    public void setRational(String rational) {
        this.rational = rational;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(User remarkId) {
        this.remarkId = remarkId;
    }

    public User getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(User remarkName) {
        this.remarkName = remarkName;
    }
}
