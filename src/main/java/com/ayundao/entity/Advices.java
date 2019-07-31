package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 消息表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_advices")
public class Advices extends BaseEntity<String>{

    private static final long serialVersionUID = 330085325200159165L;

    /**
     *  标题
     */
    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    /**
     * 消息类型
     */
    @Column(name = "TYPE" , length = 50)
    private String type;

    /**
     * 发布人员id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    /**
     * 发布时间
     */
    @Column(name = "ISSUERTIME" , length = 50)
    private String issuerTime;

    /**
     * 发送状态
     */
    @Column(name = "ADVICESSTATUS", length = 50)
    private String advicesStatus;

    /**
     * 任务内容
     */
    @Column(name = "ADVICESTEXT", length = 9999)
    private String advicesText;

    /**
     * 消息人员接收表消息id
     */
    @OneToMany(mappedBy = "advices", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdvicesInfoUser> advicesInfoUser;

    /**
     * 消息接收部门组织消息id
     */
    @OneToMany(mappedBy = "advices", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdvicesInfoDepar> advicesInfoDepar;

    /**
     * 备用字段1
     */
    @Column(name = "INFO1", length = 50)
    private String info1;
    /**
     * 备用字段2
     */
    @Column(name = "INFO2", length = 50)
    private String info2;
    /**
     * 备用字段3
     */
    @Column(name = "INFO3", length = 50)
    private String info3;
    /**
     * 备用字段4
     */
    @Column(name = "INFO4", length = 50)
    private String info4;
    /**
     * 备用字段5
     */
    @Column(name = "INFO5", length = 50)
    private String info5;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIssuerTime() {
        return issuerTime;
    }

    public void setIssuerTime(String issuerTime) {
        this.issuerTime = issuerTime;
    }

    public String getAdvicesStatus() {
        return advicesStatus;
    }

    public void setAdvicesStatus(String advicesStatus) {
        this.advicesStatus = advicesStatus;
    }

    public String getAdvicesText() {
        return advicesText;
    }

    public void setAdvicesText(String advicesText) {
        this.advicesText = advicesText;
    }

    public Set<AdvicesInfoUser> getAdvicesInfoUser() {
        return advicesInfoUser;
    }

    public void setAdvicesInfoUser(Set<AdvicesInfoUser> advicesInfoUser) {
        this.advicesInfoUser = advicesInfoUser;
    }

    public Set<AdvicesInfoDepar> getAdvicesInfoDepar() {
        return advicesInfoDepar;
    }

    public void setAdvicesInfoDepar(Set<AdvicesInfoDepar> advicesInfoDepar) {
        this.advicesInfoDepar = advicesInfoDepar;
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
