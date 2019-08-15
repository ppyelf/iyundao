package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoPersonnel
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -人事信息表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_personnel")
public class UserInfoPersonnel extends BaseEntity<String> {

    private static final long serialVerisionUID = -12154545145454L;

    /**
     * 工龄(年)
     */
    @Column(name = "WORKYEAR",length = 50)
    private String workyear;

    /**
     * 工龄(月)
     */
    @Column(name = "WORKMONTH",length = 50)
    private String workmonth;

    /**
     * 党内职务
     */
    @Column(name = "PARTYPOST",length = 50)
    private String partypost;

    /**
     * 任命时间
     */
    @Column(name = "SERVINGDATE",length = 50)
    private String servingdate;

    /**
     * 其他职务
     */
    @Column(name = "OTHERPOST",length = 50)
    private String otherpost;

    /**
     * 兼评职称
     */
    @Column(name = "JIANPINGPOST",length = 50)
    private String jianpingpost;

    /**
     * 兼评时间
     */
    @Column(name = "JIANPINGDATE",length = 50)
    private String jianpingdate;

    /**
     * 政治面貌
     */
    @Column(name = "POLITICALAPPEARANCE",length = 50)
    private String politicalappearance;

    /**
     * 入党团日期
     */
    @Column(name = "PARTYDATE",length = 50)
    private String partydate;

    /**
     * 所在支部
     */
    @Column(name = "BRANCHNAME",length = 50)
    private String branchname;

    /**
     * 工人工种
     */
    @Column(name = "TYPEWORKER",length = 50)
    private String typeworker;

    /**
     * 工人等级
     */
    @Column(name = "GRADEWORKER",length = 50)
    private String gradeworker;

    /**
     * 聘任时间
     */
    @Column(name = "APPOINTMENTTIME",length = 50)
    private String appointmenttime;

    /**
     * 婚姻状况
     */
    @Column(name = "MARITALSTATUS",length = 50)
    private String maritalstatus;

    /**
     * 户口性质
     */
    @Column(name = "HUKOUNATURE",length = 50)
    private String hukounature;

    /**
     * 户口所在
     */
    @Column(name = "HUKOUWHERE",length = 50)
    private String hukouwhere;

    /**
     * 调入前单位
     */
    @Column(name = "BEFORECOMPANY",length = 50)
    private String beforecompany;

    /**
     * 预定离院日期
     */
    @Column(name = "RESERVELEAVEDATE",length = 50)
    private String reserveleavedate;

    /**
     * 首次合同开始时间
     */
    @Column(name = "FIRSTCONTRACTDATE",length = 50)
    private String firstcontractdate;

    /**
     * 家庭地址
     */
    @Column(name = "FAMILYADDR",length = 50)
    private String familyaddr;

    /**
     * 人员类别
     */
    @Column(name = "PERSONNELTYPE",length = 50)
    private String personneltype;

    /**
     * 返聘到期时间
     */
    @Column(name = "FANPINENDDATE",length = 50)
    private String fanpinenddate;

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

    public String getWorkyear() {
        return workyear;
    }

    public void setWorkyear(String workyear) {
        this.workyear = workyear;
    }

    public String getWorkmonth() {
        return workmonth;
    }

    public void setWorkmonth(String workmonth) {
        this.workmonth = workmonth;
    }

    public String getPartypost() {
        return partypost;
    }

    public void setPartypost(String partypost) {
        this.partypost = partypost;
    }

    public String getServingdate() {
        return servingdate;
    }

    public void setServingdate(String servingdate) {
        this.servingdate = servingdate;
    }

    public String getOtherpost() {
        return otherpost;
    }

    public void setOtherpost(String otherpost) {
        this.otherpost = otherpost;
    }

    public String getJianpingpost() {
        return jianpingpost;
    }

    public void setJianpingpost(String jianpingpost) {
        this.jianpingpost = jianpingpost;
    }

    public String getJianpingdate() {
        return jianpingdate;
    }

    public void setJianpingdate(String jianpingdate) {
        this.jianpingdate = jianpingdate;
    }

    public String getPoliticalappearance() {
        return politicalappearance;
    }

    public void setPoliticalappearance(String politicalappearance) {
        this.politicalappearance = politicalappearance;
    }

    public String getPartydate() {
        return partydate;
    }

    public void setPartydate(String partydate) {
        this.partydate = partydate;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getTypeworker() {
        return typeworker;
    }

    public void setTypeworker(String typeworker) {
        this.typeworker = typeworker;
    }

    public String getGradeworker() {
        return gradeworker;
    }

    public void setGradeworker(String gradeworker) {
        this.gradeworker = gradeworker;
    }

    public String getAppointmenttime() {
        return appointmenttime;
    }

    public void setAppointmenttime(String appointmenttime) {
        this.appointmenttime = appointmenttime;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getHukounature() {
        return hukounature;
    }

    public void setHukounature(String hukounature) {
        this.hukounature = hukounature;
    }

    public String getHukouwhere() {
        return hukouwhere;
    }

    public void setHukouwhere(String hukouwhere) {
        this.hukouwhere = hukouwhere;
    }

    public String getBeforecompany() {
        return beforecompany;
    }

    public void setBeforecompany(String beforecompany) {
        this.beforecompany = beforecompany;
    }

    public String getReserveleavedate() {
        return reserveleavedate;
    }

    public void setReserveleavedate(String reserveleavedate) {
        this.reserveleavedate = reserveleavedate;
    }

    public String getFirstcontractdate() {
        return firstcontractdate;
    }

    public void setFirstcontractdate(String firstcontractdate) {
        this.firstcontractdate = firstcontractdate;
    }

    public String getFamilyaddr() {
        return familyaddr;
    }

    public void setFamilyaddr(String familyaddr) {
        this.familyaddr = familyaddr;
    }

    public String getPersonneltype() {
        return personneltype;
    }

    public void setPersonneltype(String personneltype) {
        this.personneltype = personneltype;
    }

    public String getFanpinenddate() {
        return fanpinenddate;
    }

    public void setFanpinenddate(String fanpinenddate) {
        this.fanpinenddate = fanpinenddate;
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
