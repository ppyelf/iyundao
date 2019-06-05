package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: Sign
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/4 17:09
 * @Description: 实体 - 签到
 * @Version: V1.0
 */
@Entity
@Table(name = "t_sign")
public class Sign extends BaseEntity<String> {

    private final static long serialVersionUID = -83209802184309821L;

    /**
     * 签到时间
     */
    @Column(name = "SIGNTIME", nullable = false)
    private String signTime;

    /**
     * 签到状态
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE")
    private SIGN_TYPE signType;

    /**
     * 所属活动
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVITYID", nullable = false)
    private Activity activity;

    /**
     * 用户ID
     */
    @Column(name = "USERID", nullable = false)
    private String userId;

    /**
     * 位置X
     */
    @Column(name = "AXISX", nullable = false)
    private String axisx;

    /**
     * 位置Y
     */
    @Column(name = "AXISY", nullable = false)
    private String asisy;

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

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public SIGN_TYPE getSignType() {
        return signType;
    }

    public void setSignType(SIGN_TYPE signType) {
        this.signType = signType;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAxisx() {
        return axisx;
    }

    public void setAxisx(String axisx) {
        this.axisx = axisx;
    }

    public String getAsisy() {
        return asisy;
    }

    public void setAsisy(String asisy) {
        this.asisy = asisy;
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

    public enum SIGN_TYPE{
        /**
         * 正常
         */
        normal,

        /**
         * 超出范围
         */
        out,

        /**
         * 异常
         */
        abnormal
    }
}
