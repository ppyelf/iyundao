package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoBasic
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -基础信息表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_basic")

public class UserInfoBasic extends BaseEntity<String> {

    private static final long serialVersionUID = -125421545122L;

    /**
     * 计算连续工龄时间
     */
    @Column(name = "WORKERDATE",length = 50)
    private String workerdate;

    /**
     * 合同制转正时间
     */
    @Column(name = "ZHUANZHENGDATE",length = 50)
    private String zhuanzhengdate;

    /**
     * 工资关系转移时间
     */
    @Column(name = "WAGESDATE",length = 50)
    private String wagesdate;

    /**
     * 到院时间
     */
    @Column(name = "ARRIVEDATE",length = 50)
    private String arrivedate;

    /**
     * 职工性质
     */
    @Column(name = "WORKERSNATURE",length = 50)
    private String workersnature;

    /**
     * 职工类别
     */
    @Column(name = "WORKERSCATEGORY",length = 50)
    private String workerscategory;

    /**
     * 岗位类别
     */
    @Column(name = "POSTCATEGORY",length = 50)
    private String postcategory;

    /**
     * 考勤组
     */
    @Column(name = "CHECKGROUP",length = 50)
    private String checkgroup;

    /**
     * 编制
     */
    @Column(name = "ORGANIZATION",length = 50)
    private String organization;

    /**
     * 增加方式
     */
    @Column(name = "INCREASEMODE",length = 50)
    private String increasemode;

    /**
     * 其他工号
     */
    @Column(name = "OTHERNUMBER",length = 50)
    private String othernumber;

    /**
     * 年龄
     */
    @Column(name = "AGE",length = 50)
    private String age;

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


    public void setWorkerdate(String workerdate) {
        this.workerdate = workerdate;
    }

    public String getZhuanzhengdate() {
        return zhuanzhengdate;
    }

    public void setZhuanzhengdate(String zhuanzhengdate) {
        this.zhuanzhengdate = zhuanzhengdate;
    }

    public String getWagesdate() {
        return wagesdate;
    }

    public void setWagesdate(String wagesdate) {
        this.wagesdate = wagesdate;
    }

    public String getArrivedate() {
        return arrivedate;
    }

    public void setArrivedate(String arrivedate) {
        this.arrivedate = arrivedate;
    }

    public String getWorkersnature() {
        return workersnature;
    }

    public void setWorkersnature(String workersnature) {
        this.workersnature = workersnature;
    }

    public String getWorkerscategory() {
        return workerscategory;
    }

    public void setWorkerscategory(String workerscategory) {
        this.workerscategory = workerscategory;
    }

    public String getPostcategory() {
        return postcategory;
    }

    public void setPostcategory(String postcategory) {
        this.postcategory = postcategory;
    }

    public String getCheckgroup() {
        return checkgroup;
    }

    public void setCheckgroup(String checkgroup) {
        this.checkgroup = checkgroup;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getIncreasemode() {
        return increasemode;
    }

    public void setIncreasemode(String increasemode) {
        this.increasemode = increasemode;
    }

    public String getOthernumber() {
        return othernumber;
    }

    public void setOthernumber(String othernumber) {
        this.othernumber = othernumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
