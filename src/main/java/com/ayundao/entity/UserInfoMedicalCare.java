package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoMedicalCare
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -医务护理表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_medicalCare")
public class UserInfoMedicalCare extends BaseEntity<String> {

    private static final long serialVerisionUID = -12154545145454L;

    /**
     * 执业资格等级
     */
    @Column(name = "PRACTICELEVEL",length = 50)
    private String parcticelevel;

    /**
     * 执业证号
     */
    @Column(name = "PRACTICENUMBER",length = 50)
    private String practicenumber;

    /**
     * 执业证书取得时间
     */
    @Column(name = "PRACTICEBOOKOBTAINDATE",length = 50)
    private String parcticebookobtaindate;

    /**
     * 技术资格证号
     */
    @Column(name = "TECHNOLOGYNUMBER",length = 50)
    private String technologynumber;

    /**
     * 技术资格证书取得时间
     */
    @Column(name = "TECHNOLOGYBOOKOBTAINDATE",length = 50)
    private String technologybookobtaindate;

    /**
     * 护士鞋号
     */
    @Column(name = "NURSESHOESIZE",length = 50)
    private String nurseshoesize;

    /**
     * 执业范围
     */
    @Column(name = "PRACTICERANGE",length = 50)
    private String practicerange;

    /**
     * 注册时间
     */
    @Column(name = "REGISTRATIONTIME",length = 50)
    private String registrationtime;

    /**
     * 执业年限
     */
    @Column(name = "PRACTICEYEARS",length = 50)
    private String practiceyears;

    /**
     * 执业类别
     */
    @Column(name = "PRACTICECATEGORY",length = 50)
    private String practicecategory;

    /**
     * 中断执业
     */
    @Column(name = "INTERRUPTPRACTICE",length = 50)
    private String interruptpractice;

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

    public String getParcticelevel() {
        return parcticelevel;
    }

    public void setParcticelevel(String parcticelevel) {
        this.parcticelevel = parcticelevel;
    }

    public String getPracticenumber() {
        return practicenumber;
    }

    public void setPracticenumber(String practicenumber) {
        this.practicenumber = practicenumber;
    }

    public String getParcticebookobtaindate() {
        return parcticebookobtaindate;
    }

    public void setParcticebookobtaindate(String parcticebookobtaindate) {
        this.parcticebookobtaindate = parcticebookobtaindate;
    }

    public String getTechnologynumber() {
        return technologynumber;
    }

    public void setTechnologynumber(String technologynumber) {
        this.technologynumber = technologynumber;
    }

    public String getTechnologybookobtaindate() {
        return technologybookobtaindate;
    }

    public void setTechnologybookobtaindate(String technologybookobtaindate) {
        this.technologybookobtaindate = technologybookobtaindate;
    }

    public String getNurseshoesize() {
        return nurseshoesize;
    }

    public void setNurseshoesize(String nurseshoesize) {
        this.nurseshoesize = nurseshoesize;
    }

    public String getPracticerange() {
        return practicerange;
    }

    public void setPracticerange(String practicerange) {
        this.practicerange = practicerange;
    }

    public String getRegistrationtime() {
        return registrationtime;
    }

    public void setRegistrationtime(String registrationtime) {
        this.registrationtime = registrationtime;
    }

    public String getPracticeyears() {
        return practiceyears;
    }

    public void setPracticeyears(String practiceyears) {
        this.practiceyears = practiceyears;
    }

    public String getPracticecategory() {
        return practicecategory;
    }

    public void setPracticecategory(String practicecategory) {
        this.practicecategory = practicecategory;
    }

    public String getInterruptpractice() {
        return interruptpractice;
    }

    public void setInterruptpractice(String interruptpractice) {
        this.interruptpractice = interruptpractice;
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
