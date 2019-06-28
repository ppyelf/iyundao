package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoContract
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -合同信息表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_contract")
public class UserInfoContract extends BaseEntity<String> {

    private static final long serialVerisionUID = -1515424565646L;

    /**
     * 档案管理单位
     */
    @Column(name = "ARCHIVESMANAGEMENTUNIT",length = 50)
    private String archivesmanagementunit;

    /**
     * 合同类型
     */
    @Column(name = "CONTRACTTYPE",length = 50)
    private String contracttype;

    /**
     * 合同编号
     */
    @Column(name = "CONTRACTNUMBER",length = 50)
    private String contractnumber;

    /**
     * 合同开始时间
     */
    @Column(name = "CONTRACTSTARTTIME",length = 50)
    private String contractstarttime;

    /**
     * 合同结束时间
     */
    @Column(name = "CONTRACTENDTIME",length = 50)
    private String contractendtime;

    /**
     * 签订类型
     */
    @Column(name = "SIGNATURETYPE",length = 50)
    private String signaturetype;

    /**
     * 合同期限
     */
    @Column(name = "CONTRACTPERIOD",length = 50)
    private String contractperiod;

    /**
     * 合同签定次数
     */
    @Column(name = "FREQUENCY",length = 50)
    private String frequency;

    /**
     * 用户详情ID
     */
    @Column(name = "USERINFOID",nullable = false,length = 50)
    private String userinfoid;

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

    public static long getSerialVerisionUID() {
        return serialVerisionUID;
    }

    public String getArchivesmanagementunit() {
        return archivesmanagementunit;
    }

    public void setArchivesmanagementunit(String archivesmanagementunit) {
        this.archivesmanagementunit = archivesmanagementunit;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
    }

    public String getContractstarttime() {
        return contractstarttime;
    }

    public String getContractnumber() {
        return contractnumber;
    }

    public void setContractnumber(String contractnumber) {
        this.contractnumber = contractnumber;
    }

    public void setContractstarttime(String contractstarttime) {
        this.contractstarttime = contractstarttime;
    }

    public String getContractendtime() {
        return contractendtime;
    }

    public void setContractendtime(String contractendtime) {
        this.contractendtime = contractendtime;
    }

    public String getSignaturetype() {
        return signaturetype;
    }

    public void setSignaturetype(String signaturetype) {
        this.signaturetype = signaturetype;
    }

    public String getContractperiod() {
        return contractperiod;
    }

    public void setContractperiod(String contractperiod) {
        this.contractperiod = contractperiod;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUserinfoid() {
        return userinfoid;
    }

    public void setUserinfoid(String userinfoid) {
        this.userinfoid = userinfoid;
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
