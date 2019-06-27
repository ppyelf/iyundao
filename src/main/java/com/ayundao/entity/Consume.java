package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: Consume
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 15:15
 * @Description: 实体 - 耗材
 * @Version: V1.0
 */
@Entity
@Table(name = "t_consume")
public class Consume extends BaseEntity<String> {

    private static final long serialVersionUID = 13094832104801928L;

    /**
     * 人员编号
     */
    @Excel(name = "人员编号", sort = 0)
    @Column(name = "USERCODE", nullable = false, length = 10)
    private String userCode;

    /**
     * 人员姓名
     */
    @Excel(name = "人员姓名", sort = 1)
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 初次诊断时间
     */
    @Excel(name = "初次诊断时间", sort = 2)
    @Column(name = "time", nullable = false, length = 50)
    private String time;

    /**
     * 使用器材
     */
    @Excel(name = "使用器材", sort = 3)
    @Column(name = "EQUIPMENT", length = 50)
    private String equipment;

    /**
     * 预警
     */
    @Excel(name = "预警", sort = 4)
    @Column(name = "WARN")
    private int warn;

    /**
     * 科室编号
     */
    @Excel(name = "科室编号", sort = 5)
    @Column(name = "GROUPSCODE")
    private String groupCode;

    /**
     * 科室名称
     */
    @Excel(name = "科室名称", sort = 6)
    @Column(name = "GROUPSNAME")
    private String groupName;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getWarn() {
        return warn;
    }

    public void setWarn(int warn) {
        this.warn = warn;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
