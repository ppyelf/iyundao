package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoTitleaPost
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -职务职称表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_titleapost")
public class UserInfoTitleaPost extends BaseEntity<String> {

    private static final long serialVerisionUID = -121545646486L;

    /**
     * 职务名称
     */
    @Column(name = "POSTNAME",length = 50)
    private String postname;

    /**
     * 行政职务
     */
    @Column(name = "ADMINISTRATIONPOST",length = 50)
    private String administrationpost;

    /**
     * 任职年限
     */
    @Column(name = "SERVINGYEARS",length = 50)
    private String servingyears;

    /**
     * 行政级别
     */
    @Column(name = "ADMINISTRATIONLEVEL",length = 50)
    private String administrationlevel;

    /**
     * 任命时间
     */
    @Column(name = "SERVINGDATE",length = 50)
    private String servingdate;

    /**
     * 任命截止时间
     */
    @Column(name = "SERVINGSTOPDATE",length = 50)
    private String servingstopdate;

    /**
     * 前行政职务
     */
    @Column(name = "BEFOREADMINISTRATIONPOST",length = 50)
    private String beforeadministrationpost;

    /**
     * 前行政任命时间
     */
    @Column(name = "BEFORESERVINGDATE",length = 50)
    private String beforeservingdate;

    /**
     * 前任命截止时间
     */
    @Column(name = "BEFORESERVINGSTOPDATE",length = 50)
    private String beforeservingstopdate;

    /**
     * 现资格职称
     */
    @Column(name = "QUALIFICATIONTITLE",length = 50)
    private String qualificationtitle;

    /**
     * 现资格获取时间
     */
    @Column(name = "QUALIFICATIONGETDATE",length = 50)
    private String qualificationgetdate;

    /**
     * 现聘任职称
     */
    @Column(name = "ENGAGETITLE",length = 50)
    private String engagetitle;

    /**
     * 现职称聘任时间
     */
    @Column(name = "ENGAGETITLEDATE",length = 50)
    private String engagetitledate;

    /**
     * 现聘期
     */
    @Column(name = "ENGAGETIME",length = 50)
    private String engagetime;

    /**
     * 前资格名称
     */
    @Column(name = "BEFOREQUALIFICATIONTITLE",length = 50)
    private String beforequalificationtitle;

    /**
     * 前资格获取时间
     */
    @Column(name = "BEFOREQUALIFICATIONGETDATE",length = 50)
    private String beforequalificationgetdate;

    /**
     * 前聘任职称
     */
    @Column(name = "BEFOREENGAGETITLE",length = 50)
    private String beforeengagetitle;

    /**
     * 前职称聘任时间
     */
    @Column(name = "BEFOREENGAGETITLEDATE",length = 50)
    private String beforeengagetitledate;

    /**
     * 前聘期
     */
    @Column(name = "BEFOREENGAGETIME",length = 50)
    private String beforeengagetime;

    /**
      * 前聘期截止日期
     */
    @Column(name = "BEFOREENGAGESTOPTIME",length = 50)
    private String beforeengagestoptime;

    /**
     * 职称技术级别
     */
    @Column(name = "TECHNICALLEVEL",length = 50)
    private String technicallevel;

    /**
     * 职称人员类别
     */
    @Column(name = "PERSONNELCATEGORY",length = 50)
    private String personnelcategory;

    /**
     * 职称卫技类别
     */
    @Column(name = "WEIJICATEGORY",length = 50)
    private String weijicategory;

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

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getAdministrationpost() {
        return administrationpost;
    }

    public void setAdministrationpost(String administrationpost) {
        this.administrationpost = administrationpost;
    }

    public String getServingyears() {
        return servingyears;
    }

    public void setServingyears(String servingyears) {
        this.servingyears = servingyears;
    }

    public String getAdministrationlevel() {
        return administrationlevel;
    }

    public void setAdministrationlevel(String administrationlevel) {
        this.administrationlevel = administrationlevel;
    }

    public String getServingdate() {
        return servingdate;
    }

    public void setServingdate(String servingdate) {
        this.servingdate = servingdate;
    }

    public String getServingstopdate() {
        return servingstopdate;
    }

    public void setServingstopdate(String servingstopdate) {
        this.servingstopdate = servingstopdate;
    }

    public String getBeforeadministrationpost() {
        return beforeadministrationpost;
    }

    public void setBeforeadministrationpost(String beforeadministrationpost) {
        this.beforeadministrationpost = beforeadministrationpost;
    }

    public String getBeforeservingdate() {
        return beforeservingdate;
    }

    public void setBeforeservingdate(String beforeservingdate) {
        this.beforeservingdate = beforeservingdate;
    }

    public String getBeforeservingstopdate() {
        return beforeservingstopdate;
    }

    public void setBeforeservingstopdate(String beforeservingstopdate) {
        this.beforeservingstopdate = beforeservingstopdate;
    }

    public String getQualificationtitle() {
        return qualificationtitle;
    }

    public void setQualificationtitle(String qualificationtitle) {
        this.qualificationtitle = qualificationtitle;
    }

    public String getQualificationgetdate() {
        return qualificationgetdate;
    }

    public void setQualificationgetdate(String qualificationgetdate) {
        this.qualificationgetdate = qualificationgetdate;
    }

    public String getEngagetitle() {
        return engagetitle;
    }

    public void setEngagetitle(String engagetitle) {
        this.engagetitle = engagetitle;
    }

    public String getEngagetitledate() {
        return engagetitledate;
    }

    public void setEngagetitledate(String engagetitledate) {
        this.engagetitledate = engagetitledate;
    }

    public String getEngagetime() {
        return engagetime;
    }

    public void setEngagetime(String engagetime) {
        this.engagetime = engagetime;
    }

    public String getBeforequalificationtitle() {
        return beforequalificationtitle;
    }

    public void setBeforequalificationtitle(String beforequalificationtitle) {
        this.beforequalificationtitle = beforequalificationtitle;
    }

    public String getBeforequalificationgetdate() {
        return beforequalificationgetdate;
    }

    public void setBeforequalificationgetdate(String beforequalificationgetdate) {
        this.beforequalificationgetdate = beforequalificationgetdate;
    }

    public String getBeforeengagetitle() {
        return beforeengagetitle;
    }

    public void setBeforeengagetitle(String beforeengagetitle) {
        this.beforeengagetitle = beforeengagetitle;
    }

    public String getBeforeengagetitledate() {
        return beforeengagetitledate;
    }

    public void setBeforeengagetitledate(String beforeengagetitledate) {
        this.beforeengagetitledate = beforeengagetitledate;
    }

    public String getBeforeengagetime() {
        return beforeengagetime;
    }

    public void setBeforeengagetime(String beforeengagetime) {
        this.beforeengagetime = beforeengagetime;
    }

    public String getBeforeengagestoptime() {
        return beforeengagestoptime;
    }

    public void setBeforeengagestoptime(String beforeengagestoptime) {
        this.beforeengagestoptime = beforeengagestoptime;
    }

    public String getTechnicallevel() {
        return technicallevel;
    }

    public void setTechnicallevel(String technicallevel) {
        this.technicallevel = technicallevel;
    }

    public String getPersonnelcategory() {
        return personnelcategory;
    }

    public void setPersonnelcategory(String personnelcategory) {
        this.personnelcategory = personnelcategory;
    }

    public String getWeijicategory() {
        return weijicategory;
    }

    public void setWeijicategory(String weijicategory) {
        this.weijicategory = weijicategory;
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
