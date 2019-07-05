package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: AssessmentIndex
 * @project: ayundao
 * @author: king
 * @Date: 2019/7/4 10:18
 * @Description: 实体 - 考核指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_assessment_index")
public class AssessmentIndex extends BaseEntity<String> {

    private final static long serialVersionUID = -1083240120981024309L;

//    /**
//     * 名称
//     */
//    @Column(name = "NAME", nullable = false, length = 50)
//    private String name;
//
//    /**
//     * 描述
//     */
//    @Column(name = "REMARK", length = 500)
//    private String remark;
//
//    /**
//     * 父级指标
//     */
//    @Column(name = "FATHERID",length = 50)
//    private AssessmentIndex father;
//
//    /**
//     * 编码
//     */
//    @Column(name = "CODE", length = 10, unique = true)
//    private int code;

    /**
     * 节点ID
     */
    @Column(name = "SORTEDID",length = 50,unique = true)
    private String sortedId;

    /**
     * 编码
     */
    @Column(name = "SORTEDCODE",length = 50)
    private String sortedCode;

    /**
     * 名称全称
     */
    @Column(name = "LNAME",length = 50)
    private String lname;

    /**
     * 名称缩写
     */
    @Column(name = "SNAME",length = 50)
    private String sname;

    /**
     * 父节点
     */
    @Column(name = "PARCODE",length = 50)
    private String parcode;

    /**
     * 排序
     */
    @Column(name = "NORDER",length = 50)
    private String norder;

    /**
     * 是否使用
     */
    @Column(name = "ISUSE",length = 50)
    private String isuse;

    /**
     * 所属考核ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSESSMENTID")
    private Assessment assessment;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSortedId() {
        return sortedId;
    }

    public void setSortedId(String sortedId) {
        this.sortedId = sortedId;
    }

    public String getSortedCode() {
        return sortedCode;
    }

    public void setSortedCode(String sortedCode) {
        this.sortedCode = sortedCode;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getParcode() {
        return parcode;
    }

    public void setParcode(String parcode) {
        this.parcode = parcode;
    }

    public String getNorder() {
        return norder;
    }

    public void setNorder(String norder) {
        this.norder = norder;
    }

    public String getIsuse() {
        return isuse;
    }

    public void setIsuse(String isuse) {
        this.isuse = isuse;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
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
