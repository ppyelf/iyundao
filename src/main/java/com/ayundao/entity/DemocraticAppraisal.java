package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: DemocraticAppraisal
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/15
 * @Description: 实现 - 民主评议
 * @Version: V1.0
 */
@Entity
@Table(name = "t_democratic_appraisal")
public class DemocraticAppraisal extends BaseEntity<String>{

    private static final long serialVersionUID = -5427073884601011032L;

    /**
     * 名称
     */
    @Excel(name = "名称", sort = 0)
    @Column(name = "NAME",  length = 50)
    private String name;

    /**
     * 人员编号
     */
    @Excel(name = "人员编号",sort = 1)
    @Column(name = "USERCODE", nullable = false, length = 50)
    private String userCode;

    /**
     * 机构编号
     */
    @Excel(name = "机构编号",sort = 2)
    @Column(name = "SUBJECTCODE")
    private String subjectCode;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号", sort = 3)
    @Column(name = "DEPARTCODE")
    private String departCode;

    /**
     * 组织编号
     */
    @Excel(name = "组织编号", sort = 4)
    @Column(name = "GROUPCODE")
    private String groupCode;

    /**
     * 评测结果
     */
    @Excel(name = "评测结果" , sort = 5)
    @Column(name = "RESULT")
    private String result;

    /**
     * 分数
     */
    @Excel(name = "分数" , sort = 6)
    @Column(name = "SCORE")
    private int score;

    /**
     * 评测日期
     */
    @Excel(name = "评测日期", sort = 7)
    @Column(name = "DATA")
    private String data;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
