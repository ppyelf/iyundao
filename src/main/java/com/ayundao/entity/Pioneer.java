package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: Pioneer
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 14:58
 * @Description: 实体 - 先锋人物
 * @Version: V1.0
 */
@Entity
@Table(name = "t_pioneer")
public class Pioneer extends BaseEntity<String> {

    private final static long serialVersionUID = -984132840218094L;

    /**
     * 名称
     */
    @Excel(name = "名称", sort = 1)
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 人员编号
     */
    @Excel(name = "人员编号")
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * 机构编号
     */
    @Excel(name = "机构编号",sort = 2)
    @Column(name = "SUBJECTCODE", nullable = false)
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
     * 效能情况
     */
    @Excel(name = "效能情况", sort = 5)
    @Column(name = "SCORE")
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
