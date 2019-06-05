package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: ReleaseDepart
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 9:21
 * @Description: 实体 - 发布部门
 * @Version: V1.0
 */
@Entity
@Table(name = "t_release_depart")
public class ReleaseDepart extends BaseEntity<String> {

    private final static long serialVersionUID = -82109384098320480L;

    /**
     * 机构ID
     */
    @Column(name = "DEPARTID", nullable = false)
    private String departId;

    /**
     * 活动ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVITYID", nullable = false)
    private Activity activity;

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

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
