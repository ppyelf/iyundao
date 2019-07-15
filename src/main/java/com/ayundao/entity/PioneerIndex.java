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
     * 用户ID
     */
    @Excel(name = "用户ID" , sort = 1)
    @Column(name = "USERID", nullable = false)
    private String userId;

    /**
     * 机构ID
     */
    @Excel(name = "机构ID" , sort = 2)
    @Column(name = "SUBJECTID")
    private String subjectId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID" , sort = 3)
    @Column(name = "DEPARTID")
    private String departId;

    /**
     * 组织ID
     */
    @Excel(name = "组织ID" , sort = 4)
    @Column(name = "GROUPID")
    private String groupId;

    /**
     * 分数
     */
    @Excel(name = "分数" , sort = 5)
    @Column(name = "SCORE")
    private int score;

    /**
     * 日期
     */
    @Excel(name = "初次诊断时间", sort = 6)
    @Column(name = "DATA")
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
