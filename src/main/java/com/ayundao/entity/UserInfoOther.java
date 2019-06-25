package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoOther
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -其他附属表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_other")

public class UserInfoOther extends BaseEntity<String> {

    private static final long serialVerisionUID = -1245451245452L;

    /**
     * 是否允许登录
     */
    @Column(name = "WHETHERSIGN",length = 50)
    private String whethersign;

    /**
     * 是否需要考勤
     */
    @Column(name = "WHETHERCHECK",length = 50)
    private String whethercheck;

    /**
     * 在职状态
     */
    @Column(name = "STATE",length = 50)
    private String state;

    /**
     * 离院时间
     */
    @Column(name = "LEAVEDATE",length = 50)
    private String leavedate;

    /**
     * 注销原因
     */
    @Column(name = "CANCELLATION",length = 50)
    private String cancellation;

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

    public String getWhethersign() {
        return whethersign;
    }

    public void setWhethersign(String whethersign) {
        this.whethersign = whethersign;
    }

    public String getWhethercheck() {
        return whethercheck;
    }

    public void setWhethercheck(String whethercheck) {
        this.whethercheck = whethercheck;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(String leavedate) {
        this.leavedate = leavedate;
    }

    public String getCancellation() {
        return cancellation;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
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
