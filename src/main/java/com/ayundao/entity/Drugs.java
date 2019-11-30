package com.ayundao.entity;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: Drugs
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/23 10:49
 * @Description: 实体 -  药品统计
 * @Version: V1.0
 */
@Entity
@Table(name = "t_drugs")
public class Drugs extends BaseEntity<String> {

    private static final long serialVersionUID = 197832749217983478L;

    /**
     * 所属用户
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "USERCODE")),
            @AttributeOverride(name = "name", column = @Column(name = "USERNAME"))
    })
    private BaseComponent user;

    /**
     * 所属机构
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "SUBJECTCODE")),
            @AttributeOverride(name = "name", column = @Column(name = "SUBJECTNAME"))
    })
    private BaseComponent subject;

    /**
     * 药品名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 备注
     */
    @Column(name = "REMARK", length = 100)
    private String remark;

    /**
     * 年度
     */
    @Column(name = "YEAR")
    private String year;

    public BaseComponent getUser() {
        return user;
    }

    public void setUser(BaseComponent user) {
        this.user = user;
    }

    public BaseComponent getSubject() {
        return subject;
    }

    public void setSubject(BaseComponent subject) {
        this.subject = subject;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
