package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

/**
 * @ClassName: PioneerIndex
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/13
 * @Description: 实现 -
 * @Version: V1.0
 */
@Entity
@Table(name = "t_pioneer_index")
public class PioneerIndex extends BaseEntity<String>{

    private static final long serialVersionUID = 8246160473272210424L;

    /**
     * 名称
     */
    @Excel(name = "名称", sort = 0)
    @Column(name = "NAME",  length = 50)
    private String name;

    /**
     * 用户编号
     */
    @Excel(name = "用户编号" , sort = 1)
    @Column(name = "USERCODE", nullable = false)
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
     * 分数
     */
    @Excel(name = "分数" , sort = 5)
    @Column(name = "SCORE")
    private int score;

    /**
     * 日期
     */
    @Excel(name = "日期", sort = 6)
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
