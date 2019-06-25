package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoWork
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -工作经历表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_work")
public class UserInfoWork extends BaseEntity<String> {

    private static final long serialVerisionUID = -1244545346545L;

    /**
     * 开始时间
     */
    @Column(name = "STARTDATE",length = 50)
    private String startdate;

    /**
     * 结束时间
     */
    @Column(name = "ENDDATE",length = 50)
    private String enddate;

    /**
     * 工作单位
     */
    @Column(name = "WORKUNIT",length = 50)
    private String workunit;

    /**
     * 担任职务
     */
    @Column(name = "TOSERVEPOST",length = 50)
    private String toservepost;

    /**
     * 岗位职称
     */
    @Column(name = "POSTTITLE",length = 50)
    private String posttitle;

    /**
     * 证明人
     */
    @Column(name = "WITNESS",length = 50)
    private String witness;

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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getWorkunit() {
        return workunit;
    }

    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }

    public String getToservepost() {
        return toservepost;
    }

    public void setToservepost(String toservepost) {
        this.toservepost = toservepost;
    }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
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
